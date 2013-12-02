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
	private Map<String, List<String>> wordMap = new HashMap<>();
	
	/**
	 * Adds a transition between two words.
	 * @param fromWord The starting word.
	 * @param toWord The transition word.
	 */
	public void addTransition(final String fromWord, final String toWord) {
		List<String> transitions = wordMap.get(fromWord);
		if (transitions == null) {
			transitions = new ArrayList<String>();
			wordMap.put(fromWord, transitions);
		}
		
		transitions.add(toWord);
	}
	
	/**
	 * Checks if the graph contains a given word.
	 * @param word The word to check.
	 * @return true if the graph contains the given word, false if not.
	 */
	public boolean containsWord(final String word) {
		return wordMap.containsKey(word);
	}
	
	/**
	 * Finds the shortest path between two words using a breadth-first search of the graph.
	 *
	 * @param fromWord The starting word
	 * @param toWord The ending word
	 * @return a List of words in the shortest path, or null if no path could be found
	 */
	public List<String> findShortestPath(final String fromWord, final String toWord) {
		if (!wordMap.containsKey(fromWord) || !wordMap.containsKey(toWord)) {
			return null;
		}

		Map<String, String> previous = new HashMap<>();

		Set<String> visited = new HashSet<>();
		visited.add(fromWord);

		Queue<String> q = new LinkedList<>();
		q.add(fromWord);

		String word = null;
		while (!q.isEmpty() && !(word = q.remove()).equals(toWord)) {
			for (String nextWord : wordMap.get(word)) {
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
