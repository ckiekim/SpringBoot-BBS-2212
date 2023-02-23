package com.mulcam.bbs.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class KoGPTUtil {

	@Value("${kakao.apiKey}") private String apiKey;
	
	public List<String> generateText(String prompt, int maxTokens, int numResults) throws Exception {
		URL url = new URL("https://api.kakaobrain.com/v1/inference/kogpt/generation");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
        		
        // post request
        conn.setDoOutput(true);
        // JSON 형식으로 전송
        String postParams = "{"
        		+ "        \"prompt\": \"" + prompt + "\","
        		+ "        \"max_tokens\": " + maxTokens + ","
        		+ "        \"n\": " + numResults
        		+ "        }";
        OutputStream os = conn.getOutputStream();
        byte[] buffer = postParams.getBytes();
        os.write(buffer);
        os.flush(); os.close();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuffer sb = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            sb.append(inputLine);
        }
        br.close();
//        System.out.println(sb.toString());
        
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(sb.toString());
        JSONArray generations = (JSONArray) object.get("generations");
        List<String> list = new ArrayList<>();
        for (int i=0; i<numResults; i++) {
        	JSONObject res = (JSONObject) generations.get(i);
        	String text = (String) res.get("text");
        	list.add(prompt + text); 
        }
        return list;
	}

}
