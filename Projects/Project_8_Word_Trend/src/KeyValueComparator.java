import java.util.Comparator;

public class KeyValueComparator implements Comparator<KeyValuePair<String, Integer>> {

    /**
     * Compares the value of pair1 and pair 2
     *
     * @param pair1 first pair
     * @param pair2 second pair
     */
    public int compare(KeyValuePair<String, Integer> pair1, KeyValuePair<String, Integer> pair2) {
        return pair1.getValue() - pair2.getValue();
    }
}