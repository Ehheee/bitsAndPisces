package thething.bitsAndPisces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
		filesService.getMp3Map(paths);
		YoutubeConnectorService connector = new YoutubeConnectorService();
		MapResult result = connector.getSongsWithTags("UCdO1k99A8dJEiAjNY_GvwIQ");
		DuplicateChecker checker = new DuplicateChecker();
		Set<String> titles = result.getResult().keySet();
		for (String s : titles) {
			checker.getSingleResult(s, new ArrayList<String>(titles), "\\W", 1.0f);
		}

	}

}
