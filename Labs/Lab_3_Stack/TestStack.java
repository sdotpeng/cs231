public class TestStack {

    public static void main(String[] argv) {
        System.out.println("Testing default constructor");
        CellStack cs = new CellStack(); // test constructor w/no arguments

        System.out.println("Testing pre-size constructor");
        CellStack cs2 = new CellStack(50); // test constructor w/arguments

        System.out.println("Pushing lots of stuff on stack 1");
        for(int i=0;i<20;i++) {
            cs.push(new Cell(0, 0, i));
        }

        System.out.println("Pushing more stuff on stack 2");
        for(int i=0;i<100;i++) {
            cs2.push(new Cell(i, i, 1));
        }

        System.out.println("Popping 99 things off of stack 2");
        for(int i=0;i<99;i++) {
            cs2.pop();
        }
        System.out.println("There should be 1 thing left on stack 2: " + cs2.size());
        System.out.println("stack 2 should not be empty: " + cs2.isEmpty());

        System.out.println("Popping 11 things off stack 1");
        for(int i=0;i<11;i++) {
            cs.pop();
        }

        System.out.println("There should be 9 things left on stack 1: " + cs.size());
        System.out.println("stack 1 should not be empty: " + cs.isEmpty());

        System.out.println("Popping 9 things off stack 1");
        for(int i=0;i<9;i++) {
            cs.pop();
        }
        System.out.println("stack 1 should be empty: " + cs.isEmpty());

        System.out.println("Popping 2 things off stack 2");

        cs2.pop();
        cs2.pop();

        System.out.println("stack 2 should be empty: " + cs.isEmpty());

        System.out.println("this has been your test function. Bye");
    }
}
