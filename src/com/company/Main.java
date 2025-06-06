package com.company;

public class Main {
    public static void main(String[] args) {
        // maximum capacity of the order queue
        final int MAX_SIZE = 5;
        CoffeeShop coffeeShop = new CoffeeShop(MAX_SIZE);

        // 10 customers + 10 baristas
        Thread[] customersBaristas = new Thread[20];

        // initialize 10 customers
        for (int i = 0; i < 10; i++) {
            customersBaristas[i] = new Thread(new Customer("Customer #" + (i + 1), coffeeShop));
        }

        // initialize 10 baristas
        for (int i = 10; i < 20; i++) {
            customersBaristas[i] = new Thread(new Barista("Barista #" + (i - 9), coffeeShop));
        }

        // start all threads
        for (Thread thread : customersBaristas) {
            thread.start();
        }

        // allow customers to place orders for a fixed duration
        try {
            // Random delay between 0 and 6 seconds
            Thread.sleep((int) (Math.random() * 6000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // close the coffee shop after the order duration
        coffeeShop.closeShop();

        // wait for all threads to finish
        try {
            for (Thread thread : customersBaristas) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("[SHOP] All orders have been processed. Coffee shop is now closed!");
    }
}
