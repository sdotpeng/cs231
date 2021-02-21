/*
    Stephanie Taylor (Spring 2019)
    TestPick2Customer.java
    Purpose: to test the line-choosing strategy of the Pick2Customer by visualizing
    the lines as customers join them. No customers leave.
*/

import java.util.Random;
import java.util.ArrayList;

/**
 * Project 5: Checkout lines - CS231, Colby College
 *
 * A class to represent the Checkout agent
 *
 * @file TestPick2Customer.java
 * @author Ricky Peng
 * @date 2020-10-05
 */

public class TestPick2Customer {
    // test function that creates a new LandscapeDisplay and populates it with 5 checkouts and 99 customers.
    public static void main(String[] args) throws InterruptedException {
        Random gen = new Random();
        ArrayList<CheckoutAgent> checkouts = new ArrayList<CheckoutAgent>(5);

        for (int i = 0; i < 5; i++) {
            CheckoutAgent checkout = new CheckoutAgent(i * 100 + 50, 780);
            checkouts.add(checkout);
        }
        Landscape scape = new Landscape(800, 900, checkouts);
        LandscapeDisplay display = new LandscapeDisplay(scape);

        for (int j = 0; j < 99; j++) {
            Customer cust = new Pick2Customer(1 + gen.nextInt(10));
            int choice = cust.chooseLine(checkouts);
            checkouts.get(choice).addCustomerToQueue(cust);
            display.repaint();
            Thread.sleep(250);
        }

    }

}