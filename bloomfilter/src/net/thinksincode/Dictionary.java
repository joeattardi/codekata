package net.thinksincode;

import ie.ucd.murmur.MurmurHash;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.BitSet;
import java.util.Random;

public class Dictionary {
	public static final int BIT_SET_SIZE = 400000;
	
	private BitSet bits = new BitSet(BIT_SET_SIZE);
	
	private int hash1(final String word) {
		return Math.abs(MurmurHash.hash32(word) % BIT_SET_SIZE);
	}
	
	private int hash2(final String word) {
		return Math.abs(MurmurHash.hash32(new StringBuilder(word).reverse().toString()) % BIT_SET_SIZE);
	}
	
	public void addWord(final String word) {
		bits.set(hash1(word));
		bits.set(hash2(word));
	}
	
	public boolean hasWord(final String word) {
		return bits.get(hash1(word)) && bits.get(hash2(word));
	}
	
	public void loadDictionary(final String file) {
		try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				addWord(line);
			}
		} catch (IOException ioe) {
			
		}
	}
	
	public static void main(String...args) {
		Dictionary dictionary = new Dictionary();
		dictionary.loadDictionary("wordlist.txt");

		for (int n = 0; n < 100; n++) {
			String word = randomWord();
			System.out.println(word + " --> " + dictionary.hasWord(word));
		}
	}
	
	public static String randomWord() {
		Random random = new Random();
		
		char[] letters = new char[5];
		
		for (int n = 0; n < letters.length; n++) {
			letters[n] = (char) (random.nextInt(26) + 97);
		}
		
		return new String(letters);
	}
}
