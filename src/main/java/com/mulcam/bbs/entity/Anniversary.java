package com.mulcam.bbs.entity;

public class Anniversary {
	private int aid;
	private String aname;
	private String adate;
	private int isHoliday;
	
	public Anniversary() { }
	public Anniversary(String aname, String adate, int isHoliday) {
		this.aname = aname;
		this.adate = adate;
		this.isHoliday = isHoliday;
	}
	public Anniversary(int aid, String aname, String adate, int isHoliday) {
		this.aid = aid;
		this.aname = aname;
		this.adate = adate;
		this.isHoliday = isHoliday;
	}
	
	@Override
	public String toString() {
		return "Anniversary [aid=" + aid + ", aname=" + aname + ", adate=" + adate + ", isHoliday=" + isHoliday + "]";
	}
	
	public int getAid() {
		return aid;
	}
	public void setAid(int aid) {
		this.aid = aid;
	}
	public String getAname() {
		return aname;
	}
	public void setAname(String aname) {
		this.aname = aname;
	}
	public String getAdate() {
		return adate;
	}
	public void setAdate(String adate) {
		this.adate = adate;
	}
	public int getIsHoliday() {
		return isHoliday;
	}
	public void setIsHoliday(int isHoliday) {
		this.isHoliday = isHoliday;
	}
}
