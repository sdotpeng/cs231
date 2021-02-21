import java.util.Random;
import java.util.ArrayList;

/**
 * Project 5: Checkout lines - CS231, Colby College
 *
 * A class to represent the Checkout agent
 *
 * @file RandomCustomerSimulation.java
 * @author Ricky Peng
 * @date 2020-10-05
 */

public class RandomCustomerSimulation {
    // Creates a new LandscapeDisplay and populates it with 5 checkouts and 1000 customers with max 10 items.
    public static void main(String[] args) throws InterruptedException {

        Random gen = new Random();
        ArrayList<CheckoutAgent> checkouts = new ArrayList<>(5);

        for (int i = 0; i < 5; i++) {
            CheckoutAgent checkout = new CheckoutAgent(i * 100 + 50, 580);
            checkouts.add(checkout);
        }
        Landscape scape = new Landscape(600, 600, checkouts);
        LandscapeDisplay display = new LandscapeDisplay(scape);

        for (int i = 0; i < 1000; i++) {
            Customer cust = new RandomCustomer(1 + gen.nextInt(7));
            int choice = cust.chooseLine(checkouts);
            checkouts.get(choice).addCustomerToQueue(cust);
            scape.updateCheckouts();
            display.repaint();
            Thread.sleep(5);

            if (i % 100 == 0) {
                scape.printFinishedCustomerStatistics();
            }
        }

    }

}