import java.awt.*;
import java.io.FileReader;
import java.util.HashMap;

public class Cell {
    private int row;
    private int col;
    private int value;
    private boolean isLocked;

    /**
     * Default constructor for Cell
     */
    public Cell() {
        this(0,0,0);
    }

    /**
     * Cell constructor
     * @param row row index of the cell
     * @param col column index of the cell
     * @param value value of the cell
     */
    public Cell(int row, int col, int value) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.isLocked = false;
    }

    /**
     * Another constructor that sets isLocked
     * @param row row index of the cell
     * @param col column index of the cell
     * @param value value of the cell
     * @param isLocked whether cell is locked
     */
    public Cell(int row, int col, int value, boolean isLocked) {
        this.row = row;
        this.col = col;
        this.value = value;
        this.isLocked = isLocked;
    }

    /**
     * @return row index of the cell
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return column index of the cell
     */
    public int getCol() {
        return this.col;
    }

    /**
     * @return value of the cell
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Set value of the cell
     * @param newValue new value to be set
     */
    public void setValue(int newValue) {
        this.value = newValue;
    }

    /**
     * @return whether the cell is locked
     */
    public boolean isLocked() {
        return this.isLocked;
    }

    /**
     * @param lock lock state to be set
     */
    public void setLocked(boolean lock) {
        this.isLocked = lock;
    }

    /**
     * Clone the cell
     * @return a new cell with all fields same to the previous
     */
    public Cell clone() {
        return new Cell(this.getRow(), this.getCol(), this.getValue(), this.isLocked());
    }

    /**
     * Draw the cell on the Graphics, includes different color for different value of the cell according to color
     * palette of user's choice, uses Apple SD Neo font as default, draws boundaries for smaller blocks
     *
     * @param g Graphics Object
     * @param x0 start position of the cell in x coordinates
     * @param y0 start position of the cell in y coordinates
     * @param scale scale of the rectangle that contains the cell
     * @param palette Color palette according to user's choice
     * @param font Font of the text shown on top of the rectangle
     */
    public void draw(Graphics g, int x0, int y0, int scale, HashMap<Integer, Color> palette, Font font) {
        g.setColor(Color.BLACK);
        g.setFont(font);
        // Draw the boundary for each cell
        g.drawRect(x0, y0, scale, scale);
        // Set color according to the color of the cell
        g.setColor(palette.get(value));
        // Fill the rectangle of the cell with particular color
        g.fillRect(x0, y0, scale, scale);

        // Draw the boundaries if the cell is adjacent to the smaller block or the bigger block
        g.setColor(Color.BLACK);
        int fullWidthDivisor = 40;
        int halfWidthDivisor = 20;

        if (this.getCol() == 0 || this.getCol() == 3 || this.getCol() == 6) {
            g.fillRect(x0 - scale / fullWidthDivisor, y0, scale / halfWidthDivisor, scale);
        }
        if (this.getCol() == 8) {
            if (this.getRow() == 0) {
                g.fillRect(x0 + scale - scale / fullWidthDivisor, y0 - scale / fullWidthDivisor,
                        scale / halfWidthDivisor, scale + scale / fullWidthDivisor);
            } else {
                g.fillRect(x0 + scale - scale / fullWidthDivisor, y0,
                        scale / halfWidthDivisor, scale);
            }
        }
        if (this.getRow() == 0 || this.getRow() == 3 || this.getRow() == 6) {
            g.fillRect(x0, y0 - scale / fullWidthDivisor,
                    scale, scale / halfWidthDivisor);
        }
        if (this.getRow() == 8) {
            if (this.getCol() == 0) {
                g.fillRect(x0 - scale / fullWidthDivisor, y0 + scale - scale / fullWidthDivisor,
                        scale + scale / fullWidthDivisor, scale / halfWidthDivisor);
            } else {
                g.fillRect(x0, y0 + scale - scale / fullWidthDivisor,
                        scale, scale / halfWidthDivisor);
            }
        }

        // Draw text on top of the colored cell
        String strValue = String.valueOf(value);
        g.drawString(strValue.equals("0") ? "" : strValue, (int) (x0 + scale / 2.65), (int) (y0 + scale / 1.4));
    }

    /**
     * @return a String representation of the cell
     */
    public String toString() {
        return "Cell at location (" + this.getRow() + ", " + this.getCol() + ") " + "has a value of " + this.getValue();
    }

}
