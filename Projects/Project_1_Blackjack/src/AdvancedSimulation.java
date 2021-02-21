import java.util.ArrayList;
import java.util.HashMap;

/**
 * Project1: Blackjack - CS231, Colby College
 * This AdvancedSimulation class make simulation iteratively, varied by decision value and game number
 *
 * @file AdvancedSimulation.java
 * @author Siyuan Peng
 * @date 2020-09-07
 */

public class AdvancedSimulation {

    /**
     * make multiple simulations varied by different decision value, i.e. changing the default winning 21 to others
     */
    public static void simulationVariedByDecisionValue() {
        Blackjack blackjack = new Blackjack(26);
        int playTimes = 100000;

        // use the options ArrayList for storing different decision value
        ImplementedArrayList<Integer> options = new ImplementedArrayList<>();
        // use the results HashMap for storing all results for all decision values
        HashMap<Integer, ArrayList<Double>> results = new HashMap<>();

        // initialize ArrayList options
        for (int i = 16; i < 46; i++) {
            options.add(i);
        }

        // loop through every decision value
        for (Integer option : options) {
            System.out.print(option + " ");

            // set decision value for Blackjack game
            blackjack.setDecisionValue(option);

            // repeat every simulation for 10 tens
            int repetition = 10;
            for (int i = 0; i < repetition; i++) {
                HashMap<Integer, Integer> singleResultMap = new HashMap<>() {
                    { put(0,0); put(-1,0); put(1,0); }};

                for (int j = 0; j < playTimes; j++) {
                    int result = blackjack.game(false);
                    singleResultMap.put(result, singleResultMap.get(result) + 1);
                }

                // Formatted printing on console of all ten sets of result, all of which contains number of player wins,
                // number of pushes, and number of dealer wins, at one single decision value
                System.out.printf("%-10s", String.format("%.4f", (double)singleResultMap.get(1)/playTimes));
                System.out.printf("%-10s", String.format("%.4f", (double)singleResultMap.get(0)/playTimes));
                System.out.printf("%-10s", String.format("%.4f", (double)singleResultMap.get(-1)/playTimes));
            }
            System.out.println();
        }
    }

    /**
     * Extension 6
     *
     * make multiple simulations varied by games times, i.e. changing the repetition of game played
     */
    public static void simulationVariedByGameTimes() {
        Blackjack blackjack = new Blackjack(26);

        // same as above
        ArrayList<Integer> playTimes = new ArrayList<>();
        HashMap<Integer, ArrayList<Double>> results = new HashMap<>();

        // repeat the game by 100, 1000, 10000 and so on
        for (int i = 2; i < 8; i++) {
            playTimes.add((int)Math.pow(10,i));
        }
        // set default decision value
        blackjack.setDecisionValue(21);
        int N = 10;
        // loop through all game time options
        for (Integer playTime: playTimes) {
            System.out.printf("%-11s", playTime + " ");
            for (int i = 0; i < N; i++) {
                HashMap<Integer, Integer> singleResultMap = new HashMap<>() {
                    { put(0,0); put(-1,0); put(1,0); }};

                for (int j = 0; j < playTime; j++) {
                    int result = blackjack.game(false);
                    singleResultMap.put(result, singleResultMap.get(result) + 1);
                }

                // formatted print on the console
                System.out.printf("%-10s", String.format("%.4f", (double)singleResultMap.get(1)/playTime));
                System.out.printf("%-10s", String.format("%.4f", (double)singleResultMap.get(0)/playTime));
                System.out.printf("%-10s", String.format("%.4f", (double)singleResultMap.get(-1)/playTime));
            }
            System.out.println();
        }

    }
    public static void main(String[] args) {
        simulationVariedByGameTimes();
    }
}
