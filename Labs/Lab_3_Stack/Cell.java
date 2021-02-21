public class Cell {
    private int rows;
    private int cols;
    private int value;
    private boolean isLocked;

    public Cell() {
        this(0,0,0);
    }

    public Cell(int rows, int cols, int value) {
        this.rows = rows;
        this.cols = cols;
        this.value = value;
        this.isLocked = false;
    }

    public Cell(int rows, int cols, int value, boolean isLocked) {
        this.rows = rows;
        this.cols = cols;
        this.value = value;
        this.isLocked = isLocked;
    }

    public int getRow() {
        return this.rows;
    }

    public int getCol() {
        return this.cols;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int newValue) {
        this.value = newValue;
    }

    public boolean isLocked() {
        return this.isLocked;
    }

    public void setLocked(boolean lock) {
        this.isLocked = lock;
    }

    public Cell clone() {
        Cell out = new Cell(this.getRow(), this.getCol(), this.getValue(), this.isLocked());
        return out;
    }

    public String toString() {
        return "Cell has a value of " + this.getValue();
    }

}
