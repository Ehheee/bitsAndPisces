package thething.bitsAndPisces.utils;

import java.util.Map;

public class MapUtils {

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(Object o) {
		if (o != null && o instanceof Map) {
			return (Map<String, Object>) o;
		}
		return null;
	}
}
