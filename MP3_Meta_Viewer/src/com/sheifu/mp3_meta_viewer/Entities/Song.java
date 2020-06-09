package com.sheifu.mp3_meta_viewer.Entities;

public class Song {
	private final String artist;
    private final String year;
    private final String album;
    private final String title;
    
	public String getArtist() {
		return artist;
	}

	public String getYear() {
		return year;
	}

	public String getAlbum() {
		return album;
	}

	public String getTitle() {
		return title;
	}

	public Song(String artist, String year, String album, String title) {
		super();
		this.artist = artist;
		this.year = year;
		this.album = album;
		this.title = title;
	}

	@Override
	public String toString() {
		return "Song [artist=" + artist + ", year=" + year + ", album=" + album + ", title=" + title + "]";
	}
    
}
