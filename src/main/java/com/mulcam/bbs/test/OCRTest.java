package com.mulcam.bbs.test;

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

public class OCRTest {

	public static void main(String[] args) throws Exception {
//		String apiURL = "https://r77a72wf4p.apigw.ntruss.com/custom/v1/20383/a0a44ed8cfc32b7e61befeb99bbff7706808c3fe4dcdf4750a8addb3ffcd4008/general";
//		String secretKey = "V1JpVHpDV3hjaGdtQlBkeWlyR0FSeldnRFhLbG9MUnM=";
		String apiURL = "https://ei72792oci.apigw.ntruss.com/custom/v1/20382/cdfc03af341c21e0171808b9398cd32e720127e2410ff211250f958135081153/general";
		String secretKey = "bUlaS2pvdHlHSnJFYVhqdllXdmhwWnlybHBEd216WFU=";
		String imageFile = "c:/Temp/자바_표지.jpg";

		URL url = new URL(apiURL);
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		con.setUseCaches(false);
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setReadTimeout(30000);
		con.setRequestMethod("POST");
		String boundary = "----" + UUID.randomUUID().toString().replaceAll("-", "");
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
		con.setRequestProperty("X-OCR-SECRET", secretKey);
		
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
		
		con.connect();
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		long start = System.currentTimeMillis();
		File file = new File(imageFile);
		writeMultiPart(wr, postParams, file, boundary);
		wr.close();
		
		int responseCode = con.getResponseCode();
		BufferedReader br;
		if (responseCode == 200) {
			br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {
			br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = br.readLine()) != null) {
			response.append(inputLine);
		}
		br.close();

		System.out.println(response);
	}

	private static void writeMultiPart(OutputStream out, String jsonMessage, File file, String boundary) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("--").append(boundary).append("\r\n");
		sb.append("Content-Disposition:form-data; name=\"message\"\r\n\r\n");
		sb.append(jsonMessage);
		sb.append("\r\n");
	
		out.write(sb.toString().getBytes("UTF-8"));
		out.flush();
	
		if (file != null && file.isFile()) {
			out.write(("--" + boundary + "\r\n").getBytes("UTF-8"));
			StringBuilder fileString = new StringBuilder();
			fileString
				.append("Content-Disposition:form-data; name=\"file\"; filename=");
			fileString.append("\"" + file.getName() + "\"\r\n");
			fileString.append("Content-Type: application/octet-stream\r\n\r\n");
			out.write(fileString.toString().getBytes("UTF-8"));
			out.flush();
	
			try (FileInputStream fis = new FileInputStream(file)) {
				byte[] buffer = new byte[8192];
				int count;
				while ((count = fis.read(buffer)) != -1) {
					out.write(buffer, 0, count);
				}
				out.write("\r\n".getBytes());
			}
	
			out.write(("--" + boundary + "--\r\n").getBytes("UTF-8"));
		}
		out.flush();
	}
	
}
