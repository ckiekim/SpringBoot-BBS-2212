package com.mulcam.bbs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mulcam.bbs.entity.User;
import com.mulcam.bbs.service.ProfileService;
import com.mulcam.bbs.service.UserService;

@Controller
@RequestMapping("/bbs/user")
public class UserController {

	@Autowired private UserService userService;
	@Autowired private ProfileService profileService;
	@Value("${bbsInitialUrl}") private String bbsInitialUrl;
	
	@GetMapping("/login")
	public String loginForm() {
		return "user/login";
	}
	
	@PostMapping("/login") 
	public String login(String uid, String pwd, HttpSession session, Model model) {
		int result = userService.login(uid, pwd, session);
		switch(result) {
		case UserService.CORRECT_LOGIN:
			profileService.setAsideValue(uid, session);
			model.addAttribute("msg", session.getAttribute("uname") + "님 환영합니다.");
			model.addAttribute("url", bbsInitialUrl);
			break;
		case UserService.WRONG_PASSWORD:
			model.addAttribute("msg", "잘못된 패스워드 입니다. 다시 입력하세요.");
			model.addAttribute("url", "/bbs/user/login"); 
			break;
		case UserService.UID_NOT_EXIST:
			model.addAttribute("msg", "회원 가입 페이지로 이동합니다.");
			model.addAttribute("url", "/bbs/user/register");
		}
		return "user/alertMsg";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/bbs/user/login";
	}
	
	@GetMapping("/register")
	public String registerForm() {
		return "user/register";
	}

	@PostMapping("/register")
	public String register(HttpServletRequest req, Model model) {
		String uid = req.getParameter("uid").strip();
		String pwd = req.getParameter("pwd").strip();
		String pwd2 = req.getParameter("pwd2").strip();
		String uname = req.getParameter("uname").strip();
		String email = req.getParameter("email").strip();

		User user = userService.getUser(uid);
		if (user != null) {
			model.addAttribute("msg", "중복 아이디입니다.");
			model.addAttribute("url", "/bbs/user/register");
			return "user/alertMsg";
		}
		if (pwd.equals(pwd2)) {
			user = new User(uid, pwd, uname, email);
			userService.registerUser(user);
			return "redirect:/bbs/user/login";
		} else {
			model.addAttribute("msg", "패스워드 입력이 잘못되었습니다.");
			model.addAttribute("url", "/bbs/user/register");
			return "user/alertMsg";
		}
	}
	
	@GetMapping("/list/{page}")
	public String list(@PathVariable int page, HttpSession session, Model model) {
		List<User> list = userService.getUserList(page);
		session.setAttribute("currentUserPage", page);
		
		int totalUsers = userService.getUserCount();
		int totalPages = (int) Math.ceil(totalUsers / 10.);
		List<String> pageList = new ArrayList<>();
		for (int i = 1; i <= totalPages; i++)
			pageList.add(String.valueOf(i));
		model.addAttribute("pageList", pageList);
		
		model.addAttribute("userList", list);
		return "user/list";
	}
	
	@GetMapping("/update/{uid}")
	public String updateForm(@PathVariable String uid, Model model) {
		User user = userService.getUser(uid);
		model.addAttribute("user", user);
		return "user/update";
	}
	
	@PostMapping("/update")
	public String update(HttpServletRequest req, Model model) {
		String uid = req.getParameter("uid");
		String pwd = req.getParameter("pwd").strip();
		String pwd2 = req.getParameter("pwd2").strip();
		String uname = req.getParameter("uname").strip();
		String email = req.getParameter("email").strip();
		HttpSession session = req.getSession();
		User user;
		
		if (pwd == null || pwd.equals("")) {	// 패스워드를 입력하지 않은 경우
			user = new User(uid, uname, email);
			userService.updateUser(user, "");
			session.setAttribute("uname", uname);
			session.setAttribute("sessionEmail", email);
			return "redirect:/bbs/user/list/" + session.getAttribute("currentUserPage");			
		} else if (pwd.equals(pwd2)) {			// 패스워드가 올바른 경우
			user = new User(uid, uname, email);
			userService.updateUser(user, pwd);
			session.setAttribute("uname", uname);
			session.setAttribute("sessionEmail", email);
			return "redirect:/bbs/user/list/" + session.getAttribute("currentUserPage");
		} else {								// 패스워드를 잘못 입력한 경우
			model.addAttribute("msg", "패스워드 입력이 잘못되었습니다.");
			model.addAttribute("url", "/bbs/user/update/" + uid);
			return "user/alertMsg";
		}
	}
	
	@GetMapping("/delete/{uid}")
	public String delete(@PathVariable String uid, Model model) {
		model.addAttribute("uid", uid);
		return "user/delete";
	}
	
	@GetMapping("/deleteConfirm/{uid}")
	public String deleteConfirm(@PathVariable String uid, HttpSession session) {
		userService.deleteUser(uid);
		return "redirect:/bbs/user/list/" + session.getAttribute("currentUserPage");
	}
	
}
