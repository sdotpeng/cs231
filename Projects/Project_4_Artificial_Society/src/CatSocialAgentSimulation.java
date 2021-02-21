import java.util.Random;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * Simulation for categorized social agent
 *
 * @file CatSocialAgentSimulation.java
 * @author Ricky Peng
 * @date 2020-09-26
 * @deprecated Unused, coded for testing, see Simulation (Extension 3)
 * @see Simulation
 */

@Deprecated
public class CatSocialAgentSimulation {

    public static void main(String[] args) throws InterruptedException {

        int width = 750;
        int height = 750;
        int number = 300;
        boolean moveRandomly = true;

        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  java AgentSimulation < Integer: width/height > < Integer: number of agents > < Boolean: move if randomly >");
        }

        if (args.length == 3) {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[0]);
            number = Integer.parseInt(args[1]);
            moveRandomly = Boolean.parseBoolean(args[2]);
        }

        Landscape landscape = new Landscape(width, height);

        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < number; i++) {
            landscape.addAgent(new CatSocialAgent(landscape.getWidth() * random.nextDouble(),
                    landscape.getHeight() * random.nextDouble(), 15, random.nextInt(2)));
        }

        LandscapeDisplay landscapeDisplay = new LandscapeDisplay(landscape);

        for (int i = 0; i < 200; i++) {
            landscape.updateAgents(moveRandomly);
            landscapeDisplay.repaint();
            Thread.sleep(1000);
        }
    }
}
