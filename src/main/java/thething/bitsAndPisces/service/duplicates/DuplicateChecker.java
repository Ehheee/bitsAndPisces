package thething.bitsAndPisces.service.duplicates;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

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

	public Map<String, Float> getSingleResult(String str, List<String> others, String splitPattern, float threshold) {
		Map<String, Float> resultMap = new HashMap<>();
		List<String> baseSplits = getSplits(str, splitPattern);
		for (String other : others) {
			List<Entry<String, Integer>> singleResults = new ArrayList<>();
			List<String> otherSplits = getSplits(other, splitPattern);
			for (String baseSplit : baseSplits) {
				singleResults.add(findBestMatch(baseSplit, otherSplits));
			}
			Float average = getAverage(singleResults);
			if (average < threshold) {
				resultMap.put(other, average);
			}
		}
		return resultMap;
	}

	private Float getAverage(List<Entry<String, Integer>> pairs) {
		Integer sum = 0;
		for (Entry<String, Integer> pair : pairs) {
			sum += pair.getValue();
		}
		return new Float(sum) / new Float(pairs.size());
	}

	private Entry<String, Integer> findBestMatch(String baseSplit, List<String> otherSplits) {
		int bestScore = 100;
		String bestMatch = null;
		for (String otherSplit : otherSplits) {
			int res = StringUtils.getLevenshteinDistance(baseSplit, otherSplit);
			if (res < bestScore) {
				bestScore = res;
				bestMatch = otherSplit;
			}
		}
		return new AbstractMap.SimpleEntry<String, Integer>(bestMatch, bestScore);
	}

	private List<String> getSplits(String str, String splitPattern) {
		return Arrays.stream(str.split(splitPattern)).filter(c -> c != null && !"".equals(c))
				.collect(Collectors.toList());
	}

	public void createOrderedScoresForString(String str, List<String> others) {
		Map<String, Entry<String, Double>> scores = new HashMap<>();
		for (String other : others) {
			Double score = comparer.compareWords3(str, other);
			scores.put(str, new AbstractMap.SimpleEntry<String, Double>(other, score));
		}
	}
}
