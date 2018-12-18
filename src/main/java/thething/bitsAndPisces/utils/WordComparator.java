package thething.bitsAndPisces.utils;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WordComparator {
	Logger logger = LoggerFactory.getLogger(WordComparator.class);


	public double compareWords3(String a, String b) {
		LinkedList<Character> aChars = this.wordToLinkedList(a);
		LinkedList<Character> bChars = this.wordToLinkedList(b);
		int aSize = a.length();
		double totalPoints = 0;
		// As array is modified during the loop then the size of original string has to
		// be used in comparison
		for (int ai = 0; ai < aSize; ai++) {
			char aChar = aChars.getFirst();
			int diff = bChars.indexOf(aChar);

			System.out.println("aChars: " + aChars + " bChars: " + bChars);
			System.out.println(
					"Character in string a: " + aChar + " Index in string a: " + 0 + " Index in string b: " + diff);

			// if character in string a exists also in string b give 0.5 points
			if (diff != -1) {

				System.out.println("Character: " + aChar + " also exists in b + 0.5 points");
				totalPoints += 0.5;

			}

			// if character in string a exists in same position in string b give 0.5 points
			if (diff == 0) {

				System.out.println("Character: " + aChar + " has same position in b + 0.5 points");
				totalPoints += 0.5;
				// remove the first characters as they match
				aChars.remove();
				bChars.remove();

			}

			// else if character in string a exists in b but on the next position or doesn't
			// exist at all i.e a letter is missed during typing
			else if ((diff == 1 || diff == -1) && (!aChars.isEmpty() && !bChars.isEmpty())) {

				// If the next char in a is at the current index in b, characters might be
				// switched.
				if (aChars.size() > 1 && (aChars.get(1) == bChars.get(0))) {
					// If the next characters after the possibly switched ones match, there is high
					// chance of spelling mistake
					if ((aChars.size() > 2 && bChars.size() > 2) && aChars.get(2) == bChars.get(2)) {
						System.out.println("Possibly switched characters: " + aChar + " , " + bChars.get(1));

						totalPoints += 1;
						// remove the first 2 characters as they are both evaluated and possible points
						// given

						aChars.remove();
						aChars.remove();
						bChars.remove();
						bChars.remove();
						ai++;

					}
					// bChars might possibly miss a character
					else if (bChars.get(1) == aChars.get(2)) {

						System.out.println("bChars seems to miss a character: " + aChar);
						totalPoints += 0.75;
						aChars.remove();
						aChars.remove();
						bChars.remove();
						ai++;
					}

					else {
						totalPoints += 0.5;
						aChars.remove();
						bChars.remove();
					}

				}

				// else if the char in a is the next char in b, b might have an extra character
				else if (bChars.size() > 1 && (aChar == bChars.get(1))) {
					// next chars after that are the same
					if (aChars.size() > 1 && aChars.get(1) == bChars.get(2)) {
						System.out.println("bChars seems to have an extra character: " + aChar);
						totalPoints += 0.25;
						bChars.remove();
					}
					aChars.remove();
					bChars.remove();
				} else {
					if (aChars.size() > 1) {
						aChars.remove();
					}
					if (bChars.size() > 1) {
						bChars.remove();
					}
				}
			} else {
				if (aChars.size() > 1) {
					aChars.remove();
				}
				if (bChars.size() > 1) {
					bChars.remove();
				}
			}
			System.out.println("New totalpoints: " + totalPoints);
		}
		System.out.println("Totalpoints: " + totalPoints);
		System.out.println("Theoretical average: " + totalPoints / aSize);
		return totalPoints / aSize;
	}

	private LinkedList<Character> wordToLinkedList(String s) {
		LinkedList<Character> chars = new LinkedList<Character>();
		for (char c : s.toCharArray()) {
			chars.add(c);
		}
		return chars;
	}
}
