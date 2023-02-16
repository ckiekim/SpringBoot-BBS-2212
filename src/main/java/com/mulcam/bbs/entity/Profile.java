package com.mulcam.bbs.entity;

/*
	CREATE TABLE `profile` (
		uid VARCHAR(20) NOT null PRIMARY KEY,
		github VARCHAR(20),
		instagram VARCHAR(20),
		facebook VARCHAR(20),
		twitter VARCHAR(20),
		homepage VARCHAR(80),
		blog VARCHAR(80),
		addr VARCHAR(20),
		image MEDIUMBLOB,
		size INT,
		filename VARCHAR(40)
	);
 */

public class Profile {
	private String uid;
	private String github;
	private String instagram;
	private String facebook;
	private String twitter;
	private String homepage;
	private String blog;
	private String addr;
	private byte[] image;
	private int size;
	private String filename;
	
	public Profile() { }
	public Profile(String github, String instagram, String facebook, String twitter, String homepage,
			String blog, String addr, byte[] image, int size, String filename) {
		this.github = github;
		this.instagram = instagram;
		this.facebook = facebook;
		this.twitter = twitter;
		this.homepage = homepage;
		this.blog = blog;
		this.addr = addr;
		this.image = image;
		this.size = size;
		this.filename = filename;
	}
	public Profile(String uid, String github, String instagram, String facebook, String twitter, String homepage,
			String blog, String addr, byte[] image, int size, String filename) {
		this.uid = uid;
		this.github = github;
		this.instagram = instagram;
		this.facebook = facebook;
		this.twitter = twitter;
		this.homepage = homepage;
		this.blog = blog;
		this.addr = addr;
		this.image = image;
		this.size = size;
		this.filename = filename;
	}
	
	@Override
	public String toString() {
		return "Profile [uid=" + uid + ", github=" + github + ", instagram=" + instagram + ", facebook=" + facebook
				+ ", twitter=" + twitter + ", homepage=" + homepage + ", blog=" + blog + ", addr=" + addr + ", size="
				+ size + ", filename=" + filename + "]";
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getGithub() {
		return github;
	}
	public void setGithub(String github) {
		this.github = github;
	}
	public String getInstagram() {
		return instagram;
	}
	public void setInstagram(String instagram) {
		this.instagram = instagram;
	}
	public String getFacebook() {
		return facebook;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	public String getHomepage() {
		return homepage;
	}
	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
	public String getBlog() {
		return blog;
	}
	public void setBlog(String blog) {
		this.blog = blog;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
