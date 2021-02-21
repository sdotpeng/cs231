public class KeyValuePair<K, V> {

    private K k;
    private V v;

    /**
     * Default constructor for
     * @param k Key value
     * @param v Value value
     */
    public KeyValuePair(K k, V v) {
        this.k = k;
        this.v = v;
    }

    /**
     * @return the Key
     */
    public K getKey() {
        return this.k;
    }

    /**
     * @return the Value
     */
    public V getValue() {
        return this.v;
    }

    /**
     * Set new value of V
     * @param v new Value
     */
    public void setValue(V v) {
        this.v = v;
    }

    /**
     * @return a String containing both the ket and the value
     */
    public String toString() {
        return k.toString() + ": " + v.toString();
    }

    public static void main(String[] args){
        KeyValuePair<String, Integer> testPair = new KeyValuePair<>("Test", 10);
        System.out.println(testPair);
        testPair.setValue(20);
        System.out.println(testPair);
        System.out.println("Key: " + testPair.getKey() + ", Value: " + testPair.getValue());
    }
}
