package com.mulcam.bbs.entity;

public class FireStation {
	private String name;
	private String addr;
	private String tel;
//	private double lng;
//	private double lat;
	
	public FireStation() { }
	public FireStation(String name, String addr, String tel) {
		this.name = name;
		this.addr = addr;
		this.tel = tel;
	}
	
	@Override
	public String toString() {
		return "FireStation [name=" + name + ", addr=" + addr + ", tel=" + tel + "]";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
}
