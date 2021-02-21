import java.util.Random;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * Simulation for social agent
 *
 * @file SocialAgentSimulation.java
 * @author Ricky Peng
 * @date 2020-09-27
 * @deprecated Unused, coded for testing, see Simulation (Extension 3)
 * @see Simulation
 */

@Deprecated
public class SocialAgentSimulation {

    public static void main(String[] args) throws InterruptedException {

        int width = 750;
        int height = 750;
        int number = 300;
        boolean moveRandomly = true;

        if (args.length == 2) {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[0]);
            number = Integer.parseInt(args[1]);
        }

        Landscape landscape = new Landscape(width, height);

        Random random = new Random(System.currentTimeMillis());

        for (int i = 0; i < number; i++) {
            landscape.addAgent(new SocialAgent(landscape.getWidth() * random.nextDouble(),
                    landscape.getHeight() * random.nextDouble(), 15));
        }

        LandscapeDisplay landscapeDisplay = new LandscapeDisplay(landscape);

        for (int i = 0; i < 100; i++) {
            landscape.updateAgents(moveRandomly);
            landscapeDisplay.repaint();
            Thread.sleep(1000);
        }
    }
}
