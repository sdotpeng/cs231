import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Project2: Conway's Game of Life - CS231, Colby College
 *
 * Landscape holds a 2D grid of Cell object references. The Landscape class should have a field to hold the array
 * of Cell object references and implement the following methods. You may also want to have fields to hold
 * the number of rows and the number of columns in the grid.
 *
 * @file Landscape.java
 * @author Ricky Peng
 * @date 2020-09-10
 */

public class Landscape {

    private Cell[][] grid;
    private int rows;
    private int cols;
    private HashMap<Integer, Color> palette;

    /**
     * Constructor for Landscape class, iterates through all cells within the landscape and sets cell to dead
     * @param rows rows of the landscape grid
     * @param cols columns of the landscape grid
     */
    public Landscape(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        this.palette = getPalette();

        // iterates through and sets all cell status dead
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    /**
     * This resets status to dead for all cells in the grid
     */
    public void reset() {
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                cell.reset();
            }
        }
    }

    /**
     * @return number of rows of the grid
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * @return number of columns of the grid
     */
    public int getCols() {
        return this.cols;
    }

    /**
     * @param row row index of the cell
     * @param col column index of the cell
     * @return Cell object at index (row, col)
     */
    public Cell getCell(int row, int col) {
        try {
            return this.grid[row][col];
        } catch (IndexOutOfBoundsException ignored) {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * @return a color palette for visualizing the cell based on its age and state
     */
    public static HashMap<Integer, Color> getPalette() {
        return new HashMap<>() {
            {
                put(1, new Color(255, 243, 220));
                put(2, new Color(180, 225, 192));
                put(3, new Color(149, 213, 178));
                put(4, new Color(116, 198, 157));
                put(5, new Color(82, 183, 136));
                put(6, new Color(64, 145, 108));
                put(7, new Color(45, 106, 79));
                put(8, new Color(27, 67, 50));
                put(9, new Color(11, 45, 30));
                put(10, new Color(8, 28, 21));
            }
        };
    }

    /**
     * Returns a list of references to the neighbors of the {@code Cell} at location ({@code row}, {@code col}),
     * attention to the boundaries of the Landscape is paid
     *
     * @param row row index of central cell
     * @param col column index of central cell
     * @return a list of Cell objects
     */
    public ArrayList<Cell> getNeighbors(int row, int col) {

        if (row < 0 || row >= this.rows || col < 0 || col >= this.cols) {
            // check if the given index is not in the range of this grid
            throw new IndexOutOfBoundsException("Index Out Of Bounds: given index " +
                    "(" + rows + "," +cols + ") not in the grid");
        }

        ArrayList<Cell> neighbors = new ArrayList<>();
        for (int i = row - 1; i < row + 2; i++) {
            for (int j = col - 1; j < col + 2; j++) {
                try {
                    // Ignore if the cell is out of bound
                    if (i != row || j != col) {
                        // Avoid adding itself
                        neighbors.add(this.getCell(i, j));
                    }
                } catch (IndexOutOfBoundsException ignored) { }
            }
        }
        return neighbors;
    }

    /**
     * This should move all {@code Cell} forward one generation. Note that the rules for the Game of Life require all
     * {@code Cell} to be updated simultaneously
     */
    public void advance() {
        Cell[][] tempGrid = this.getDuplicatedGrid();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                tempGrid[i][j].updateState(this.getNeighbors(i, j));
            }
        }
        this.grid = tempGrid;
    }

    /**
     * @return a duplicate of field grid of Cell[][]
     */
    public Cell[][] getDuplicatedGrid() {
        Cell[][] copiedGrid = new Cell[this.rows][this.cols];
        for (int i = 0; i < this.rows; i++) {
            /*if (this.cols >= 0) {
                System.arraycopy(grid[i], 0, copiedGrid[i], 0, this.cols);
            }*/
            for (int j = 0; j < this.cols; j++) {
                copiedGrid[i][j] = new Cell(grid[i][j].getAlive());
                copiedGrid[i][j].setAge(grid[i][j].getAge());
            }
        }

        return copiedGrid;
    }

    /**
     * @return used only for interactive mode, for create a new instance in the memory
     */
    public Landscape getCopiedLandscape() {
        Landscape out = new Landscape(this.getRows(), this.getCols());
        out.grid = this.getDuplicatedGrid();
        out.palette = getPalette();

        return out;
    }

    /**
     * randomize the landscape and approximate the density of all live cells
     * @param randomGenerator a Random() object
     * @param density density of all live cells
     */
    public void randomize(Random randomGenerator, double density) {
        this.reset();
        for (int i = 0; i < this.getRows(); i++) {
            for (int j = 0; j < this.getCols(); j++ ) {
                this.getCell( i, j ).setAlive(randomGenerator.nextDouble() <= density);
            }
        }
    }

    /**
     * @return String: message that represents the entire grid
     */
    public String toString() {
        StringBuilder outString = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (j == grid[i].length - 1) {
                    outString.append(this.getCell(i, j).toString());
                } else {
                    outString.append(this.getCell(i, j).toString()).append(" ");
                }
            }
            outString.append("\n");
        }
        return outString.toString();
    }

    /**
     * Draws on the Graphics
     * @param g Graphics object
     * @param gridScale drawn scale of one cell
     */
    public void draw(Graphics g, int gridScale) {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                grid[i][j].draw(g, i * gridScale, j * gridScale, gridScale, this.palette);
            }
        }
    }

    /**
     * Method for testing the class
     * @param args unused
     * @throws IndexOutOfBoundsException when getNeighbors() is passed with a cell at the grid's boundary
     */
    public static void mainTesting(String[] args) throws IndexOutOfBoundsException {
        System.out.println(">> Testing Landscape class...");
        System.out.println(">> Initializing Landscape of 4 x 4...");
        Landscape testLandscape = new Landscape(7,7);
        System.out.println(">> Resetting all to false...");
        testLandscape.reset();
        System.out.println(">> Testing toString()...");
        System.out.println(testLandscape);
        System.out.println();
        System.out.println(">> Setting cell at index (5,5), (5,1) and (5,4) alive");
        testLandscape.getCell(5,1).setAlive(true);
        testLandscape.getCell(5,4).setAlive(true);
        testLandscape.getCell(5,5).setAlive(true);
        System.out.println(testLandscape);
        // One side out of bounds
        // System.out.println(">> Getting neighbors at index (3,4)");
        // System.out.println(testLandscape.getNeighbors(3,4));
        // Normal case
        System.out.println(">> Getting neighbors at index (1,1)");
        System.out.println(testLandscape.getNeighbors(1,1));
        // Both sides out of bounds
        System.out.println(">> Getting neighbors at index (6,6)");
        System.out.println(testLandscape.getNeighbors(6,6));
        System.out.println(">> Getting neighbors at index (6,1)");
        System.out.println(testLandscape.getNeighbors(6,1));
        System.out.println(">> Getting neighbors at index (6,0)");
        System.out.println(testLandscape.getNeighbors(6,0));
    }

    /**
     * method for testing advance()
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println(">> Initializing a Landscape of 10 x 10...");
        Landscape testLandscape = new Landscape(10,10);
        System.out.println(">> Initial state follows...");
        System.out.println(testLandscape);
        System.out.println(">> Setting few cells alive...");
        testLandscape.getCell(1,1).setAlive(true);
        testLandscape.getCell(2,1).setAlive(true);
        testLandscape.getCell(3,1).setAlive(true);
        testLandscape.getCell(4,1).setAlive(true);
        testLandscape.getCell(1,3).setAlive(true);
        testLandscape.getCell(9,9).setAlive(true);
        System.out.println(">> First state follows....");
        System.out.println(testLandscape);
        System.out.println(">> Advance to second state...");
        testLandscape.advance();
        System.out.println(">> Second state follows...");
        System.out.println(testLandscape);
    }
}
