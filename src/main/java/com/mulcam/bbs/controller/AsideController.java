package com.mulcam.bbs.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mulcam.bbs.service.ImageUtil;
import com.mulcam.bbs.service.MapUtil;

@Controller
@RequestMapping("/aside")
public class AsideController {

	@Autowired private ImageUtil imageUtil;
	@Autowired private MapUtil mapUtil;
	@Value("${weatherKey}") private String weatherKey;
	@Value("${spring.servlet.multipart.location}") private String uploadDir;
	
	@ResponseBody
	@PostMapping("/profile")
	public String profile(MultipartFile profile, HttpSession session) throws Exception {
//	public String profile(MultipartHttpServletRequest req) throws Exception {	// 이 코드도 가능
//		MultipartFile profile = req.getFile("profile");
		String fname = profile.getOriginalFilename();
		File profileFile = new File(uploadDir + "/" + fname);
		profile.transferTo(profileFile);
		
		String newFname = imageUtil.squareImage(fname);
		session.setAttribute("sessionProfile", newFname);
		return newFname;
	}
	
	@ResponseBody
	@GetMapping("/stateMsg")
	public String stateMsg(String stateMsg, HttpSession session) {
		session.setAttribute("sessionStateMsg", stateMsg);
		return "0";
	}
	
	@ResponseBody
	@GetMapping("/address")
	public String addressChange(String addr, HttpSession session) {
		session.setAttribute("sessionAddress", addr);
		return "0";
	}
	
	@ResponseBody
	@GetMapping("/weather")
	public String weather(String addr, HttpSession session) throws Exception {
		String place = addr.strip() + "청";
		String roadAddr = mapUtil.getRoadAddr(place);
		List<String> geocode = mapUtil.getGeocode(roadAddr);
		String lat = geocode.get(1);
		String lon = geocode.get(0);
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather" 
					+ "?lat=" + lat + "&lon=" + lon + "&units=metric"
					+ "&appid=" + weatherKey + "&lang=kr";
		
		URL url = new URL(apiUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		while((line = br.readLine()) != null)
			sb.append(line);
		br.close();
//		System.out.println(sb.toString());
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		JSONObject weather = (JSONObject) ((JSONArray) object.get("weather")).get(0);
		String desc = (String) weather.get("description");
		String iconCode = (String) weather.get("icon");
		String iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
		JSONObject main = (JSONObject) object.get("main");
		Double temp_ = (Double) main.get("temp");
		String temp = String.format("%.1f", temp_);
		
//		String html = "<img src=\"" + iconUrl + "\" height=\"16\" class=\"me-2\">"
		String html = "<img src=\"" + iconUrl + "\" height=\"28\"> "
					+ desc + ", 온도: " + temp + "&#8451";
		session.setAttribute("sessionWeather", html);
		return html;
	}

}
