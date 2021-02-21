public class Simulation {

    public static void main(String[] args) {
        Sudoku sudoku = new Sudoku("./src/resources/board_nsp_10.txt", 50);
        System.out.println(sudoku.toString());
        sudoku.solve(0, false);
        System.out.println(sudoku.getStepCounts());
        System.out.println(sudoku.toString());
    }

    public static void main3(String[] args) {
        Sudoku sudoku = new Sudoku(10);
        int steps = 0;
        int sum = 0;
        int solved = 0;
        int failed = 0;
        for (int population = 10; population < 20; population++) {
            for (int repetition = 0; repetition < 100; repetition++) {
                sudoku.reset(population);
                sudoku.solve(0, false);
                if (sudoku.isSolved()) {
                    solved++;
                    steps = sudoku.getStepCounts();
                    sum += steps;
                } else {
                    failed++;
                }
            }
            System.out.print(population + " " + sum/100);
            System.out.println();
            solved = 0;
            failed = 0;
            sum = 0;
        }
    }

    public static void main2(String[] args) {
        Sudoku sudoku = new Sudoku(10);
        StringBuilder out = new StringBuilder();
        int steps = 0;
        int solved = 0;
        int failed = 0;
        for (int population = 10; population < 45; population += 5) {
            for (int repetition = 0; repetition < 10; repetition++) {
                sudoku.reset(population);
                sudoku.solve(0, false);
                if (sudoku.isSolved()) {
                    solved++;
                    steps = sudoku.getStepCounts();
                    System.out.print(steps + " ");
                } else {
                    failed++;
                }
            }
            System.out.println();
            out.append("Population: ").append(population).append(" has solved ").append(solved)
                    .append(" and failed ").append(failed).append("\n");
            solved = 0;
            failed = 0;
        }
        System.out.println(out);
    }
}
