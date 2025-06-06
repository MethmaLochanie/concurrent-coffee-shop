package com.company;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CoffeeShop implements Shop {

    private final Queue<String> orderQueue;
    private final int CAPACITY;
    private final Lock lock = new ReentrantLock();
    private final Condition queueFull = lock.newCondition();
    private final Condition queueEmpty = lock.newCondition();
    // flag to indicate if the shop is open
    private volatile boolean open = true;

    public CoffeeShop(int CAPACITY) {
        this.orderQueue = new LinkedList<>();
        if (CAPACITY <= 0) {
            // Validate CAPACITY should be greater than 0
            throw new IllegalArgumentException("Capacity must be greater than 0.");
        } else {
            this.CAPACITY = CAPACITY;
        }
    }

    public void closeShop() {
        lock.lock();
        try {
            // mark the shop as closed
            open = false;
            System.out.println("\n[SHOP] ********************** Coffee shop is now CLOSED for new orders **********************\n");
            // notify baristas that no more orders will be placed
            queueEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void placeOrder(String order) {
        lock.lock();
        try {
            // Check if the shop is still open
            while (orderQueue.size() == CAPACITY || !open) {
                if (!open) {
                    // If the shop is closed, do not allow new orders
                    System.out.println("[SHOP] The shop is CLOSED. " + order + " cannot be placed.");
                    return;
                }
                System.out.println("[SHOP] Queue is FULL. " + order + " must wait...");
                // Wait until space is available or the shop closes
                queueFull.await();
            }
            // If the shop is still open and there's space in the queue, add the order
            orderQueue.offer(order);
            System.out.println("[CUSTOMER] " + order + " placed successfully.");
            // Notify baristas that an order is available
            queueEmpty.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String prepareOrder() {
        lock.lock();
        try {
            // wait if the queue is empty and the shop is still open
            while (orderQueue.isEmpty()) {
                if (!open) {
                    // if the shop is closed and no orders are left, exit
                    return null;
                }
                System.out.println("[BARISTA] Queue is EMPTY. Waiting for orders...");
                queueEmpty.await();
            }
            // remove an order from the queue
            String order = orderQueue.poll();
            System.out.println("[BARISTA] " + order + " is now being prepared.");
            // notify customers that space is available
            queueFull.signalAll();
            return order;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            lock.unlock();
        }
    }
}
