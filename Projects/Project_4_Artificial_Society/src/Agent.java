import java.awt.Graphics;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * Abstract class of Agent
 *
 * @file Agent.java
 * @author Ricky Peng
 * @date 2020-09-26
 */

public abstract class Agent {

    double x;
    double y;

    /**
     * Default constructor of Agent class
     * @param x0 x coordinates of the agent
     * @param y0 y coordinates of the agent
     */
    public Agent(double x0, double y0) {
        this.x = x0;
        this.y = y0;
    }

    /**
     * @return x coordinates of the agent
     */
    public double getX() {
        return this.x;
    }

    /**
     * Set x coordinates of the agent
     * @param newX new x coordinates of the agent
     */
    public void setX(double newX) {
        this.x = newX;
    }

    /**
     * @return y coordinates of the agent
     */
    public double getY() {
        return this.y;
    }

    /**
     * Set x coordinates of the agent
     * @param newY new x coordinates of the agent
     */
    public void setY(double newY) {
        this.y = newY;
    }

    /**
     * @return a String message to represent the Agent class
     */
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    /**
     * Abstract method that update state of one agent, needs to be override
     *
     * @param scape Landscape object
     * @param moveRandomly true if mode randomly
     */
    public abstract void updateState(Landscape scape, boolean moveRandomly);

    /**
     * Abstract method that draws on agent on Graphics object g
     * @param g Graphics object
     */
    public abstract void draw(Graphics g);
}
