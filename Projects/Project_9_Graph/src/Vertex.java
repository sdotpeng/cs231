import java.lang.Math;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Color;

/**
 * Project 9 - CS231, Colby College
 *
 * @author Ricky Peng
 * @file Vertex.java
 * @date 2020-12-01
 */

public class Vertex implements Comparable<Vertex> {

    private ArrayList<Vertex> adjacent;
    private int x;
    private int y;
    private boolean visible;
    private double cost;
    private boolean visited;
    private Vertex parent;

    /**
     * Default Constructor
     * @param x x position
     * @param y y position
     */
    public Vertex(int x, int y) {
        this.x = x;
        this.y = y;
        this.visible = false;
        this.adjacent = new ArrayList<>();
        this.cost = 0;
        this.visited = false;
        this.parent = null;
    }

    public Vertex(int x, int y, boolean visited) {
        this.x = x;
        this.y = y;
        this.visible = false;
        this.adjacent = new ArrayList<>();
        this.cost = 0;
        this.visited = visited;
        this.parent = null;
    }

    /**
     * @return x position of vertex
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return y position of vertex
     */
    public int getY() {
        return this.y;
    }

    /**
     * Set x and y position
     * @param x x position
     * @param y y position
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return true if visible
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Set visible
     * @param visible new visibility
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * @return cost
     */
    public double getCost() {
        return this.cost;
    }

    /**
     * Set cost
     * @param cost new cost
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * @return true if visited
     */
    public boolean isVisited() {
        return this.visited;
    }

    /**
     * Set visited or not
     * @param visited whether visited
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    /**
     * @return the parent
     */
    public Vertex getParent() {
        return this.parent;
    }

    /**
     * Set parent
     * @param parent given parent
     */
    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    /**
     * Calculate the Euclidean distance between this vertex and the other vertex
     * @param other other vertex
     * @return distance between these two
     */
    public double distance(Vertex other) {
        return Math.sqrt(Math.pow((this.x - other.getX()), 2) + Math.pow((this.y - other.getY()), 2));
    }

    /**
     * Connect the vertex with the new given other
     * @param other new vertex to be connected with
     */
    public void connect(Vertex other) {
        this.adjacent.add(other);
    }

    /**
     * Return the Vertex at (x,y) if the Vertex is in the adjacent list
     * @param x given x position
     * @param y given y position
     * @return Vertex at (x,y)
     */
    public Vertex getNeighbor(int x, int y) {
        for (Vertex vertex : adjacent) {
            if (vertex.x == x && vertex.y == y) {
                return vertex;
            }
        }
        return null;
    }

    /**
     * @return an ArrayList of all the Vertex's neighbors
     */
    public ArrayList<Vertex> getNeighbors() {
        return this.adjacent;
    }

    /**
     * @return the number of connected vertices
     */
    public int numNeighbors() {
        return this.adjacent.size();
    }

    /**
     * @return a String representation of the vertex
     */
    public String toString() {
        String result = "  -> Position: (" + this.getX() + "," + this.getY() + ")\n" +
                "  -> Number of neighbors: " + this.numNeighbors() + "\n" +
                "  -> Cost: " + this.cost + "\n" + "  -> Visited: " + this.visited;

        return result;
    }

    /**
     * @param a Vertex a
     * @param b Vertex b
     * @return true if their x and y positions match
     */
    public static boolean matchPosition(Vertex a, Vertex b) {
        return a.distance(b) == 0;
    }

    /**
     * @param other another vertex
     * @return < 0 if this vertex comes before other,
     * 0 if this is equal to other,
     * > 0 if this comes after other
     */
    public int compareTo(Vertex other) {
        return Double.compare(this.cost, other.cost);
    }

    public static void main(String[] args) {

        Vertex a = new Vertex(1,1,false);
        Vertex b = new Vertex(4,5,false);
        Vertex c = new Vertex(5,5,false);

        a.connect(b);
        a.connect(c);

        System.out.println("Position of A: " + a.getX() + ", " + a.getY());
        System.out.println("Is A visible: " + a.isVisible());
        System.out.println("Cost of A: " + a.getCost());
        System.out.println("Is A visited: " + a.isVisited());
        System.out.println("Distance between A and B should be 5: " + a.distance(b));
        System.out.println("Num of neighbors " + a.numNeighbors());
        System.out.println("To string method:\n" + a.toString());
        System.out.println("Match position: " + matchPosition(a, b));

    }
}