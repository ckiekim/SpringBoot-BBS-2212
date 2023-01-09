package com.mulcam.bbs.entity;

public class Genie {
	private int rank;
	private String imgSrc;
	private String title;
	private String artist;
	private String album;
	
	public Genie() { }
	public Genie(int rank, String imgSrc, String title, String artist, String album) {
		this.rank = rank;
		this.imgSrc = imgSrc;
		this.title = title;
		this.artist = artist;
		this.album = album;
	}
	
	@Override
	public String toString() {
		return "Genie [rank=" + rank + ", imgSrc=" + imgSrc + ", title=" + title + ", artist=" + artist + ", album="
				+ album + "]";
	}
	
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public String getImgSrc() {
		return imgSrc;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}
	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}
}
