import java.util.EmptyStackException;

public class CellStack {

    private Cell[] array;
    private int top;
    private final static int CAPACITY = 10;

    /**
     * Default constructor that sets initial size to 10
     */
    public CellStack() {
        this(CAPACITY);
    }

    /**
     * Another constructor that customizes initial size of the stack
     * @param maximum
     */
    public CellStack(int maximum) {
        array = new Cell[maximum];
        top = -1;
    }

    /**
     * Push an Cell item to the stack
     * @param cell pushed cell
     */
    public void push(Cell cell) {
        if (top + 1 >= CAPACITY) {
            // Create another array with double capacity
            Cell[] arr = new Cell[top * 2];
            for (int i = 0; i < top + 1; i++) {
                // Copy every item to and create another space by using 'New'
                arr[i] = new Cell(array[i].getRow(), array[i].getCol(), array[i].getValue(), array[i].isLocked());
            }
            array = arr;
        }
        array[++top] = cell;
    }

    /**
     * Pop the last item
     * @return the last Cell item
     */
    public Cell pop() {
        if (top < 0) {
            throw new EmptyStackException();
        } else {
            return array[top--];
        }
    }

    /**
     * @return size of the stack
     */
    public int size() {
        return top + 1;
    }

    /**
     * @return true if the stack is empty, otherwise false
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }


}
