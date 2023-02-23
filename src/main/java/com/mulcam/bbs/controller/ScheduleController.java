package com.mulcam.bbs.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulcam.bbs.entity.Anniversary;
import com.mulcam.bbs.entity.SchDay;
import com.mulcam.bbs.entity.Schedule;
import com.mulcam.bbs.service.AnniversaryService;
import com.mulcam.bbs.service.SchedUtil;
import com.mulcam.bbs.service.ScheduleService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired private SchedUtil schedUtil;
	@Autowired private ScheduleService schedService;
	@Autowired private AnniversaryService annivService;
	
	@GetMapping(value = {"/calendar/{arrow}", "/calendar"})
	public String calendar(@PathVariable(required = false) String arrow, HttpSession session, Model model) {
		LocalDate today = LocalDate.now();
		String date = "일 월 화 수 목 금 토".split(" ")[today.getDayOfWeek().getValue() % 7];
		int year = 2000, month = 1;
		String sessionMonthYear = (String) session.getAttribute("scheduleMonthYear");
		if (sessionMonthYear == null) {
			year = today.getYear();
			month = today.getMonthValue();
		} else {
			year = Integer.parseInt(sessionMonthYear.substring(0,4));
			month = Integer.parseInt(sessionMonthYear.substring(5));
		}
		
		if (arrow != null) {
			switch(arrow) {
			case "left":
				month = month - 1;
				if (month == 0) {
					month = 12;
					year = year - 1;
				}
				break;
			case "right":
				month = month + 1;
				if (month == 13) {
					month = 1;
					year = year + 1;
				}
				break;
			case "left2":
				year = year - 1;
				break;
			case "right2":
				year = year + 1;
			}
		}
		sessionMonthYear = String.format("%d.%02d", year, month);
		session.setAttribute("scheduleMonthYear", sessionMonthYear);
		String sessionUid = (String) session.getAttribute("uid");
		
		List<SchDay> week = new ArrayList<>();
		List<List<SchDay>> calendar = new ArrayList<>();
		LocalDate startDay = LocalDate.parse(String.format("%d-%02d-01", year, month));
		int startDate = startDay.getDayOfWeek().getValue() % 7;		// 1 ~ 7 사이의 값을 0 ~ 6 사이의 값으로
		LocalDate lastDay = startDay.withDayOfMonth(startDay.lengthOfMonth());
		int lastDate = lastDay.getDayOfWeek().getValue() % 7;
		
		// 아래에서 k는 날짜, i는 요일을 가리킴
		String sdate = null;
		// 첫번째 주
		if (startDate != 0) {
			LocalDate prevSunDay = startDay.minusDays(startDate);
			int prevDay = prevSunDay.getDayOfMonth();
			int prevYear = prevSunDay.getYear();
			int prevMonth = prevSunDay.getMonthValue();
			for (int i=0; i<startDate; i++) {
				sdate = String.format("%d%02d%d", prevYear, prevMonth, prevDay+i);
				week.add(schedService.generateSchDay(sessionUid, sdate, i, 1));
			}
		}
		for (int i=startDate, k=1; i<7; i++, k++) {
			sdate = String.format("%d%02d%02d", year, month, k);
			week.add(schedService.generateSchDay(sessionUid, sdate, i, 0));
		}
		calendar.add(week);
		
		// 둘째 주부터 해당월의 마지막까지
		int day = 8 - startDate;
		for (int k=day, i=0; k<=lastDay.getDayOfMonth(); k++, i++) {
			if (i % 7 == 0)
				week = new ArrayList<>();
			sdate = String.format("%d%02d%02d", year, month, k);
			week.add(schedService.generateSchDay(sessionUid, sdate, i % 7, 0));
			if (i % 7 == 6)
				calendar.add(week);
		}
		// 마지막 주 다음달 내용
		if (lastDate != 6) {
			LocalDate nextDay = lastDay.plusDays(1);
			int nextYear = nextDay.getYear();
			int nextMonth = nextDay.getMonthValue();
			for (int i=lastDate+1, k=1; i<7; i++, k++) {
				sdate = String.format("%d%02d%02d", nextYear, nextMonth, k);
				week.add(schedService.generateSchDay(sessionUid, sdate, i, 1));
			}
			calendar.add(week);
		}
		
		model.addAttribute("calendar", calendar);
		model.addAttribute("today", today + "(" + date + ")");
		model.addAttribute("year", year);
		model.addAttribute("month", String.format("%02d", month));
		model.addAttribute("numberOfWeeks", calendar.size());
		model.addAttribute("timeList", schedUtil.genTime());
		return "schedule/calendar";
	}
	
	@PostMapping("/insert")
	public String insert(HttpServletRequest req, HttpSession session) {
		int isImportant = (req.getParameter("importance") == null) ? 0 : 1;
		String title = req.getParameter("title");
		String startDate = req.getParameter("startDate");
		String startTime = req.getParameter("startTime");
		LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T" + startTime + ":00");
		String endDate = req.getParameter("endDate");
		String endTime = req.getParameter("endTime");
		LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T" + endTime + ":00");
		String place = req.getParameter("place");
		String memo = req.getParameter("memo");
		String sdate = startDate.replace("-", "");
		String uid = (String) session.getAttribute("uid");
		Schedule schedule = new Schedule(uid, sdate, title, place, startDateTime, endDateTime, isImportant, memo);
		schedService.insert(schedule);
		return "redirect:/schedule/calendar";
	}
	
	// Ajax로 detail data 받음
	@ResponseBody
	@GetMapping("/detail/{sid}")
	public String detail(@PathVariable int sid) {
		Schedule sched = schedService.getSchedule(sid);
		JSONObject jSched = new JSONObject();
		jSched.put("sid", sid);
		jSched.put("title", sched.getTitle());
		jSched.put("place", sched.getPlace());
		jSched.put("startTime", sched.getStartTime().toString());
		jSched.put("endTime", sched.getEndTime().toString());
		jSched.put("isImportant", sched.getIsImportant());
		jSched.put("memo", sched.getMemo());
//		System.out.println(jSched.toString());
		return jSched.toString();
	}
	
	@PostMapping("/update")
	public String update(HttpServletRequest req, HttpSession session) {
		int isImportant = (req.getParameter("importance") == null) ? 0 : 1;
		int sid = Integer.parseInt(req.getParameter("sid"));
		String title = req.getParameter("title");
		String startDate = req.getParameter("startDate");
		String startTime = req.getParameter("startTime");
		LocalDateTime startDateTime = LocalDateTime.parse(startDate + "T" + startTime + ":00");
		String endDate = req.getParameter("endDate");
		String endTime = req.getParameter("endTime");
		LocalDateTime endDateTime = LocalDateTime.parse(endDate + "T" + endTime + ":00");
		String place = req.getParameter("place");
		String memo = req.getParameter("memo");
		String sdate = startDate.replace("-", "");
		String uid = (String) session.getAttribute("uid");
		Schedule schedule = new Schedule(sid, uid, sdate, title, place, startDateTime, endDateTime, isImportant, memo);
		schedService.update(schedule);
		return "redirect:/schedule/calendar";
	}
	
	@GetMapping("/delete/{sid}")
	public String delete(@PathVariable int sid) {
		schedService.delete(sid);
		return "redirect:/schedule/calendar";
	}
	
	@PostMapping("/insertAnniv")
	public String insertAnniv(HttpServletRequest req) {
		String aname = req.getParameter("title");
		int isHoliday = (req.getParameter("holiday") == null) ? 0 : 1;
		String adate = req.getParameter("annivDate").replace("-", "");
		Anniversary anniv = new Anniversary(aname, adate, isHoliday);
		annivService.insert(anniv);
		return "redirect:/schedule/calendar";
	}
	
	@GetMapping("/list/{page}")
	public String listView(@PathVariable int page, HttpSession session, Model model) {
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		String date = "일 월 화 수 목 금 토".split(" ")[today.getDayOfWeek().getValue() % 7];
		String sessionUid = (String) session.getAttribute("uid");
		session.setAttribute("currentSchedulePage", page);
		List<Schedule> list = schedService.getSchedListByPage(sessionUid, today.toString().replace("-", ""), page);
		
		int totalScheds = schedService.getSchedCount(sessionUid, today.toString().replace("-", ""));
		int totalPages = (int) Math.ceil(totalScheds / 15.);
		List<String> pageList = new ArrayList<>();
		for (int i = 1; i <= totalPages; i++)
			pageList.add(String.valueOf(i));
		
		model.addAttribute("pageList", pageList);
		model.addAttribute("today", today + "(" + date + ")");
		model.addAttribute("year", year);
		model.addAttribute("month", String.format("%02d", month));
		model.addAttribute("schedList", list);
		return "schedule/list";
	}
	
}
