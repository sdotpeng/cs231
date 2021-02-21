/**
 * Project 6 - CS231, Colby College
 *
 * @author Ricky Peng
 * @file WCTester.java
 * @date 2020-10-20
 */

public class WCTester {
    public static void main(String[] argv) {
        WordCounter wc = new WordCounter();
        wc.analyze("counttest.txt");
        wc.writeWordCountFile("counts_ct.txt");
        wc.readWordCountFile("counts_ct.txt");
        wc.writeWordCountFile("counts_ct_v2.txt");
    }
}