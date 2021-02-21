/**
 * Project 6 - CS231, Colby College
 *
 * @file WordCounter.java
 * @author Ricky Peng
 * @date 2020-10-20
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.ArrayList;


public class WordCounter {
    KeyValuePair<String, Integer> kvp;
    BSTMap<String, Integer> bstmap;
    int wordCount;

    /**
     * Makes an empty BSTMap and sets the total word count to zero
     */
    public WordCounter() {
        bstmap = new BSTMap<>(new AscendingString());
        wordCount = 0;
    }

    /**
     * @return BSTMap
     */
    public BSTMap<String, Integer> getMap() {
        return bstmap;
    }

    /**
     * Generates the word counts from a file of words
     * @param filename filename
     */
    public void analyze(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader buff = new BufferedReader(fr);
            String line = buff.readLine();

            while (line != null) {
                String[] words = line.split("[^a-zA-Z0-9']");
                for (String s : words) {
                    String word = s.trim().toLowerCase();
                    if (word.length() == 0) {
                        continue;
                    }
                    Integer frequency = bstmap.get(word);
                    if (frequency == null) {
                        bstmap.put(word, 1);
                    } else {
                        frequency++;
                        bstmap.put(word, frequency);
                    }
                    wordCount++;
                }
                line = buff.readLine();
            }
            buff.close();
            return;
        } catch (FileNotFoundException ex) {
            System.out.println("[WARNING]: Unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("[WARNING]: Error reading file " + filename);
        }
    }

    /**
     * @return the total word count
     */
    public int getTotalWordCount() {
        return this.wordCount;
    }

    /**
     * @return the number of unique words
     */
    public int getUniqueWordCount() {
        return this.bstmap.size();
    }

    /**
     * @param word input word
     * @return the frequency value associated with this word
     */
    public int getCount(String word) {
        if (bstmap.get(word) == null)
            return 0;
        return bstmap.get(word);
    }

    /**
     * @param word input word
     * @return frequency of the word
     */
    public double getFrequency(String word) {
        return (double) this.getCount(word) / this.wordCount;
    }

    /**
     * Writes word count file
     * @param filename filename
     */
    public void writeWordCountFile(String filename) {

        try {
            FileWriter myWriter = new FileWriter(filename);
            myWriter.write("totalWordCount : " + this.wordCount + "\n");
            ArrayList<KeyValuePair<String, Integer>> keyValueList = this.bstmap.entrySet();
            for (KeyValuePair<String, Integer> stringIntegerKeyValuePair : keyValueList) {
                myWriter.write(stringIntegerKeyValuePair + " \n");
            }
            myWriter.close();
        } catch (FileNotFoundException ex) {
            System.out.println("[WARNING]: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("[WARNING]: error reading file " + filename);
            ex.printStackTrace();
        }
    }

    /**
     * Reads the contents of a word count file
     * @param filename filename
     */
    public void readWordCountFile(String filename) {
        try {
            FileReader fr = new FileReader(filename);
            BufferedReader bfr = new BufferedReader(fr);

            int count = 0;

            while (true) {

                String res = bfr.readLine();

                // skip the first line
                count++;
                if (count == 1) continue;

                if (res == null) break;
                else {
                    String[] pair = res.split("\\s+");
                    if (!wordmap.containsKey(pair[0])) {
                        wordmap.put(pair[0], Integer.parseInt(pair[1]));
                    }
                }
            }

            bfr.close();
            fr.close();

        } catch (FileNotFoundException ex) {
            System.out.println("readWordCountFile():: unable to open file " + filename);
        } catch (IOException ex) {
            System.out.println("readWordCountFile():: error reading file " + filename);
        }
    }

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("Usage: WordCounter inputFile1.txt inputFile2.txt ...");
            return;
        }

        for (String arg : args) {
            WordCounter wordCounter = new WordCounter();
            wordCounter.getMap().clear();
            String[] files = arg.split(" ");
            String newFileName = files[0] + "_wordcount.txt";
            String newFileName2 = files[0] + "_frequentword.txt";

            double start = System.currentTimeMillis();
            wordCounter.analyze(arg);
            wordCounter.writeWordCountFile(newFileName);
            double end = System.currentTimeMillis();

            double time = end - start;
            System.out.println("Time taken to read " + arg + "is " + time);
            System.out.println("Total word count: " + wordCounter.getTotalWordCount());
            System.out.println("Unique word count: " + wordCounter.getUniqueWordCount());
        }


        // Scanner scan = new Scanner(System.in);
        // 		System.out.println("Enter the filename");
        // 		String fn = scan.next();
        // wordcounter.analyze(fn);
        // System.out.println(wordcounter.commonWords());
        // System.out.println(wordcounter.getTotalWordCount());
        // System.out.println(wordcounter.getUniqueWordCount());
        // System.out.println(wordcounter.getCount("times"));
        // System.out.println(wordcounter.getCount("of"));
        // System.out.println(wordcounter.getCount("the"));
        // System.out.println(wordcounter.getCount("it"));
        // System.out.println(wordcounter.getCount("worst"));
        // System.out.println(wordcounter.getCount("foolishness"));
        // System.out.println(wordcounter.getFrequency("foolishness"));


    }

}