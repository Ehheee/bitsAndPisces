package thething.bitsAndPisces;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.YouTubeScopes;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;

public class YoutubeConnector {

	/** Application name. */
	private static final String APPLICATION_NAME = "API Sample";

	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

	/** Global instance of the HTTP transport. */
	private HttpTransport HTTP_TRANSPORT;

	/**
	 * Global instance of the scopes required by this quickstart.
	 *
	 * If modifying these scopes, delete your previously saved credentials at
	 * ~/.credentials/drive-java-quickstart
	 */
	private YouTube youtube;

	private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);

	public YoutubeConnector() throws IOException {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		youtube = getYouTubeService();
	}

	public List<PlaylistItem> getPlayListItems(String playlistId) throws IOException {
		YouTube.Playlists.List playList = youtube.playlists().list("contentDetails,snippet");
		playList.setId(playlistId);
		List<Playlist> list = playList.execute().getItems();
		Long size;
		if (list.size() > 0) {
			size = list.get(0).getContentDetails().getItemCount();
			for (Entry<String, Object> e : list.get(0).entrySet()) {
				System.out.println(e.getKey() + " - " + e.getValue());
			}
		} else {
			return null;
		}
		List<PlaylistItem> endResult = new ArrayList<PlaylistItem>();
		YouTube.PlaylistItems.List songs = youtube.playlistItems().list("snippet");
		songs.setPlaylistId(playlistId);
		songs.setMaxResults(50l);
		PlaylistItemListResponse response = songs.execute();
		endResult.addAll(response.getItems());
		String nextPageToken = response.getNextPageToken();
		while (nextPageToken != null) {
			System.out.println(nextPageToken);
			songs.setPageToken(nextPageToken);
			response = songs.execute();
			nextPageToken = response.getNextPageToken();
			endResult.addAll(response.getItems());
		}
		System.out.println(endResult.size());
		for (PlaylistItem item : endResult) {
			System.out.println(item.getSnippet().getTitle());
		}
		return endResult;
	}
	public YouTube getYouTubeService() throws IOException {
		return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME)
				.setSuppressAllChecks(true)
				.setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyDXKjMFhlqtfQzltqSeO5k-GmMh5jLoHwM"))
				.build();
	}

}
