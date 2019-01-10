package thething.bitsAndPisces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import thething.bitsAndPisces.service.duplicates.DuplicateChecker;
import thething.bitsAndPisces.service.files.FilesService;
import thething.bitsAndPisces.service.utils.MapResult;
import thething.bitsAndPisces.service.youtube.YoutubeConnectorService;

public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		List<String> paths;
		paths = FilesService.getFileList("C:\\muusika", "mp3", 6);
		FilesService filesService = new FilesService();
		Map<String, Object> mp3Map = filesService.getMp3Map(paths);
		YoutubeConnectorService connector = new YoutubeConnectorService();
		MapResult result = connector.getSongsWithTags("UCdO1k99A8dJEiAjNY_GvwIQ");
		DuplicateChecker checker = new DuplicateChecker();
		Set<String> titles = result.getResult().entrySet().stream()
				.map(c -> ((Map) c.getValue()).get("title").toString()).collect(Collectors.toSet());
		for (Entry<String, Object> e : mp3Map.entrySet()) {
			String title = ((Map<String, Object>) e.getValue()).get("title").toString();
			logger.info(title + " --------- "
					+ checker.getSingleResult(title, new ArrayList<String>(titles), "\\W", 2.0f).toString());
		}

	}

}
