package thething.bitsAndPisces.service.duplicates;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import thething.bitsAndPisces.utils.WordComparator;
public class DuplicateChecker {

	private WordComparator comparer;

	public DuplicateChecker() {
		comparer = new WordComparator();
	}

	public void createOrderedScoreMap(List<String> strings) {
		WordComparator comparer = new WordComparator();
		for (String str : strings) {

		}

	}

	public void createOrderedScoresForString(String str, List<String> others) {
		Map<String, Entry<String, Double>> scores = new HashMap<>();
		for (String other : others) {
			Double score = comparer.compareWords3(str, other);
			scores.put(str, new AbstractMap.SimpleEntry<String, Double>(other, score));
		}
	}
}
