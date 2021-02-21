import java.util.Scanner;

/**
 * Project1: Blackjack - CS231, Colby College
 * A class that implements a simple version of the card game. The class will need to have fields for a Deck,
 * a Hand for the player, a Hand for the dealer, and a field for the number of cards below which
 * the deck must be reshuffled. The main function for the Blackjack class should implement one complete game.
 *
 * @file Blackjack.java
 * @author Siyuan Peng
 * @date 2020-09-04
 */

public class Blackjack {
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private int CUT_OFF;
    private int DECISION_VALUE = 21;
    private int PLAYER_MAX = DECISION_VALUE - 5;
    private int DEALER_MAX = PLAYER_MAX + 1;

    /**
     * store the reshuffleCutoff and set up a game
     * @param reshuffleCutoff input cutoff value
     */
    public Blackjack(int reshuffleCutoff) {
        this.CUT_OFF = reshuffleCutoff;
        this.deck = new Deck();
        this.deck.shuffle();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
        this.reset();
    }

    /**
     * Use user-input number as the decision value
     * @param value new decision value
     */
    public void setDecisionValue(int value) {
        DECISION_VALUE = value;
        PLAYER_MAX = DECISION_VALUE - 5;
        DEALER_MAX = PLAYER_MAX + 1;
    }

    /**
     * Just for testing the value of PLAYER_MAX after setting new decision value
     * @return value of PLAYER_MAX
     */
    public int getPlayerMax() {
        return PLAYER_MAX;
    }

    /**
     * reset the game
     *
     * Both the player Hand and dealer Hand should start with no cards. If the number of
     * cards in the deck is less than the reshuffle cutoff, then the method should create a fresh (complete),
     * shuffled deck. Otherwise, it should not modify the deck, just clear the player and dealer hands
     */
    public void reset() {
        playerHand.reset();
        dealerHand.reset();

        // refill and shuffle the deck if size is lower than CUT_OFF value
        if (deck.size() < CUT_OFF) {
            deck = new Deck();
            deck.shuffle();
        }
    }

    /**
     * deal out two cards to both players from the Deck
     */
    public void deal() {
        for (int i = 0; i < 2; i++){
            playerHand.add(deck.deal());
            dealerHand.add(deck.deal());
        }
    }

    /**
     * have the player draw cards until the total value of the player's hand is equal to or above 16
     * The method should return false if the player goes over 21 (bust)
     * @return False if player goes over 21
     */
    public boolean playerTurn() {
        while (playerHand.getTotalValue() < PLAYER_MAX) {
            // deal to player only if the values are less than PLAYER_MAX
            playerHand.add(deck.deal());
        }

        return playerHand.getTotalValue() <= DECISION_VALUE;
    }

    /**
     * Extension 1
     *
     * player turn interactive mode, the program will ask the players to either hit/stand/pick if players has
     * not busted once busted, it return false; it iterates if players hit or pick, until they bust or choose to stand
     * @return false if busted, true if stood
     */
    public boolean playerTurnInteractive() {
        Scanner input = new Scanner(System.in);
        while (playerHand.getTotalValue() <= DECISION_VALUE) {

            // enter the loop if player's cards value is less than DECISION_VALUE (default 21)
            System.out.println(">> You have: " + playerHand.toString());
            System.out.print(">> Do you want to hit (h) or stand (s) or pick (p): ");

            // allow user to stand, hit, or pick
            String choice = input.next();

            // input checking, only h/s/p is allowed for this input
            while (!choice.equals("h") && !choice.equals("s") && !choice.equals("p")) {
                System.out.print(">> [WARNING] INVALID RESPONSE, RE-ENTER (h/s/p): ");
                choice = input.next();
            }
            if (choice.equals("h")) {
                // if player wants to hit
                System.out.println(">> You chose to hit...");
                playerHand.add(deck.deal());
            } else if (choice.equals("p")) {
                // if player wants to pick
                System.out.println(">> You chose to hit by picking...");
                System.out.print(">> " + deck.size() + " cards remaining, enter index of card you want to pick at: ");
                int index = 0;

                // input checking, avoid non-integer input and out-ranged integer input
                while (input.hasNext()) {
                    if (input.hasNextInt()){
                        index = input.nextInt();
                        if (index >= deck.size()) {
                            // avoid out-ranged integer
                            System.out.print(">> [WARNING] INDEX OUT OF RANGE, ENTER AGAIN: ");
                        } else {
                            break;
                        }
                    } else {
                        // avoid non-integer
                        System.out.print(">> [WARNING] INVALID INPUT, ENTER AGAIN: ");
                        input.next();
                    }
                }
                playerHand.add(deck.pick(index));
            } else {
                System.out.println(">> You chose to stand...");
                return true;
            }
        }

        return false;
    }

    /**
     * have the dealer draw cards until the total of the dealer's hand is equal to or above 17
     * The method should return false if the dealer goes over 21.
     * @return False if player goes over 21
     */
    public boolean dealerTurn() {
        while (dealerHand.getTotalValue() < DEALER_MAX) {
            dealerHand.add(deck.deal());
        }
        return dealerHand.getTotalValue() <= DECISION_VALUE;
    }

    /**
     * should assign the new cutoff value to the internal reshuffle cutoff field
     * @param cutoff value of cutoff
     */
    public void setReshuffleCutoff(int cutoff) {
        this.CUT_OFF = cutoff;
    }

    /**
     * returns the current value of the reshuffle cutoff field
     * @return value of cutoff
     */
    public int getReshuffleCutoff() {
        return CUT_OFF;
    }

