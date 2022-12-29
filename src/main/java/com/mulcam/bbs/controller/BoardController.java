package com.mulcam.bbs.controller;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mulcam.bbs.entity.Board;
import com.mulcam.bbs.entity.Reply;
import com.mulcam.bbs.service.BoardService;
import com.mulcam.bbs.service.JSONUtil;

@Controller
@RequestMapping("/bbs/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@GetMapping("/list")
	public String list(HttpServletRequest req, Model model) {
		String page_ = req.getParameter("p");
		String field = req.getParameter("f");
		String query = req.getParameter("q");
		
		int page = (page_ == null || page_.equals("")) ? 1 : Integer.parseInt(page_);
		field = (field == null || field.equals("")) ? "title" : field;
		query = (query == null || query.equals("")) ? "" : query;
		List<Board> list = boardService.getBoardList(page, field, query);
		
		HttpSession session = req.getSession();
		session.setAttribute("currentBoardPage", page);
		model.addAttribute("field", field);
		model.addAttribute("query", query);
		
		int totalBoardNo = boardService.getBoardCount("bid", "");
		int totalPages = (int) Math.ceil(totalBoardNo / 10.);
		
		int startPage = (int)(Math.ceil((page-0.5)/10) - 1) * 10 + 1;
		int endPage = Math.min(totalPages, startPage + 9);
		List<String> pageList = new ArrayList<>();
		for (int i = startPage; i <= endPage; i++) 
			pageList.add(String.valueOf(i));
		model.addAttribute("pageList", pageList);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalPages", totalPages);
		
		String today = LocalDate.now().toString(); // 2022-12-28
		model.addAttribute("today", today);
		model.addAttribute("boardList", list);
		return "board/list";
	}

	@GetMapping("/detail")
	public String detail(HttpServletRequest req, Model model) {
		int bid = Integer.parseInt(req.getParameter("bid"));
		String uid = req.getParameter("uid");
		String option = req.getParameter("option");
		HttpSession session = req.getSession();
		String sessionUid = (String) session.getAttribute("uid");
		
		// 조회수 증가. 단, 본인이 읽거나 댓글 작성후에는 제외.
		if (option == null && (!uid.equals(sessionUid))) 
			boardService.increaseViewCount(bid);
		
		Board board = boardService.getBoard(bid);
		String jsonFiles = board.getFiles();
		if (!(jsonFiles == null || jsonFiles.equals(""))) {
			JSONUtil json = new JSONUtil();
			List<String> fileList = json.parse(jsonFiles);
			model.addAttribute("fileList", fileList);
		}
		model.addAttribute("board", board);
		List<Reply> replyList = boardService.getReplyList(bid);
		model.addAttribute("replyList", replyList);
		
		return "board/detail";
	}
	
	@PostMapping("/reply")
	public String reply(HttpServletRequest req, Model model) {
		String content = req.getParameter("content");
		int bid = Integer.parseInt(req.getParameter("bid"));
		String uid = req.getParameter("uid"); // 게시글의 uid
		
		// 게시글의 uid와 댓글을 쓰려고 하는 사람의 uid가 같으면 isMine이 1
		HttpSession session = req.getSession();
		String sessionUid = (String) session.getAttribute("uid");
		int isMine = (uid.equals(sessionUid)) ? 1 : 0;
		
		Reply reply = new Reply(content, isMine, sessionUid, bid);
		boardService.insertReply(reply);
		boardService.increaseReplyCount(bid);
		return "redirect:/bbs/board/detail?bid=" + bid + "&uid=" + uid + "&option=DNI";	// Do Not Increase
	}
	
	@GetMapping("/write")
	public String write() {
		return "board/write";
	}
	
	@PostMapping("/write")
	public String write(MultipartHttpServletRequest req) {
		String uid = (String) req.getParameter("uid");
		String title = (String) req.getParameter("title");
		String content = (String) req.getParameter("content");
		List<MultipartFile> fileList = req.getFiles("files");
		List<String> list = new ArrayList<>();
		// File upload
		for (MultipartFile file: fileList) {
			list.add(file.getOriginalFilename());
			
			File fileName = new File(file.getOriginalFilename());
			try {
				file.transferTo(fileName);
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
		JSONUtil json = new JSONUtil();
		String files = json.stringify(list);
		Board board = new Board(uid, title, content, files); 
		boardService.insertBoard(board);
		return "redirect:/bbs/board/list?p=1&f=&q=";
	}
	
	@GetMapping("/update")
	public String updateForm(HttpServletRequest req, Model model) {
		int bid = Integer.parseInt(req.getParameter("bid"));
		Board board = boardService.getBoard(bid);
		HttpSession session = req.getSession();
		
		String jsonFiles = board.getFiles();
		if (!(jsonFiles == null || jsonFiles.equals(""))) {
			JSONUtil json = new JSONUtil();
			List<String> fileList = json.parse(jsonFiles);
			session.setAttribute("fileList", fileList);
		}
		model.addAttribute("board", board);
		return "board/update";
	}
	
	@Value("${spring.servlet.multipart.location}")
	String uploadDir;
	
	@PostMapping("/update")
	public String update(MultipartHttpServletRequest req) {
		int bid = Integer.parseInt(req.getParameter("bid"));
		String uid = req.getParameter("uid");
		String title = (String) req.getParameter("title");
		String content = (String) req.getParameter("content");
		
		HttpSession session = req.getSession();
		List<String> additionalFileList = (List<String>) session.getAttribute("fileList");
		String[] delFileList = req.getParameterValues("delFile");
		for (String delName: delFileList) {
			File delFile = new File(uploadDir + "/" + delName);
			delFile.delete();
			additionalFileList.remove(delName);
		}
		
		List<MultipartFile> fileList = req.getFiles("files");
		List<String> newFileList = new ArrayList<>();
		// File upload
		for (MultipartFile file: fileList) {
			newFileList.add(file.getOriginalFilename());
			
			File fileName = new File(file.getOriginalFilename());
			try {
				file.transferTo(fileName);
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
		for (String fname: newFileList)
			additionalFileList.add(fname);
		JSONUtil json = new JSONUtil();
		String files = json.stringify(additionalFileList);
		Board board = new Board(bid, title, content, files);
		boardService.updateBoard(board);
		
		return "redirect:/bbs/board/detail?bid=" + bid + "&uid=" + uid + "&option=DNI";
	}
	
	@GetMapping("/delete")
	public String delete(HttpServletRequest req, Model model) {
		int bid = Integer.parseInt(req.getParameter("bid"));
		model.addAttribute("bid", bid);
		return "board/delete";
	}
	
	@GetMapping("/deleteConfirm")
	public String deleteConfirm(HttpServletRequest req) {
		int bid = Integer.parseInt(req.getParameter("bid"));
		boardService.deleteBoard(bid);
		
		HttpSession session = req.getSession();
		return "redirect:/bbs/board/list?p=" + session.getAttribute("currentBoardPage") + "&f=&q=";
	}
	
	/* 아래의 코드는 과거 데이터와의 호환성 때문에 남겨둠 */

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
