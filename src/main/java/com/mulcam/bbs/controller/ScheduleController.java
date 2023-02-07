package com.mulcam.bbs.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {

	@GetMapping("/index")
	public String index(Model model) {
		LocalDate today = LocalDate.now();
		int year = today.getYear();
		int month = today.getMonthValue();
		String date = "일 월 화 수 목 금 토".split(" ")[today.getDayOfWeek().getValue()];
		
		List<String> week = new ArrayList<>();
		List<List<String>> calendar = new ArrayList<>();
		LocalDate startDay = LocalDate.parse(String.format("%d-%02d-01", year, month));
		int startDate = startDay.getDayOfWeek().getValue();
		LocalDate lastDay = LocalDate.parse(String.format("%d-%02d-28", year, month));
		int lastDate = lastDay.getDayOfWeek().getValue();
		
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
		for (int i=day, count=0; i<=28; i++, count++) {
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
		return "schedule/index";
	}
}
