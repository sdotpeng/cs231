/**
	Homework1

	File: Question2.java
	Author: Siyuan Peng
	Date: 2020-09-03

	Description: Find the printed value before you run this code:
 */

public class Question2 {

    public static void main(String[] args) {

        int a =2, b = 3;

        System.out.println(a++);
        System.out.println(a++);
        System.out.println(++a);

        a = ++b;

        System.out.println(b++);
        System.out.println(a);
        System.out.println(b);

        // It will print 2,3,5,4,4,5
    }

}