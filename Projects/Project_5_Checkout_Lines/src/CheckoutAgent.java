import java.awt.*;

/**
 * Project 5: Checkout lines - CS231, Colby College
 *
 * A class to represent the Checkout agent
 *
 * @file CheckoutAgent.java
 * @author Ricky Peng
 * @date 2020-10-05
 */

public class CheckoutAgent {

    private int x;
    private int y;
    private int unit;
    private MyQueue<Customer> queue;

    /**
     * Default constructor for CheckoutAgent
     * @param x x coordinate of the agent
     * @param y y coordinate of the agent
     */
    public CheckoutAgent(int x, int y) {
        this.x = x;
        this.y = y;
        this.queue = new MyQueue<>();
        this.unit = 10;
    }

    /**
     * Add one customer to the queue
     * @param customer a Customer object to be added into the queue
     */
    public void addCustomerToQueue(Customer customer) {
        queue.offer(customer);
    }

    /**
     * @return number of customers in the queue
     */
    public int getNumInQueue() {
        return queue.size();
    }

    /**
     * Draw the agent in the graphics
     * @param g a Graphics object
     */
    public void draw(Graphics g) {
        drawCounter(g);
        int textY = drawCustomers(g);
        drawText(g, textY);
    }

    /**
     * Draw the counter
     * @param g Graphics
     */
    public void drawCounter(Graphics g) {
        g.setColor(new Color(144, 224, 239));
        g.fillRect(x + 2 * unit, (int)(y - 2.8 * unit), 2 * unit, (int)(0.8 * unit));
        g.fillRect(x + 2 * unit, y - 2 * unit, (int)(0.8 * unit), 2 * unit);
        double newX = this.x;
        double newY = this.y;
        g.setColor(new Color(0, 119, 182));
        g.fillOval((int)(newX + 3 * unit), (int)(newY - 1.4 * unit), (int)(0.8 * unit), (int)(0.8 * unit));
    }

    /**
     * Draw the customer
     * @param g Graphics
     * @return y coordinate of the last customer drawn
     */
    public int drawCustomers(Graphics g) {
        int x = this.x + unit / 2;
        int y = this.y - 3 * unit / 2;
        g.setColor(new Color(116, 198, 157));
        for (Customer customer : queue) {
            drawCustomer(g, x, y, customer.getNumItems());
            y -= unit * 3 / 2;
        }
        return y;
    }

    /**
     * Draw the text over the last customer
     * @param g Graphics
     * @param textY text to be drawn
     */
    public void drawText(Graphics g, int textY) {
        int x = this.x + 3 * unit / 4;
        int y = textY + unit;
        g.drawString(String.valueOf(queue.size()), x, y);
    }

    /**
     * Draw individual customer
     * @param g Graphics
     * @param x x coordinate
     * @param y y coordinate
     * @param numItems number of items that customer has
     */
    public void drawCustomer(Graphics g, int x, int y, int numItems) {
        g.fillRect(x, y, unit, unit);
    }

    /**
     * Update the state of the scape
     * @param scape Landscape
     */
    public void updateState(Landscape scape) {
        for (Customer customer : queue) {
            customer.incrementTime();
        }

        if (queue.peek() == null) {
            return;
        }

        queue.peek().giveUpItem();

        if (queue.peek().getNumItems() == 0) {
            scape.addFinishedCustomer(queue.poll());
        }
    }
}
