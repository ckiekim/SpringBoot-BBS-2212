package com.mulcam.bbs.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

import com.mulcam.bbs.service.ApiUtil;
import com.mulcam.bbs.service.ImageUtil;
import com.mulcam.bbs.service.MapUtil;
import com.mulcam.bbs.service.OcrUtil;
import com.mulcam.bbs.service.TransUtil;


@Controller
@RequestMapping("/api")
public class ApiController {

	@Autowired private ApiUtil apiUtil;
	@Autowired private MapUtil mapUtil;
	@Autowired private TransUtil transUtil;
	@Autowired private ImageUtil imageUtil;
	@Autowired private OcrUtil ocrUtil;
	@Value("${naver.accessId}") private String accessId;
	@Value("${naver.secretKey}") private String secretKey;
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
		String url = mapUtil.getHotPlacesUrl(places, level);
		model.addAttribute("url", url);
		return "api/hotPlacesResult";
	}
	
	@GetMapping("/detect")
	public String detectForm() {
		return "api/detectForm";
	}
	
	@PostMapping("/detect")
	public String detect(MultipartFile upload, Model model) throws Exception {
		String detectFile_ = uploadDir + "/detect" + upload.getOriginalFilename();
		File uploadFile = new File(detectFile_);
		upload.transferTo(uploadFile);				// uploadDir에 파일 저장
		String fileName = uploadFile.getName();
		imageUtil.resizeImage(fileName);
		
		String jsonResult = apiUtil.getObjectDetectResult(fileName);
        model.addAttribute("fileName", fileName);
        model.addAttribute("jsonResult", jsonResult);
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
        String text = apiUtil.getSpeechRecogResult(audioFile, language);
		model.addAttribute("text", text);
		return "api/speechRecogResult";
	}
	
	@ResponseBody
	@PostMapping("/audioUpload")
	public String audioUpload(MultipartFile audioBlob) throws Exception {
		String audioFile_ = uploadDir + "/rawAudio.wav"; 
		File uploadFile = new File(audioFile_);
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
		String fileName = uploadFile.getName();
		imageUtil.resizeImage(fileName);
		
		String jsonResult = apiUtil.getPoseEstimationResult(uploadFile);
        model.addAttribute("fileName", fileName);
        model.addAttribute("jsonResult", jsonResult);
		return "api/poseResult";
	}
	
	@GetMapping("/sentiment")
	public String sentimentForm() {
		return "api/sentimentForm";
	}
	
	@PostMapping("/sentiment")
	public String sentiment(String content, Model model) throws Exception {
		String result = apiUtil.getSentimentResult(content);
		model.addAttribute("content", content);
		model.addAttribute("result", result);
		return "api/sentimentResult";
	}
	
	@GetMapping("/ocr")
	public String ocrForm() {
		return "api/ocrForm";
	}
	
	@PostMapping("/ocr")
	public String ocr(MultipartFile upload, Model model) throws Exception {
		String ocrFile = uploadDir + "/ocr" + upload.getOriginalFilename();
		File uploadFile = new File(ocrFile);
		upload.transferTo(uploadFile);
		
		String jsonResult = ocrUtil.getOcrResult(ocrFile);
		String fileName = uploadFile.getName();
        model.addAttribute("fileName", fileName);
        model.addAttribute("jsonResult", jsonResult);
		return "api/ocrResult";
	}
	
}
