package com.mulcam.bbs.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mulcam.bbs.dao.AnniversaryDao;
import com.mulcam.bbs.dao.ScheduleDao;
import com.mulcam.bbs.entity.Anniversary;
import com.mulcam.bbs.entity.SchDay;
import com.mulcam.bbs.entity.Schedule;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	
	@Autowired private ScheduleDao schedDao;
	@Autowired private AnniversaryDao annivDao;

	@Override
	public List<Schedule> getDaySchedList(String uid, String sdate) {
		List<Schedule> list = schedDao.getSchedList(uid, sdate, sdate);
		return list;
	}

	@Override
	public List<Schedule> getMonthSchedList(String uid, String month, int lastDay) {
		String startDay = month + "01";
		String endDay = month + lastDay;
		List<Schedule> list = schedDao.getSchedList(uid, startDay, endDay);
		return list;
	}

	@Override
	public List<Schedule> getCalendarSchedList(String uid, String startDate, String endDate) {
		List<Schedule> list = schedDao.getSchedList(uid, startDate, endDate);
		return list;
	}

	@Override
	public SchDay generateSchDay(String uid, String sdate, int date, int isOtherMonth) {
		List<Anniversary> annivList = annivDao.getAnnivList(sdate, sdate);
		List<Schedule> schedList = schedDao.getSchedList(uid, sdate, sdate);
		int day = Integer.parseInt(sdate.substring(6));
		int isHoliday = 0;
		List<String> aList = new ArrayList<>();
		for (Anniversary a: annivList) {
			aList.add(a.getAname());
			if (isHoliday == 0)
				isHoliday = a.getIsHoliday();
		}
		SchDay schDay = new SchDay(day, date, isHoliday, isOtherMonth, sdate, aList, schedList);
		return schDay;
	}

	@Override
	public void insert(Schedule schedule) {
		schedDao.insert(schedule);
	}

	@Override
	public Schedule getSchedule(int sid) {
		Schedule sched = schedDao.getSchedule(sid);
		return sched;
	}

	@Override
	public void update(Schedule schedule) {
		schedDao.update(schedule);
	}

	@Override
	public void delete(int sid) {
		schedDao.delete(sid);
	}

	@Override
	public List<Schedule> getSchedListByPage(String uid, String startDate, int page) {
		int offset = (page - 1) * 15;
		List<Schedule> list = schedDao.getSchedListByPage(uid, startDate, offset);
		return list;
	}

	@Override
	public int getSchedCount(String uid, String startDate) {
		int count = schedDao.getSchedCount(uid, startDate);
		return count;
	}

}
