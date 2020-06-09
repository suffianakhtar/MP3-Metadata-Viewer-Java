package com.sheifu.mp3_meta_viewer.Utils;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;

public class FileNameReader {

	public static Collection<String> readFileNames(Path path) {
		File filesPath = path.toFile();
		String[] fileNames = filesPath.list(new Mp3FilesFilter());
		for (int i = 0; i < fileNames.length; i++) {
			fileNames[i] = path.toString() + "\\" + fileNames[i].substring(0);
		}
		return Arrays.asList(fileNames);
	}

}

class Mp3FilesFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {

		return name.endsWith(".mp3") || name.endsWith(".MP3");
	}

}
