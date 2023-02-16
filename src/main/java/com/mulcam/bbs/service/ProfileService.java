package com.mulcam.bbs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mulcam.bbs.dao.ProfileDao;
import com.mulcam.bbs.entity.Profile;

@Service
public class ProfileService {

	@Autowired private ProfileDao profileDao;
	
	public void insert(Profile profile) {
		profileDao.insert(profile);
	}
	
	public Profile getProfile(String uid) {
		Profile profile = profileDao.getProfile(uid);
		return profile;
	}
	
}
