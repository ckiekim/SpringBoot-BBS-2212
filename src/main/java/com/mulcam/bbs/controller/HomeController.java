package com.mulcam.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping("/bbs")
	public String index() {
		return "redirect:/bbs/user/login";
	}
	
}
