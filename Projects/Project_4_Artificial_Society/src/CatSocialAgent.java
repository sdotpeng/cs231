import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * Categorized social agent, extended from SocialAgent class
 *
 * @file CatSocialAgent.java
 * @author Ricky Peng
 * @date 2020-09-26
 * @see Agent
 */

public class CatSocialAgent extends SocialAgent {

    private int category;

    /**
     * Default constructor
     * @param x0 x coordinate of agent
     * @param y0 y coordinate of agent
     * @param radius radius that the agent has
     * @param category category of the agent
     */
    public CatSocialAgent(double x0, double y0, int radius, int category) {
        super(x0, y0, radius);
        this.category = category;
    }

    /**
     * @return the category number of the agent
     */
    public int getCategory() {
        return category;
    }

    /**
     * @return a String message that represents the agent
     */
    @Override
    public String toString() {
        return String.valueOf(category);
    }

    /**
     * Draw the agent on the Graphics
     * @param g Graphics object
     */
    @Override
    public void draw(Graphics g) {
        // Make the circle located at the agent's center
        double newX = x - 6.0 / 2;
        double newY = y - 6.0 / 2;
        // Distinguish category
        if (category == 1) {
            g.setColor(moved ? new Color(224, 170, 255) : new Color(123, 44, 191));
        } else {
            g.setColor(moved ? new Color(255, 158, 187) : new Color(185, 30, 0));
        }
        // Draw circle
        g.fillOval((int) newX, (int) newY, 6, 6);
    }

    /**
     * Update state of one agent at one iteration
     * @param scape Landscape
     */
    public void updateState(Landscape scape) {
        Random random = new Random();
        ArrayList<Agent> neighbors = scape.getNeighbors(x, y, radius);

        // Initialize two values that represent number of same categories and number of different categories
        int countSame = 0;
        int countDiff = 0;
        for (Agent neighbor : neighbors) {
            if (neighbor instanceof CatSocialAgent) {
                // Count same and different
                if (((CatSocialAgent) neighbor).getCategory() == this.getCategory()) {
                    countSame++;
                } else {
                    countDiff++;
                }
            }
        }

        if (neighbors.size() >= 2 && countSame > countDiff) {
            if (random.nextDouble() <= 0.01) {
                this.moveRandomly(random, this.x, this.y);
            } else {
                // Not moved
                this.moved = false;
                return;
            }
        } else {
            this.moveRandomly(random, this.x, this.y);
        }

        this.moved = true;
    }
}
