/**
 * Interface for a data structure that maps a key to a value, like a
 * Python dictionary would.  This is a subset of the Java Map interface.
 *
 * @author srtaylor, bmaxwell
 */
import java.util.ArrayList;

public interface MapSet<K,V> {

    // adds or updates a key-value pair
    // If there is already a piar with new_key in the map, then update
    // the pair's value to new_value.
    // If there is not already a pair with new_key, then
    // add pair with new_key and new_value.
    // returns the old value or null if no old value existed
    public V put( K new_key, V new_value );

    // Returns true if the map contains a key-value pair with the given key
    public boolean containsKey( K key );

    // Returns the value associated with the given key.
    // If that key is not in the map, then it returns null.
    public V get( K key );

    // Returns an ArrayList of all the keys in the map. There is no
    // defined order for the keys.
    public ArrayList<K> keySet();

    // Returns an ArrayList of all the values in the map. These should
    // be in the same order as the keySet.
    public ArrayList<V> values();

    // return an ArrayList of pairs.
    public ArrayList<KeyValuePair<K,V>> entrySet();

    // Returns the number of key-value pairs in the map.
    public int size();

    // removes all mappings from this MapSet
    public void clear();
}