    /**
     * returns a String that has represents the state of the game. It may be helpful to show the player
     * and dealer hands as well as their current total value
     * @return message that represents the state of the game
     */
    public String toString() {

        String outString = ">> Game Status: \n" +
                ">> Player hand: " + playerHand.toString() + "\n" +
                ">> Player total value: " + playerHand.getTotalValue() + "\n" +
                ">> Dealer hand: " + dealerHand.toString() + "\n" +
                ">> Dealer total value: " + dealerHand.getTotalValue() + "\n";
        return outString;
    }

    /**
     * Extension 1
     *
     * Game in interactive mode
     * this game continue iterating unless player indicates no more game at the end of each round, it allow player
     * to either hit/stand/pick during his/her round
     * @return true if players wants one more game, false if not
     */
    public boolean gameInteractive() {
        System.out.println();
        // reset the deck and hands at the beginning, refill and shuffle the deck if the size of the deck is
        // lower than cutoff value
        reset();
        deal();

        String message;

        System.out.println("====================================");
        System.out.println("==      Blackjack Interactive     ==");
        System.out.println("====================================\n");

        if (playerTurnInteractive()) {
            if (dealerTurn()) {
                if (playerHand.getTotalValue() > dealerHand.getTotalValue()) {
                    message = ">> [Result] Player wins";
                } else if (playerHand.getTotalValue() == dealerHand.getTotalValue()) {
                    message = ">> [Result] It's a push";
                } else {
                    message = ">> [Result] Dealer wins";
                }
            } else {
                message = ">> [Result] Dealer busted! You won.";
            }
        } else {
            message = ">> [Result] You busted! Dealer won.";
        }

        System.out.println();
        System.out.println(message);
        System.out.printf("%-15s", ">> You have: ");
        System.out.println(playerHand.toString());
        System.out.println(">> Dealer has: " + dealerHand.toString());

        // ask the player if he/she wants another round
        System.out.print("\n>> One more? (y/n): ");
        Scanner input = new Scanner(System.in);

        return input.next().equals("y");

    }

    /**
     * play a single game of Blackjack following the procedure outlined above. The game method should call
     * the reset method at the start of each game. The game method should return a -1 if the dealer wins,
     * 0 in case of a push (tie), and a 1 if the player wins
     * @param verbose print out detailed result if true
     * @return -1, 0, 1 for dealer wins, push game, and player wins
     */
    public int game(boolean verbose) {
        reset();
        deal();

        String status;
        int result;

        StringBuilder outString = new StringBuilder();
        outString.append("================== Game Summary ==================\n\n")
                .append(">> Initial player hand: ")
                .append(playerHand.toString())
                .append("\n")
                .append(">> Initial dealer hand: ")
                .append(dealerHand.toString())
                .append("\n");

        if (playerTurn()) {
            if (dealerTurn()) {
                if (playerHand.getTotalValue() > dealerHand.getTotalValue()) {
                    status = "Player wins";
                    result = 1;
                } else if (playerHand.getTotalValue() == dealerHand.getTotalValue()) {
                    status = "It's a push";
                    result = 0;
                } else {
                    status = "Dealer wins";
                    result = -1;
                }

            } else {
                status = "Player wins";
                result = 1;
            }
        } else {
            status = "Dealer wins";
            result = -1;
        }

        outString.append(">> Final player hand: ")
                .append(playerHand.toString())
                .append("\n")
                .append(">> Final dealer hand: ")
                .append(dealerHand.toString())
                .append("\n")
                .append(">> Game Result: ")
                .append(status)
                .append("\n");

        if (verbose) {
            // prints out game summary if verbose is given true
            System.out.println(outString);
        }

        return result;
    }

    /**
     * Method for testing the class
     * @param args unused
     */
    public static void mainTesting(String[] args) {
        System.out.println(">> Testing Blackjack class...\n");
        System.out.println(">> Initializing blackjack class");
        Blackjack testBlackjack = new Blackjack(20);
        System.out.println(">> Testing getReshuffleCutoff()...");
        System.out.println(testBlackjack.getReshuffleCutoff());
        System.out.println(">> Testing setReshuffleCutoff()...");
        System.out.println(">> Setting new vale to 26...");
        testBlackjack.setReshuffleCutoff(49);
        System.out.println(testBlackjack.getReshuffleCutoff());

        System.out.println();
        System.out.println(">> Starting the game...");
        System.out.println(">> Dealing...");
        testBlackjack.deal();

        System.out.println(">> Player hand: " + testBlackjack.playerHand.toString());
        System.out.println(">> Dealer hand: " + testBlackjack.dealerHand.toString());

        String status = "";

        if (testBlackjack.playerTurn()) {
            if (testBlackjack.dealerTurn()) {
                if (testBlackjack.playerHand.getTotalValue() > testBlackjack.dealerHand.getTotalValue()) {
                    status = "Player";
                } else if (testBlackjack.playerHand.getTotalValue() == testBlackjack.dealerHand.getTotalValue()) {
                    status = "Push";
                } else {
                    status = "Dealer";
                }

            } else {
                status = "Player";
            }
        } else {
            status = "Dealer";
        }

        System.out.println("\n================ Game Conclusion ================");
        System.out.println(">> Player hand: " + testBlackjack.playerHand.toString());
        System.out.println(">> Dealer hand: " + testBlackjack.dealerHand.toString());
        System.out.println(status);

        System.out.println("\n>> Reset game");
        System.out.println(">> Deck size: " + testBlackjack.deck.size());
        testBlackjack.reset();
        System.out.println(">> Deck size after reset: " + testBlackjack.deck.size());

    }

    /**
     * Main method to output three games' results
     * @param args unused
     */
    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack(26);
        for (int i = 0; i < 3; i++) {
            blackjack.game(true);
        }
    }

}
