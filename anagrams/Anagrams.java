import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Anagrams {
    public static void main(String[] args) {
        Map<String, List<String>> anagramMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("wordlist.txt"))) {
            String line = "";
            while ((line = reader.readLine()) != null) {
                String sorted = sortLetters(line);
                List<String> anagrams = anagramMap.get(sorted);
                if (anagrams == null) {
                    anagrams = new ArrayList<>();
                    anagramMap.put(sorted, anagrams);
                }

                anagrams.add(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        for (List<String> anagrams : anagramMap.values()) {
            if (anagrams.size() > 1) {
                System.out.println(anagrams);
            }
        }

    }

    public static String sortLetters(final String word) {
        char[] letters = word.toLowerCase().toCharArray();
        Arrays.sort(letters);
        return new String(letters);
    }
}