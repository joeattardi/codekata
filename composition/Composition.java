import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * A fast solution to Code Kata Eight: Conflicting Objectives
 * http://codekata.pragprog.com/2007/01/kata_eight_conf.html
 *
 * Finds all six-letter words that are the composite of two smaller words.
 * For example:
 *    sonnet => son + net
 *    output => out + put
 *    tenor => ten + or
 *
 * This solution partitions the dictionary by word length and starting letter, forming a tree structure.
 * The dictionary has one List for each word length. Each of those Lists contains 26 lists, one for each
 * letter of the alphabet. Those lists will contain each word of the given length and starting with the
 * given letter. This makes a much smaller set of data to search for a given word.
 *
 * I extended this from the original problem. The default behavior is as the kata specifies - it will find
 * all six-letter words that are made up of two smaller words. The program takes two optional command-line
 * arguments. The first argument is the word length to use, and the second is the number of sub-words.
 *
 * Composition 6 2  --> prints all 6-letter words that are made up of 2 smaller words
 * Composition 10 3 --> prints all 10-letter words that are made up of 3 smaller words
 *
 * @author Joe Attardi (joe@attardi.net)
 */
public class Composition {
  private int wordLength;
  private int numSubWords;
  private Dictionary dictionary;

  public Composition(final int wordLength, final int numSubWords) {
    this.wordLength = wordLength;
    this.numSubWords = numSubWords;
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
      List<String> subWords = findSubWords(word, numSubWords);
      if (subWords != null) {
        System.out.println(word + " => " + subWords);
      }
    }
  }

  /**
   * Recursively searches for sub-words that make up a given word.
   * @param word The base word
   * @param numWords The number of sub-words to find inside the base word
   * @return A List of the sub-words that make up the word, or null if the word is not made up of numWords sub-words.
   */
  private List<String> findSubWords(final String word, final int numWords) {      
    if (numWords == 1) {      
      if (dictionary.findWord(word)) {
        return Arrays.asList(word);
      } else {
        return null;
      }
    } else {
      List<String> words = new ArrayList<>();
      for (int n = 1; n < word.length(); n++) {
        String subword = word.substring(0, n);        
        if (dictionary.findWord(subword)) {          
          List<String> subwords = findSubWords(word.substring(n), numWords - 1);
          if (subwords != null) {
            words.addAll(0, subwords);
            words.add(0, subword);
            return words;
          }
        }
      }
    }

    return null;
  }

  public static void main(String...args) {
    int wordLength = 6;
    int subWords = 2;

    if (args.length > 0) {
      try {
        wordLength = Integer.parseInt(args[0]);
      } catch (NumberFormatException nfe) {
        // ignore the input, use the default value
      }
    }

    if (args.length > 1) {
      try {
        subWords = Integer.parseInt(args[1]);
      } catch (NumberFormatException nfe) {
        // ignore the input, use the default value
      }
    }

    new Composition(wordLength, subWords).findComposites();
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
