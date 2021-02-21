/**
 * Project1: Blackjack - CS231, Colby College
 * Interactive mode for Blackjack, allows user to select hit, stand, or pick a card at user-input index
 * automatically print game result, dealer hand's content and player hand's content
 * user can play as many games as the user wants
 *
 * @file Interactive.java
 * @author Siyuan Peng
 * @date 2020-09-07
 */

public class Interactive {
    public static void main(String[] args) {
        Blackjack blackjack = new Blackjack(26);
        // loop until user says no to question "One more game?"
        while (blackjack.gameInteractive()) {}
    }
}
