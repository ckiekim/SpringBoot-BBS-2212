package com.mulcam.bbs.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransUtil {
	@Value("${papago.accessId}") private String accessId;
	@Value("${papago.secretKey}") private String secretKey;
	
	public String detectLanguage(String text) throws Exception {
		String query = URLEncoder.encode(text, "UTF-8");
        String apiURL = "https://naveropenapi.apigw.ntruss.com/langs/v1/dect";
        URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        // post request
        String postParams = "query=" + query;
        conn.setDoOutput(true);
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.writeBytes(postParams);
        dos.flush();
        dos.close();
		
        int responseCode = conn.getResponseCode();
        BufferedReader br;
        if(responseCode==200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {  				// 오류 발생
            br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        String inputLine;
        StringBuffer sb = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        br.close();
        if (responseCode != 200)
        	return sb.toString();
        
        JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		String langCode = (String) object.get("langCode");
		return langCode;
	}
	
	public String translate(String src, String dst, String text_) throws Exception {
		String text = URLEncoder.encode(text_, "UTF-8");
        String apiURL = "https://naveropenapi.apigw.ntruss.com/nmt/v1/translation";
        URL url = new URL(apiURL);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
        conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
        // post request
        String postParams = "source=" + src + "&target=" + dst + "&text=" + text;
        conn.setDoOutput(true);
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        dos.writeBytes(postParams);
        dos.flush();
        dos.close();
		
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
        if (responseCode != 200)
        	return sb.toString();
        
        JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		JSONObject result = (JSONObject) ((JSONObject) object.get("message")).get("result");
		String transText = (String) result.get("translatedText");
		return transText;
	}
	
}
