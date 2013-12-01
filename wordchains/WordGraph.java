import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	 * Finds the shortest path in the graph between two words. Uses Dijkstra's algorithm.
	 * @param fromWord The starting word
	 * @param toWord The ending word
	 * @return A List of the words that make up the shortest path between the two words, or null if no path was found.
	 */
	public List<String> findShortestPath(final String fromWord, final String toWord) {
		if (!wordMap.containsKey(fromWord) || !wordMap.containsKey(toWord)) {
			return null;
		}

		Map<String, Integer> distances = new HashMap<>();
		List<String> visited = new ArrayList<>();
		Map<String, String> previous = new HashMap<>();
		Set<String> wordsToVisit = new HashSet<>();
		
		distances.put(fromWord, 0);
		wordsToVisit.add(fromWord);
		
		String visitWord = null;
		while (!wordsToVisit.isEmpty() && !(visitWord = getNextWord(wordsToVisit, distances)).equals(toWord)) {
			wordsToVisit.remove(visitWord);
			visited.add(visitWord);

			for (String nextWord : wordMap.get(visitWord)) {
				int distance = distances.get(visitWord) + 1;
				if (!distances.containsKey(nextWord) || distance < distances.get(nextWord)) {
					distances.put(nextWord, distance);
					previous.put(nextWord, visitWord);
					if (!visited.contains(nextWord)) {
						wordsToVisit.add(nextWord);
					}
				}
			}
		}
		
		// Check if we have a solution. We found a path if previous[toWord] exists.
		if (previous.containsKey(toWord)) {
			List<String> path = new ArrayList<>();
			
			String word = toWord;
			while (!word.equals(fromWord)) {
				String previousWord = previous.get(word);
				path.add(0, previousWord);
				word = previousWord;
			}
			
			path.add(toWord);
			return path;
		} else {
			return null;
		}
		
		
	}
	
	/**
	 * Determines the next word to visit to find the shortest path.
	 * @param wordsToVisit The set of candidate words to visit.
	 * @param distances The currently known distances.
	 */
	private String getNextWord(final Set<String> wordsToVisit, final Map<String, Integer> distances) {
		int minDistance = Integer.MAX_VALUE;
		String nextWord = null;
		
		for (String word : wordsToVisit) {
			int distance = distances.get(word);
			if (distance < minDistance) {
				minDistance = distance;
				nextWord = word;
			}
		}
		
		return nextWord;
	}
}
