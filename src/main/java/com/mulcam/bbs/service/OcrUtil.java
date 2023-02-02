package com.mulcam.bbs.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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
	
}
