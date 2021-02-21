import java.util.*;

public class Sudoku {

    private Board board;
    private LandscapeDisplay display;
    private int stepCounts;
    private boolean solved;

    public Sudoku() {
        this.board = new Board();
        this.solved = false;
        this.stepCounts = 0;
    }

    public Sudoku(String fileName, int scale) {
        this.board = new Board();
        this.board.read(fileName);
        this.solved = false;
        this.stepCounts = 0;
        display = new LandscapeDisplay(board, scale);
    }

    public Sudoku(int population) {
        this.board = new Board();
        Random randomGenerator = new Random(System.currentTimeMillis());
        int randomRow, randomCol, randomValue;
        for (int i = 0; i < population; i++) {
            do {
                randomRow = randomGenerator.nextInt(9);
                randomCol = randomGenerator.nextInt(9);
            } while (this.board.getValue(randomRow, randomCol) != 0);
            do {
                randomValue = 1 + randomGenerator.nextInt(9);
            } while (!this.board.isValidValue(randomRow, randomCol, randomValue));
            this.board.set(randomRow, randomCol, randomValue, true);
        }
        this.stepCounts = 0;
    }

    public Sudoku(int population, int scale) {
        this.board = new Board();
        Random randomGenerator = new Random(System.currentTimeMillis());
        int randomRow, randomCol, randomValue;
        for (int i = 0; i < population; i++) {
            do {
                randomRow = randomGenerator.nextInt(9);
                randomCol = randomGenerator.nextInt(9);
            } while (this.board.getValue(randomRow, randomCol) != 0);
            do {
                randomValue = 1 + randomGenerator.nextInt(9);
            } while (!this.board.isValidValue(randomRow, randomCol, randomValue));
            this.board.set(randomRow, randomCol, randomValue, true);
        }
        display = new LandscapeDisplay(board, scale);
        this.stepCounts = 0;
    }

    public void reset(int population) {
        // Reset the board
        this.stepCounts = 0;
        this.board.reset();
        Random randomGenerator = new Random(System.currentTimeMillis());
        int randomRow, randomCol, randomValue;
        for (int i = 0; i < population; i++) {
            do {
                randomRow = randomGenerator.nextInt(9);
                randomCol = randomGenerator.nextInt(9);
            } while (this.board.getValue(randomRow, randomCol) != 0);
            do {
                randomValue = 1 + randomGenerator.nextInt(9);
            } while (!this.board.isValidValue(randomRow, randomCol, randomValue));
            this.board.set(randomRow, randomCol, randomValue, true);
        }
    }

    public void solve(int delay, boolean hasDisplay) {

        CellStack cellStack = new CellStack();
        int numLocked = this.board.numLocked();

        while (cellStack.size() < 81 - numLocked) {
            // select the next cell to check
            Cell bestCell = this.findBestCell();
            // if there is a valid next cell to try
            if (bestCell != null) {
                // set new available value
                int value = this.board.getValidValue(bestCell.getRow(), bestCell.getCol());
                bestCell.setValue(value);
                // push the cell onto the stack
                cellStack.push(bestCell);
                // update the board
                this.board.set(bestCell.getRow(), bestCell.getCol(), value, true);
                this.stepCounts++;
                if (hasDisplay) {
                    if (delay > 0) {
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException ex) {
                            System.out.println("Interrupted");
                        }
                        display.repaint();
                        display.setProgressBarValue(this.board.numLocked());
                    }
                }
            } else {
                // while it is necessary and possible to backtrack
                while (cellStack.size() > 0) {
                    // pop a cell off the stack
                    Cell poppedCell = cellStack.pop();
                    // check if there are other untested values this cell could try
                    int availableValue = this.board.getValidValue(poppedCell.getRow(), poppedCell.getCol());
                    if (availableValue != -1) {
                        poppedCell.setValue(availableValue);
                        // push the cell with its new value onto the stack
                        cellStack.push(poppedCell);
                        // update the board
                        this.board.set(poppedCell.getRow(), poppedCell.getCol(), availableValue, true);
                        this.stepCounts++;
                        if (hasDisplay) {
                            if (delay > 0) {
                                try {
                                    Thread.sleep(delay);
                                } catch (InterruptedException ex) {
                                    System.out.println("Interrupted");
                                }
                                display.repaint();
                                display.setProgressBarValue(this.board.numLocked());
                            }
                        }
                        // break
                        break;
                    } else {
                        // set this cell's value to 0 on the board
                        this.board.set(poppedCell.getRow(), poppedCell.getCol(), 0, false);
                        this.stepCounts++;
                        if (hasDisplay) {
                            display.setProgressBarValue(this.board.numLocked());
                        }
                    }

                }

                // if the stack size is 0 (no more backtracking possible)
                if (cellStack.size() == 0) {
                    // return false: there is no solution
                    this.solved = false;
                    return;
                }
            }
        }

