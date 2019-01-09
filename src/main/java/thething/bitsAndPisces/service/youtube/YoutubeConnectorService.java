package thething.bitsAndPisces.service.youtube;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.google.api.services.youtube.model.PlaylistListResponse;

import thething.bitsAndPisces.service.utils.MapResult;
import thething.bitsAndPisces.utils.MapUtils;

public class YoutubeConnectorService {

	Logger logger = LoggerFactory.getLogger(YoutubeConnectorService.class);
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

	@SuppressWarnings("unused")
	private static final List<String> SCOPES = Arrays.asList(YouTubeScopes.YOUTUBE_READONLY);

	public YoutubeConnectorService() throws IOException {
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (Throwable t) {
			logger.error("Error creating YoutubeConnectorService:", t);
		}
		youtube = getYouTubeService();
	}

	public MapResult getSongsWithTags(String channelId) throws IOException {
		List<Playlist> playLists = this.getPlayListsByChannel(channelId);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		createSongTagMap(resultMap, playLists);
		MapResult result = new MapResult();
		result.setResult(resultMap);
		return result;
	}

	private Map<String, Object> createSongTagMap(Map<String, Object> resultMap, List<Playlist> playLists)
			throws IOException {
		for (Playlist playList : playLists) {
			for (PlaylistItem item : getPlayListItems(playList.getId())) {
				addTag(processPlayListItem(item, resultMap), playList.getSnippet().getTitle());

			}
		}
		return resultMap;
	}

	@SuppressWarnings("unchecked")
	private void addTag(Map<String, Object> songData, String tag) {
		if (songData.get("tags") != null && songData.get("tags") instanceof Set) {
			((Set<String>) songData.get("tags")).add(tag);
		}
	}

	private Map<String, Object> processPlayListItem(PlaylistItem item, Map<String, Object> resultMap) {
		String videoId = item.getContentDetails().getVideoId();
		String title = item.getSnippet().getTitle();
		Map<String, Object> songData = MapUtils.toMap(resultMap.get(videoId));
		if (songData == null) {
			songData = new HashMap<>();
			resultMap.put(videoId, songData);
			songData.put("title", title);
			songData.put("videoId", videoId);
			songData.put("tags", new TreeSet<String>());
		}
		return songData;
	}

	public List<Playlist> getPlayListsByChannel(String channelId) throws IOException {
		YouTube.Playlists.List list = youtube.playlists().list("contentDetails,snippet");
		list.setChannelId(channelId);
		list.setMaxResults(50L);
		PlaylistListResponse response = list.execute();
		return response.getItems();
	}

	public Playlist getPlayList(String playlistId) throws IOException {
		YouTube.Playlists.List playList = youtube.playlists().list("contentDetails,snippet");
		playList.setId(playlistId);
		List<Playlist> lists = playList.execute().getItems();
		Playlist list = lists.get(0);
		return list;
	}

	public List<PlaylistItem> getPlayListItems(String playlistId) throws IOException {
		List<PlaylistItem> endResult = new ArrayList<PlaylistItem>();
		YouTube.PlaylistItems.List songs = youtube.playlistItems().list("contentDetails,snippet");
		songs.setPlaylistId(playlistId);
		songs.setMaxResults(50l);
		PlaylistItemListResponse response = songs.execute();
		endResult.addAll(response.getItems());
		String nextPageToken = response.getNextPageToken();
		while (nextPageToken != null) {
			songs.setPageToken(nextPageToken);
			response = songs.execute();
			nextPageToken = response.getNextPageToken();
			endResult.addAll(response.getItems());
		}
		logger.info("Result size: " + endResult.size());
		return endResult;
	}
	public YouTube getYouTubeService() throws IOException {
		return new YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, null).setApplicationName(APPLICATION_NAME)
				.setSuppressAllChecks(true)
				.setYouTubeRequestInitializer(new YouTubeRequestInitializer("AIzaSyDXKjMFhlqtfQzltqSeO5k-GmMh5jLoHwM"))
				.build();
	}

}
