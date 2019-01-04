package thething.bitsAndPisces.service.utils;

import java.io.IOException;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

public class Mp3TagReader {

	public String titleFromTag(String path) throws UnsupportedTagException, InvalidDataException, IOException {
		Mp3File mp3 = new Mp3File(path);
		String result = "";
		if (mp3.hasId3v1Tag()) {

		} else if (mp3.hasId3v2Tag()) {

		} else {
			// WUT
		}
		return result;
	}
}
