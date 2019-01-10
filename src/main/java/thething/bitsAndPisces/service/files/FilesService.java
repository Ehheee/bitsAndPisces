package thething.bitsAndPisces.service.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Functions;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.UnsupportedTagException;

import thething.bitsAndPisces.service.utils.Mp3TagReader;

public class FilesService {

	public static List<String> getFileList(String path, String extension, int depth) throws IOException {
		try (Stream<Path> stream = Files.walk(Paths.get(path), depth)) {
			return stream.map(p -> p.toAbsolutePath().toString()).filter(s -> s.endsWith("." + extension))
					.collect(Collectors.toList());
		}
	}

	public Map<String, Object> getMp3Map(List<String> paths) {
		Map<String, Object> result = new HashMap<>();
		paths.stream().map(s -> getSingleMp3Data(s)).forEach(m -> result.put(m.get("path").toString(), m));
		return result;
	}

	public Map<String, Object> getSingleMp3Data(String path) {
		Map<String, Object> result = new HashMap<>();
		try {
			result.put("title", Mp3TagReader.titleFromTag(path));
			result.put("path", path);
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
