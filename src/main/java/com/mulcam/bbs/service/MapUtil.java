package com.mulcam.bbs.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MapUtil {
	@Value("${naver.accessId}") private String accessId;
	@Value("${naver.secretKey}") private String secretKey;
	@Value("${roadAddrKey}") private String roadAddrKey;
	
	/**
	 * 건물명으로부터 도로명 주소를 구해주는 메소드
	 */
	public String getRoadAddr(String keyword) throws Exception {
		int currentPage = 1;
		int countPerPage = 10;
		String resultType = "json";
		keyword = URLEncoder.encode(keyword, "utf-8");
		String apiUrl = "https://business.juso.go.kr/addrlink/addrLinkApi.do"
						+ "?confmKey=" + roadAddrKey
						+ "&currentPage=" + currentPage
						+ "&countPerPage=" + countPerPage
						+ "&keyword=" + keyword
						+ "&resultType=" + resultType;
		
		URL url = new URL(apiUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		while((line = br.readLine()) != null)
			sb.append(line);
		br.close();
		
		// JSON 데이터에서 원하는 값 추출하기
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		JSONObject results = (JSONObject) object.get("results");
		JSONArray juso = (JSONArray) results.get("juso");
		if (juso == null || juso.size() == 0)
			return null;
		JSONObject jusoItem = (JSONObject) juso.get(0);
		String roadAddr = (String) jusoItem.get("roadAddr");
		
		return roadAddr;
	}
	
	/**
	 * 도로명주소로부터 경도, 위도 정보를 구해주는 메소드
	 */
	public List<String> getGeocode(String query) throws Exception {
		String apiUrl = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode";
		query = URLEncoder.encode(query, "utf-8");
		apiUrl += "?query=" + query;
		
		URL url = new URL(apiUrl);
		// 헤더 설정
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", accessId);
		conn.setRequestProperty("X-NCP-APIGW-API-KEY", secretKey);
		conn.setDoInput(true);
		
		// 응답 결과 확인
		int responseCode = conn.getResponseCode();
		
		// 데이터 수신
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		while((line = br.readLine()) != null)
			sb.append(line);
		br.close();
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(sb.toString());
		JSONObject address = (JSONObject) ((JSONArray) object.get("addresses")).get(0);
		String lng = (String) address.get("x");
		String lat = (String) address.get("y");
		
		List<String> list = new ArrayList<>();
		list.add(lng); list.add(lat);
		return list;
	}
	
	/**
	 *  여러개 위치를 주면 그곳들의 좌표를 찾아 지도 API url을 반환하는 메소드
	 */
	public String getHotPlacesUrl(String[] places, int level) throws Exception {
		List<List<String>> dataList = new ArrayList<>();
		for (String place: places) {
			if (place.equals("")) 
				continue;
			List<String> row = new ArrayList<>();
			String roadAddr = getRoadAddr(place);
			List<String> geocode = getGeocode(roadAddr);
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
		
		return url + marker;
	}
	
}
