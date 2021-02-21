/**
 * Project1: Blackjack - CS231, Colby College
 * A class representing the Card object in Blackjack game
 *
 * @file Card.java
 * @author Siyuan Peng
 * @date 2020-09-04
 */

public class Card {

    private int value;

    /**
     * A blank constructor for Card class, use for testing
     */
    public Card() {
        this(10);
    }

    /**
     * A constructor with the value of the card, possibly doing range checking
     * @param value given value of the card
     */
    public Card(int value) {
        this.value = value;
    }

    /**
     * Returns the numeric value of the card.
     * @return value of the card object
     */
    public int getValue() {
        return value;
    }

    /**
     * Return a string that represents the Card object. This will override the default toString method in the Object class
     * @return a message that represents one Card object
     */
    public String toString() {
        return "The card value is " + value + ".";
    }

    /**
     * Method for testing the class
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println(">> Testing Card class...\n");
        System.out.println(">> Creating a card of value 9...");
        Card testCard = new Card(9);
        System.out.println(">> Testing getValue()...");
        System.out.println(testCard.getValue());
        System.out.println(">> Testing toString()...");
        System.out.println(testCard);
    }
}
