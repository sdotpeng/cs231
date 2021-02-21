import java.util.Random;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * Simulation for all types of agent, command line input enabled
 *
 * @file Simulation.java
 * @author Ricky Peng
 * @date 2020-09-28
 */
public class Simulation {

    public static void main(String[] args) throws InterruptedException {

        String type = "normal";
        int width = 800;
        int height = 800;
        int number = 400;
        int radius = 15;
        boolean moveRandomly = true;

        // Print usage document when no arguments are inputted
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  java Simulation <String type> <int width/height> <int number> <int radius> <boolean moveRandomly>");
            return;
        }

        // Parse arguments
        try {
            type = args[0];
            width = Integer.parseInt(args[1]);
            height = Integer.parseInt(args[1]);
            number = Integer.parseInt(args[2]);
            radius = Integer.parseInt(args[3]);
            if (args.length == 5) {
                moveRandomly = Boolean.parseBoolean(args[4]);
            }
        } catch (IndexOutOfBoundsException ignore) {}

        // Start simulation for different types of social agents
        startSimulation(type, width, height, number, radius, moveRandomly);

    }

    /**
     * Start different types of simulation
     * @param type type name of the social agents
     * @param width width of the graphics window
     * @param height height of the graphics window
     * @param number number of agents on the graphics window
     * @param radius radius that each agent has
     * @param moveRandomly whether to move randomly
     * @throws InterruptedException when interrupted
     */
    public static void startSimulation(String type, int width, int height, int number, int radius, boolean moveRandomly) throws InterruptedException {
        switch (type) {
            case "covid" -> {
                Landscape landscape = new Landscape(width, height);
                Random random = new Random();

                for (int i = 0; i < number; i++) {
                    CovidSocialAgent agent = new CovidSocialAgent(landscape.getWidth() * random.nextDouble(),
                            landscape.getHeight() * random.nextDouble(), radius);
                    // Set 2% agents to be vaccinated and another 2% to be infected
                    if (random.nextDouble() < 0.02) agent.setVaccinated();
                    if (random.nextDouble() < 0.02) agent.setInfected();
                    landscape.addAgent(agent);
                }

                LandscapeDisplay landscapeDisplay = new LandscapeDisplay(landscape);

                for (int i = 0; i < 500; i++) {
                    landscape.updateAgents(true);
                    landscapeDisplay.repaint();
                    Thread.sleep(50);
                }
            }
            case "cat" -> {
                Landscape landscape = new Landscape(width, height);
                Random random = new Random();

                for (int i = 0; i < number; i++) {
                    landscape.addAgent(new CatSocialAgent(landscape.getWidth() * random.nextDouble(),
                            landscape.getHeight() * random.nextDouble(), radius, random.nextInt(2)));
                }

                LandscapeDisplay landscapeDisplay = new LandscapeDisplay(landscape);

                for (int i = 0; i < 200; i++) {
                    landscape.updateAgents(moveRandomly);
                    landscapeDisplay.repaint();
                    Thread.sleep(500);
                }
            }
            default -> {
                Landscape landscape = new Landscape(width, height);
                Random random = new Random();

                for (int i = 0; i < number; i++) {
                    landscape.addAgent(new SocialAgent(landscape.getWidth() * random.nextDouble(),
                            landscape.getHeight() * random.nextDouble(), radius));
                }

                LandscapeDisplay landscapeDisplay = new LandscapeDisplay(landscape);

                for (int i = 0; i < 200; i++) {
                    landscape.updateAgents(moveRandomly);
                    landscapeDisplay.repaint();
                    Thread.sleep(500);
                }
            }
        }
    }
}
