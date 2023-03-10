package com.mulcam.bbs.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mulcam.bbs.service.KoGPTUtil;

@Controller
@RequestMapping("/python")
public class PythonController {
	
	@Autowired private KoGPTUtil koGPTUtil;
	@Value("${naver.IPaddr}") private String ipAddr;

	@GetMapping("/chatbot")
	public String chatbot(Model model) {
		model.addAttribute("ipAddr", ipAddr);
		return "python/chatbot";
	}
	
	@GetMapping("/chatbot2")
	public String chatbot2Form() {
		return "python/chatbot2";
	}
	
	@ResponseBody
	@PostMapping("/chatbot2")
	public String chatbot2Result(String userInput) throws Exception {
		userInput = URLEncoder.encode(userInput, "utf-8");
		String apiUrl = "http://" + ipAddr + ":5000/chatbot?userInput=" + userInput;
		
		URL url = new URL(apiUrl);
		BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
		StringBuffer sb = new StringBuffer();
		String line = null;
		
		while((line = br.readLine()) != null)
			sb.append(line);
		br.close();
		
		return sb.toString();
	}
	
	@GetMapping("/genText")
	public String genTextForm() {
		return "python/genText";
	}
	
	@PostMapping("/genText")
	public String genTextResult(String prompt, int maxTokens, int numResults, Model model) throws Exception {
		List<String> list = koGPTUtil.generateText(prompt, maxTokens, numResults);
//		list.forEach(x -> System.out.println(x));
		model.addAttribute("textList", list);
		return "python/genText";
	}
	
}
