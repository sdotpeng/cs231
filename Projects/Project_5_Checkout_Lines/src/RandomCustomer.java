import java.util.ArrayList;
import java.util.Random;

/**
 * Project 5: Checkout lines - CS231, Colby College
 *
 * A class to represent the Random Customer, extended from abstract class Customer
 *
 * @file RandomCustomer.java
 * @author Ricky Peng
 * @date 2020-10-05
 */

public class RandomCustomer extends Customer {

    /**
     * Child constructor for the class of RandomCustomer
     * @param numItems number of items one customer has
     */
    public RandomCustomer(int numItems) {
        super(numItems, 1);
    }

    /**
     * Choose a line randomly for this customer
     * @param checkouts ArrayList of CheckOutAgent
     * @return a random number from 0 to size of the arrayList
     */
    @Override
    public int chooseLine(ArrayList<CheckoutAgent> checkouts) {
        Random random = new Random();
        return random.nextInt(checkouts.size());
    }
}
