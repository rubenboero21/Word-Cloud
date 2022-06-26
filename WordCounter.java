import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * WordCounter.java
 * Reads a given file and prints one of three outputs. 
 * 1) frequency: list of words from most frequent to least frequent excluding stop words.
 * 2) alphabetical: list of words in alphabetical order.
 * 3) cloud: creates a word cloud of a given size containing the most frequent words in a file
 * The main program will be in charge of opening a text 
 * file, counting all of the words it contains, and producing one of 
 * three types of output.
 * 
 * @author Ruben Boero
 * 
 * How to use the scanner was found on this site:
 * https://www.w3schools.com/java/java_files_read.asp
 */
public class WordCounter {

    /**
     * Prints to the screen a list of words in alphabetical order and the number of times the word
     * appears in the given text file. The list does not include words in the given set of 
     * stop words.
     * @param myScanner
     * @param readFile
     * @param tree
     * @param stopWords
     */
    private static void alphabetical(Scanner myScanner, File readFile, WordCountMap tree, ArrayList stopWords) {
        String finalStr; // string to store the edited line (no punctuation)
        boolean isStop;

            while (myScanner.hasNext()) {
                isStop = false; // set isStop to false when checking a new word

                String data = myScanner.next(); // get the next word
                // get rid of punctuation and make lowercase to match stop words file
                finalStr = data.replaceAll("[^a-zA-Z]","").toLowerCase();
                    
                // check if the current word is a stop word
                for (int i = 0; i < stopWords.size(); i++) {
                    if (stopWords.get(i).equals(finalStr)){
                        isStop = true;
                    }
                }

                // if the word is not a stop word, add it to the tree
                if (isStop == false) {
                    tree.incrementCount(finalStr);
                }
        }
        // create an array from the tree of all the words
        ArrayList alphaList = new ArrayList();
        alphaList = tree.getWordCountsByWord();

        // print the array
        for (int i = 0; i < alphaList.size(); i++) {
            System.out.println(alphaList.get(i));
        }
    }

    /**
     * Prints to the screen a list of words in descending order of most frequently occuring along
     * with the number of times the word appears in a given text file. The 
     * list does not include words in the given set of stop words.
     * @param myScanner
     * @param readFile
     * @param tree
     * @param stopWords
     */
    private static void frequency(Scanner myScanner, File readFile, WordCountMap tree, ArrayList stopWords) {
        String finalStr;
        boolean isStop;

        while (myScanner.hasNext()) {
            isStop = false; // set isStop to false when checking a new word

            String data = myScanner.next(); // split scanner every word, not every line
            // get rid of punctuation and make lowercase to match stop words file
            finalStr = data.replaceAll("[^a-zA-Z]","").toLowerCase();

            // check if the current word is a stop word
            for (int i = 0; i < stopWords.size(); i++) {
                if (stopWords.get(i).equals(finalStr)){
                    isStop = true;
                }
            }

            // if the word is not a stop word, add it to the tree
            if (isStop == false) {
                tree.incrementCount(finalStr);
            }
        }
        // create an array from the tree of all the words
        ArrayList freqList = new ArrayList();
        freqList = tree.getWordCountsByCount();
        
        // print the array
        for (int i = 0; i < freqList.size(); i++) {
            System.out.println(freqList.get(i));
        }
    }

