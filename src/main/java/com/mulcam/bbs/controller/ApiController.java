package com.mulcam.bbs.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

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

import com.mulcam.bbs.service.MapUtil;
import com.mulcam.bbs.service.TransUtil;


@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired private MapUtil mapUtil;
	@Autowired private TransUtil transUtil;
	@Value("${naver.accessId}") private String accessId;
	@Value("${naver.secretKey}") private String secretKey;
	@Value("${roadAddrKey}") private String roadAddrKey;
	@Value("${spring.servlet.multipart.location}") private String uploadDir;
	
	@GetMapping("/roadAddr/{keyword}")
	public String roadAddr(@PathVariable String keyword, Model model) throws Exception {
		String addr = mapUtil.getRoadAddr(keyword);
		model.addAttribute("keyword", keyword);
		model.addAttribute("addr", addr);
		return "api/roadAddr";
	}
	
	@GetMapping("/geocode/{keyword}")
	public String geocode(@PathVariable String keyword, Model model) throws Exception {
		List<String> coord = mapUtil.getGeocode(keyword);
		model.addAttribute("keyword", keyword);
		model.addAttribute("coord", coord);
		return "api/geocode";
	}
	
	@GetMapping("/hotPlaces")
	public String hotPlacesForm(Model model) {
		List<String> list = new ArrayList<>();
		for (int i=1; i<=20; i++)
			list.add(String.valueOf(i));
		model.addAttribute("levelList", list);
		return "api/hotPlacesForm";
	}
	
	@PostMapping("/hotPlaces")
	public String hotPlaces(String[] places, int level, Model model) throws Exception {
		List<List<String>> dataList = new ArrayList<>();
		for (String place: places) {
			if (place.equals("")) 
				continue;
			List<String> row = new ArrayList<>();
			String roadAddr = mapUtil.getRoadAddr(place);
			List<String> geocode = mapUtil.getGeocode(roadAddr);
			row.add(place); row.add(roadAddr);
			row.add(geocode.get(0)); row.add(geocode.get(1));
			dataList.add(row);
		}
		
		String marker = "";
		double lngSum = 0.0, latSum = 0.0;
		for (List<String> list: dataList) {
			double lng = Double.parseDouble(list.get(2));
			double lat = Double.parseDouble(list.get(3));
			lngSum += lng; latSum += lat;
			marker += "&markers=type:t|size:tiny|pos:" + lng + "%20" + lat + "|label:"
					+ URLEncoder.encode(list.get(0), "utf-8") + "|color:red";
		}
		double lngCenter = lngSum / dataList.size();
		double latCenter = latSum / dataList.size();
		String url = "https://naveropenapi.apigw.ntruss.com/map-static/v2/raster"
				+ "?w=" + 600 + "&h=" + 400
				+ "&center=" + lngCenter + "," + latCenter
				+ "&level=" + level + "&scale=" + 2
				+ "&X-NCP-APIGW-API-KEY-ID=" + accessId
				+ "&X-NCP-APIGW-API-KEY=" + secretKey;
		
		model.addAttribute("url", url+marker);
		return "api/hotPlacesResult";
	}
	
	@GetMapping("/detect")
	public String detectForm() {
		return "api/detectForm";
	}
	
	@PostMapping("/detect")
	public String detect(MultipartFile upload, Model model) throws Exception {
		File uploadFile = new File(upload.getOriginalFilename());
		upload.transferTo(uploadFile);				// uploadDir에 파일 저장
		
		String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-obj/v1/detect"; // 객체 인식
		URL url = new URL(apiURL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST"); 		// 생략 가능
        
        // multipart request
        String boundary = "---" + System.currentTimeMillis() + "---";
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        
        // 파일 전송 준비
        OutputStream os = conn.getOutputStream();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
        String LF = "\n";		// line feed
        String fileName = uploadFile.getName();
        out.append("--" + boundary).append(LF);
        out.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"").append(LF);
        out.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LF);
        out.append(LF).flush();
        // 실제 파일을 읽어서 전송
        FileInputStream fis = new FileInputStream(uploadDir + "/" + uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = fis.read(buffer)) != -1)
        	os.write(buffer, 0, bytesRead);			// buffer의 처음부터 읽은 데이터 수 만큼 전송
        os.flush();
        fis.close();
        out.append(LF);
        out.append("--" + boundary + "--").append(LF).flush();
        out.close();
        
        // 응답 결과 확인
 		int responseCode = conn.getResponseCode();
 		if (responseCode != 200)
 			System.out.println("error!!!!!!! responseCode= " + responseCode);
        
        // 결과 확인
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = br.readLine()) != null)
        	sb.append(line);
        br.close();
        
        model.addAttribute("fileName", fileName);
        model.addAttribute("jsonResult", sb.toString());
		return "api/detectResult";
	}
	
	@GetMapping("/translate")
	public String translateForm(Model model) {
		String[] langCode = {"ko", "en", "ja", "fr", "es", "zh-CN"};
		String[] langName = {"한국어", "영어", "일본어", "프랑스어", "스페인어", "중국어 간체"};
		List<List<String>> optionList = new ArrayList<>();
		for (int i=0; i<langCode.length; i++) {
			List<String> list = new ArrayList<>();
			list.add(langCode[i]); list.add(langName[i]);
			optionList.add(list);
		}
		model.addAttribute("options", optionList);
		return "api/transForm";
	}
	
	@PostMapping("/translate")
	public String translate(String text, String lang, Model model) throws Exception {
		String src = transUtil.detectLanguage(text);
		String transText = transUtil.translate(src, lang, text);
		model.addAttribute("lang", lang);
		model.addAttribute("srcText", text);
		model.addAttribute("dstText", transText);
		return "api/transResult";
	}
	
	@GetMapping("/speechRecog")
	public String speechRecogForm() {
		return "api/speechRecogForm";
	}
	
	@PostMapping("/speechRecog")
	public String speechRecog(String language, Model model) throws Exception {
		String audioFile_ = uploadDir + "/rawAudio.wav";
        File audioFile = new File(audioFile_);
        String apiURL = "https://naveropenapi.apigw.ntruss.com/recog/v1/stt?lang=" + language;
        URL url = new URL(apiURL);
        
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/octet-stream");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        
        OutputStream os = conn.getOutputStream();
        FileInputStream is = new FileInputStream(audioFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush(); is.close();

        BufferedReader br = null;
        int responseCode = conn.getResponseCode();
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  // 오류 발생
            System.out.println("error!!!!!!! responseCode= " + responseCode);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        
        String inputLine;
        StringBuffer sb = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        br.close();
        
        JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		String text = (String) object.get("text");
		model.addAttribute("text", text);
		return "api/speechRecogResult";
	}
	
	@ResponseBody
	@PostMapping("/audioUpload")
	public String audioUpload(MultipartFile audioBlob) throws Exception {
		File uploadFile = new File("rawAudio.wav");
		audioBlob.transferTo(uploadFile);
		return "0";
	}
	
	@GetMapping("/pose")
	public String poseForm() {
		return "api/poseForm";
	}
	
	@PostMapping("/pose")
	public String pose(MultipartFile upload, Model model) throws Exception {
		String imageFile_ = uploadDir + "/pose" + upload.getOriginalFilename();
		File uploadFile = new File(imageFile_);
		upload.transferTo(uploadFile);
        String apiURL = "https://naveropenapi.apigw.ntruss.com/vision-pose/v1/estimate"; // 사람 인식
        URL url = new URL(apiURL);
		
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setUseCaches(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        // multipart request
        String boundary = "---" + System.currentTimeMillis() + "---";
        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
		
        OutputStream os = conn.getOutputStream();
        PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "UTF-8"), true);
        String LINE_FEED = "\n";
        // file 추가
        String fileName = uploadFile.getName();
        out.append("--" + boundary).append(LINE_FEED);
        out.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"").append(LINE_FEED);
        out.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
        out.append(LINE_FEED);
        out.flush();
        
        FileInputStream is = new FileInputStream(uploadFile);
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
        while ((bytesRead = is.read(buffer)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.flush(); is.close();
        out.append(LINE_FEED).flush();
        out.append("--" + boundary + "--").append(LINE_FEED);
        out.close();
		
        BufferedReader br = null;
        int responseCode = conn.getResponseCode();
        if(responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  // 오류 발생
            System.out.println("error!!!!!!! responseCode= " + responseCode);
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }
        
        String inputLine;
        StringBuffer sb = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        br.close();
		
        model.addAttribute("fileName", fileName);
        model.addAttribute("jsonResutl", sb.toString());
		return "api/poseResult";
	}
	
}
