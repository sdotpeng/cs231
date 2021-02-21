import java.util.ArrayList;

/**
 * Project1: Blackjack - CS231, Colby College
 * A class representing the Hand object in Blackjack game,
 * should hold a set of cards
 *
 * @file Hand.java
 * @author Siyuan Peng
 * @date 2020-09-04
 */

public class Hand {

    // used simple-implemented ArrayList created by myself
    private ImplementedArrayList<Card> hand;

    /**
     * initialize the ArrayList
     */
    public Hand() {
        this.hand = new ImplementedArrayList<>();
    }

    /**
     * reset the hand to empty
     */
    public void reset() {
        hand.clear();
    }

    /**
     * add the card object to the hand
     * @param card given card
     */
    public void add(Card card) {
        hand.add(card);
    }

    /**
     * returns the number of cards in the hand
     * @return number of cards in the hand object
     */
    public int size() {
        return hand.size();
    }

    /**
     * returns the card with index i
     * @param position index of card
     * @return card at index position
     */
    public Card getCard(int position) {
        return hand.get(position);
    }

    /**
     * returns the sum of the values of the cards in the hand
     * @return sum
     */
    public int getTotalValue() {
        int sum = 0;
        for (Card card: hand) {
            sum += card.getValue();
        }
        return sum;
    }

    /**
     * returns a String that has the contents of the hand presented in a nice format
     * @return output String
     */
    public String toString() {
        StringBuilder outString = new StringBuilder("[ ");
        for (Card card: hand) {
            // iterates through all cards in the hand
            outString.append(card.getValue());
            outString.append(" ");
        }
        outString.append("] ").append("Total value: ").append(this.getTotalValue());
        return outString.toString();
    }

    /**
     * Method for testing the class
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println(">> Testing Hand class...\n");
        System.out.println(">> Initializing a Hand object...");
        Hand testHand = new Hand();
        System.out.println(">> Testing add()...");
        System.out.println(">> Adding card 1 and 9...");
        Card testCardOne = new Card(1);
        Card testCardTwo = new Card(9);
        testHand.add(testCardOne);
        testHand.add(testCardTwo);
        System.out.println(">> Testing size()...");
        System.out.println(testHand.size());
        System.out.println(">> Testing getCard() at index 1...");
        System.out.println(testHand.getCard(1).getValue());
        System.out.println(">> Testing getTotalValue()...");
        System.out.println(testHand.getTotalValue());
        System.out.println(">> Testing toString()...");
        System.out.println(testHand);
        System.out.println(">> Testing reset()...");
        testHand.reset();
        System.out.println(testHand);
    }

}
