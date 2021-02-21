import java.util.Random;

public class Grid {
    public static void main(String[] args) {

        if (args.length == 0) {
            System.out.println("Usage: Test Command Line Arguments...");
            return;
        }

        int yogi = 0, booboo = 0;

        for (int i = 0; i < 2; i++) {
            yogi = Integer.parseInt(args[0]);
            booboo = Integer.parseInt(args[1]);
        }

        String[][] ranger = new String[yogi][booboo];

        Random randomGenerator = new Random();

        for (int i = 0; i < ranger.length; i++) {
            for (int j = 0; j < ranger[i].length; j++) {
                int ascii = randomGenerator.nextInt(26) + 65;
                ranger[i][j] = String.valueOf((char)ascii);
            }
        }

        for (int i = 0; i < ranger.length; i++) {
            for (int j = 0; j < ranger[i].length; j++) {
                System.out.print(ranger[i][j] + " ");
            }
            System.out.println();
        }
    }
}
