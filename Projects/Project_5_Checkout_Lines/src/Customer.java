import java.util.ArrayList;

/**
 * Project 5: Checkout lines - CS231, Colby College
 *
 * This abstract class represents the Customer class and needs to be the parent class for all three types of customers.
 *
 * @file Customer.java
 * @author Ricky Peng
 * @date 2020-10-05
 */

public abstract class Customer {

    private int numItems;
    private int timeSteps;

    /**
     * Default constructor
     * @param numItems number of items an customer has
     */
    public Customer(int numItems) {
        this(numItems, 0);
    }

    /**
     * Constructor
     * @param numItems number of items an customer has
     * @param timeSteps time steps taken for the customer to finish paying
     */
    public Customer(int numItems, int timeSteps) {
        this.numItems = numItems;
        this.timeSteps = timeSteps;
    }

    /**
     * Increments the number of time steps
     */
    public void incrementTime() {
        this.timeSteps++;
    }

    /**
     * @return the number of time steps
     */
    public int getTime() {
        return timeSteps;
    }

    /**
     * Decrements the number of items
     */
    public void giveUpItem() {
        this.numItems--;
    }

    /**
     * @return the number of items
     */
    public int getNumItems() {
        return numItems;
    }

    /**
     * Abstract method for picking the line, needs to be overridden according to different type of agent
     * @param checkouts ArrayList of CheckOutAgent
     * @return number of line to go to
     */
    public abstract int chooseLine(ArrayList<CheckoutAgent> checkouts);

}
