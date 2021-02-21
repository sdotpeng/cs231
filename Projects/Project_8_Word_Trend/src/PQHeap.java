import java.util.Comparator;

/**
 * Project 8 - CS231, Colby College
 *
 * @file PQHeap.java
 * @author Ricky Peng
 * @date 2020-11-07
 */

public class PQHeap<T> {

    private Object[] heap;
    private int size;
    private Comparator<T> comparator;

    private final static int INIT_SIZE = 5;

    /**
     * Default constructor
     *
     * @param comparator comparator
     */
    public PQHeap(Comparator<T> comparator) {
        this.heap = new Object[INIT_SIZE];
        this.size = 0;
        this.comparator = comparator;
    }

    /**
     * @return the size of the heap
     */
    public int size() {
        return this.size;
    }

    /**
     * Adds the value to the heap and increments the size
     *
     * @param element new added element
     */
    public void add(T element) {
        heap[size] = element;
        size++;

        if (heap.length >= (size - 2)) {
            this.resize(size * 2);
        }

        int current = size - 1;

        while (comparator.compare((T) heap[current], (T) heap[parent(current)]) > 0) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    /**
     * Clears the heap and sets it to the empty heap
     */
    public void clear() {
        this.heap = new Object[INIT_SIZE];
        this.size = 0;
    }

    /**
     * @param position given position
     * @return the parent index from a child index
     */
    public int parent(int position) {
        return position / 2;
    }

    /**
     * @param position given position
     * @return left child index from a parent index
     */
    public int leftChild(int position) {
        return (2 * position) + 1;
    }

    /**
     * @param position given position
     * @return right child index from a parent index
     */
    private int rightChild(int position) {
        return (2 * position) + 2;
    }

    /**
     * Swaps the first position and the second position
     *
     * @param i first position
     * @param j second position
     */
    private void swap(int i, int j) {
        Object temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * @param position given position
     * @return true if it is a leaf at a given position
     */
    private boolean isLeaf(int position) {
        return position >= (size / 2) && position <= size;
    }


    /**
     * @return top priority object
     */
    public T peek() {
        return (T) heap[0];
    }

    /**
     * Removes the highest priority element from the heap
     *
     * @return the highest priority element from the heap
     */
    public T remove() {
        if (size == 0) return null;
        Object popped = heap[0];
        size--;
        heap[0] = heap[size];
        maxHeapify(0);
        return (T) popped;
    }

    /**
     * Resize the heap when capacity meets the limit
     *
     * @param capacity new capacity
     */
    private void resize(int capacity) {
        Object[] newHeap = new Object[capacity];
        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }

    

    

    

    /**
     * Rrders the element according to the priority
     *
     * @param position given position
     */
    private void maxHeapify(int position) {
        if (isLeaf(position)) return;

        if ((comparator.compare((T) heap[position], (T) heap[leftChild(position)]) < 0) || comparator.compare((T) heap[position], (T) heap[rightChild(position)]) < 0) {
            if (comparator.compare((T) heap[leftChild(position)], (T) heap[rightChild(position)]) > 0) {
                swap(position, leftChild(position));
                maxHeapify(leftChild(position));
            } else {
                swap(position, rightChild(position));
                maxHeapify(rightChild(position));
            }
        }
    }

    public static void main(String[] args) {
        PQHeap<Integer> pq = new PQHeap<>(new TestIntComparator());
        for (int i = 10; i <= 100; i += 10) {
            pq.add(i);
        }
        Integer itg = null;
        Integer hmm = 100;
        while (true) {
            itg = pq.remove();
            if (itg == null)
                break;
            System.out.println("Removing a number, it should be " + hmm + " and it is " + itg);
            hmm -= 10;
        }

        int[] test = {20, 10, 40, 90, 50, 100, 30, 60, 70, 80};
        pq = new PQHeap<Integer>(new TestIntComparator());
        for (int i = 0; i < test.length; i++)
            pq.add(test[i]);
        hmm = 100;
        while (true) {
            itg = pq.remove();
            if (itg == null)
                break;
            System.out.println("Removing a number, it should be " + hmm + " and it is " + itg);
            hmm -= 10;
        }

        // Here begins the test code involving KeyValue pairs. You can begin with it commented out,
        // if you want to.
        PQHeap<KeyValuePair<Integer, Float>> ppq = new PQHeap<>(new KVTestComparator());
        ppq.add(new KeyValuePair<>(1, 2.0f));
        ppq.add(new KeyValuePair<>(3, 1.0f));
        ppq.add(new KeyValuePair<>(4, 0.0f));
        KeyValuePair<Integer, Float> pair = ppq.remove();
        System.out.println("Removing a pair, which should be (4, 0.0) and is " + pair);
        pair = ppq.remove();
        System.out.println("Removing a pair, which should be (3, 1.0) and is " + pair);
        pair = ppq.remove();
        System.out.println("Removing a pair, which should be (1, 2.0) and is " + pair);
        pair = ppq.remove();
        System.out.println("Removing a pair, which should be null and is " + pair);

    }
}

class TestIntComparator implements Comparator<Integer> {
    public TestIntComparator() {
        ;
    }

    public int compare(Integer o1, Integer o2) {
        return o1 - o2;
    }
}

class KVTestComparator implements Comparator<KeyValuePair<Integer, Float>> {
    public int compare(KeyValuePair<Integer, Float> i1, KeyValuePair<Integer, Float> i2) {
        // returns negative number if i2 comes after i1 lexicographically
        float diff = i1.getValue() - i2.getValue();
        if (diff == 0.0)
            return 0;
        if (diff < 0.0)
            return 1;
        else
            return -1;
    }
}