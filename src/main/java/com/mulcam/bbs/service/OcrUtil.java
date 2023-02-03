package com.mulcam.bbs.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mulcam.bbs.entity.Receipt;

@Service
public class OcrUtil {

	@Value("${naver.ocr.apiUrl}") private String apiURL;
	@Value("${naver.ocr.secretKey}") private String secretKey;
	@Value("${spring.servlet.multipart.location}") private String uploadDir;
	
	public String getOcrResult(String fileName) throws Exception {
		URL url = new URL(apiURL);
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setReadTimeout(30000);
		conn.setRequestMethod("POST");
		String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		conn.setRequestProperty("X-OCR-SECRET", secretKey);
		
		JSONObject json = new JSONObject();
		json.put("version", "V2");
		json.put("requestId", UUID.randomUUID().toString());
		json.put("timestamp", System.currentTimeMillis());
		JSONObject image = new JSONObject();
		image.put("format", "jpg");
		image.put("name", "demo");
		JSONArray images = new JSONArray();
		images.put(image);
		json.put("images", images);
		String postParams = json.toString();
		
		conn.connect();
		DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
		long start = System.currentTimeMillis();
		File file = new File(fileName);
		writeMultiPart(wr, postParams, file, boundary);
		wr.close();
		
		int responseCode = conn.getResponseCode();
		BufferedReader br;
		if (responseCode == 200) {
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		String inputLine;
		StringBuffer sb = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			sb.append(inputLine);
		}
		br.close();
		
		return sb.toString();
	}
	
	private void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws Exception {
		StringBuilder sb = new StringBuilder();
		String crlf = "\r\n";
		sb.append("--").append(boundary).append(crlf);
		sb.append("Content-Disposition:form-data; name=\"message\"" + crlf + crlf);
		sb.append(jsonMessage);
		sb.append(crlf);
	
		out.write(sb.toString().getBytes("UTF-8"));
		out.flush();
	
		if (file != null && file.isFile()) {
			out.write(("--" + boundary + crlf).getBytes("UTF-8"));
			StringBuilder fileString = new StringBuilder();
			fileString
				.append("Content-Disposition:form-data; name=\"file\"; filename=");
			fileString.append("\"" + file.getName() + "\"" + crlf);
			fileString.append("Content-Type: application/octet-stream" + crlf + crlf);
			out.write(fileString.toString().getBytes("UTF-8"));
			out.flush();
	
			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] buffer = new byte[8192];
				int count;
				while ((count = fis.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
				out.write(crlf.getBytes());
			}
	
			out.write(("--" + boundary + "--" + crlf).getBytes("UTF-8"));
		}
		out.flush();
	}
	
	public Receipt getReceipt(String jsonStr) {
        JSONObject json = new JSONObject(jsonStr);
        JSONObject result = (JSONObject) ((JSONArray) json.getJSONArray("images")).getJSONObject(0);
        JSONArray fields = (JSONArray) result.getJSONArray("fields");
        List<String> list = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        for (Object field: fields) {
            String text = ((JSONObject) field).getString("inferText");
            list.add(text);
            if (text.length() <= 2)
                continue;
            if (map.containsKey(text)) {	// ("32,900", 1) -> ("32,900", 2) -> ...
                int val = map.get(text);
                map.put(text, val+1);
            } else {
                map.put(text, 1);
            }
        }
        
        String shop="", date_="2000-01-01", price_="0";
        for (int i=0; i<list.size()-2; i++) {
            if (list.get(i).contains("매장") || list.get(i).contains("상호")) {
                if (list.get(i+1).length() <= 1)	// 매장 뒤에 콜론이 오는 경우
                    shop = list.get(i+2);
                else
                    shop = list.get(i+1);
                break;
            }
        }
        if (shop.equals("")) {
            for (int i=0; i<list.size()-3; i++) {
                if (list.get(i).equals("상")) {
                    if (list.get(i+1).contains("호")) {
                        if (list.get(i+2).length() <= 1)
                            shop = list.get(i+3);
                        else
                            shop = list.get(i+2);
                        break;
                    }
                }
            }
        }
        
        Pattern datePattern = Pattern.compile("\\d{4}[-/년]\\d{2}[-/월]\\d{2}");
        for (String token: list) {
            if (token.length() < 10)
                continue;
            Matcher matcher = datePattern.matcher(token);
            boolean matchFound = matcher.find();
            if (matchFound) {
            	int start = matcher.start();	// 패턴이 시작하는 위치
                date_ = token.substring(start);
                break;
            }
        }
        String dateStr = date_.substring(0,4) + "-" + date_.substring(5, 7) + "-" + date_.substring(8, 10);
        
        Set<String> keySet = map.keySet();
        Pattern numberPattern = Pattern.compile("\\d{1,3}(,\\d{3})*");
        int maxVal = 0;
        for (String key: keySet) {
            if (map.get(key) > maxVal) {
            	Matcher matcher = numberPattern.matcher(key);
            	boolean matchFound = matcher.find();
            	if (matchFound) {
	                maxVal = map.get(key);
	                price_ = key;
            	}
            }
        }
        int price = Integer.parseInt(price_.replaceAll(",", ""));
        
        Receipt receipt = new Receipt(shop, LocalDate.parse(dateStr), price);
        return receipt;
    }
	
}
