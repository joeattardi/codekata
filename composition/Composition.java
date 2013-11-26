import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.List;
import java.util.ArrayList;

/**
 * Solution to Code Kata Eight: Conflicting Objectives
 * http://codekata.pragprog.com/2007/01/kata_eight_conf.html
 *
 * Finds all six-letter words that are the composite of two smaller words.
 * For example:
 *    sonnet => son + net
 *    output => out + put
 *    tenor => ten + or
 *
 * @author Joe Attardi (joe@attardi.net)
 */
public class Composition {
  private int wordLength;
  private Dictionary dictionary;

  public Composition(int wordLength) {
    this.wordLength = wordLength;
    dictionary = new Dictionary(wordLength);
    loadDictionary();
  }

  private void loadDictionary() {
    try (BufferedReader reader = new BufferedReader(new FileReader("wordlist.txt"))) {
      String word = "";
      while ((word = reader.readLine()) != null) {
        if (word.length() <= wordLength) {
          dictionary.addWord(word);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  /**
   * Processes the dictionary, finding all composite word pairs for the 6-letter words.
   * Results will be printed to stdout.
   */
  public void findComposites() {
    List<String> words = dictionary.getWords(wordLength);
    for (String word: words) {
      processWord(word);
    }
  }

  /**
   * Finds any composite word pairs for a given word.
   * @param word The word to operate on.
   */
  private void processWord(final String word) {
    for (int n = wordLength - 1; n > 0; n--) {
      findSubWords(word, n);
    }
  }

  /**
   * Finds any composite word pairs for a given word, of a given first word length.
   * For example:
   *   For the word "soothe" and first word length 4, we could find "soot" (4 letters) and "he".
   *
   * @param word The word to operate on.
   * @param firstWordLength The length of the first sub-word.
   */
  private void findSubWords(final String word, final int firstWordLength) {
    String word1 = word.substring(0, firstWordLength);
    if (dictionary.findWord(word1)) {
      String word2 = word.substring(firstWordLength);
      if (dictionary.findWord(word2)) {
        System.out.println(word + " => " + word1 + " + " + word2);
      }
    }
  }

  public static void main(String...args) {
    int wordLength = 6;

    if (args.length > 0) {
      try {
        wordLength = Integer.parseInt(args[0]);
      } catch (NumberFormatException nfe) {
        // ignore the input, use the default value
      }
    }
    new Composition(wordLength).findComposites();
  }
}

class Dictionary {
  private List<List<List<String>>> dictionary = new ArrayList<>();

  /**
   * Creates a Dictionary with a given maximum word length.
   * @param maxWordLength The maximum word length this Dictionary will contain.
   */
  public Dictionary(final int maxWordLength) {
    for (int len = 0; len < maxWordLength; len++) {
      List<List<String>> letterList = new ArrayList<>();
      for (int letter = 0; letter < 26; letter++) {
        letterList.add(new ArrayList<String>());
      }

      dictionary.add(letterList);
    }
  }

  /**
   * Gets all the words of a given length.
   * @param length The desired length
   * @return A List<String> containing all words in the dictionary of the specified length.
   */
  public List<String> getWords(final int length) {
    List<String> words = new ArrayList<>();
    for (int letter = 0; letter < 25; letter++) {
      words.addAll(dictionary.get(length - 1).get(letter));
    }

    return words;
  }

  /**
   * Searches the dictionary for a given word.
   * @param word The word to search for.
   * @return true if the word is found, false if not
   */
  public boolean findWord(final String word) {
    return getWordList(word).contains(word);
  }

  /**
   * Gets the correct word list for storing or retrieving a given word.
   * @param word The word to store or retrieve.
   * @return The proper List<String> to contain the given word.
   */
  private List<String> getWordList(final String word) {
    // Some words contain characters like hyphens, which will make the list index < 0. To prevent an
    // ArrayIndexOutOfBoundsException, if the first character is a non-alphabetic character, stick it
    // in the first list.
    return dictionary.get(word.length() - 1).get(Math.max(0, (int) word.toLowerCase().charAt(0) - (int) 'a'));
  }

  /**
   * Adds a word to the dictionary.
   * @param word The word to add
   */
  public void addWord(final String word) {
    getWordList(word).add(word);
  }
}
