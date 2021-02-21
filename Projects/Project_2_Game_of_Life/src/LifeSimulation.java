import java.util.ArrayList;
import java.util.Random;

/**
 * Project2: Conway's Game of Life - CS231, Colby Colege
 *
 * This class models on {@code LandscapeDisplay.main()}. It should include a loop that calls the {@code advance()}
 * method of the {@code Landscape}, calls the {@code repaint()} method of LandscapeDisplay, and calls
 * {@code Thread.sleep(250)} to pause for 250 milliseconds before starting the next iteration. Each iteration of the
 * loop is one time step of the simulation
 *
 * @file LifeSimulation.java
 * @author Ricky Peng
 * @date 2020-09-14
 */

public class LifeSimulation {
    Landscape landscape;
    Random randomGenerator;
    private int rows, cols;
    private int numIterations;
    private double density;
    private int GRID_SCALE = 8;
    private static int interactive = 0;

    /**
     * Default constructor
     * @param rows number of rows of the grid
     * @param cols number of columns of the grid
     * @param numIterations number of iteration of the simulation
     * @param density how many percentage of the cells are set to be alive
     */
    public LifeSimulation(int rows, int cols, int numIterations, double density) {
        this.rows = rows;
        this.cols = cols;
        this.numIterations = numIterations;
        this.density = density;
        landscape = new Landscape(rows, cols);
        randomGenerator = new Random(System.currentTimeMillis());
    }

    /**
     * Initialize the landscape and randomize the cell's state according to the given density
     */
    public void randomInitialize() {
        landscape.randomize(randomGenerator, density);
    }

    /**
     * Open another window to visualize the simulation
     * @throws InterruptedException from Thread.sleep()
     */
    public void simulate() throws InterruptedException {
        LandscapeDisplay display = new LandscapeDisplay(landscape, GRID_SCALE);
        for (int i = 0; i < this.numIterations; i++) {
            landscape.advance();
            display.repaint();
            Thread.sleep(250);
        }
    }

    /**
     * Prints out visualization result onto the console
     */
    public void simulateToText() {
        for (int i = 0; i < this.numIterations; i++) {
            System.out.println(landscape);
            System.out.println();
            landscape.advance();
        }
    }

    /**
     * Allow users to control the simulation process
     * @throws InterruptedException from Thread.sleep()
     */
    public void simulateInteractive() throws InterruptedException {

        LandscapeInteractiveDisplay displayInteractive = new LandscapeInteractiveDisplay(landscape, GRID_SCALE);

        ArrayList<Landscape> landscapes = new ArrayList<>();

        // Storing all landscapes in ArrayList landscapes
        for (int i = 0; i < numIterations; i++) {
            landscapes.add(landscape.getCopiedLandscape());
            landscape.advance();
        }

        displayInteractive.scape = landscapes.get(1).getCopiedLandscape();
        displayInteractive.repaint();

        // Index of current frame
        int arrayPointer = 1;

        while (getInteractive() != -1) {
            int status = getInteractive();
            System.out.print("");
            if (status == 1) {
                // When Simulate button is clicked
                for (int i = arrayPointer; i < numIterations; i++) {
                    displayInteractive.scape = landscapes.get(i).getCopiedLandscape();
                    displayInteractive.repaint();
                    Thread.sleep(500);
                }
                arrayPointer = numIterations - 1;
                setInteractive(101);
            } else if (status == 2) {
                // When Back button is clicked
                try {
                    if (arrayPointer == 1) {
                        throw new IndexOutOfBoundsException();
                    } else {
                        displayInteractive.scape = landscapes.get(arrayPointer - 1).getCopiedLandscape();
                        arrayPointer--;
                        displayInteractive.repaint();
                    }
                } catch (IndexOutOfBoundsException ignored) {}
                setInteractive(103);
            } else if (status == 3) {
                // When Next button is clicked
                try {
                    displayInteractive.scape = landscapes.get(arrayPointer + 1).getCopiedLandscape();
                    arrayPointer++;
                    displayInteractive.repaint();
                } catch (IndexOutOfBoundsException ignored) {}
                setInteractive(102);
            } else if (status == 4) {
                // When Reset button is clicked
                displayInteractive.scape = landscapes.get(1).getCopiedLandscape();
                arrayPointer = 1;
                displayInteractive.repaint();
                setInteractive(104);
            }
        }
    }

    /**
     * Set user click signal for interaction
     * @param interactive signal
     */
    public static void setInteractive(int interactive) {
        LifeSimulation.interactive = interactive;
    }

    /**
     * Get user click signal for interaction
     * @return signal
     */
    public static int getInteractive() {
        return interactive;
    }

    /**
     * Command line arguments integrated
     * @param args --mode Mode of the Simulation -d Density -rc rows columns -i number of iterations
     * @throws InterruptedException from Thread.sleep()
     */
    public static void main(String[] args) throws InterruptedException {

        int rows = 100;
        int cols = 100;
        int numIterations = 20;
        double density = 0.3;
        String mode = "";

        if (args.length == 0) {
            System.out.println("[WARNING] Please specify display mode");
            System.out.println("[WARNING] Add \"--help\" in command line argument to view usage");
            return;
        } else if (args.length == 1 && args[0].equals("--help")) {
            System.out.println("Usage: --mode <String> -D <double> -RC <int int> -I <int>");
            System.out.println();
            System.out.println("       --mode <String>  (NO_DEFAULT) : select between \"graphics\", \"interactive\" and \"text\",");
            System.out.println("                                     : for \"text\", add \"> fileName.txt\" at the end to store the result");
            System.out.println("       -d     <double>  (0.3)        : density of cells in the grid");
            System.out.println("       -rc    <int int> (100, 100)   : rows and columns of the landscape");
            System.out.println("       -i     <int>     (20)         : number of iterations");
        } else {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-d":
                        try {
                            density = Double.parseDouble(args[i + 1]);
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("[ERROR] No density value follows");
                        } catch (NumberFormatException ex) {
                            System.out.println("[ERROR] Invalid format. Requires double format");
                        }
                        break;

                    case "-rc":
                        try {
                            rows = Integer.parseInt(args[i + 1]);
                            cols = Integer.parseInt(args[i + 2]);
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("[ERROR] No number of rows and columns value follow");
                        } catch (NumberFormatException ex) {
                            System.out.println("[ERROR] Invalid format. Requires integer format");
                        }
                        break;

                    case "-i":
                        try {
                            numIterations = Integer.parseInt(args[i + 1]);
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("[ERROR] No number of iteration value follows");
                        } catch (NumberFormatException ex) {
                            System.out.println("[ERROR] Invalid format. Requires integer format");
                        }
                        break;

                    case "--mode":
                        try {
                            mode = args[i + 1];
                        } catch (IndexOutOfBoundsException ex) {
                            System.out.println("[ERROR] No mode specification follows");
                        }

                    default:

                }
            }
        }

        switch (mode) {
            case "graphics" -> {
                LifeSimulation simulation = new LifeSimulation(rows, cols, numIterations, density);
                simulation.randomInitialize();
                simulation.simulate();
            }
            case "text" -> {
                LifeSimulation simulation = new LifeSimulation(rows, cols, numIterations, density);
                simulation.randomInitialize();
                simulation.simulateToText();
            }
            case "interactive" -> {
                LifeSimulation simulation = new LifeSimulation(rows, cols, numIterations, density);
                simulation.randomInitialize();
                simulation.simulateInteractive();
            }
        }

    }
}