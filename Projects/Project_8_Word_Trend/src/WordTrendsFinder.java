public class WordTrendsFinder {

    public static void main(String[] args) {

        // I am using a straight forward method here, creating 8 different word counter
        // to analyze the text file in corresponding year
        WordCounter2 wc08 = new WordCounter2("hashmap");
        wc08.analyze("reddit_comments_2008.txt");
        WordCounter2 wc09 = new WordCounter2("hashmap");
        wc09.analyze("reddit_comments_2009.txt");
        WordCounter2 wc10 = new WordCounter2("hashmap");
        wc10.analyze("reddit_comments_2010.txt");
        WordCounter2 wc11 = new WordCounter2("hashmap");
        wc11.analyze("reddit_comments_2011.txt");
        WordCounter2 wc12 = new WordCounter2("hashmap");
        wc12.analyze("reddit_comments_2012.txt");
        WordCounter2 wc13 = new WordCounter2("hashmap");
        wc13.analyze("reddit_comments_2013.txt");
        WordCounter2 wc14 = new WordCounter2("hashmap");
        wc14.analyze("reddit_comments_2014.txt");
        WordCounter2 wc15 = new WordCounter2("hashmap");
        wc15.analyze("reddit_comments_2015.txt");

        // Prints out the word and its frequency for each year
        System.out.println("Year,2008,2009,2010,2011,2012,2013,2014,2015");
        for (String arg : args) {
            System.out.print(arg + "," + wc08.getFrequency(arg) + "," + wc09.getFrequency(arg) + ","
                    + wc10.getFrequency(arg) + "," + wc11.getFrequency(arg) + "," + wc12.getFrequency(arg) + "," +
                    wc13.getFrequency(arg) + "," + wc14.getFrequency(arg) + "," + wc15.getFrequency(arg));
            System.out.println("\n");
        }
    }
}
