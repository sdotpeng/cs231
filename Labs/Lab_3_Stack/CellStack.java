public class CellStack {

    private Cell[] array;
    private int top;
    private static int CAPACITY = 10;

    public CellStack() {
        this(CAPACITY);
    }

    public CellStack(int maximum) {
        array = new Cell[maximum];
        top = -1;
    }

    public void push(Cell cell) {
        if (top + 1 < CAPACITY) {
            array[++top] = cell;
        } else {
            Cell[] arr = new Cell[top * 2];
            for (int i = 0; i < top + 1; i++) {
                arr[i] = new Cell(array[i].getRow(), array[i].getCol(), array[i].getValue(), array[i].isLocked());
            }
            array = arr;
            array[++top] = cell;
        }
    }

    public Cell pop() {
        if (top < 0) {
            System.out.println("Nothing to be poped out");
        } else {
            return array[top--];
        }

        return new Cell();
    }

    public Cell peek() {
        return array[top];
    }

    public int size() {
        return top + 1;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }


}
