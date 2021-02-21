/**
 * Project 6 - CS231, Colby College
 *
 * @file BSTMap.java
 * @author Ricky Peng
 * @date 2020-10-20
 */

import java.util.ArrayList;
import java.util.Comparator;

public class BSTMap<K, V> implements MapSet<K, V> {

    TNode root;
    Comparator<K> comparator;
    int size;

    /**
     * Constructor, takes in a Comparator object
     *
     * @param comp a comparator
     */
    public BSTMap(Comparator<K> comp) {
        this.comparator = comp;
    }

    /***************************************************/
    /*                    Quiz 1                       */
    /***************************************************/

    /**
     * Get depth of the key
     * @param key key value
     * @return depth at that key
     */
    public int depth(K key) {
        return root.depth(key);
    }


    /***************************************************/
    /*                    Quiz 2                       */
    /***************************************************/

    /**
     * @return true if is a BST
     */
    public boolean isBST() {
        return root.isBST();
    }

    /***************************************************/
    /*                    Quiz 4                       */
    /***************************************************/

    /**
     * @return number of nodes
     */
    public int numNodes() {
        ArrayList<KeyValuePair<K, V>> entrySet = this.entrySet();
        return entrySet.size();
    }

    /**
     * @param key key
     * @return true if the tree contains the given key
     */
    public boolean containsKey(K key) {
        if (root == null)
            return false;
        return root.containsKey(key, comparator);
    }

    /**
     * @return ArrayList of all keys
     */
    public ArrayList<K> keySet() {
        ArrayList<K> keyList = new ArrayList<>();
        if (root != null) {
            root.keySet(keyList);
        }
        return keyList;
    }

    /**
     * @return ArrayList of all values
     */
    public ArrayList<V> values() {
        ArrayList<V> valueList = new ArrayList<>();
        if (root != null) {
            root.values(valueList);
        }
        return valueList;

    }

    /***************************************************/
    /*                    Quiz 4                       */
    /***************************************************/

    /**
     * @return ArrayList of key and value pair
     */
    public ArrayList<KeyValuePair<K, V>> entrySet() {
        ArrayList<KeyValuePair<K, V>> keyValue = new ArrayList<>();
        if (root != null) {
            root.entrySet(keyValue);
        }
        return keyValue;
    }

    /**
     * @return number of keys in the free
     */
    public int size() {
        if (root == null) {
            return 0;
        } else {
            return sizeNode(root);
        }
    }

    /**
     * @param node given node
     * @return size of the node
     */
    private int sizeNode(TNode node) {
        if (node == null) {
            return 0;
        } else {
            return (1 + sizeNode(node.getLeft()) + sizeNode(node.getRight()));
        }
    }

    /**
     * Clear the Tree
     */
    public void clear() {
        size = 0;
        root = null;
    }

    /**
     * Put one key and value here
     * @param key key
     * @param value vale
     * @return V
     */
    public V put(K key, V value) {
        if (this.root == null) {
            this.root = new TNode(key, value);
            this.size++;
            return null;
        } else {
            this.size++;
            return this.root.put(key, value, this.comparator);
        }
    }

    /**
     * Gets the value at the specified key or null
     * @param key input key
     * @return value of the key
     */
    public V get(K key) {
        if (this.root == null) {
            return null;
        }
        return this.root.get(key, comparator);
    }

    private class TNode {

        TNode left, right;
        KeyValuePair<K, V> kvp;

        /**
         * Constructor
         * @param k Key
         * @param v Value
         */
        public TNode(K k, V v) {
            left = null;
            right = null;
            kvp = new KeyValuePair<>(k, v);
        }

        /***************************************************/
        /*                    Quiz 1                       */
        /***************************************************/

        /**
         * Get depth of the node
         * @param k key
         * @return depth of the node
         */
        public int depth(K k) {
            return depthRecursive(k, 1);
        }

        /***************************************************/
        /*                    Quiz 1                       */
        /***************************************************/

        /**
         * Get depth of the node
         * @param k key
         * @param depth current depth
         * @return depth
         */
        public int depthRecursive(K k, int depth) {
            if (kvp == null)
                return 0;

            if (kvp.getKey() == k)
                return depth;

            if (this.left == null) {
                if (this.right == null) {
                    return 0;
                } else {
                    return this.right.depthRecursive(k, depth + 1);
                }
            } else {
                return this.left.depthRecursive(k, depth + 1);
            }

        }

        /***************************************************/
        /*                    Quiz 2                       */
        /***************************************************/

        /**
         * @return true if BST
         */
        public boolean isBST() {
            return isBSTRecursive(kvp);
        }

        /***************************************************/
        /*                    Quiz 2                       */
        /***************************************************/

        /**
         * @param pair key value pair
         * @return true if BST
         */
        public boolean isBSTRecursive(KeyValuePair pair) {
            if (pair == null) return true;
            if (left == null && right == null) return true;
            if ((left != null) && (right != null)) {
                return (this.isBSTRecursive(left.kvp) && this.isBSTRecursive(right.kvp));
            }
            return false;
        }

