package com.mulcam.bbs.service;

import java.io.BufferedReader;
import java.io.FileReader;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mulcam.bbs.dao.ProfileDao;
import com.mulcam.bbs.entity.Profile;

@Service
public class ProfileService {

	@Autowired private ProfileDao profileDao;
	@Value("${spring.servlet.multipart.location}") private String uploadDir;
	
	public void insert(Profile profile, HttpSession session) {
		profileDao.insert(profile);
		setSessionValue(profile, session);
	}
	
	public Profile getProfile(String uid) {
		Profile profile = profileDao.getProfile(uid);
		return profile;
	}
	
	// 로그인할 경우 사용
	public void setAsideValue(String uid, HttpSession session) {
		Profile profile = profileDao.getProfile(uid);
		if (profile == null)
			return;
		setSessionValue(profile, session);
	}

	public void update(Profile profile, HttpSession session) {
		profileDao.update(profile);
		setSessionValue(profile, session);
	}
	
	public void updateWithoutImage(Profile profile, HttpSession session) {
		profileDao.updateWithoutImage(profile);
		setSessionValue(profile, session);
	}
	
	public void setSessionValue(Profile profile, HttpSession session) {
		if (profile.getGithub() != null)
			session.setAttribute("sessionGithub", profile.getGithub());
		if (profile.getInstagram() != null)
			session.setAttribute("sessionInstagram", profile.getInstagram());
		if (profile.getFacebook() != null)
			session.setAttribute("sessionFacebook", profile.getFacebook());
		if (profile.getTwitter() != null)
			session.setAttribute("sessionTwitter", profile.getTwitter());
		if (profile.getHomepage() != null)
			session.setAttribute("sessionHomepage", profile.getHomepage());
		if (profile.getBlog() != null)
			session.setAttribute("sessionBlog", profile.getBlog());
		if (profile.getAddr() != null)
			session.setAttribute("sessionAddress", profile.getAddr());
		if (profile.getFilename() != null)
			session.setAttribute("sessionFilename", profile.getFilename());
	}
	
	public JSONObject makeJsonProfile(Profile profile) {
		JSONObject obj = new JSONObject();
		obj.put("github", profile.getGithub());
		obj.put("instagram", profile.getInstagram());
		obj.put("facebook", profile.getFacebook());
		obj.put("twitter", profile.getTwitter());
		obj.put("homepage", profile.getHomepage());
		obj.put("blog", profile.getBlog());
		obj.put("addr", profile.getAddr());
		obj.put("filename", profile.getFilename());
		return obj;
	}
	
	public String getTodayQuote() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(uploadDir + "/data/todayQuote.txt"), 1024);
		int index = (int) Math.floor(Math.random() * 100);
		String result = null;
		for (int i=0; i<=index; i++)
			result = br.readLine();
		br.close();
		return result;
	}
	
}
