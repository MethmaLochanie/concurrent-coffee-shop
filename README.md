# Concurrent Coffee Shop Simulation

## Overview
This project simulates a concurrent coffee shop scenario using Java threads and synchronization mechanisms. Multiple customers place orders while multiple baristas prepare them, all coordinated through a shared order queue with a fixed capacity. The simulation demonstrates thread communication, synchronization, and resource sharing in a real-world inspired context.

## Features
- Simulates a coffee shop with multiple customers and baristas
- Uses Java threads to represent customers and baristas
- Thread-safe order queue with a configurable maximum capacity
- Synchronization using `ReentrantLock` and `Condition` variables
- Graceful shop closing and thread shutdown
- Console output for all major actions (placing orders, preparing orders, waiting, etc.)

## Project Structure

```
src/
└── com/
    └── company/
        ├── Main.java              # Main application entry point
        ├── Barista.java       # Barista implementation
        └── CoffeeShop.java # Coffee shop implementation
        └── Customer.java # Customer implementation
        └── Shop.java # Shop interface implementation
```

## Architecture
- **Main.java**: Entry point. Initializes the coffee shop, creates and starts customer and barista threads, and manages the shop's open/close lifecycle.
- **CoffeeShop.java**: Implements the `Shop` interface. Manages the order queue, synchronization, and shop state.
- **Customer.java**: Runnable representing a customer. Each customer places up to 3 orders, waiting if the queue is full.
- **Barista.java**: Runnable representing a barista. Each barista prepares orders from the queue, waiting if the queue is empty.
- **Shop.java**: Interface defining `placeOrder` and `prepareOrder` methods.

## Concurrency Details
- The order queue is protected by a `ReentrantLock`.
- Two `Condition` variables (`queueFull`, `queueEmpty`) manage waiting and notification for full/empty queue states.
- Customers wait if the queue is full; baristas wait if the queue is empty.
- The shop can be closed, after which no new orders are accepted and baristas finish remaining orders.

## Requirements
- Java 8 or higher
- No external dependencies

## How to Build and Run
1. **Compile the source code:**
   ```sh
   javac -d out/production/Scenario_1 src/com/company/*.java
   ```
2. **Run the simulation:**
   ```sh
   java -cp out/production/Scenario_1 com.company.Main
   ```

## Usage Example
Sample console output:
```
[CUSTOMER] Customer #1's Order #1 placed successfully.
[BARISTA] Barista #1 starts preparing: Customer #1's Order #1
[BARISTA] Barista #1 has finished preparing: Customer #1's Order #1
[SHOP] Queue is FULL. Customer #2's Order #1 must wait...
[BARISTA] Queue is EMPTY. Waiting for orders...
[SHOP] ********************** Coffee shop is now CLOSED for new orders **********************
[BARISTA] Barista #2 has no more orders to process and is SHUTTING DOWN.
[SHOP] All orders have been processed. Coffee shop is now closed!
```

## Author & Institution
Created as part of a concurrent programming coursework.
- **Author:** Rathnayaka
- **Institution:** Informatics Institute of Technology (IIT) affiliated by University of Westminster (UoW)
- **Scenario:** 1 (Concurrent Coffee Shop)
