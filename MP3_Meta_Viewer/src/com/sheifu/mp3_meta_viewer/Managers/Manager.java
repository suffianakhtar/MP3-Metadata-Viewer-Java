package com.sheifu.mp3_meta_viewer.Managers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import com.sheifu.mp3_meta_viewer.Dao.SongDao;
import com.sheifu.mp3_meta_viewer.Entities.Song;
import com.sheifu.mp3_meta_viewer.Utils.FileNameReader;
import com.sheifu_mp3_meta_viewer.Controllers.SongServlet;

public class Manager {

	public static void main(String[] args) {

		// Step 1: Check program input
		if (!(args.length == 1)) {
			throw new IllegalArgumentException("Please, Specify at least and only one mp3 file directory");
		}
		Path path = Path.of(args[0]);

		if (!Files.exists(path)) {
			throw new IllegalArgumentException("Invalid path specified. The given path does not exists");
		}

		// Step 2: Reading only mp3 file names into a List
		Collection<String> filesListAsString = new ArrayList<>();
		filesListAsString = FileNameReader.readFileNames(path);

		System.out.print("Following files are present in the directory: ");
		for (String pathofFile : filesListAsString) {
			System.out.print("\n" + pathofFile + " ");
		}

		// Step 3: Obtaining mp3 meta-data as Song object using external library
		// "mp3agic"
		Collection<Song> songs = new ArrayList<>();

		for (String songPath : filesListAsString) {
			Song song = mp3AttributesReader(songPath);
			System.out.println("\n" + song);
			songs.add(song);
		}

		// Step 4: Storing information in the database
		SongDao.createDatabase();
		SongDao.saveSong(songs);

		// Step 5: Starting Server and displaying information
		startServer();
	}

	private static void startServer() {
		Server server = new Server(8080);
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.setResourceBase(System.getProperty("java.io.tmpdir"));
		server.setHandler(context);
		context.addServlet(SongServlet.class, "/songs");
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (Desktop.isDesktopSupported()) {
			try {
				Desktop.getDesktop().browse(new URI("http://localhost:8080/songs"));
			} catch (IOException | URISyntaxException e) {
				e.printStackTrace();
			}
		}
	}

	private static Song mp3AttributesReader(String path) {

		Mp3File mp3 = null;
		try {
			mp3 = new Mp3File(path);
		} catch (UnsupportedTagException | InvalidDataException | IOException e) {
			e.printStackTrace();
		}
		ID3v2 id3 = mp3.getId3v2Tag();
		String artist = id3.getArtist();
		String album = id3.getAlbum();
		String year = id3.getYear();
		String title = id3.getTitle();
		return new Song(artist, album, year, title);

	}
}
