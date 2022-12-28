package com.mulcam.bbs.entity;

import java.time.LocalDateTime;

public class Reply {
	private int rid;
	private String content;
	private LocalDateTime regDate;
	private int isMine;
	private String uid;
	private int bid;
	private String uname;
	
	public Reply() {}
	public Reply(String content, int isMine, String uid, int bid) {
		this.content = content;
		this.isMine = isMine;
		this.uid = uid;
		this.bid = bid;
	}
	public Reply(int rid, String content, LocalDateTime regDate, int isMine, String uid, int bid, String uname) {
		super();
		this.rid = rid;
		this.content = content;
		this.regDate = regDate;
		this.isMine = isMine;
		this.uid = uid;
		this.bid = bid;
		this.uname = uname;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getRegDate() {
		return regDate;
	}
	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}
	public int getIsMine() {
		return isMine;
	}
	public void setIsMine(int isMine) {
		this.isMine = isMine;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public int getBid() {
		return bid;
	}
	public void setBid(int bid) {
		this.bid = bid;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
}
