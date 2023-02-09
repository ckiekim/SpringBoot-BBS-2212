package com.mulcam.bbs.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mulcam.bbs.service.DiaryService;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
	
	@Autowired private DiaryService diaryService;

	@GetMapping(value = {"/index/{arrow}", "/index"})
	public String index(@PathVariable(required = false) String arrow, HttpSession session, Model model) {
		LocalDate today = LocalDate.now();
		String date = "일 월 화 수 목 금 토".split(" ")[today.getDayOfWeek().getValue()];
		int[] monthLength = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31}; 
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
				break;
			default:
			}
		}
		sessionMonthYear = String.format("%d.%02d", year, month);
		session.setAttribute("scheduleMonthYear", sessionMonthYear);
		if (diaryService.isLeapYear(year))
			monthLength[1] = 29;
		
		List<String> week = new ArrayList<>();
		List<List<String>> calendar = new ArrayList<>();
		LocalDate startDay = LocalDate.parse(String.format("%d-%02d-01", year, month));
		int startDate = startDay.getDayOfWeek().getValue() % 7;		// 1 ~ 7 사이의 값
		LocalDate lastDay = LocalDate.parse(String.format("%d-%02d-%d", year, month, monthLength[month-1]));
		int lastDate = lastDay.getDayOfWeek().getValue() % 7;
		
		if (startDate != 0) {
			LocalDate prevSunDay = startDay.minusDays(startDate);
			int prevDay = prevSunDay.getDayOfMonth();
			for (int i=0; i<startDate; i++)
				week.add((prevDay+i) + "");
		}
		for (int i=startDate, k=1; i<7; i++, k++)
			week.add(k + "");
		calendar.add(week);
		
		int day = 8 - startDate;
		for (int i=day, count=0; i<=monthLength[month-1]; i++, count++) {
			if (count % 7 == 0)
				week = new ArrayList<>();
			week.add(i + "");
			if (count % 7 == 6)
				calendar.add(week);
		}
		for (int i=lastDate+1, k=1; i<7; i++, k++)
			week.add(k + "");
		if (lastDate != 6)
			calendar.add(week);
		
		/*
		 * for (List<String> w: calendar) { w.forEach(x -> System.out.print(x + ", "));
		 * System.out.println(); }
		 */
		
		model.addAttribute("calendar", calendar);
		model.addAttribute("today", today + "(" + date + ")");
		model.addAttribute("year", year);
		model.addAttribute("month", String.format("%02d", month));
		model.addAttribute("numberOfWeeks", calendar.size());
		return "schedule/index";
	}
	
}