    /**
     * Returns an array list of words in descending order from a given text file, exluding given stop words.
     * @param myScanner
     * @param readFile
     * @param tree
     * @param stopWords
     * @return an ArrayList containing the words sorted in decreasing order of count
     */
    private static ArrayList frequencyNoPrint(Scanner myScanner, File readFile, WordCountMap tree, 
    ArrayList stopWords) {
        String finalStr;
        boolean isStop;

        while (myScanner.hasNext()) {
            isStop = false; // set isStop to false when checking a new word

            String data = myScanner.next(); // split scanner every word, not every line
            // get rid of punctuation and make lowercase to match stop words file
            finalStr = data.replaceAll("[^a-zA-Z]","").toLowerCase();
            
            // check if the current word is a stop word
            for (int i = 0; i < stopWords.size(); i++) {
                if (stopWords.get(i).equals(finalStr)){
                    isStop = true;
                }
            }

            // if the word is not a stop word, add it to the tree
            if (isStop == false) {
                tree.incrementCount(finalStr);
            }
        }
        // create an array from the tree of all the words
        ArrayList freqList = new ArrayList();
        freqList = tree.getWordCountsByCount();
        
        return freqList;
    }

    /**
     * Prints the html text for a word cloud
     * @param myScanner
     * @param readFile
     * @param tree
     * @param stopWords
     * @param numWords
     */
    private static void cloud(Scanner myScanner, File readFile, WordCountMap tree, 
    ArrayList stopWords, int numWords) {
        
        // create an array list with words in descending order, excluding the stop words
        ArrayList arrayList = frequencyNoPrint(myScanner, readFile, tree, stopWords);

        // shorten the list of words down to be the size given by the user
        while (arrayList.size() > numWords) {
            arrayList.remove(arrayList.size() - 1);
        }

        // create the html
        String html = WordCloudMaker.getWordCloudHTML("Word Cloud", arrayList);
        
        // print the html to the screen
        System.out.println(html);
    }

    /**
     * Finds and returns the number of words in a given file
     * @param readFile
     * @return word count
     */
    private static int getWordCount(File readFile) {
        int wordCount = 0;

        try {
            Scanner wordCounter = new Scanner(readFile);

            // count all the words in the file
            while (wordCounter.hasNext()) {
                wordCount++;
                wordCounter.next();
            }

            wordCounter.close(); // done with scanner

            return wordCount; // return the word count

        } catch (FileNotFoundException e) {
            System.out.println("No file was found with that name");
        }
        return wordCount; // ensure that something is always returned
    }

    public static void main(String[] args) {

        try {
            String function = args[0];
            File readFile = new File(args[1]);
            File stopWords = new File("StopWords.txt");

            // create a list of stop words
            Scanner myScanner = new Scanner(stopWords);
            ArrayList stopWordsList = new ArrayList<>();

            while (myScanner.hasNext()) {
                String word = myScanner.next(); // get the next word
                stopWordsList.add(word); // add it to the list of stop words
            }
            
            myScanner.close();

            // make an empty array to use later
            ArrayList emptyStopWords = new ArrayList<>();

            // set scanner to read the input file
            myScanner = new Scanner(readFile);
            // create a tree to add the words from input file into
            WordCountMap wordTree = new WordCountMap(); 

            // decide what to do with command line argument
            if (function.equals("alphabetical")) {
                alphabetical(myScanner, readFile, wordTree, stopWordsList);
            } else if (function.equals("frequency")) {
                frequency(myScanner, readFile, wordTree, stopWordsList);
            } else if (function.equals("cloud")) {
                // store the number of words to use in the word cloud
                int numWords = Integer.parseInt(args[2]);

                /* check to see if there are enough words in the text file to make a 
                cloud of the given size */
                
                int wordCount = getWordCount(readFile);

                /*if there are enough non-stop-words to make a cloud with the specified number
                of words, then make it. If there are not enough non-stop-words, use all words in
                in the given file, including stop words*/
                if (wordCount >= numWords) {
                    System.out.println("and here");
                    cloud(myScanner, readFile, wordTree, stopWordsList, numWords);
                } else {
                    System.out.println("here");
                    // note that emptyStopWords is an empty is an empty array
                    cloud(myScanner, readFile, wordTree, emptyStopWords, numWords);
                }
            }

            myScanner.close(); // done with the scanner, so close it

        } catch (FileNotFoundException e) {
            System.out.println("No file was found with that name");
        }
    }
}
