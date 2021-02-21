import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * Simulation for categorized social agent
 *
 * @file CatSocialAgentSimulation.java
 * @author Ricky Peng
 * @date 2020-09-26
 * @see Agent
 */

public class CovidSocialAgent extends SocialAgent {

    private int state;

    /**
     * Default constructor
     * @param x0 x coordinate of the agent
     * @param y0 y coordinate of the agent
     * @param radius radius of the agent
     */
    public CovidSocialAgent(double x0, double y0, int radius) {
        super(x0, y0, radius);
        // Initialize state to normal (not infected not vaccinated)
        this.state = 0;
    }

    /**
     * Set an normal agent to infected
     */
    public void setInfected() {
        if (this.state == 0) this.state = 1;
    }

    /**
     * Set an normal agent to vaccinated
     */
    public void setVaccinated() {
        if (this.state == 0) this.state = 2;
    }

    /**
     * Set an vaccinated agent to infected
     */
    public void reinfected() {
        if (this.state == 2) this.state = 1;
    }

    /**
     * @return state of the agent
     */
    public int getState() {
        return state;
    }

    /**
     * Draw the agent on graphics
     *
     * Normal agent -> Gray
     * Infected agent -> Red
     * Vaccinated agent -> Green
     *
     * @param g Graphics
     */
    @Override
    public void draw(Graphics g) {

        double newX = x - 6.0 / 2;
        double newY = y - 6.0 / 2;

        switch (this.state) {
            case 1 -> {
                g.setColor(Color.RED);
            }
            case 2 -> {
                g.setColor(Color.GREEN);
            }
            default -> {
                g.setColor(Color.GRAY);
            }
        }

        g.fillOval((int) newX, (int) newY, 6, 6);
    }

    /**
     * Update state of the agent according to the COVID rule, described in the report
     * @param scape Landscape
     * @param moveRandomly unused
     */
    @Override
    public void updateState(Landscape scape, boolean moveRandomly) {
        Random random = new Random();
        ArrayList<Agent> neighbors = scape.getNeighbors(this.x, this.y, this.radius);
        if (neighbors.size() > 1) {
            // Get number of infected people and vaccinated people
            int numInfected = this.getInfected(neighbors);
            int numVaccinated = this.getVaccinated(neighbors);
            if (numInfected > 0) {
                // There is infected person
                if (numInfected >= numVaccinated) {
                    // If number of infected people is greater than number of vaccinated people
                    // With 20% chance get infected
                    if (random.nextDouble() < 0.25) setInfected();
                } else {
                    // If number of vaccinated people is greater than number of infected people
                    // With 15% chance get infected
                    if (random.nextDouble() < 0.2) setInfected();
                    // With 10% chance persuaded to get vaccinated
                    else if (random.nextDouble() < 0.3) setVaccinated();
                }
            } else {
                // If there is no infected person
                if (numVaccinated > 0) {
                    // If there is vaccinated person
                    // With 10% chance persuaded to get vaccinated
                    if (random.nextDouble() < 0.1) setVaccinated();
                }
            }
        }
        // With customized chance the vaccines are not working
        double chance = 0.001;
        if (random.nextDouble() < chance) {
            reinfected();
        }

        if (this.getState() == 1) {
            // Some infected people move randomly
            if (random.nextDouble() < 0.20) {
                this.moveRandomly(random, this.x, this.y);
            }
        } else {
            // Others move randomly
            this.moveRandomly(random, this.x, this.y);
        }

    }

    /**
     * Get number of infected neighbors
     * @param neighbors An ArrayList of neighboring agents
     * @return number of infected neighbors
     */
    public int getInfected(ArrayList<Agent> neighbors) {
        int infected = 0;
        for (Agent neighbor : neighbors) {
            if (neighbor instanceof CovidSocialAgent) {
                if (((CovidSocialAgent) neighbor).getState() == 1) infected++;
            }
        }
        return infected;
    }

    /**
     * Get number of vaccinated neighbors
     * @param neighbors An ArrayList of neighboring agents
     * @return number of vaccinated neighbors
     */
    public int getVaccinated(ArrayList<Agent> neighbors) {
        int vaccinated = 0;
        for (Agent neighbor : neighbors) {
            if (neighbor instanceof CovidSocialAgent) {
                if (((CovidSocialAgent) neighbor).getState() == 2) vaccinated++;
            }
        }
        return vaccinated;
    }
}
