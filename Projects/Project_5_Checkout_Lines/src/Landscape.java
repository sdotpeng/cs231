import java.awt.*;
import java.util.ArrayList;

/**
 * Project 5: Checkout lines - CS231, Colby College
 * <p>
 * Landscape class that contains checkout agents and customers that have finished checkout
 *
 * @author Ricky Peng
 * @file Landscape.java
 * @date 2020-10-05
 */

public class Landscape {

    private int width;
    private int height;
    private ArrayList<CheckoutAgent> agents;
    private LinkedList<Customer> customers;

    public Landscape(int width, int height, ArrayList<CheckoutAgent> agents) {
        this.width = width;
        this.height = height;
        this.agents = agents;
        this.customers = new LinkedList<>();
    }

    /**
     * @return width of the landscape
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of the landscape
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return a String message to represent the Landscape
     */
    @Override
    public String toString() {
        return agents.size() + " customers are in checkout & " + customers.size() + " finished.";
    }

    /**
     * @param customer add the Customer to the list of finished customers
     */
    public void addFinishedCustomer(Customer customer) {
        customers.addLast(customer);
        ;
    }

    /**
     * Draw the landscape on the graphics
     *
     * @param g a Java Graphics object
     */
    public void draw(Graphics g) {
        for (CheckoutAgent agent : agents) {
            agent.draw(g);
        }
    }

    /**
     * Update states for all Checkout Agent, calling updateState() on every Agent
     */
    public void updateCheckouts() {
        for (CheckoutAgent agent : agents) {
            agent.updateState(this);
        }
    }

    /**
     * Print the statistics on console
     */
    public void printFinishedCustomerStatistics() {
        double sum = 0.0;

        for (Customer customer : customers) {
            sum += customer.getTime();
        }

        double avg = sum / customers.size();

        double variance = 0.0;
        for (Customer customer : customers) {
            variance += (customer.getTime() - avg) * (customer.getTime() - avg) / customers.size();
        }

        double stdev = Math.sqrt(variance);

        System.out.printf("Average: %.3f; Standard deviation: %.3f \n", avg, stdev);
    }
}
