import java.util.Comparator;

/**
 * Project 6 - CS231, Colby College
 *
 * @file AscendingString.java
 * @author Ricky Peng
 * @date 2020-10-20
 */

public class AscendingString implements Comparator<String> {

    /**
     * Compare string a and b
     * @param a String a
     * @param b String b
     * @return a.compareTo(b)
     */
    @Override
    public int compare(String a, String b) {
        return a.compareTo(b);
    }
}
