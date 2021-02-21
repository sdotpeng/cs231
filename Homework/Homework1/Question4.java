/**
	Homework1

	File: Question4.java
	Author: Siyuan Peng
	Date: 2020-09-03

	Description: Use a for loop to print the string "Java is cool!" backwards.
 */



public class Question4 {

	public static void main(String[] args) {

		String fact = "Java is cool!";

    	//write a for loop here
		int last = fact.length() - 1;

		for (int i = last; i >= 0; i--) {
			System.out.print(fact.charAt(i));
		}

	}

}


