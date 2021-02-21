import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Project 9 - CS231, Colby College
 *
 * @author Ricky Peng
 * @file Graph.java
 * @date 2020-12-01
 */

public class Graph {

    private ArrayList<Vertex> vertices;

    /**
     * Default Constructor
     */
    public Graph() {
        this.vertices = new ArrayList<>();
    }

    /**
     * @return number of vertices in the graph
     */
    public int vertexCount() {
        return vertices.size();
    }

    /**
     * @param query query Vertex
     * @return true if the query Vertex is in the graph's vertex list
     */
    public boolean inGraph(Vertex query) {
        return this.vertices.contains(query);
    }

    /**
     * Adds v1 and v2 to the graph (if necessary) and adds an edge connecting v1 to v2, creating a uni-directional link
     * @param v1 vertex 1
     * @param v2 vertex 2
     */
    public void addUniEdge(Vertex v1, Vertex v2) {
        if (!this.inGraph(v1)) {
            this.vertices.add(v1);
        }
        if (!this.inGraph(v2)) {
            this.vertices.add(v2);
        }
        v1.connect(v2);
    }

    /**
     * Adds v1 and v2 to the graph (if necessary), adds an edge connecting v1 to v2, and adds a second edge connecting v2 to v1
     * @param v1 vertex 1
     * @param v2 vertex 2
     */
    public void addBiEdge(Vertex v1, Vertex v2) {
        if (!this.inGraph(v1)) {
            this.vertices.add(v1);
        }
        if (!this.inGraph(v2)) {
            this.vertices.add(v2);
        }
        v1.connect(v2);
        v2.connect(v1);
    }

    /**
     * Implements Dijkstra's algorithm to find the shortest path
     * @param v0 Vertex
     */
    public void shortestPath(Vertex v0) {
        // Initialize
        for (Vertex v : vertices) {
            v.setVisited(false);
            v.setParent(null);
            v.setCost(Integer.MAX_VALUE);
        }

        // Using Priority Queue
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>();
        v0.setCost(0);
        priorityQueue.add(v0);

        while (!priorityQueue.isEmpty()) {

            // remove v from pq where v is the vertex with lowest cost
            Vertex v = priorityQueue.remove();

            // if v is already marked as visited, continue
            if (v.isVisited()) {
                continue;
            }
            // mark v as visited
            v.setVisited(true);

            for (Vertex neighbor : v.getNeighbors()) {
                double distance = v.distance(neighbor);
                if ((!neighbor.isVisited()) && ((v.getCost() + distance) < neighbor.getCost())) {
                    neighbor.setCost(v.getCost() + distance);
                    neighbor.setParent(v);
                    priorityQueue.add(neighbor);
                }
            }
        }
    }

    /**
     * @return all Vertices in a ArrayList
     */
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }

    public static void main(String[] args) {

        Graph graph = new Graph();

        // Create 10 vertices
        Vertex v1 = new Vertex(0, 0);
        Vertex v2 = new Vertex(0, 1);
        Vertex v3 = new Vertex(0, 2);
        Vertex v4 = new Vertex(0, 3);
        Vertex v5 = new Vertex(1, 0);
        Vertex v6 = new Vertex(1, 1);
        Vertex v7 = new Vertex(1, 2);
        Vertex v8 = new Vertex(2, 0);
        Vertex v9 = new Vertex(2, 1);
        Vertex v10 = new Vertex(3, 0);

        // Connect the BiEdge
        graph.addBiEdge(v1, v2);
        graph.addBiEdge(v2, v3);
        graph.addBiEdge(v3, v4);
        graph.addBiEdge(v1, v5);
        graph.addBiEdge(v2, v6);
        graph.addBiEdge(v3, v7);
        graph.addBiEdge(v5, v6);
        graph.addBiEdge(v6, v7);
        graph.addBiEdge(v5, v8);
        graph.addBiEdge(v6, v9);
        graph.addBiEdge(v8, v9);
        graph.addBiEdge(v8, v10);

        ArrayList<Vertex> vertices = graph.getVertices();
        System.out.println(">> Before shortestPath");
        for (Vertex v : vertices) {
            System.out.println(v);
            System.out.println();
        }

        System.out.println(">> Set Root (0,0)");
        graph.shortestPath(v1);
        System.out.println(">> After Dijkstra's");
        for (Vertex v : vertices) {
            System.out.println(v);
            System.out.println();
        }

        System.out.println(">> Set Root (2,1)");
        graph.shortestPath(v9);
        System.out.println(">> After Dijkstra's");
        for (Vertex v : vertices) {
            System.out.println(v);
            System.out.println();
        }
    }
}

