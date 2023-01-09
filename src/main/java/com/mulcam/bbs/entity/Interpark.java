package com.mulcam.bbs.entity;

public class Interpark {
	private int rank;
	private String imgSrc;
	private String title;
	private String author;
	private String company;
	private int price;
	
	public Interpark() { }
	public Interpark(int rank, String imgSrc, String title, String author, String company, int price) {
		this.rank = rank;
		this.imgSrc = imgSrc;
		this.title = title;
		this.author = author;
		this.company = company;
		this.price = price;
	}
	
	@Override
	public String toString() {
		return "Interpark [rank=" + rank + ", title=" + title + ", author=" + author
				+ ", company=" + company + ", price=" + price + ", imgSrc=" + imgSrc + "]";
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
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
}
