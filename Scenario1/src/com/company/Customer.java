package com.company;

public class Customer implements Runnable {

    private final String customerName;
    private final CoffeeShop coffeeshop;

    public Customer(String customerName, CoffeeShop coffeeshop) {
        this.customerName = customerName;
        this.coffeeshop = coffeeshop;
    }

    @Override
    public void run() {
        try {
            int MAX_ORDERS = 3;
            for (int i = 1; i <= MAX_ORDERS; i++) {
                // create a unique order string for each order
                String order = customerName + "'s Order #" + i;
                // place the order in the coffee shop. If the queue is full, it waits.
                coffeeshop.placeOrder(order);
                // simulate some delay between placing orders
                Thread.sleep((int) (Math.random() * 1000));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            // restore the interrupted status of the thread.
            Thread.currentThread().interrupt();
        }
    }
}
