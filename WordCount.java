/**
 * WordCount creates an object that contains a word and its associated count.
 * @author Ruben Boero
 */
public class WordCount implements Comparable<WordCount> {
    public String word;
    public int count;

    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    /**
     * function to return the word value stored within a WordCount object
     * @return the word stored in a WordCount Object
     */
    public String getWord() {
        return word;
    }

    /**
     * function to return the count value stored within a WordCount object
     * @return the count stored in a WordCount Object
     */
    public int getCount() {
        return count;
    }
    
    /**
     * to string function for the WordCount class
     */
    public String toString() {
        String str = this.word + ": " + this.count;
        return str;
    }

    public int compareTo(WordCount count){
        if (this.count == count.count) {
            return 0;
        } else if (this.count < count.count) {
            return -1;
        } else {
            return 1;
        }
    }
    
}
    

