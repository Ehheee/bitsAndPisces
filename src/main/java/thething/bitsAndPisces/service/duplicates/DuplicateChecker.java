package thething.bitsAndPisces.service.duplicates;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thething.bitsAndPisces.utils.WordComparator;
public class DuplicateChecker {

	Logger logger = LoggerFactory.getLogger(DuplicateChecker.class);
	private WordComparator comparer;

	public DuplicateChecker() {
		comparer = new WordComparator();
	}

	public void createOrderedScoreMap(List<String> strings) {
		WordComparator comparer = new WordComparator();
		for (String str : strings) {

		}

	}

	public void checkBySplits(String str, List<String> others, String splitPattern) {
		Map<String, Object> totalMap = new HashMap<>();
		String[] splits = str.split(splitPattern);
		for (String other : others) {
			String[] otherSplits = other.split(splitPattern);
			for (String split : splits) {
				String bestMatch = null;
				int bestScore = 100;
				if (split.length() < 2) {
					continue;
				}
				for (String otherSplit : otherSplits) {
					if (otherSplit.length() < 2) {
						continue;
					}
					int res = StringUtils.getLevenshteinDistance(split, otherSplit);
					if (res < bestScore) {
						bestScore = res;
						bestMatch = otherSplit;
					}
				}
				logger.info(split + " - " + bestMatch + " - " + bestScore);
				bestScore = 100;
				bestMatch = null;
			}
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
