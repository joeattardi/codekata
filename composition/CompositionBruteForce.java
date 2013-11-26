import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.LinkedList;

/**
 * A brute-force implementation of Code Kata Eight: Conflicting Objectives
 * http://codekata.pragprog.com/2007/01/kata_eight_conf.html
 *
 * Finds all six-letter words that are the composite of two smaller words.
 * For example:
 *    sonnet => son + net
 *    output => out + put
 *    tenor => ten + or
 *
 * This solution puts all the words in one big List. For each word of the desired word length,
 * all possible two-word combinations are checked to see if they exist in the list.
 */
public class CompositionBruteForce {

  public static void main(String...args) {
    int wordLength = 6;
    if (args.length > 0) {
      try {
        wordLength = Integer.parseInt(args[0]);
      } catch (NumberFormatException nfe) {
        // ignore input, use default
      }
    }

    List<String> baseWords = new LinkedList<>();
    List<String> words = new LinkedList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader("wordlist.txt"))) {
      String word = "";
      while ((word = reader.readLine()) != null) {
        if (word.length() == wordLength) {
          baseWords.add(word);
        } else if (word.length() < wordLength) {
          words.add(word);
        }
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    for (String baseWord : baseWords) {
      for (int n = 1; n < baseWord.length(); n++) {
        String word1 = baseWord.substring(0, n);
        String word2 = baseWord.substring(n);

        if (words.contains(word1) && words.contains(word2)) {
          System.out.println(baseWord + " => " + word1 + " + " + word2);
        }
      }
    }
  }
}