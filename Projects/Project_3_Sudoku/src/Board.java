import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Project 3: Sudoku - CS231, Colby College
 *
 * holds the array of Cells that make up the Sudoku board. It will be able to read a board from a file,
 * test if a value is valid at a certain position on the board, and test if the board is solved.
 *
 * @file Board.java
 * @author Ricky Peng
 * @date 2020-09-17
 */

public class Board {

    private Cell[][] grid;
    private final static int GRID_SIZE = 9;

    public Board() {
        // Initialize the grid
        this.grid = new Cell[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // Initialize the Cell object at every position on the grid
                grid[i][j] = new Cell(i,j,0,false);
            }
        }
    }

    public void reset() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                // Initialize the Cell object at every position on the grid
                grid[i][j].setValue(0);
                grid[i][j].setLocked(false);
            }
        }
    }

    /**
     * @return the number of columns
     */
    public int getCols() {
        return this.grid[0].length;
    }

    /**
     * @return the number of rows
     */
    public int getRows() {
        return this.grid.length;
    }

    /**
     * @param row row of the Cell
     * @param col column of the Cell
     * @return the Cell at location r, c.
     */
    public Cell get(int row, int col) {
        return this.grid[row][col];
    }

    /**
     * Check whether one cell is locked at position row, col
     * @param row row of the Cell
     * @param col column of the Cell
     * @return whether the Cell at r, c, is locked.
     */
    public boolean isLocked(int row, int col) {
        return this.get(row, col).isLocked();
    }

    /**
     * @return the number of locked Cells on the board.
     */
    public int numLocked() {
        int number = 0;
        for (Cell[] cells : this.grid) {
            for (Cell cell : cells) {
                // Increment number when there's locked
                number += cell.isLocked() ? 1 : 0;
            }
        }

        return number;
    }

    /**
     * @param row row of the Cell
     * @param col column of the Cell
     * @return the value at Cell row, col.
     */
    public int getValue(int row, int col) {
        return this.get(row, col).getValue();
    }

    /**
     * Sets the value of the Cell at r, c.
     * @param row row of the Cell
     * @param col column of the Cell
     * @param value new value
     */
    public void set(int row, int col, int value) {
        this.get(row, col).setValue(value);
    }

    /**
     * Sets the value and locked fields of the Cell at row, col.
     * @param row row of the Cell
     * @param col column of the Cell
     * @param value new value
     * @param locked new locked status
     */
    public void set(int row, int col, int value, boolean locked) {
        this.get(row, col).setValue(value);
        this.get(row, col).setLocked(locked);
    }

    /**
     * Check if a given value at given position on the grid is valid. It has to satisfy several condition,
     * (1) not out of the bound (2) does not have same value across the row it is located (3) does not have same
     * value across the column it is located, (4) does not have the same value at the smaller 3*3 block it is located.
     * If the same number is found, return false, otherwise true
     *
     * @param row row index
     * @param col column index
     * @param value given value
     * @return false if invalid, true if valid
     */
    public boolean isValidValue(int row, int col, int value) {

        // Check the range of the given index
        if (row < 0 || row > 8 || col < 0 || col > 8) {
            throw new IndexOutOfBoundsException("[ERROR] Given element not in the grid.");
        }
        // Test row
        for (int j = 0; j < 9; j++) {
            if (j == col) {
                continue;
            }
            if (value == getValue(row, j)) {
                return false;
            }
        }

        // Test column
        for (int i = 0; i < 9; i++) {
            if (i == row) {
                continue;
            }
            if (value == getValue(i, col)) {
                return false;
            }
        }

        // Test square
        int startRow = row / 3 * 3;
        int startCol = col / 3 * 3;
        for (int i = startRow; i < startRow + 3; i++){
            for (int j = startCol; j < startCol + 3; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (value == getValue(i,j)) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Check the grid and see if the board is solved. It iterates through every cell and call {@code isValidValue()} on
     * it, return false if any cell is not valid or any cell has value of 0, meaning incomplete solution
     * @return False if one cell is invalid, otherwise true
     */
    public boolean isValidSolution() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                int value = getValue(i, j);
                if (value == 0 || !isValidValue(i, j, value)) {
                    // If cell is 0 or if cell is not valid
                    return false;
                }
            }
        }

        // No invalid cell is found
        return true;
    }

    /**
     * Return the next available value for the given cell. The return value is always greater than the current value,
     * if not valid is found, return -1, meaning there's no possible value thus forced backtracking.
     *
     * @param row row index of the cell
     * @param col column index of the cell
     * @return next possible value
     */
    public int getValidValue(int row, int col) {
        int currentValue = getValue(row, col);
        // Start from the next value of the current value
        for (int i = currentValue + 1; i < 10; i++) {
            if (isValidValue(row, col, i)) {
                // Return the first found valid value
                return i;
            }
        }

        return -1;
    }

    /**
     * Return the color palette of a given theme, theme "light rainbow" is chosen as default
     *
     * @param themeName Name of the theme that user can choose
     * @return a HashMap<Integer, Color> that contains colors for different value of the cell
     */
    public HashMap<Integer, Color> getPalette(String themeName) {
        switch (themeName) {
            case "Ocean" -> {
                return new HashMap<>() {
                    {
                        put(0, new Color(255, 255, 255));
                        put(1, new Color(151, 223, 252));
                        put(2, new Color(142, 181, 240));
                        put(3, new Color(133, 138, 227));
                        put(4, new Color(115, 100, 210));
                        put(5, new Color(97, 61, 193));
                        put(6, new Color(88, 41, 167));
                        put(7, new Color(78, 20, 140));
                        put(8, new Color(61, 14, 97));
                        put(9, new Color(44, 7, 53));
                    }
                };
            }
            case "Peach" -> {
                return new HashMap<>() {
                    {
                        put(0, new Color(255, 255, 255));
                        put(1, new Color(243, 235, 213));
                        put(2, new Color(245, 215, 195));
                        put(3, new Color(247, 195, 177));
                        put(4, new Color(248, 175, 158));
                        put(5, new Color(250, 155, 140));
                        put(6, new Color(251, 135, 122));
                        put(7, new Color(253, 115, 104));
                        put(8, new Color(254, 105, 95));
                        put(9, new Color(254, 95, 85));

                    }
                };
            }
            case "Modern" -> {
                return new HashMap<>() {
                    {
                        put(0, new Color(255, 255, 255));
                        put(1, new Color(241, 196, 83));
                        put(2, new Color(239, 234, 90));
                        put(3, new Color(185, 231, 105));
                        put(4, new Color(131, 227, 119));
                        put(5, new Color(22, 219, 147));
                        put(6, new Color(22, 219, 147));
                        put(7, new Color(4, 139, 168));
                        put(8, new Color(44, 105, 154));
                        put(9, new Color(84, 71, 140));
                    }
                };
            }

            case "Rainbow" -> {
                return new HashMap<>() {
                    {
                        put(0, new Color(255, 255, 255));
                        put(1, new Color(51, 168, 199));
                        put(2, new Color(82, 227, 225));
                        put(3, new Color(160, 228, 38));
                        put(4, new Color(253, 241, 72));
                        put(5, new Color(255, 171, 0));
                        put(6, new Color(247, 121, 118));
                        put(7, new Color(240, 80, 174));
                        put(8, new Color(216, 131, 255));
                        put(9, new Color(147, 54, 253));
                    }
                };
            }

            default -> {
                return new HashMap<>() {
                    {
                        put(0, new Color(255, 255, 255));
                        put(1, new Color(133, 199, 242));
                        put(2, new Color(255, 182, 39));
                        put(3, new Color(92, 164, 169));
                        put(4, new Color(68, 118, 4));
                        put(5, new Color(239, 171, 255));
                        put(6, new Color(176, 254, 118));
                        put(7, new Color(255, 105, 120));
                        put(8, new Color(44, 87, 132));
                        put(9, new Color(165, 56, 96));
                    }
                };
            }


        }
    }

    /**
     * Draw the board on Graphics, sets up color theme and font
     * @param g Graphics
     * @param scale scale of the cell
     */
    public void draw(Graphics g, int scale, String themeName) {
        HashMap<Integer, Color> palette = getPalette(themeName);
        Font font = new Font("Apple SD Gothic Neo", Font.PLAIN, 25);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.get(i, j).draw(g, j * scale + scale / 2, i * scale + scale / 2, scale, palette, font);
            }
        }
    }

    /**
     * Reads an file and store its content into the board
     * @param fileName name of the file
     * @return true if file is found, false if failed
     */
    public boolean read(String fileName) {

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();
            String[] array;
            int row = 0;

            while (line != null) {
                // Split the array by black spaces or "\n" using regular expression
                array = line.strip().split("[\n ]+");
                if (array.length == 9) {
                    for (int col = 0; col < 9; col++) {
                        // Update the board
                        int value = Integer.parseInt(array[col]);
                        boolean locked = value != 0;
                        set(row, col, value, locked);
                    }
                    // Move to the next line
                    row++;
                }
                line = bufferedReader.readLine();
            }

            bufferedReader.close();

            return true;
        } catch (FileNotFoundException ex) {
            System.out.println("Board.read(): Unable to find file \"" + fileName + "\"");
        } catch (IOException ignored) {

        }

        return false;

    }

    /**
     * @return a String representation of the Cell Grid
     */
    public String toString() {
        StringBuilder row = new StringBuilder();
        StringBuilder out = new StringBuilder();
        int countRow = 1;
        int countCol = 1;
        for (Cell[] cells : grid) {
            for (Cell cell : cells) {
                // Print row of sudoku
                row.append(cell.getValue()).append(" ");
                row.append((countRow == 3 || countRow == 6) ? "| " : "");
                countRow++;
            }
            // Set row count back to 1
            countRow = 1;
            out.append(row.toString().strip()).append("\n");
            out.append((countCol == 3 || countCol == 6) ? "- - - - - - - - - - -\n" : "");

            // Set back to default again
            countCol++;
            row = new StringBuilder();
        }
        return out.toString();
    }

    /**
     * Second main method for testing
     * @param args unused
     */
    public static void main2(String[] args) {
        Board testBoard = new Board();
        System.out.println(">> This board should have 9 rows:          " + testBoard.getRows());
        System.out.println(">> This board should have 9 columns:       " + testBoard.getCols());
        System.out.println(">> Setting numbers: (1,1), (4,4), (3,2)");
        testBoard.set(1,1,9,true);
        testBoard.set(4,4,2,true);
        testBoard.set(3,2,9);
        System.out.println(">> This board should have 2 locked items:  " + testBoard.numLocked());
        System.out.println(">> Cell at (1,1) should be locked:         " + testBoard.isLocked(1,1));
        System.out.println(">> Cell at (3,2) should not be locked:     " + testBoard.isLocked(3,2));
        System.out.println(">> Cell at (3,2) should have value of:     " + testBoard.getValue(3,2));
        System.out.println(">> Print board");
        System.out.println(testBoard);
    }

    /**
     * Main method for testing the class
     * @param args can be fileName
     */
    public static void main(String[] args) {
        Board testBoard = new Board();
        String fileName = "board_sp_20";
        testBoard.read("./src/resources/" + fileName + ".txt");
        System.out.println(testBoard);
        System.out.println(">> This board is a valid solution: " + testBoard.isValidSolution());
        fileName = "board_sp_20";
        testBoard.read("./src/resources/" + fileName + ".txt");
        System.out.println(">> This board is not a valid solution: " + testBoard.isValidSolution());

        System.out.println(testBoard.getValidValue(8, 8));
    }


}


