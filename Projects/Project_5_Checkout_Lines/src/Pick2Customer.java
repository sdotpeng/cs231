import java.util.ArrayList;
import java.util.Random;

/**
 * Project 5: Checkout lines - CS231, Colby College
 *
 * A class to represent the Checkout agent
 *
 * @file Pick2Customer.java
 * @author Ricky Peng
 * @date 2020-10-19
 */

public class Pick2Customer extends Customer {

    /**
     * Constructor for Pick2 Customer
     * @param numItems number of items the custoomer has
     */
    public Pick2Customer(int numItems) {
        super(numItems);
    }

    /**
     * Return the index of the shorter of two randomly chosen queues
     * @param checkouts ArrayList of CheckOutAgent
     * @return the index of the shorter of two randomly chosen queues
     */
    @Override
    public int chooseLine(ArrayList<CheckoutAgent> checkouts) {
        Random random = new Random();
        int first = random.nextInt(checkouts.size());
        int second = random.nextInt(checkouts.size());
        while (first == second) {
            second = random.nextInt(checkouts.size());
        }
        return checkouts.get(first).getNumInQueue() < checkouts.get(second).getNumInQueue() ? first : second;
    }
}
