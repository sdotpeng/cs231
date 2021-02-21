import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project2: Conway's Game of Life - CS231, Colby College
 *
 * A Cell object represents one location on a regular grid
 *
 * @file Cell.java
 * @author Ricky Peng
 * @date 2020-09-10
 */

public class Cell {

    private boolean alive;
    private int age;

    /**
     * default constructor, setting status to false
     */
    public Cell() {
        this(false);
    }

    /**
     * constructor, setting status to given boolean
     * @param alive user-given status
     */
    public Cell(boolean alive) {
        this.alive = alive;
        this.age = 0;
    }

    /**
     * reset status to false
     */
    public void reset() {
        this.alive = false;
        this.age = 0;
    }

    /**
     * set status to given boolean
     * @param alive user-given status
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    /**
     * @return true if cell is alive, false if not
     */
    public boolean getAlive() {
        return this.alive;
    }

    public void increaseAge() {
        this.age = this.age + 1;
    }

    /**
     * This updates whether or not the {@code Cell} is alive in the next time step, given its {@code neighbors} in the
     * current time step. It should look at the {@code Cell}'s {@code neighbors} on the provided Landscape and update
     * its own state information, the default rule should be if a live {@code Cell} has either two or three live
     * {@code neighbors}, then it will remain alive. If a dead {@code Cell} has exactly three living {@code neighbors},
     * it will be set to alive. Otherwise, the {@code Cell} will remain dead
     *
     * @param neighbors an ArrayList of Cell object that represents the surrounding cell of the current cell
     */
    public void updateState(ArrayList<Cell> neighbors) {
        int count = 0;
        for (Cell neighbor : neighbors) {
            count += neighbor.getAlive() ? 1 : 0;
        }
        if (this.getAlive()) {
            // When the cell is currently alive
            if (count == 3 || count == 2) {
                // Continue living, increase age
                this.increaseAge();
            } else {
                // Turn dead, back to age 0
                this.setAlive(false);
                this.setAge(0);
            }
        } else {
            // When the cell is currently dead
            if (count == 3) {
                // Turn alive, initialize age to 1
                this.setAlive(true);
                this.setAge(1);
            }
        }
    }

    /**
     * Draw cell according to its age and state
     * @param g Graphics
     * @param x x-coordinate
     * @param y y-coordinate
     * @param scale rectangle scale
     * @param palette color palette
     */
    public void draw(Graphics g, int x, int y, int scale, HashMap<Integer, Color> palette) {
        if (this.getAlive()) {
            Color cellColor = palette.get(this.age);
            // If age is greater than the maximum option, use the maximum one
            g.setColor(cellColor != null ? cellColor : palette.get(10));
        } else {
            // If cell is dead, use plain gray
            g.setColor(new Color(206, 212, 218));
        }

        if (this.getAlive()) {
            // Use 3DRect for alive cells
            g.fill3DRect(x, y, scale, scale, true);
        } else {
            // Use normal Rect for dead cells
            g.fillRect(x, y, scale, scale);
        }
    }

    /**
     * String that represents the status
     * @return status message
     */
    public String toString() {
        return alive ? "\uD83C\uDF32" : "\uD83C\uDF42";
    }

    public static void main(String[] args) {
        System.out.println(">> Testing Cell class...");
        System.out.println(">> Initializing an alive Cell instance...");
        Cell testCell = new Cell(true);
        System.out.println(">> Getting status...");
        System.out.println(">> " + testCell.toString());
        System.out.println(">> Resetting...");
        testCell.reset();
        System.out.println(">> " + testCell.toString());
        System.out.println(">> Setting it alive...");
        testCell.setAlive(true);
        System.out.println(">> " + testCell.toString());

        System.out.println(">> Testing updateState()...");
        testCell.setAlive(false);
        System.out.println(">> Cell is set dead");
        System.out.println(">> Neighbors of three alive cells are passed in");
        ArrayList<Cell> neighbors = new ArrayList<>() {
            {
                add(new Cell(true));
                add(new Cell());
                add(new Cell(true));
                add(new Cell(true));
            }
        };
        testCell.updateState(neighbors);
        System.out.println(">> Cell current state: " + testCell.getAlive());
    }
}
