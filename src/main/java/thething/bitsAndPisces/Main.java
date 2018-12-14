package thething.bitsAndPisces;

import java.io.IOException;

import thething.bitsAndPisces.service.youtube.YoutubeConnectorService;

public class Main {

	public static void main(String[] args) throws IOException {
		YoutubeConnectorService connector = new YoutubeConnectorService();
		connector.getSongsWithTags("UCdO1k99A8dJEiAjNY_GvwIQ");
	}

}
