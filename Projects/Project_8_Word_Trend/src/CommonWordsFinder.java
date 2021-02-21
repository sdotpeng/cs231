import java.util.ArrayList;
import java.util.Scanner;

/**
 * Project 8 - CS231, Colby College
 *
 * @author Ricky Peng
 * @file CommonWordsFinder.java
 * @date 2020-11-07
 */

public class CommonWordsFinder {

    private PQHeap<KeyValuePair<String, Integer>> heap;
    private WordCounter2 wordCounter;

    /**
     * Default Constructor
     */
    public CommonWordsFinder() {
        this.wordCounter = new WordCounter2("hashmap");
        this.heap = new PQHeap<>(new KeyValueComparator());
    }

    /**
     * Clears the heap
     */
    public void clearHeap() {
        this.heap.clear();
    }

    /**
     * Get top element
     *
     * @return top element
     */
    public KeyValuePair<String, Integer> getTop() {
        return this.heap.remove();
    }

    /**
     * Read file, and writes n most common words
     *
     * @param n        number of words to be written
     * @param filename name of the file
     */
    public void writeCommonWords(int n, String filename) {
        this.clearHeap();
        wordCounter.getMap().clear();
        wordCounter.analyze(filename);

        for (KeyValuePair<String, Integer> kvp : wordCounter.getMap().entrySet()) {
            heap.add(kvp);
        }

        System.out.println("Most N frequent words: " + "\n");
        for (int i = 0; i < n; i++) {
            KeyValuePair<String, Integer> top = this.getTop();
            double frequency = ((double) top.getValue()) / wordCounter.totalWordCount();
            System.out.println("word: " + top.getKey() + "\n" + "frequency: " + frequency);
        }
    }

    public static void main( String[] args ) {
        CommonWordsFinder commonWordsFinder = new CommonWordsFinder();
        Scanner scan = new Scanner(System.in);
        System.out.print("How many words to find: ");
        String n = scan.next();

        ArrayList<String> files = new ArrayList<>();

        int[] years = {2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015};
        for (Integer year : years) {
            files.add("./resources/reddit_comments_" + year + ".txt");
        }

        for (String file : files) {
            System.out.println(file);
            commonWordsFinder.writeCommonWords(Integer.parseInt(n), file);
        }
    }
}
