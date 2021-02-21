import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Project 7 - CS231, Colby College
 *
 * @author Ricky Peng
 * @file WordCounter2.java
 * @date 2020-11-02
 */

public class WordCounter2 {

    private MapSet<String, Integer> map;
    private int totalCount;
    private final static Comparator<String> COMPARATOR = new AscendingString();

    /**
     * Default Constructor
     *
     * @param dataStructure name of the data strcuture
     */
    public WordCounter2(String dataStructure) {
        if (dataStructure.equals("bst")) {
            map = new BSTMap<>(COMPARATOR);
        } else if (dataStructure.equals("hashmap")) {
            map = new HashMap<>(COMPARATOR);
        } else {
            System.out.println("WordCounter2():: Unrecognizable data structure");
        }
    }

    /**
     * Given the filename of a text file, read the text file and return an ArrayList list of all the words in the file.
     *
     * @param fileName fileName of the text file
     * @return ArrayList containing all words
     */
    public ArrayList<String> readWords(String fileName) {
        ArrayList<String> words = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String[] wordList;
            while (line != null) {
                wordList = line.split("[^A-Za-z0-9']");
                for (String s : wordList) {
                    String word = s.trim().toLowerCase();
                    if (word.length() == 0) continue;
                    words.add(word);
                    this.totalCount++;
                }
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
            return words;
        } catch (FileNotFoundException ex) {
            System.out.println("readWords():: File Not Found");
        } catch (IOException ex) {
            System.out.println("readWords():: IO Error");
        }

        return null;
    }

    /**
     * Analyze the file
     * @param filename fileName
     */
    public void analyze(String filename) {
        try {
            FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while (true) {
                String res = bufferedReader.readLine();
                if (res == null) break;
                String[] words = res.split("[^A-Za-z0-9']");
                for (String s : words) {
                    String word = s.trim().toLowerCase();
                    if (word.length() == 0) continue;
                    Integer tentative = this.map.get(word);
                    if (tentative != null) {
                        this.map.put(word, tentative + 1);
                    } else {
                        this.map.put(word, 1);
                    }
                    totalCount++;
                }
            }
            bufferedReader.close();
            fileReader.close();

        } catch (FileNotFoundException ex) {
            System.out.println("analyze(): File Not Found - " + filename);
        } catch (IOException ex) {
            System.out.println("analyze(): IO Error - " + filename);
        }
    }

    /**
     * @return map
     */
    public MapSet<String, Integer> getMap() {
        return this.map;
    }

    /**
     * @param words ArrayList of words
     * @return time taken to build the map from the words
     */
    public double buildMap(ArrayList<String> words) {
        double start = System.currentTimeMillis();
        for (String word : words) {
            Integer frequency = map.get(word);
            if (frequency == null) {
                frequency = 1;
            } else {
                frequency++;
            }
            map.put(word, frequency);
        }
        double end = System.currentTimeMillis();
        return (end - start);
    }

    /**
     * clear the map data structure
     */
    public void clearMap() {
        map.clear();
        totalCount = 0;
    }

    /**
     * @return total word count
     */
    public int totalWordCount() {
        return totalCount;
    }

    /**
     * @return unique word count
     */
    public int uniqueWordCount() {
        return map.size();
    }

    /**
     * @param word input word
     * @return the count of this word in the map
     */
    public int getCount(String word) {
        return map.get(word);
    }

    /**
     * @param word input word
     * @return the frequency of this word in the map
     */
    public double getFrequency(String word) {
        return (double) getCount(word) / totalWordCount();
    }

    /**
     * Write the word count to a file
     *
     * @param fileName name of the file
     * @return true if successful
     */
    public boolean writeWordCount(String fileName) {
        try {
            FileWriter fileWriter = new FileWriter(fileName);
            fileWriter.write("totalWordCount: " + this.totalCount + "\n");

            ArrayList<KeyValuePair<String, Integer>> keyValueList = this.map.entrySet();

            for (KeyValuePair<String, Integer> pair : keyValueList) {
                fileWriter.write(pair + " \n");
            }
            fileWriter.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("writeWordCount():: File Not Fount - " + fileName);
        } catch (IOException ex) {
            System.out.println("writeWordCount():: IO Error - " + fileName);
        }
        return false;
    }

    /**
     * Regenerate the tree using a word count file
     *
     * @param fileName name of the file
     * @return true if successful
     */
    public boolean readWordCount(String fileName) {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            int count = 0;
            while (true) {
                String line = bufferedReader.readLine();

                count++;
                if (count == 1) continue;
                if (line == null) break;
                else {
                    String[] pair = line.split("\\s+");
                    if (!map.containsKey(pair[0])) {
                        map.put(pair[0], Integer.parseInt(pair[1]));
                    }
                }
            }
            bufferedReader.close();
            fileReader.close();
            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("readWordCountFile():: File Not Found - " + fileName);
        } catch (IOException ex) {
            System.out.println("readWordCountFile():: IO Error - " + fileName);
        }
        return false;
    }

    public static void main(String[] args) {
        /*WordCounter2 wc = new WordCounter2("bst");
        wc.buildMap(wc.readWords("./resources/reddit_comments_2009.txt"));
        if (wc.map instanceof BSTMap) {
            System.out.println("Collisions " + ((BSTMap) wc.map).getHeight());
        }*/

        for (int i = 2008; i < 2016; i++) {
            System.out.println("Year: " + i);
            WordCounter2 wordCounter2 = new WordCounter2("hashmap");
            ArrayList<String> words = wordCounter2.readWords("./resources/reddit_comments_" + String.valueOf(i) + ".txt");
            double total = 0;
//            for (int j = 0; j < 2; j++) {
//                double time = wordCounter2.buildMap(words);
//                wordCounter2.clearMap();
//                total += time;
//            }
            double time = wordCounter2.buildMap(words);
            System.out.println("Time: " + time + " Total: " + wordCounter2.totalWordCount() + " Unique: " + wordCounter2.uniqueWordCount());
            System.out.println();
        }
    }
}