        /**
         * Adds or updates a key-value pair
         * @param key key
         * @param value value
         * @param comp comparator
         * @return V
         */
        public V put(K key, V value, Comparator comp) {
            // implement the binary search tree put
            int compare = comp.compare(key, this.kvp.getKey());
            if (compare == 0) {
                V returnVal = this.kvp.getValue();
                this.kvp.setValue(value);
                return returnVal;
            } else if (compare < 0) {
                if (this.left == null) {
                    this.left = new TNode(key, value);
                    return null;
                }
                return this.left.put(key, value, comp);
            } else {
                if (this.right == null) {
                    this.right = new TNode(key, value);
                    return null;
                }
                return this.right.put(key, value, comp);
            }
        }

        /**
         * Takes in a key and a comparator, and returns the value associated with the key or null
         * @param key key
         * @param comp comparator
         * @return value
         */
        public V get(K key, Comparator comp) {
            int compare = comp.compare(key, this.kvp.getKey());
            if (compare == 0) {
                return this.kvp.getValue();
            } else if (compare < 0) {
                if (this.left == null) {
                    return null;
                }
                return this.left.get(key, comp);
            } else {
                if (this.right == null) {
                    return null;
                }
                return this.right.get(key, comp);
            }
        }

        /**
         * @return left node
         */
        public TNode getLeft() {
            return this.left;
        }

        /**
         * @return right node
         */
        public TNode getRight() {
            return this.right;
        }

        /**
         * Returns true if the map contains a key-value pair with the given key
         * @param key key
         * @param comp comparator
         * @return true if map contains
         */
        public boolean containsKey(K key, Comparator comp) {
            int compare = comp.compare(key, this.kvp.getKey());
            if (compare == 0) {
                return true;
            } else if (compare < 0) {
                if (this.left == null) {
                    return false;
                }
                return this.left.containsKey(key, comp);
            } else {
                if (this.right == null) {
                    return false;
                }
                return this.right.containsKey(key, comp);
            }
        }

        /***************************************************/
        /*                    Quiz 4                       */
        /***************************************************/

        /**
         * Adds the key value pair to the arraylist
         */
        public void entrySet(ArrayList<KeyValuePair<K, V>> keyValueList) {
            keyValueList.add(this.kvp);
            if (this.left != null)
                this.left.entrySet(keyValueList);
            if (this.right != null)
                this.right.entrySet(keyValueList);
        }

        /**
         * Adds the keys to the arraylist
         * @param keyList key List
         */
        public void keySet(ArrayList<K> keyList) {
            keyList.add(this.kvp.getKey());
            if (this.left != null)
                this.left.keySet(keyList);
            if (this.right != null)
                this.right.keySet(keyList);
        }

        /**
         * Adds the values to the arraylist
         */
        public void values(ArrayList<V> valueList) {
            valueList.add(this.kvp.getValue());
            if (this.left != null)
                this.left.values(valueList);
            if (this.right != null)
                this.right.values(valueList);
        }

    }

    public static void main(String[] argv) {

        BSTMap<String, Integer> bst = new BSTMap<>(new AscendingString());

        bst.put("one", 1);
        bst.put("two", 2);
        bst.put("three", 3);
        bst.put("four", 4);
        bst.put("five", 5);
        bst.put("six", 6);
        bst.put("seven", 7);
        bst.put("eight", 8);
        bst.put("nine", 9);


        // KeyValuePair pair = new KeyValuePair("twenty", 20);
        System.out.println("contains 2? " + bst.containsKey("two"));

        System.out.println(bst.toString());
        // System.out.println( "isBST()? : " + bst.isBST() );


        // System.out.println( bst.get( "eleven" ) );
        // System.out.println( bst.get( "ten" ) );
        // System.out.println( bst.get( "twenty" ) );
        // System.out.println( bst.get( "six" ) );
        // System.out.println( bst.get( "one" ) );
        // System.out.println("depth: "+bst.depth("eleven"));

        System.out.println(bst.entrySet());
        // System.out.println( bst.keySet());
        // System.out.println( bst.values());


        // System.out.println( bst.containsKey("one") );
        // System.out.println( bst.containsKey("eleven") );

        // System.out.println( "Should return 5: " + bst.size() );

        // bst.clear();
        // System.out.println( bst.get( "eleven" ) );
        // System.out.println( bst.get( "ten" ) );
        // System.out.println( bst.get( "twenty" ) );
        // System.out.println( bst.get( "six" ) );
        // System.out.println( bst.get( "one" ) );
        // System.out.println( "Should return 0: " + bst.size() );

        // bst.put( "eleven", 11 );
        // System.out.println( bst.get( "eleven" ) );
        // System.out.println( "Should return 1: " + bst.size() );

        // System.out.println( bst.entrySet());


    }

}
