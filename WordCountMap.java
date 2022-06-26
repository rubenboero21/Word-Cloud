import java.util.ArrayList;
import java.util.Collections;

/**
 * A class whose objects are binary search trees. Nodes contain a word and the count
 * of the number of times that word appears. Tree is ordered alphabetically.
 * @author Ruben Boero with help from Rebecca Hicke
 */
public class WordCountMap {

    private Node root;

    /**
     * constructor for the WordCountMap class
     */
    public WordCountMap() { 
        this.root = null;
    }

    private class Node {
        public String word;
        public int count;

        //public WordCount wordCount;
        public Node left;
        public Node right;

        /**
         * constructor for the Node class
         * @param dataItem
         */
        private Node(String dataItem) {
            word = dataItem;
            count = 1; // initializing a node with a count of 1 bc when the first item 
                       // is added there is 1 of it

            //wordCount = new WordCount(dataItem, 1);
            left = null;
            right = null;
        }
    }

    /**
     * Helper function for the toString function
     * @param root
     * @return a string representation of the WorldCountMap BST in alphabetical order
     */
    private String toStringHelper(Node root) {
        String str = "";
        if (root != null) {
            str = toStringHelper(root.left) + root.word + root.count + toStringHelper(root.right);
        }
        return str;
    }

    public String toString() {
        return toStringHelper(this.root);
    }

    /**
     * Helper function for the contains function
     * @param word word to search for
     * @param root root to begin searching at
     * @return true if the word is found, false otherwise
     */
    private boolean containsHelper(String word, Node root) {
        if (root == null) {
            return false;
        } else if (root.word.equals(word)) {
            return true;
        } else {
            int comparison = word.compareTo(root.word); // CHECK THAT THIS IS THE RIGHT ORDER OF COMPARISON
            if (comparison < 0) {
                return containsHelper(word, root.left);
            } else {
                return containsHelper(word, root.right);
            }
        }
    }

    /** Returns true if the specified word is contained in the WordCountMap
    * and false otherwise.
    */
    public boolean contains(String word) {
        return containsHelper(word, this.root);
    }

    /**
     * Helper function for the incrementCount function
     * @param word
     * @param root
     * @return
     */
    private Node incrementCountHelper(String word, Node root) {
        if (root == null) {
            // create and return the new node to add
            Node newNode = new Node(word);
            return newNode;
        } else if (root.word.equals(word)) {
            // increment count
            root.count = root.count + 1;
        } else {
            int comparison = word.compareTo(root.word);

            // if node to add is smaller than the parent
            if (comparison < 0) {
                // try to add it to the left child spot
                root.left = incrementCountHelper(word, root.left);
                //return incrementCountHelper(word, root.left);
            } else { // otherwise try to add it to the right child spot
                root.right = incrementCountHelper(word, root.right);
                //return incrementCountHelper(word, root.right);
            }
        }
        return root;
    }

    /**
     * If the specified word is already in this WordCountMap, then its
     * count is increased by one. Otherwise, the word is added to this map
     * with a count of 1.
     */
    public void incrementCount(String word) {
        // if the tree is empty, add a root node
        if (root == null) {
            Node newNode = new Node(word);
            this.root = newNode;
        } else { // otherwise find where to add the new node, then add it
            incrementCountHelper(word, this.root);
        }

    }

    /*
    At least one of your getWordCountsBy methods in your WordCountMap should use a 
    tree traversal to get the list of words and counts in the correct order. For the 
    other method, you can use built in Java methods if you’d like.
    */
    // what built in methods are there?
        // theres an iterator method in ArryList class
    // i was just gonna use recursion to try and traverse the tree

    /*
    Also, I heard back from Layla; you’ll want to convert to WordCount objects only in the functions
     that return lists of WordCounts instead of including them in the tree
    */

    /**
     * A helper function for the getWordCountsByCount function. Creates an ArrayList with items from 
     * the BST, then sorts it in descending order.
     * @param list ArrayList to update
     * @return an ArrayList sorted in descending order
     */
    private ArrayList<WordCount> descending(ArrayList list) {
        list = inOrder(this.root, list); // create the ArrayList
        Collections.sort(list, Collections.reverseOrder()); // sort the ArrayList

        return list; // return the sorted ArrayList
    }    
        
    /**
     * Returns an array list of WordCount objects, one per word stored in this
     * WordCountMap, sorted in decreasing order by count.
     */
    public ArrayList<WordCount> getWordCountsByCount() {
        ArrayList wordList = new ArrayList(); // create new array list with capacity 10

        wordList = descending(wordList);
        return wordList;
    }
    
    /**
     * Helper function for the getWordCountsByWord and getWordCountsByCount functions
     * @param root root to begin at
     * @param list ArrayList to update
     * @return Returns a list of WordCount objects, one per word stored in this
     * WordCountMap, sorted alphabetically by word. (Does this with in-order traversal)
     */
    private ArrayList<WordCount> inOrder(Node root, ArrayList list) {
        if (root != null) { // if there is a node to add to the list
            inOrder(root.left, list); // search the left child of the current root

            // add the current root to the list
            WordCount current = new WordCount(root.word, root.count); 
            
            // make sure there is room for the Node to be added to the list, then add it
            list.ensureCapacity(list.size() + 1);
            list.add(current);

            inOrder(root.right, list); // search the right child of the current root
        }
        return list; // return the finished ArrayList
    }
    /**
     * Returns a list of WordCount objects, one per word stored in this
     * WordCountMap, sorted alphabetically by word.
     */
    public ArrayList<WordCount> getWordCountsByWord() {
        ArrayList wordList = new ArrayList(); // create new array list with capacity 10

        return inOrder(this.root, wordList);
    }

    public static void main(String[] args) {
        WordCountMap tree = new WordCountMap(); 

        tree.incrementCount("banana");
        tree.incrementCount("apple");
        tree.incrementCount("croissant");
        tree.incrementCount("banana");
        tree.incrementCount("apple");

        tree.incrementCount("croissant");
        tree.incrementCount("croissant");
        System.out.println(tree);

        // System.out.println(tree.contains("croissant")); 
        // System.out.println(tree.contains("false")); 

        System.out.println();
        ArrayList alphaList = new ArrayList();
        alphaList = tree.getWordCountsByWord();

        System.out.println(alphaList);    
        
        System.out.println();

        ArrayList countList = new ArrayList();
        countList = tree.getWordCountsByCount();

        System.out.println(countList);

    }
}
