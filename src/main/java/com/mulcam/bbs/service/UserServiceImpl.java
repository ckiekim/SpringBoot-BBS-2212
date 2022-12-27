package com.mulcam.bbs.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mulcam.bbs.dao.UserDao;
import com.mulcam.bbs.entity.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<User> getUserList(int page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String uid) {
		User user = userDao.getUser(uid);
		return user;
	}

	@Override
	public void registerUser(User u) {
		String cryptedPwd = BCrypt.hashpw(u.getPwd(), BCrypt.gensalt()); 
		u.setPwd(cryptedPwd);
		userDao.registerUser(u);
	}

	@Override
	public void updateUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String uid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int login(String uid, String pwd, HttpSession session) {
		User u = userDao.getUser(uid);
		if (u != null && u.getUid() != null) {		// uid 가 존재
			if (BCrypt.checkpw(pwd, u.getPwd())) {
				session.setAttribute("uid", u.getUid());
				session.setAttribute("uname", u.getUname());
				return UserService.CORRECT_LOGIN;
			} else {
				return UserService.WRONG_PASSWORD;
			}
		} 				// uid 가 없음
		return UserService.UID_NOT_EXIST;
	}
	
	

}
