import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * Social agent class extended from abstract Agent class
 *
 * @file CatSocialAgentSimulation.java
 * @author Ricky Peng
 * @date 2020-09-27
 * @see Agent
 */

public class SocialAgent extends Agent {

    protected boolean moved;
    protected int radius;

    /**
     * Default constructor of SocialAgent class, extends from Agent class
     * @param x0 x coordinates of the agent
     * @param y0 y coordinates of the agent
     * @param radius radius of the agent
     */
    public SocialAgent(double x0, double y0, int radius) {
        super(x0, y0);
        this.radius = radius;
        this.moved = false;
    }

    /**
     * Set new radius for the agent
     * @param radius new radius
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * @return radius of the agent
     */
    public int getRadius() {
        return this.radius;
    }

    @Override
    public void draw(Graphics g) {
        double newX = x - 6.0 / 2;
        double newY = y - 6.0 / 2;
        g.setColor(moved ? new Color(133,138,227) : new Color(61,14,97));
        g.fillOval((int) newX, (int) newY, 5, 5);
    }

    /**
     * Update state of the agent
     * @param scape Landscape object
     * @param moveRandomly true if mode randomly
     */
    @Override
    public void updateState(Landscape scape, boolean moveRandomly) {
        Random random = new Random();
        ArrayList<Agent> neighbors = scape.getNeighbors(this.x, this.y, this.radius);
        if (neighbors.size() >= 3) {
            // With the chance of 1%
            if (random.nextDouble() <= 0.01) {
                this.moveRandomly(random, this.x, this.y);
            } else {
                // Not moved
                this.moved = false;
                return;
            }
        } else {
            if (moveRandomly) {
                this.moveRandomly(random, this.x, this.y);
            } else {
                if (neighbors.size() == 1) {
                    this.moveRandomly(random, this.x, this.y);
                } else {
                    this.moveBasedOnClusterCenter(neighbors, this.x, this.y, random.nextBoolean());
                }
            }
        }

        this.moved = true;
    }

    /**
     * Move randomly in x and y of [-10,10)
     * @param random Random object
     * @param x0 x coordinate of the agent
     * @param y0 y coordinate of the agent
     */
    public void moveRandomly(Random random, double x0, double y0) {
        this.setX(x0 + 20 * random.nextDouble() - 10);
        this.setY(y0 + 20 * random.nextDouble() - 10);
    }

    /**
     * Extension 2 - Move towards or away from the cluster
     * @param neighbors ArrayList of neighboring agents
     * @param x0 x coordinate of the agent
     * @param y0 y coordinate of the agent
     * @param moveTowards true to move towards, false to move away from
     */
    public void moveBasedOnClusterCenter(ArrayList<Agent> neighbors, double x0, double y0, boolean moveTowards) {
        // Calculate the cluster's center
        int number = neighbors.size();
        double sumX = 0, sumY = 0;
        for (Agent neighbor : neighbors) {
            sumX += neighbor.getX();
            sumY += neighbor.getY();
        }

        double centerX = sumX / number;
        double centerY = sumY / number;

        if (moveTowards) {
            // Move towards the cluster
            this.setX(this.x + (centerX - this.x) / 4);
            this.setY(this.y + (centerY - this.y) / 4);
        } else {
            // Move away from the cluster
            this.setX(this.x + (this.x - centerX) / 4);
            this.setY(this.y + (this.y - centerY) / 4);
        }
    }

    public static void main(String[] args) {
        SocialAgent testSocialAgent = new SocialAgent(5,5,5);
        System.out.println(">> This agent has x " + testSocialAgent.getX());
        System.out.println(">> This agent has y " + testSocialAgent.getY());
        System.out.println(">> This agent has radius " + testSocialAgent.getRadius());
        System.out.println(">> Set x to 1, y to 1, and radius to 10");
        testSocialAgent.setX(1);
        testSocialAgent.setY(1);
        testSocialAgent.setRadius(10);
        System.out.println(">> This agent has x " + testSocialAgent.getX());
        System.out.println(">> This agent has y " + testSocialAgent.getY());
        System.out.println(">> This agent has radius " + testSocialAgent.getRadius());
    }
}
