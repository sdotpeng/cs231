/**
 * File: Shuffle.java
 * Author: Siyuan Peng
 * Date: 09/01/2020
 */

import java.util.ArrayList;
import java.util.Random;

public class Shuffle {

    public static void main(String[] args) {
        ArrayList<Integer> arr = new ArrayList<Integer>();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            arr.add(random.nextInt(100));
        }

        System.out.println("Print all values in the ArrayList: ");

        for (int i = 0; i < 10; i++) {
            System.out.print(arr.get(i) + " ");
        }

//        System.out.println("\nRemove value in the ArrayList:");
//
//        for (int i = 0; i < 10; i++) {
//            System.out.print(arr.remove(random.nextInt(arr.size())) + " ");
//        }

        System.out.println("\nShuffle the ArrayList with naive method: ");
        System.out.println(shuffleArrayList(arr).toString());
    }

    /**
     * Shuffle the elements in an ArrayList
     * @param arr input ArrayList
     * @return an ArrayList with shuffled elements
     */
    private static ArrayList<Integer> shuffleArrayList(ArrayList<Integer> arr) {

        Random random = new Random();

        ArrayList<Integer> output = new ArrayList<Integer>();

        int size = arr.size();
        for(int i = 0; i < size; i++) {
            output.add(arr.remove(random.nextInt(arr.size())));
        }

        return output;
    }

}
