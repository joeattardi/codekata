import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

/**
 * A graph to hold word chain data, and find the shortest path between words.
 *
 * The graph is represented using a Map of each word to its next words.
 *
 * Part of Code Kata 19: Word Chains
 * http://codekata.pragprog.com/2007/01/kata_nineteen_w.html
 * 
 * @author Joe Attardi (joe@attardi.net)
 *
 */
public class WordGraph {
	private List<String> wordList;

	public WordGraph(final List<String> wordList) {
		this.wordList = wordList;
	}

	/**
	 * Finds the shortest path between two words using a breadth-first search of the graph.
	 * and building the graph on the fly.
	 *
	 * @param fromWord The starting word
	 * @param toWord The ending word
	 * @return a List of words in the shortest path, or null if no path could be found
	 */
	public List<String> findShortestPath(final String fromWord, final String toWord) {
		Map<String, String> previous = new HashMap<>();

		Set<String> visited = new HashSet<>();
		visited.add(fromWord);

		Queue<String> q = new LinkedList<>();
		q.add(fromWord);

		String word = null;
		while (!q.isEmpty() && !(word = q.remove()).equals(toWord)) {
			List<String> nextWords = getNextWords(word);			
			for (String nextWord : nextWords) {
				if (!visited.contains(nextWord)) {
					visited.add(nextWord);
					previous.put(nextWord, word);
					q.add(nextWord);
				}
			}
		}

		return getPath(previous, fromWord, toWord);
	}

	/**
	 * Gets all the words that can be reached from a given word by changing one letter.
	 * @param word The starting word.
	 * @return A List of all words that can be reached from the starting word 
	 */
	private List<String> getNextWords(final String word) {
		List<String> nextWords = new ArrayList<>();
		for (String currWord : wordList) {
			if (getWordDiff(word, currWord) == 1) {
				nextWords.add(currWord);
			}
		}

		return nextWords;
	}

	/**
	 * Calculates the number of letter differences between two words.
	 * @param word1 The first word
	 * @param word2 The second word
	 * @return The number of differing letters in the two words.
	 */
	private int getWordDiff(final String word1, final String word2) {
		int diff = 0;
		for (int n = 0; n < word1.length(); n++) {
			if (word1.charAt(n) != word2.charAt(n)) {
				diff++;
			}
		}

		return diff;
	}

	/**
	 * Constructs a path from a start word to an end word, using predecessor data from a breadth-first search.
	 * @param previous A Map from words to their predecessor in the shortest path
	 * @param fromWord The starting word in the path.
	 * @param toWord The ending word in the path.
	 * @return a List of words in the path given the precedessor data, or null if no path can be constructed.
	 */
	private List<String> getPath(final Map<String, String> previous, final String fromWord, final String toWord) {
		if (!previous.containsKey(toWord)) {
			return null;
		}

		List<String> path = new ArrayList<>();
		String word = toWord;
		while (!word.equals(fromWord)) {
			path.add(0, word);
			word = previous.get(word);
		}

		path.add(0, fromWord);
		return path;
	}
}
