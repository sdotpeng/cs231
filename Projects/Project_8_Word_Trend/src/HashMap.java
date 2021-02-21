import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Project 7 - CS231, Colby College
 *
 * @file HashMap.java
 * @author Ricky Peng
 * @date 2020-11-02
 */

public class HashMap<K, V> implements MapSet<K, V> {

    private final static int INIT_SIZE = 100;
    private Object[] hashTable;
    private Comparator<K> comparator;
    private int collisions;
    private int size;

    /**
     * Constructor
     * @param comparator input comp
     */
    public HashMap(Comparator<K> comparator) {
        this.comparator = comparator;
        this.size = 0;
        this.hashTable = new Object[INIT_SIZE];
        this.collisions = 0;
    }

    /**
     * Constructor
     * @param comparator input comp
     * @param capacity given capacity
     */
    public HashMap(Comparator<K> comparator, int capacity) {
        this.comparator = comparator;
        this.size = 0;
        this.hashTable = new Object[capacity];
        this.collisions = 0;
    }

    /**
     * Put new Key and Value
     * @param new_key new key
     * @param new_value new value
     * @return value
     */
    @Override
    public V put(K new_key, V new_value) {
        int index = Math.abs(new_key.hashCode()) % hashTable.length;

        if (hashTable[index] == null) {
            BSTMap<K, V> mapAtIndex = new BSTMap<>(comparator);
            mapAtIndex.put(new_key, new_value);
            hashTable[index] = mapAtIndex;
            size++;
            return null;
        } else {
            collisions++;
            BSTMap<K, V> mapAtIndex = (BSTMap<K, V>) hashTable[index];
            size++;
            return mapAtIndex.put(new_key, new_value);
        }
    }

    /**
     * @param key input key
     * @return true if key exists
     */
    @Override
    public boolean containsKey(K key) {
        int index = Math.abs(key.hashCode()) % hashTable.length;
        if (hashTable[index] != null) {
            BSTMap<K, V> mapAtIndex = (BSTMap<K, V>) hashTable[index];
            return mapAtIndex.containsKey(key);
        }
        return false;

    }

    /**
     * @param key input key
     * @return value of that key
     */
    @Override
    public V get(K key) {
        int index = Math.abs(key.hashCode()) % hashTable.length;
        if (hashTable[index] != null) {
            BSTMap<K, V> mapAtIndex = (BSTMap<K, V>) hashTable[index];
            return mapAtIndex.get(key);
        }
        return null;
    }

    /**
     * @return all keys
     */
    @Override
    public ArrayList<K> keySet() {
        ArrayList<K> keys = new ArrayList<>();
        for (Object o : hashTable) {
            if (o != null) {
                keys.addAll(((BSTMap<K, V>) o).keySet());
            }
        }
        return keys;

    }

    /**
     * @return all values
     */
    @Override
    public ArrayList<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for (Object o : hashTable) {
            if (o != null) {
                BSTMap<K, V> mapAtIndex = (BSTMap<K, V>) o;
                values.addAll(mapAtIndex.values());
            }
        }
        return values;
    }

    /**
     * @return all entries
     */
    @Override
    public ArrayList<KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K, V>> entries = new ArrayList<>();
        for (Object o : hashTable) {
            if (o != null) {
                BSTMap<K, V> mapAtIndex = (BSTMap<K, V>) o;
                entries.addAll(mapAtIndex.entrySet());
            }
        }
        return entries;

    }

    /**
     * @return size of the map
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Clears everything
     */
    @Override
    public void clear() {
        Arrays.fill(hashTable, null);
        this.size = 0;
    }

    /**
     * @return number of collisions
     */
    public int getCollisions() {
        return collisions;
    }

    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap<>(new AscendingString());
        hashMap.put("one", 1);
        hashMap.put("two", 2);
        hashMap.put("three", 3);
        hashMap.put("four", 4);
        hashMap.put("five", 5);
        hashMap.put("six", 6);
        hashMap.put("seven", 7);
        hashMap.put("eight", 8);
        hashMap.put("nine", 9);
        System.out.println("HashMap has size of : " + hashMap.size());
        System.out.println("HashMap contains key \"nine\": " +hashMap.containsKey("nine"));
        System.out.println("Get value of four: " + hashMap.get("four"));
        System.out.println("HashMap's keys are: " + hashMap.keySet());
        System.out.println("Hashmap's values are: " + hashMap.values());
        System.out.println("HashMap's entries are: " + hashMap.entrySet());
        System.out.println("There's collisions of: " + hashMap.getCollisions());
        hashMap.clear();
        System.out.println("HashMap has size after clear: " + hashMap.size());
    }

}