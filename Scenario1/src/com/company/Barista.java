package com.company;

public class Barista implements Runnable {

    private final String baristaName;
    private final CoffeeShop coffeeshop;

    public Barista(String baristaName, CoffeeShop coffeeshop) {
        this.baristaName = baristaName;
        this.coffeeshop = coffeeshop;
    }

    @Override
    public void run() {
        try {
                // keep taking orders in a loop
                while (true)
                {
                    // retrieve an order from the coffee shop. If no orders are available, it waits.
                    String order = coffeeshop.prepareOrder();
                    if (order == null) {
                        System.out.println("[BARISTA] " + baristaName + " has no more orders to process and is SHUTTING DOWN.");
                        // exit the loop if there are no more orders
                        break;
                    }
                    // barista is starting to prepare the order.
                    System.out.println("[BARISTA] " + baristaName + " starts preparing: " + order);
                    // simulate the time taken to prepare the coffee (2 seconds).
                    Thread.sleep(2000);
                    // order is finished.
                    System.out.println("[BARISTA] " + baristaName + " has finished preparing: " + order);
                }
            } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}
