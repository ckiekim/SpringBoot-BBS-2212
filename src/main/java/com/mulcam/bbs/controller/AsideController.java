package com.mulcam.bbs.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/aside")
public class AsideController {

	@Value("${weatherKey}") private String weatherKey;
	
	@ResponseBody
	@GetMapping("/weather")
	public String weather(String addr) throws Exception {
		System.out.println(addr);
		Double lat = 37.311494, lon = 127.075369;
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather" 
					+ "?lat=" + lat
					+ "&lon=" + lon
					+ "&appid=" + weatherKey
					+ "&units=metric&lang=kr";
		
		URL url = new URL(apiUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		while((line = br.readLine()) != null)
			sb.append(line);
		br.close();
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		JSONObject weather = (JSONObject) ((JSONArray) object.get("weather")).get(0);
		String desc = (String) weather.get("description");
		String iconCode = (String) weather.get("icon");
		String iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";
		JSONObject main = (JSONObject) object.get("main");
		Double temp = (Double) main.get("temp");
		Double tempMin = (Double) main.get("temp_min");
		Double tempMax = (Double) main.get("temp_max");
		
		String html = "<img src=\"" + iconUrl + "\" height=\"24\">"
					+ desc + ", 온도: "
					+ temp + "&#8451";
		return html;
	}
}
