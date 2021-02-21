import java.util.*;

/**
 * Project1: Blackjack - CS231, Colby College
 * A simple ArrayList I implemented by using Java default Array class. Implements Iterable so can be used in foreach
 * loop, performs essential function as similar to default ArrayList, including dynamically growing (automatically
 * extendable) size, iterator, element getter/setter, and other features
 *
 * @file ImplementedArrayList.java
 * @author Siyuan Peng
 * @date 2020-09-07
 */

public class ImplementedArrayList<E> implements Iterable<E> {

    private final static int DEFAULT_SIZE = 10;
    private Object[] data;
    private int size = 0;

    /**
     * Default constructor that instantiates an ArrayList with size of DEFAULT_SIZE
     */
    public ImplementedArrayList() {
        this(DEFAULT_SIZE);
    }

    /**
     * Size-customized constructor
     * @param size user-input initial size of ArrayList
     */
    public ImplementedArrayList(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Minimum size is constraint to " + this.size);
        } else {
            data = new Object[size];
        }
    }

    /**
     * Self-defined iterator that helps my ImplementedArrayList with foreach loop that I often use
     * @return
     */
    public Iterator<E> iterator() {

        return new Iterator<>() {

            private int nextPos = 0;

            /**
             * @return false if no more next element, true if there is
             */
            @Override
            public boolean hasNext() {
                return nextPos < size;
            }

            /**
             * return next element in the ArrayList
             * @return next element
             * @throws NoSuchElementException when there's no next element
             */
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("");
                }
                return (E) data[nextPos++];
            }

            /**
             * removal not allowed in this case
             * @throws UnsupportedOperationException when trying to remove an element when iterating
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("[WARNING] Removal not allowed.");
            }
        };
    }

    /**
     * add element to the ImplementedArrayList instance
     * @param e an Object
     */
    public void add(E e) {
        // check if the size suits the need
        isSizeEnough(size + 1);
        data[size++] = e;
    }

    /**
     * check if the size of current Array fits the need when adding an element
     * @param intendedSize new size
     */
    private void isSizeEnough(int intendedSize) {
        // if more spaces are needed, extendArray until size is enough
        if (intendedSize > DEFAULT_SIZE) {
            extendArray(intendedSize);
        }
    }

    /**
     * extend the array length when size is not enough, adding to the length by 5 at a time
     * @param intendedSize new size of array
     */
    private void extendArray(int intendedSize) {
        // first add 5 to new length
        int extendedLength = data.length + 5;

        // if not enough, use the given new size directly
        if (extendedLength - intendedSize < 0) {
            extendedLength = intendedSize;
        }

        // extend the array
        data = Arrays.copyOf(data, extendedLength);
    }

    /**
     * check the range for operations including getter, setter, and removal
     * @param index index of element given
     * @throws IndexOutOfBoundsException when index given is out of bound
     */
    private void checkRange(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
    }

    /**
     * getter for this ArrayList
     * @param index index of element
     * @return element at that index
     */
    public E get(int index) {
        // always check range
        checkRange(index);
        return (E) data[index];
    }

    /**
     * setter for this ArrayList
     * @param index index of element that wants to be modified
     * @param element new element that is about to replace the old element at givn index
     */
    public void set(int index, E element) {
        checkRange(index);
        data[index] = element;
    }

    /**
     * @return size of the ArrayList
     */
    public int size() {
        return this.size;
    }

    /**
     * remove one element at the given index
     * @param index of the lement
     * @return element that is removed
     */
    public E remove(int index) {
        checkRange(index);
        E object = get(index);
        // calculate the length of array that is gonna be moved
        int moveSize = size - index - 1;
        // use arraycopy method to generate new array that remove the element at given index
        if (moveSize > 0) {
            System.arraycopy(data,index + 1, data, index,size - index - 1);
        }
        // null the last element as one element is removed, thus length of array decreases
        data[--size] = null;
        return object;
    }

    /**
     * clear the ArrayList
     */
    public void clear() {
        for (int end = size, i = size = 0; i < end; i++) {
            data[i] = null;
        }
    }

    /**
     * give an representation of all elements
     * @return message
     */
    public String toString() {
        StringBuilder outString = new StringBuilder("[ ");
        for (int i = 0; i < size; i++) {
            outString.append(data[i]).append(" ");
        }
        outString.append("]");

        return outString.toString();
    }

    /**
     * method for testing the class
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println(">> Testing ImplementedArrayList class...");
        ImplementedArrayList<String> testArr = new ImplementedArrayList<>();
        testArr.add("111");
        testArr.add("222");
        System.out.println(">> Get size: " + testArr.size());
        System.out.println(">> Get element at index 1: " + testArr.get(0));
        testArr.add("333");
        System.out.println(">> Remove element at index 2: " + testArr.remove(2));
        testArr.set(1, "444");
        System.out.println(">> Set element at index 1: " + testArr.get(1));
        testArr.clear();
        System.out.println(">> Get size: " + testArr.size());
        System.out.println(">> Putting elements...");
        for (int i = 0; i < 10; i++) {
            testArr.add(Double.toString(Math.pow(2, i)));
        }
        System.out.println(">> Print toString: " + testArr.toString());
        for (String ele : testArr) {
            System.out.println(ele);
        }
    }


}
