package com.mulcam.bbs.service;

import org.springframework.stereotype.Service;

@Service
public class DiaryServiceImpl implements DiaryService {

	@Override
	public boolean isLeapYear(int year) {
		if (year % 400 == 0)
			return true;
		if (year % 100 == 0)
			return false;
		if (year % 4 == 0)
			return true;
		return false;
	}

}
