package com.mulcam.bbs.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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
import org.springframework.stereotype.Service;

@Service
public class ApiUtil {
	
	@Value("${naver.accessId}") private String accessId;
	@Value("${naver.secretKey}") private String secretKey;
	@Value("${spring.servlet.multipart.location}") private String uploadDir;
	
	public String getObjectDetectResult(String fileName) throws Exception {
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
        out.append("--" + boundary).append(LF);
        out.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + fileName + "\"").append(LF);
        out.append("Content-Type: "  + URLConnection.guessContentTypeFromName(fileName)).append(LF);
        out.append(LF).flush();
        // 실제 파일을 읽어서 전송
        FileInputStream fis = new FileInputStream(uploadDir + "/" + fileName);
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
        
        return sb.toString();
	}
	
	public String getSpeechRecogResult(File audioFile, String language) throws Exception {
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
        os.flush(); is.close(); os.close();

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
		
		return text;
	}
	
	public String getPoseEstimationResult(File uploadFile) throws Exception {
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
        
        return sb.toString();
	}
	
	public String getSentimentResult(String content) throws Exception {
        String apiURL = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";
        URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        conn.setRequestProperty("Content-Type", "application/json");
        		
        // post request
        conn.setDoOutput(true);
        // JSON 형식으로 전송
        String postParams = "{\"content\": \"" + content + "\"}";
        OutputStream os = conn.getOutputStream();
        byte[] buffer = postParams.getBytes();
        os.write(buffer);
        os.flush(); os.close();
		
        int responseCode = conn.getResponseCode();
        BufferedReader br;
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  // 오류 발생
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String inputLine;
        StringBuffer sb = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        br.close();
        if (responseCode!=200)
        	return sb.toString();
    	
    	JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		JSONObject document = (JSONObject) object.get("document");
		String sentiment = (String) document.get("sentiment");
		JSONObject confidence = (JSONObject) document.get("confidence");
		double proba = (Double) confidence.get(sentiment);
		sentiment += String.format(" (%.2f%%)", proba);
		
		return sentiment;
	}
	
}
