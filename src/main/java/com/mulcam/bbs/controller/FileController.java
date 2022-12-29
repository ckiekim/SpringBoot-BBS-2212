package com.mulcam.bbs.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping("/bbs/file")
public class FileController {
	
	@ResponseBody
	@PostMapping("/upload")
	public String upload(MultipartHttpServletRequest req) {
		String callback = req.getParameter("CKEditorFuncNum");
        String error = "";
        String url = "";
        Map<String, MultipartFile> map = req.getFileMap();
        for (Map.Entry<String, MultipartFile> pair: map.entrySet()) {
        	MultipartFile file = pair.getValue();
        	String fname = file.getOriginalFilename();
			String now = LocalDateTime.now().toString().substring(0,22).replaceAll("[-T:.]", "");
            int idx = fname.lastIndexOf('.');
            fname = now + fname.substring(idx);	// 유니크한 파일 이름으로 변경
			File fileName = new File(fname);
			try {
				file.transferTo(fileName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			url = "/bbs/file/download?file=" + fileName;
        }
		String data = "<script> "
                + "     window.parent.CKEDITOR.tools.callFunction(" 
                +           callback + ", '" + url + "', '" + error + "'); "
                + "</script>";
		return data;
	}
	
	@Value("${spring.servlet.multipart.location}")
	String uploadDir;
	
	@GetMapping("/download")
	public ResponseEntity<Resource> download(HttpServletRequest req) {
		String fileName = req.getParameter("file");
		Path path = Paths.get(uploadDir + "/" + fileName);
		try {
			String contentType = Files.probeContentType(path);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDisposition(
				ContentDisposition.builder("attachment")
					.filename(fileName, StandardCharsets.UTF_8)
					.build()
			);
			headers.add(HttpHeaders.CONTENT_TYPE, contentType);
			Resource resource = new InputStreamResource(Files.newInputStream(path));
			return new ResponseEntity<>(resource, headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
