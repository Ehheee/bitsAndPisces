package thething.bitsAndPisces.service.utils;

import java.io.IOException;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;


public class Mp3TagReader {

	static Logger logger = LoggerFactory.getLogger(Mp3TagReader.class);

	public static String titleFromTag(String path) throws UnsupportedTagException, InvalidDataException, IOException {
		Mp3File mp3 = new Mp3File(path);
		ID3v1 v1 = mp3.getId3v1Tag();
		ID3v2 v2 = mp3.getId3v2Tag();
		String artist = "";
		String title = "";
		String result = "";
		if (v2 != null) {
			try {
				artist = v2.getArtist();
				title = v2.getTitle();
			} catch (Exception e) {
				logger.warn("Problem reading v2 tag: " + path, e);
			}
		}
		if (v1 != null) {
			artist = "".equals(artist) ? v1.getArtist() : artist;
			title = "".equals(title) ? v1.getTitle() : title;
		}
		if (title == null || artist == null || "".equals(title) || "".equals(artist)) {
			result = Paths.get(path).getFileName().toString();
			if (result.endsWith(".mp3")) {
				result = result.substring(0, result.length() - 4);
			}
		} else {
			result = artist + " - " + title;
		}
		return result;
	}
}
