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
  private List<List<List<String>>> dictionary;

  public Composition() {
    dictionary = loadDictionary();
  }

  /**
   * Processes the dictionary, finding all composite word pairs for the 6-letter words.
   * Results will be printed to stdout.
   */
  public void findComposites() {
    List<List<String>> words = dictionary.get(5);
    for (List<String> subWords : words) {
      for (String word: subWords) {
        processWord(word);
      }
    }
  }

  /**
   * Finds any composite word pairs for a given word.
   * @param word The word to operate on.
   */
  private void processWord(final String word) {
    for (int n = 4; n >= 0; n--) {
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
    String word1 = word.substring(0, firstWordLength + 1);
    if (findWord(word1)) {
      String word2 = word.substring(firstWordLength + 1);
      if (findWord(word2)) {
        System.out.println(word + " => " + word1 + " + " + word2);
      }
    }
  }

  /**
   * Checks if a word exists in the dictionary.
   * @param word The word to look up.
   * @return true if the word is found in the dictionary, false if not.
   */
  private boolean findWord(final String word) {
    return dictionary.get(word.length() - 1).get(getFirstLetterIndex(word)).contains(word);
  }

  /**
   * Gets the first letter index of a given word.
   * @param word The word to operate on.
   * @return An integer, between 0 and 25, representing the letter's position in the alphabet
   */
  private int getFirstLetterIndex(String word) {
    return (int) word.toLowerCase().charAt(0) - (int) 'a';
  }

  /**
   * Processes the word list file and creates the dictionary data structure.
   */
  private List<List<List<String>>> loadDictionary() {
    List<List<List<String>>> dictionary = new ArrayList<>();
    for (int len = 0; len < 6; len++) {
      List<List<String>> letterList = new ArrayList<>(26);
      for (int letter = 0; letter < 26; letter++) {
        List<String> wordList = new ArrayList<>();
        letterList.add(wordList);
      }
      dictionary.add(letterList);
    }

    try (BufferedReader reader = new BufferedReader(new FileReader("wordlist.txt"))) {
      String line = "";
      while ((line = reader.readLine()) != null) {
        if (line.length() <= 6) {
          List<String> wordList = dictionary.get(line.length() - 1).get(getFirstLetterIndex(line));
          wordList.add(line);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    return dictionary;
  }

  public static void main(String...args) {
    new Composition().findComposites();
  }
}