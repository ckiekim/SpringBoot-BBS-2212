package com.mulcam.bbs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mulcam.bbs.entity.FireStation;
import com.mulcam.bbs.entity.Genie;
import com.mulcam.bbs.entity.Interpark;
import com.mulcam.bbs.service.CrawlingService;

@Controller
@RequestMapping("/crawling")
public class CrawlingController {
	@Autowired private CrawlingService crawling;
	
	@GetMapping("/interpark")
	public String interpark(Model model) throws Exception {
		List<Interpark> list = crawling.interpark();
		model.addAttribute("bookList", list);
		return "crawling/interpark";
	}
	
	@GetMapping("/genie")
	public String genie(Model model) throws Exception {
		List<Genie> list = crawling.genie();
		model.addAttribute("songList", list);
		return "crawling/genie";
	}
	
	@GetMapping("/genie2")
	public String genie2(Model model) throws Exception {
		List<Genie> list = crawling.genie();
		model.addAttribute("songList", list);
		return "crawling/genie2";
	}
	
	@GetMapping("/fireStation")
	public String fireStationSpinner() {
		return "crawling/fireStationSpinner";
	}
	
	@PostMapping("/fireStation")
	public String fireStation(Model model) throws Exception {
		List<FireStation> list = crawling.fireStation();
		model.addAttribute("stationList", list);
		return "crawling/fireStation";
	}

}
