package com.mulcam.bbs.service;

import java.util.List;

import com.mulcam.bbs.entity.Anniversary;

public interface AnniversaryService {
	
	List<Anniversary> getDayAnnivList(String sdate);

	List<Anniversary> getAnnivDays(String start, String end);
	
}
