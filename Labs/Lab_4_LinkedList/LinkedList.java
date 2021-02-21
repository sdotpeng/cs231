import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Project 4: Artificial Society - CS231, Colby College
 *
 * My own implemented generic LinkedList with iterator to be used in the project.
 *
 * @file LinkedList.java
 * @author Ricky Peng
 * @date 2020-09-26
 * @param <T> of any type
 */

public class LinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Default constructor, sets head and tail to null, set size to 0
     */
    public LinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Clear the LinkedList
     */
    public void clear() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * @return the size of the linkedlist
     */
    public int size() {
        return this.size;
    }

    /**
     * Add the item to the start of the list
     * @param item of one type to be added
     */
    public void addFirst(T item) {
        if (this.head == null) {
            this.head = new Node<>(item);
            this.tail = this.head;
        } else {
            Node<T> temp = new Node<>(item);
            temp.setNext(this.head);
            this.head = temp;
        }
        this.size++;
    }

    /**
     * Add the item to the end of the list
     * @param item of one type to be added
     */
    public void addLast(T item) {
        if (this.tail == null) {
            this.head = new Node<>(item);
            this.tail = this.head;
        } else {
            Node<T> temp = new Node<>(item);
            this.tail.setNext(temp);
            this.tail = temp;
        }
        this.size++;
    }

    /**
     * Add one item to a specified location in the linkedlist
     * @param index position of the added item
     * @param item of one type to be added
     */
    public void add(int index, T item) {
        // Check if index out of range
        assert index >= 0 && index <= size;

        if (index == 0) {
            // If item is to be added at the start
            addFirst(item);
        } else if (index == size) {
            // If item is to be added at the end
            addLast(item);
        } else {
            // If item is in-between
            Node<T> temp = new Node<>(item);

            Node<T> before = new Node<>(null);
            before.setNext(this.head);

            for (int i = 0; i < index; i++) {
                before = before.getNext();
            }

            Node<T> next = before.getNext();
            before.setNext(temp);
            temp.setNext(next);
            this.size++;
        }
    }

    /**
     * Remove one item from the Linkedlist
     * @param index position of the list
     */
    public void remove(int index) {
        // Check if index is out of range
        assert index >= 0 && index < size;

        if (index == 0) {
            // If the first element is to be removed
            this.head = this.head.getNext();
        } else {
            Node<T> before = new Node<>(null);
            before.setNext(this.head);

            for (int i = 0; i < index; i++) {
                before = before.getNext();
            }

            if (index == size - 1) {
                // If the last element is to be removed
                this.tail = before;
                this.tail.setNext(null);
            } else {
                // If the element is not the first nor the last one
                before.setNext(before.getNext().getNext());
            }
        }

        this.size--;
    }

    /**
     * @return an ArrayList of the list contents in order
     */
    public ArrayList<T> toArrayList() {
        ArrayList<T> array = new ArrayList<>();
        for (T element : this) {
            array.add(element);
        }
        return array;
    }

    /**
     * @return an ArrayList of the list contents in shuffled order
     */
    public ArrayList<T> toShuffledList() {
        ArrayList<T> array = this.toArrayList();
        Collections.shuffle(array);
        return array;
    }

    /**
     * See if the LinkedList contains the given object
     * @param obj given object
     * @return true if contains
     */
    public boolean contains(T obj) {
        if (size == 0) return false;
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (current.getData().equals(obj)) return true;
            current = current.getNext();
        }
        return false;
    }

    /**
     * @return a String representation of the LinkedList
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("<");
        Node<T> current = head;
        for (int i = 0; i < size; i++) {
            if (i == size - 1) stringBuilder.append(current.getData().toString());
            else stringBuilder.append(current.getData().toString()).append(", ");
            current = current.getNext();
        }
        return stringBuilder.append(">").toString();
    }

    /**
     * Replace the second value by the given newValue
     * @param newValue new given value for the second item
     */
    public void replaceSecondValue(T newValue) {
        if (size < 2) return;
        Node<T> newSecond = new Node<>(newValue);
        if (size > 2) {
            Node<T> third = head.getNext().getNext();
            newSecond.setNext(third);
        }
        head.setNext(newSecond);
    }

    /**
     * Reverse the order within the LinkedList
     */
    public void reverse() {
        if (size <= 1) return;
        Node<T> previous = null;
        Node<T> next = null;
        Node<T> current = head;
        this.tail = current;
        for (int i = 0; i < size; i++) {
            next = current.getNext();
            current.setNext(previous);
            previous = current;
            current = next;
            if (current ==  null) this.head = previous;
        }
    }

    /**
     * Iterator method
     * @return iterator for this LinkedList
     */
    public Iterator<T> iterator() {
        return new Iterator<>() {

            Node<T> next = head;

            /**
             * Check if there is next element
             * @return true if current Node points to a Node, false if points to null
             */
            @Override
            public boolean hasNext() {
                return next != null;
            }

            /**
             * @return next element
             */
            @Override
            public T next() {
                T out = next.getData();
                next = next.getNext();
                return out;
            }

            /**
             * Unsupported
             */
            @Override
            public void remove() {
                throw new UnsupportedOperationException("[WARNING] Removal Not Allowed");
            }
        };
    }

    /**
     * Private class, defined element of the LinkedList
     * @param <T> of any type
     */
    private class Node<T> {

        private Node<T> next;
        private T data;

        public Node(T item) {
            this.next = null;
            this.data = item;
        }

        /**
         * Get data of one node
         * @return data of one node
         */
        public T getData() {
            return this.data;
        }

        /**
         * Get next node where the current one points to
         * @return next node
         */
        public Node<T> getNext() {
            return next;
        }

        /**
         * Set the pointer to ext
         * @param next pointer to be set
         */
        public void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {

        LinkedList<Integer> llist = new LinkedList<>();

        llist.addFirst(5);
        llist.addFirst(10);
        llist.addFirst(20);

        System.out.printf("\nAfter setup %d\n", llist.size());
        for(Integer item: llist) {
            System.out.printf("thing %d\n", item);
        }

        llist.clear();

        System.out.printf("\nAfter clearing %d\n", llist.size());
        for (Integer item: llist) {
            System.out.printf("thing %d\n", item);
        }

        llist.addLast(5);
        llist.addLast(10);
        llist.addLast(20);

        System.out.printf("\nAfter setup %d\n", llist.size());
        for(Integer item: llist) {
            System.out.printf("thing %d\n", item);
        }

        llist.clear();

        System.out.printf("\nAfter clearing %d\n", llist.size());
        for (Integer item: llist) {
            System.out.printf("thing %d\n", item);
        }

        for (int i = 10; i > 0; i -= 2) {
            llist.add(0, i);
        }

        llist.add(5, 12);
        llist.add(3, 0);

        System.out.printf("\nAfter setting %d\n", llist.size());
        for (Integer item: llist) {
            System.out.printf("thing %d\n", item);
        }

        llist.remove(0);
        System.out.printf("\nAfter removing %d\n", llist.size());
        for (Integer item: llist) {
            System.out.printf("thing %d\n", item);
        }

        llist.remove(2);
        System.out.printf("\nAfter removing %d\n", llist.size());
        for (Integer item: llist) {
            System.out.printf("thing %d\n", item);
        }

        llist.remove(4);
        System.out.printf("\nAfter removing %d\n", llist.size());
        for (Integer item: llist) {
            System.out.printf("thing %d\n", item);
        }

        ArrayList<Integer> alist = llist.toArrayList();
        System.out.printf("\nAfter copying %d\n", alist.size());
        for(Integer item: alist) {
            System.out.printf("thing %d\n", item);
        }

        alist = llist.toShuffledList();
        System.out.printf("\nAfter copying %d\n", alist.size());
        for(Integer item: alist) {
            System.out.printf("thing %d\n", item);
        }

        System.out.println(llist);
        System.out.println(llist.size());
        llist.reverse();
        System.out.println(llist);
        System.out.println(llist.contains(10));
        llist.replaceSecondValue(1);
        System.out.println(llist);
    }
}
