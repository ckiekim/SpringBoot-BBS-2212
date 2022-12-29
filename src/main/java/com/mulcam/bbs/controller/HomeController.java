package com.mulcam.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	@GetMapping("/bbs")
	public String index() {
		return "redirect:/bbs/user/login";
	}
	
}
