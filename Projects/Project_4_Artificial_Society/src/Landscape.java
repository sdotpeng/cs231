import java.awt.Graphics;
import java.util.ArrayList;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * Landscape class
 *
 * @file Landscape.java
 * @author Ricky Peng
 * @date 2020-09-27
 */

public class Landscape {

    private int width;
    private int height;
    private LinkedList<Agent> agents;

    /**
     * Default constructor
     *
     * @param width of the landscape
     * @param height of the landscape
     */
    public Landscape(int width, int height) {
        this.width = width;
        this.height = height;
        this.agents = new LinkedList<>();
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
     * Add an agent to the LinkedList
     * @param agent any type of agent
     */
    public void addAgent(Agent agent) {
        agents.addFirst(agent);
    }

    /**
     * @return a String representation of the Landscape
     */
    public String toString() {
        return "Landscape contains " + agents.size() + " agents";
    }

    /**
     * Get the neighbor agents
     * @param x0 x coordinate of the agent
     * @param y0 y coordinate of the agent
     * @param radius radius of the agent
     * @return ArrayList of neighboring agents
     */
    public ArrayList<Agent> getNeighbors(double x0, double y0, double radius) {
        ArrayList<Agent> out = new ArrayList<>();
        for (Agent agent : agents) {
            // If radius is greater than the distance, add it to the list
            if (Math.pow(agent.getX() - x0, 2) + Math.pow(agent.getY() - y0, 2) < radius * radius) {
                out.add(agent);
            }
        }
        return out;
    }

    /**
     * Update all agents
     * @param moveRandomly whether to move randomly
     */
    public void updateAgents(boolean moveRandomly) {
        ArrayList<Agent> shuffledAgents = agents.toShuffledList();
        for (Agent agent : shuffledAgents) {
            agent.updateState(this, moveRandomly);
        }
    }

    /**
     * Draw every agent
     * @param g Graphics
     */
    public void draw(Graphics g) {
        for (Agent agent : agents) {
            agent.draw(g);
        }
    }

    public static void main(String[] args) {
//        Landscape testLandscape = new Landscape(100, 100);
//        System.out.println(">> Landscape has width and height: " + testLandscape.getWidth() + ", " + testLandscape.getHeight());
//        System.out.println(">> Adding four agent");
//        testLandscape.addAgent(new SocialAgent(2,2,5));
//        testLandscape.addAgent(new SocialAgent(2,3,5));
//        testLandscape.addAgent(new SocialAgent(3,2,5));
//        testLandscape.addAgent(new SocialAgent(3,3,5));
//        System.out.println(">> " + testLandscape.toString());
//        System.out.println(">> Neighbors at 2.5, 2.5 for radius 0.5");
//        System.out.println(testLandscape.getNeighbors(2.5, 2.5, 1));

        Landscape landscape = new Landscape(500, 500);
        SocialAgent base = new SocialAgent(5,5, 25);
        landscape.addAgent(base);
        landscape.addAgent(new SocialAgent(10,10, 5));
        landscape.addAgent(new SocialAgent(15,15, 5));
        landscape.addAgent(new SocialAgent(20,20, 5));
        // base.updateState(landscape);
        System.out.println();
        System.out.println(base.getX() + " " + base.getY());
        System.out.println(landscape.getNeighbors(base.getX(), base.getY(), base.getRadius()).size());
    }

}
