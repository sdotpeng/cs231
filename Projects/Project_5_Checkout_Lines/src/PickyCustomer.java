import java.util.ArrayList;
import java.util.Vector;

/**
 * Project 5: Checkout lines - CS231, Colby College
 *
 * A class to represent Picky Customer, extended from abstract class Customer
 *
 * @file PickyRandom.java
 * @author Ricky Peng
 * @date 2020-10-05
 */

public class PickyCustomer extends Customer {

    /**
     * Constructor to create PickyCustomer
     * @param numItems number of items the customer has
     * @param numLines initial time steps
     */
    public PickyCustomer(int numItems, int numLines) {
        super(numItems, numLines);
    }

    /**
     * Find the index of the CheckoutAgent with the shortest line
     * @param checkouts ArrayList of CheckOutAgent
     * @return index of the CheckoutAgent with the shortest line
     */
    @Override
    public int chooseLine(ArrayList<CheckoutAgent> checkouts) {
        int shortest = checkouts.get(0).getNumInQueue();
        int index = 0;
        int current;
        for (int i = 0; i < checkouts.size(); i++) {
            current = checkouts.get(i).getNumInQueue();
            if (current < shortest) {
                shortest = current;
                index = i;
            }
        }
        return index;
    }
}
