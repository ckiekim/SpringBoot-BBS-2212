package com.mulcam.bbs.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
	CREATE TABLE SCHEDULE (
		sid INT PRIMARY KEY AUTO_INCREMENT,
		uid VARCHAR(20) NOT NULL,
		sdate CHAR(8) NOT NULL,
		title VARCHAR(40) NOT NULL,
		place VARCHAR(40),
		startTime DATETIME,
		endTime DATETIME,
		isImportant INT DEFAULT 0,
		memo VARCHAR(100)
	);
 */

public class Schedule {
	private int sid;
	private String uid;
	private String sdate;
	private String title;
	private String place;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private int isImportant;
	private String memo;
	
	public Schedule() { }
	public Schedule(String uid, String sdate, String title, String place, LocalDateTime startTime,
			LocalDateTime endTime, int isImportant, String memo) {
		this.uid = uid;
		this.sdate = sdate;
		this.title = title;
		this.place = place;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isImportant = isImportant;
		this.memo = memo;
	}
	public Schedule(int sid, String uid, String sdate, String title, String place, LocalDateTime startTime,
			LocalDateTime endTime, int isImportant, String memo) {
		this.sid = sid;
		this.uid = uid;
		this.sdate = sdate;
		this.title = title;
		this.place = place;
		this.startTime = startTime;
		this.endTime = endTime;
		this.isImportant = isImportant;
		this.memo = memo;
	}
	
	@Override
	public String toString() {
		return "Schedule [sid=" + sid + ", uid=" + uid + ", sdate=" + sdate + ", title=" + title + ", place=" + place
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", isImportant=" + isImportant + ", memo="
				+ memo + "]";
	}
	
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public LocalDateTime getEndTime() {
		return endTime;
	}
	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	public int getIsImportant() {
		return isImportant;
	}
	public void setIsImportant(int isImportant) {
		this.isImportant = isImportant;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
}
