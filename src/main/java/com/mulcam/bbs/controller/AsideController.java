package com.mulcam.bbs.controller;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mulcam.bbs.entity.Profile;
import com.mulcam.bbs.service.ImageUtil;
import com.mulcam.bbs.service.MapUtil;
import com.mulcam.bbs.service.ProfileService;

@Controller
@RequestMapping("/aside")
public class AsideController {

	@Autowired private ImageUtil imageUtil;
	@Autowired private MapUtil mapUtil;
	@Autowired private ProfileService profileService;
	@Value("${weatherKey}") private String weatherKey;
	@Value("${spring.servlet.multipart.location}") private String uploadDir;
	
	@ResponseBody
	@GetMapping("/stateMsg")
	public String stateMsg(String stateMsg, HttpSession session) {
		session.setAttribute("sessionStateMsg", stateMsg);
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
	
	@ResponseBody
	@GetMapping("/profile/{uid}")
	public String profileForm(@PathVariable String uid) {
		Profile profile = profileService.getProfile(uid);
		JSONObject obj = profileService.makeJsonProfile(profile);
		return obj.toString();
	}
	
	@ResponseBody
	@PostMapping("/profile")
	public String profile(MultipartHttpServletRequest req, HttpSession session, Model model) throws Exception {
		String uid = req.getParameter("uid");
		String github = req.getParameter("github");
		String instagram = req.getParameter("instagram");
		String facebook = req.getParameter("facebook");
		String twitter = req.getParameter("twitter");
		String homepage = req.getParameter("homepage");
		String blog = req.getParameter("blog");
		String addr = req.getParameter("addr");
		String filename = req.getParameter("filename");
		
		byte[] bytes = null;
		int size = 0;
		boolean hasImage = false;		// 이미지를 변경하지 않는 경우 대처
		MultipartFile image = req.getFile("image");
		if (image != null) {
			hasImage = true;
			bytes = image.getBytes();
			filename = image.getOriginalFilename();
			size = bytes.length;
		}
		Profile profile = new Profile(uid, github, instagram, facebook, twitter, homepage, blog, addr,
				bytes, size, filename);
//		System.out.println(profile);
		if (profileService.getProfile(uid) == null)
			profileService.insert(profile, session);
		else if (hasImage)
			profileService.update(profile, session);	// 이미지를 변경하는 경우
		else
			profileService.updateWithoutImage(profile, session);
		
		JSONObject obj = profileService.makeJsonProfile(profile);
		return obj.toString();
	}
	
	// BLOB 타입의 이미지를 출력하는 방법
	@GetMapping("/blob/{uid}")
	public void blob(@PathVariable String uid, HttpServletResponse res) throws Exception {
		Profile profile = profileService.getProfile(uid);
		int idx = profile.getFilename().lastIndexOf('.');
        String format = profile.getFilename().substring(idx + 1);
		
        byte[] bytes = imageUtil.squareImage(profile.getImage(), format);
		InputStream is = new ByteArrayInputStream(bytes);
		
		File file = new File(profile.getFilename());
	    String mimeType = URLConnection.guessContentTypeFromName(file.getName());
//	    System.out.println("MIME type: " + mimeType);
		res.setContentType(mimeType);
		IOUtils.copy(is, res.getOutputStream());
	}
	
}
