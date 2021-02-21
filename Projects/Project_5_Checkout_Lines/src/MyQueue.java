import java.util.Iterator;

/**
 * Project 5: Checkout lines - CS231, Colby College
 *
 * My implementation of Queue
 *
 * @file MyQueue.java
 * @author Ricky Peng
 * @date 2020-10-08
 */

public class MyQueue<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    /**
     * Default constructor
     */
    public MyQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    /**
     * Add one item to the end of the queue
     * @param value value of the new added item
     */
    public void offer(T value) {
        Node<T> temp = new Node(value);
        if (this.size == 0) {
            this.head = temp;
        } else {
            this.tail.setNext(temp);
            temp.setPrev(tail);
        }
        this.tail = temp;
        size++;
    }

    /**
     * Remove first item from one queue and return the item
     * @return the first item
     */
    public T poll() {
        if (this.size == 0) {
            // System.out.println("Queue is empty");
            return null;
        } else if (this.size == 1) {
            T output = this.head.getValue();
            this.head = null;
            this.tail = null;
            size--;
            return (T) output;
        } else {
            T output = this.head.getValue();
            Node<T> second = this.head.getNext();
            second.setPrev(null);
            this.head = second;
            size--;
            return (T) output;
        }
    }

    /**
     * Inspect the first item without removing it
     * @return the first item in the queue
     */
    public T peek() {
        if (this.size == 0) {
            // System.out.println("Queue is empty");
            return null;
        } else {
            return this.head.getValue();
        }
    }

    /**
     * @return the size of the queue
     */
    public int size() {
        return this.size;
    }

    /**
     * Iterator() method for the Queue
     * @return Iterator instance of the queue
     */
    public Iterator<T> iterator() {
        return new Iterator<>(){

            Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.getValue();
                current = current.getNext();
                return value;
            }

        };
    }

    /**
     * @return true if empty
     */
    public boolean empty() {
        return this.size == 0;
    }

    /**
     * @return A String representation of the Queue
     */
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder("[ ");
        Iterator<T> iter = this.iterator();
        while (iter.hasNext()) {
            out.append(iter.next().toString()).append(" ");
        }
        out.append("]");
        return out.toString();
    }

    /**
     * If one queue is equal to another
     * @param other another queue
     * @return true if equal, false if not
     */
    public boolean equals(MyQueue<T> other) {
        assert this.size() == other.size();
        assert this.head.getClass().equals(other.head.getClass());
        MyQueue<T> queue1 = this.clone();
        MyQueue<T> queue2 = other.clone();
        while (!queue1.empty()) {
            if (queue1.peek().equals(queue2.peek())) {
                queue1.poll();
                queue2.poll();
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Clone the current queue and return another
     * @return The same queue
     */
    public MyQueue<T> clone() {
        MyQueue<T> newQueue = new MyQueue<>();
        for (T value : this) {
            newQueue.offer(value);
        }
        return newQueue;
    }

    /**
     * Node class
     * @param <T>
     */
    private class Node<T> {

        private Node prev, next;
        private T value;

        /**
         * Default constructor
         * @param value value of the node
         */
        public Node(T value) {
            this.prev = null;
            this.next = null;
            this.value = value;
        }

        /**
         * Set next pointer
         * @param n next node
         */
        public void setNext(Node n) {
            this.next = n;
        }

        /**
         * Set previous pointer
         * @param n previous node
         */
        public void setPrev(Node n) {
            this.prev = n;
        }

        /**
         * Get next node
         * @return next node
         */
        public Node<T> getNext() {
            return this.next;
        }

        /**
         * Get previous node
         * @return previous node
         */
        public Node<T> getPrev() {
            return this.prev;
        }

        /**
         * Get value of the current node
         * @return value of the node
         */
        public T getValue() {
            return this.value;
        }

        /**
         * Set value of the current node
         * @param value new value
         */
        public void setValue(T value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        System.out.println("Initializing queue of Integer");
        MyQueue<Integer> queue = new MyQueue<>();
        System.out.println("Adding numbers");
        for (int i = 0; i < 12; i += 2) {
            queue.offer(i);
        }
        System.out.println("The first number should be 0: " + queue.peek());
        System.out.println("Removing the first number, which is 0: " + queue.poll());
        System.out.println("Now the first number of should be 2: " + queue.peek());
        System.out.println("Adding 12 and 14 to the queue");
        queue.offer(12);
        queue.offer(14);
        Iterator<Integer> iter = queue.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
        System.out.println("The queue should have size of 7: " + queue.size());
        System.out.println("Print out the queue: " + queue.toString());

        System.out.println("==========");
        MyQueue<Integer> queue1 = new MyQueue<>();
        MyQueue<Integer> queue2 = new MyQueue<>();
        for (int i = 0; i < 5; i++) {
            queue1.offer(i);
            queue2.offer(i);
        }
        System.out.println("Queue 1: " + queue1.toString());
        System.out.println("Queue 2: " + queue2.toString());
        System.out.println("They should be equal: " + queue1.equals(queue2));

        System.out.println("==========");
        System.out.println("Removing first element of Queue 1: " + queue1.poll());
        System.out.println("Queue 1: " + queue1.toString());
        System.out.println("Queue 2: " + queue2.toString());
        System.out.println("They should be not equal now: " + queue1.equals(queue2));

        System.out.println("==========");
        MyQueue<String> queue3 = new MyQueue<>();
        queue3.offer("H");
        MyQueue<Integer> queue4 = new MyQueue<>();
        queue4.offer(0);
        System.out.println("Queue 3: " + queue3.toString());
        System.out.println("Queue 4: " + queue4.toString());
        System.out.println("Queue 3 and Queue 4 should not be equal: " + queue3.equals(queue4));
    }

}