import java.util.ArrayList;
import java.util.Random;

/**
 * Project1: Blackjack - CS231, Colby College
 * A class representing the Deck object that holds a set of cards in Blackjack game
 *
 * @file Deck.java
 * @author Siyuan Peng
 * @date 2020-09-04
 */

public class Deck {

    // implemented a simple ArrayList created by myself
    private ImplementedArrayList<Card> deck;

    public Deck() {
        this.deck = new ImplementedArrayList<>();
        this.build();
    }

    /**
     * Build the deck, change ArrayList deck in-place
     */
    public void build() {
        this.reset();
        for (int i = 0; i < 4; i++) {
            for (int j = 1; j < 10; j++) {
                deck.add(new Card(j));
            }

            for (int j = 0; j < 4; j++) {
                deck.add(new Card(10));
            }
        }
    }

    /**
     * Clear deck in-place
     */
    public void reset() {
        deck.clear();
    }

    /**
     * Return size of the deck
     * @return size of the deck
     */
    public int size() {
        return deck.size();
    }

    /**
     * returns the top card (position zero) and removes it from the deck.
     * @return one card from the deck at index 0
     */
    public Card deal() {
        return deck.remove(0);
    }

    /**
     * (optional) returns the card at position i and removes it from the deck.
     * @param position position of card given
     * @throws IndexOutOfBoundsException when position is not within the range
     * @return one card from the deck at given position
     */
    public Card pick(int position) {
        if (position < 0 || position >= size()) {
            throw new IndexOutOfBoundsException("Index out of bound");
        }
        return deck.remove(position);
    }

    /**
     * Shuffle the deck in-place using random swapping method
     */
    public void shuffle() {

        // use currentTimeMillis as seed for the Random instance
        Random randomGenerator = new Random(System.currentTimeMillis());
        ImplementedArrayList<Card> tempArray = deck;

        for (int i = 0; i < tempArray.size(); i++) {
            int randomPosition = randomGenerator.nextInt(tempArray.size());
            // swap card between position i and random position
            Card tempCard = tempArray.get(i);
            tempArray.set(i, tempArray.get(randomPosition));
            tempArray.set(randomPosition, tempCard);
        }

        deck = tempArray;
    }

    /**
     * returns a String that has the contents of the deck "written" in a nice format (so that you can see the ordering of the card values)
     * @return card values in a String
     */
    public String toString() {
        StringBuilder outString = new StringBuilder("[ ");
        for (Card card : deck) {
            outString.append(card.getValue());
            outString.append(" ");
        }
        outString.append("]");
        return outString.toString();
    }

    /**
     * Method for testing the class
     * @param args unused
     */
    public static void main(String[] args) {
        System.out.println(">> Testing Deck class...\n");
        System.out.println(">> Initializing deck...");
        Deck testDeck = new Deck();
        System.out.println(">> Testing toString()...");
        System.out.println(testDeck);
        System.out.println(">> Testing deal()...");
        System.out.println(testDeck.deal().getValue());
        System.out.println(">> Testing pick() at index 33...");
        System.out.println(testDeck.pick(33).getValue());
        testDeck.build();
        System.out.println(">> Testing shuffle()");
        testDeck.shuffle();
        System.out.println(testDeck);
        System.out.println(">> Dealing the top card... ");
        System.out.println(testDeck.deal().getValue());
    }
}
