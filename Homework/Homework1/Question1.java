/**
	Homework1

	File: Question1.java
	Author: Siyuan Peng
	Date: 2020-09-03

	Description: Use a while loop to print the string "Java is cool!" backwards.
 */

public class Question1 {

	public static void main(String[] args) {

		String fact = "Java is cool!";

    	//write a while loop here
		int last = fact.length() - 1;
		int index = last;

		while (index >= 0) {
			System.out.print(fact.charAt(index));
			index--;
		}

	}

}


