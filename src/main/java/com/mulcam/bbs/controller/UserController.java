package com.mulcam.bbs.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulcam.bbs.service.UserService;

@Controller
@RequestMapping("/bbs/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}
	
	@ResponseBody
	@PostMapping("/login") 
	public String login(HttpServletRequest req) {
		String uid = req.getParameter("uid");
		String pwd = req.getParameter("pwd");
		HttpSession session = req.getSession();
		int result = userService.login(uid, pwd, session);
		return "" + result;
	}
	
}
