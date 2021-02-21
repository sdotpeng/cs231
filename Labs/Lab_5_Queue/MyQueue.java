import java.util.Iterator;

import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;

public class MyQueue<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    public MyQueue() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void offer(T value) {
        Node<T> temp = new Node(value);
        if (this.size == 0) {
            this.head = temp;
            this.tail = temp;
        } else {
            this.tail.setNext(temp);
            temp.setPrev(tail);
            this.tail = temp;
        }
        size++;
    }

    public T poll() {
        if (this.size == 0) {
            System.out.println("Queue is empty");
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

    public T peek() {
        if (this.size == 0) {
            System.out.println("Queue is empty");
            return null;
        } else {
            return this.head.getValue();
        }
    }

    public int size() {
        return this.size;
    }

    public Iterator<T> iterator() {
        return new Iterator<T>(){

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

    public boolean empty() {
        return this.size == 0;
    }

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

    public MyQueue<T> clone() {
        MyQueue<T> newQueue = new MyQueue<>();
        for (T value : this) {
            newQueue.offer(value);
        }
        return newQueue;
    }

    private class Node<T> {

        private Node prev, next;
        private T value;

        public Node(T value) {
            this.prev = null;
            this.next = null;
            this.value = value;
        }

        public void setNext(Node n) {
            this.next = n;
        }

        public void setPrev(Node n) {
            this.prev = n;
        }

        public Node<T> getNext() {
            return this.next;
        }

        public Node<T> getPrev() {
            return this.prev;
        }

        public T getValue() {
            return this.value;
        }

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