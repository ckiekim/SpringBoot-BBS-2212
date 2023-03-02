package com.mulcam.bbs.entity;

import java.time.LocalDateTime;

public class Like {
	private int lid;
	private int bid;
	private String uid;
	private LocalDateTime likeTime;
	
	public Like() { }
	public Like(int bid, String uid) {
		this.bid = bid;
		this.uid = uid;
	}
	public Like(int lid, int bid, String uid, LocalDateTime likeTime) {
		this.lid = lid;
		this.bid = bid;
		this.uid = uid;
		this.likeTime = likeTime;
	}
	
	@Override
	public String toString() {
		return "Like [lid=" + lid + ", bid=" + bid + ", uid=" + uid + ", likeTime=" + likeTime + "]";
	}
	
	public int getLid() {
		return lid;
	}
	public void setLid(int lid) {
		this.lid = lid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public LocalDateTime getLikeTime() {
		return likeTime;
	}
	public void setLikeTime(LocalDateTime likeTime) {
		this.likeTime = likeTime;
	}
}
