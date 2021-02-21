import java.util.HashMap;
import java.util.Scanner;

/**
 * Project1: Blackjack - CS231, Colby College
 * This class should have only a main function that executes 1000 games of Blackjack. The code should create
 * and re-use a single Blackjack object. It should keep track of how many games the player wins, how many the
 * dealer wins, and many are pushes. Print out the total in the end both as raw numbers and as percentages.
 *
 * @file Simulation.java
 * @author Siyuan Peng
 * @date 2020-09-04
 */

public class Simulation {

    public static void main(String[] args) throws Exception {

        // this simulation is from the project description

        Blackjack blackjack = new Blackjack(26);
        System.out.println(">> Blackjack simulation has been initialized with default cutoff value " + blackjack.getReshuffleCutoff());

        Scanner input = new Scanner(System.in);

        // user can customize the number of simulations they want to perform
        System.out.print(">> How many simulations do you have to perform: ");
        int playTimes = 0;
        while (input.hasNext()) {
            if (input.hasNextInt()){
                playTimes = input.nextInt();
                if (playTimes <= 0) {
                    System.out.println(">> [WARNING] NON-POSITIVE INPUT, ENTER AGAIN: ");
                    input.next();
                } else {
                    break;
                }
            } else {
                // type checking, avoid non-integer input
                System.out.print(">> [WARNING] INVALID INPUT, ENTER AGAIN: ");
                input.next();
            }
        }

        // used one HashMap to store the result of simulation, keys = [0,-1,1], values = [PUSHES, DEALER_WINS, PLAYER_WINS]
        HashMap<Integer, Integer> resultMap = new HashMap<>() {
            {
                put(0,0);
                put(-1,0);
                put(1,0);
            }
        };

        for (int i = 0; i < playTimes; i++) {
            int result = blackjack.game(false);
            // put result of single game in HashMap
            resultMap.put(result, resultMap.get(result) + 1);
        }

        // print simulation summary
        System.out.println("\n==== Simulation Summary ====");
        System.out.println(">> Game played: " + playTimes);
        System.out.println(">> Player wins: " + resultMap.get(1) + " (" + resultMap.get(1)/(double)playTimes + "%)");
        System.out.println(">> Dealer wins: " + resultMap.get(-1) + " (" + resultMap.get(-1)/(double)playTimes + "%)");
        System.out.println(">> It's a push: " + resultMap.get(0) + " (" + resultMap.get(0)/(double)playTimes + "%)");
    }
}
