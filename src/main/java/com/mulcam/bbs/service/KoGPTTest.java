package com.mulcam.bbs.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class KoGPTTest {

	public static void main(String[] args) throws Exception {
		String apiKey = "060f9648568c04a02525e784bab79b9a";
		URL url = new URL("https://api.kakaobrain.com/v1/inference/kogpt/generation");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		
		conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
        		
        // post request
        conn.setDoOutput(true);
        // JSON 형식으로 전송
        String prompt = "오늘 아침은 기온이 급강하해서";
        int maxTokens = 120;
        int numResults = 2;
        String postParams = "{"
        		+ "        \"prompt\": \"" + prompt + "\","
        		+ "        \"max_tokens\": " + maxTokens + ","
        		+ "        \"n\": " + numResults
        		+ "        }";
        System.out.println(postParams);
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
        
        System.out.println(sb.toString());
        
        JSONParser parser = new JSONParser();
        JSONObject object = (JSONObject) parser.parse(sb.toString());
        JSONArray generations = (JSONArray) object.get("generations");
        JSONObject res1 = (JSONObject) generations.get(0);
        String text1 = (String) res1.get("text");
        JSONObject res2 = (JSONObject) generations.get(1);
        String text2 = (String) res2.get("text");

        System.out.println(prompt + text1);
        System.out.println(prompt + text2);
	}

}
