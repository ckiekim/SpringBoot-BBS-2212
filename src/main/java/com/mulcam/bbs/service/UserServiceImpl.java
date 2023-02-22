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
		int offset = (page - 1) * 10;
		List<User> list = userDao.getUserList(offset);
		return list;
	}

	@Override
	public User getUser(String uid) {
		User user = userDao.getUser(uid);
		return user;
	}

	@Override
	public void registerUser(User user) {
		String cryptedPwd = BCrypt.hashpw(user.getPwd(), BCrypt.gensalt()); 
		user.setPwd(cryptedPwd);
		userDao.insertUser(user);
	}

	@Override
	public void updateUser(User user, String newPwd) {
		if (newPwd.length() > 0) {
			String cryptedPwd = BCrypt.hashpw(newPwd, BCrypt.gensalt());
			user.setPwd(cryptedPwd);
			userDao.updateUser(user);	 
		} else 
			userDao.updateUserWithoutPassword(user);
	}

	@Override
	public void deleteUser(String uid) {
		userDao.deleteUser(uid);
	}

	@Override
	public int login(String uid, String pwd, HttpSession session) {
		User u = userDao.getUser(uid);
		if (u != null && u.getUid() != null) {		// uid 가 존재
			if (BCrypt.checkpw(pwd, u.getPwd())) {
				session.setAttribute("uid", u.getUid());
				session.setAttribute("uname", u.getUname());
				session.setAttribute("sessionEmail", u.getEmail());
				return UserService.CORRECT_LOGIN;
			} else {
				return UserService.WRONG_PASSWORD;
			}
		} 				// uid 가 없음
		return UserService.UID_NOT_EXIST;
	}

	@Override
	public int getUserCount() {
		int count = userDao.getUserCount();
		return count;
	}

}
