import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Solution to Code Kata 19: Word Chains
 * http://codekata.pragprog.com/2007/01/kata_nineteen_w.html
 *
 * Builds a graph of all word transitions, then uses Dijkstra's algorithm to find
 * the shortest path from the starting word to the ending word.
 *
 * @author Joe Attardi (joe@attardi.net)
 */
public class WordChain {
    private List<String> wordList;
    private String startWord;
    private String endWord;
    private WordGraph graph = new WordGraph();

    public static void main(String...args) {
        if (args.length < 2) {
            System.err.println("Usage: WordChains <start word> <end word>");
            System.exit(1);
        } 

        String startWord = args[0];
        String endWord = args[1];
        if (startWord.length() != endWord.length()) {
            System.err.println("Words must be of the same length.");
            System.exit(1);
        }
        
        WordChain chainFinder = new WordChain(startWord, endWord);
        List<String> chain = chainFinder.getChain();

        System.out.println(chain != null ? chain : "No solution found.");
    }

    /**
     * Loads the word list and builds the word graph.
     * @param startWord The starting word for the chain.
     * @param endWord The ending word for the chain.
     */
    public WordChain(final String startWord, final String endWord) {
        this.startWord = startWord;
        this.endWord = endWord;
        
        wordList = loadWords(startWord.length());        
        buildWordGraph(startWord);
    }      

    /**
     * Gets the word chain.
     * @return the word chain, or null if no solution could be found
     */
    public List<String> getChain() {
        return graph.findShortestPath(startWord, endWord);
    }

    /**
     * Builds the word graph for a given word, then recursively builds
     * the word graph for all words it's connected to.
     * @param word The word to start at.
     */
    private void buildWordGraph(String word) {
    	for (String currWord : wordList) {
    		if (isChain(word, currWord)) {
    			graph.addTransition(word, currWord);
    			if (!graph.containsWord(currWord)) {
    				buildWordGraph(currWord);
    			}
    		}
    	}
    }
    
    /**
     * Checks if two words are adjacent in a word chain.
     * @param word1 The first word
     * @param word2 The second word
     * @return true if word1 and word2 differ by exactly one letter, false otherwise.
     */
    private boolean isChain(final String word1, final String word2) {
        int diff = 0;
        for (int n = 0; n < word1.length(); n++) {
            if (word1.charAt(n) != word2.charAt(n)) {
                diff++;
            }
        }

        return diff == 1;
    }

    /**
     * Loads the words from disk. Word chains can only be constructed between words of the same
     * length, so only the words of that length are loaded.
     * @param wordLength The length of words to load.
     */
    private List<String> loadWords(final int wordLength) {
        List<String> wordList = new ArrayList<String>();

        try (BufferedReader reader = new BufferedReader(new FileReader("wordlist.txt"))) {
            String word = "";
            while ((word = reader.readLine()) != null) {                
                if (word.length() == wordLength) {
                    wordList.add(word);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 

        return wordList;    
    }    
}