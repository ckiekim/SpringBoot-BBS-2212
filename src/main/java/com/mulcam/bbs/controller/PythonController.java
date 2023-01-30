package com.mulcam.bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/python")
public class PythonController {

	@GetMapping("/chatbot")
	public String chatbot() {
		return "python/chatbot";
	}
	
}
