package com.mulcam.bbs.service;

import java.util.List;

import com.mulcam.bbs.entity.Board;
import com.mulcam.bbs.entity.Reply;

public interface BoardService {

	public List<Board> getBoardList(int page, String field, String query);
	
	public Board getBoard(int bid);
	
	public void insertBoard(Board board);
	
	public void updateBoard(Board board);
	
	public void deleteBoard(int bid);
	
	public int getBoardCount(String field, String query);
	
	public void increaseViewCount(int bid);
	
	public void increaseReplyCount(int bid);
	
	public List<Reply> getReplyList(int bid);
	
	public void insertReply(Reply reply);
	
	public int updateLikeCount(int bid, String uid);
	
}
