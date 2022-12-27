package com.mulcam.bbs.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.mulcam.bbs.entity.User;

public interface UserService {
	public static final int CORRECT_LOGIN = 0;
	public static final int WRONG_PASSWORD = 1;
	public static final int UID_NOT_EXIST = 2;

	List<User> getUserList(int page);

	User getUser(String uid);

	void registerUser(User u);
	
	void updateUser(User u);
	
	void deleteUser(String uid);

	int login(String uid, String pwd, HttpSession session);
	
}
