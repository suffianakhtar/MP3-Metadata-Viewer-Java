package com.sheifu.mp3_meta_viewer.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.sheifu.mp3_meta_viewer.Entities.Song;

public class SongDao {
	private static String jdbcURLFirstRun = "jdbc:h2:~/mydatabase;AUTO_SERVER=TRUE;INIT=runscript from './create.sql'";
	private static String jdbcURL = "jdbc:h2:~/mydatabase";
	private static String username = "sa";
	private static String password = "";

	public static void createDatabase() {

		try (Connection conn = DriverManager.getConnection(jdbcURLFirstRun, username, password)) {

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void saveSong(Collection<Song> songs) {

		try (Connection conn = DriverManager.getConnection(jdbcURL, username, password);
				PreparedStatement statement = conn
						.prepareStatement("INSERT INTO SONGS (artist, year, album, title) values (?,?,?,?);")) {
			for (Song song : songs) {
				String artist = song.getArtist();
				String year = song.getYear();
				String album = song.getAlbum();
				String title = song.getTitle();
				statement.setString(1, artist);
				statement.setString(2, year);
				statement.setString(3, album);
				statement.setString(4, title);
				statement.addBatch();
			}
			int[] record = statement.executeBatch();
			System.out.println("Inserted " + record.length + " no of records into database");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Collection<Song> getSong() {

		Collection<Song> songs = new ArrayList<>();
		try (Connection conn = DriverManager.getConnection(jdbcURL, username, password);
				Statement statement = conn.createStatement();) {
			String sql = "SELECT * From SONGS;";
			ResultSet rs = statement.executeQuery(sql);

			while (rs.next()) {
				String artist = rs.getString("artist");
				String year = rs.getString("year");
				String album = rs.getString("album");
				String title = rs.getString("title");
				songs.add(new Song(artist, year, album, title));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return songs;
	}

}
