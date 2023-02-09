package com.mulcam.bbs.entity;

public class Diary {
	private String uid;
	private String dayStr;
	private String anniversary;
	private String title;
	private String content;
	private String sentiment;
	private int isHoliday;
	
	public Diary() { }
	public Diary(String uid, String dayStr, String anniversary, String title, String content, String sentiment,
			int isHoliday) {
		this.uid = uid;
		this.dayStr = dayStr;
		this.anniversary = anniversary;
		this.title = title;
		this.content = content;
		this.sentiment = sentiment;
		this.isHoliday = isHoliday;
	}
	
	@Override
	public String toString() {
		return "Diary [uid=" + uid + ", dayStr=" + dayStr + ", anniversary=" + anniversary + ", title=" + title
				+ ", content=" + content + ", sentiment=" + sentiment + ", isHoliday=" + isHoliday + "]";
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getDayStr() {
		return dayStr;
	}
	public void setDayStr(String dayStr) {
		this.dayStr = dayStr;
	}
	public String getAnniversary() {
		return anniversary;
	}
	public void setAnniversary(String anniversary) {
		this.anniversary = anniversary;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public int getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(int isHoliday) {
		this.isHoliday = isHoliday;
	}
}