        this.solved = true;
    }

    public boolean isSolved() {
        return solved;
    }

    public int getStepCounts() {
        return stepCounts;
    }

    public Cell findBestCellNaive() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board.getValue(i, j) == 0) {
                    return board.get(i, j);
                }
            }
        }

        return null;
    }

    public Cell findBestCell() {

        int options;
        int row = 0, col = 0;
        int min = 10;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!board.isLocked(i, j)) {
                    options = getNumberOptions(i, j);
                    if (options == 0) {
                        return null;
                    } else {
                        if (options < min) {
                            row = i;
                            col = j;
                            min = options;
                        }
                    }
                }
            }
        }

        return board.get(row, col);
    }

    public int getNumberOptions(int row, int col) {

        Set<Integer> relatedCells = new HashSet<>();

        for (int j = 0; j < 9; j++) {
            relatedCells.add(board.getValue(row, j));
        }

        for (int i = 0; i < 9; i++) {
            relatedCells.add(board.getValue(i, col));
        }

        int startX = row / 3 * 3;
        int startY = col / 3 * 3;

        for (int i = startX; i < startX + 3; i++) {
            for (int j = startY; j < startY + 3; j++) {
                relatedCells.add(board.getValue(i, j));
            }
        }

        relatedCells.remove(this.board.getValue(row, col));
        relatedCells.remove(0);

        return 9 - relatedCells.size();

    }

    public String toString() {
        return this.board.toString();
    }

    public static void main(String[] args) throws InterruptedException {
        // int population = Integer.parseInt(args[0]);
        // int population = 10;
        // Sudoku testSudoku = new Sudoku(population);
        // System.out.println(testSudoku);

//        Sudoku test = new Sudoku(20);
//        System.out.println(test);
//        /*test.board.read("./src/resources/board_sp_20.txt");
//        System.out.println(test);
//        System.out.println(test.getNumberOptions(8,8));*/
//        System.out.println(test.findBestCell());
        int population = 10;
        int scale = 60;
        int delay = 10;

        if (args.length > 0) {
            population = Integer.parseInt(args[0]);
            scale = Integer.parseInt(args[1]);
            delay = Integer.parseInt(args[2]);
        }
        Sudoku test = new Sudoku(population, scale);
        boolean exitState = LandscapeDisplay.getExitState();
        while (!exitState) {
            boolean solveState = LandscapeDisplay.getSolveState();
            if (solveState) {
                test.solve(delay, true);
                LandscapeDisplay.setStepCounts(test.stepCounts);
                if (test.solved) {
                    LandscapeDisplay.setMessage("Solution found.  ");
                } else {
                    LandscapeDisplay.setMessage("Failed to solve.  ");
                }
                LandscapeDisplay.setSolveState(false);
                LandscapeDisplay.setResetState(false);
                test.display.printResult();
            }
            boolean resetState = LandscapeDisplay.getResetState();
            if (resetState) {
                test.reset(population);
                test.display.repaint();
                LandscapeDisplay.setResetState(false);
                test.display.resetResult();
            }
            exitState = LandscapeDisplay.getExitState();
        }
    }
}
