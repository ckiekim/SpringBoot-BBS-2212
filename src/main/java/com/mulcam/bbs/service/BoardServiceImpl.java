package com.mulcam.bbs.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mulcam.bbs.dao.BoardDao;
import com.mulcam.bbs.dao.ReplyDao;
import com.mulcam.bbs.entity.Board;
import com.mulcam.bbs.entity.Reply;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private ReplyDao replyDao;
	
	@Override
	public List<Board> getBoardList(int page, String field, String query) {
		int offset = (page - 1) * 10;
		query = "%"+query+"%";
		List<Board> list = boardDao.getBoardList(offset, field, query);
		return list;
	}

	@Override
	public Board getBoard(int bid) {
		Board board = boardDao.getBoard(bid);
		return board;
	}

	@Override
	public void insertBoard(Board board) {
		boardDao.insertBoard(board);
	}

	@Override
	public void updateBoard(Board board) {
		boardDao.updateBoard(board);
	}

	@Override
	public void deleteBoard(int bid) {
		boardDao.deleteBoard(bid);
	}

	@Override
	public int getBoardCount(String field, String query) {
		query = "%"+query+"%";
		int count = boardDao.getBoardCount(field, query);
		return count;
	}

	@Override
	public void increaseViewCount(int bid) {
		String field = "viewCount";
		boardDao.increaseCount(bid, field);
	}

	@Override
	public void increaseReplyCount(int bid) {
		String field = "replyCount";
		boardDao.increaseCount(bid, field);
	}

	@Override
	public List<Reply> getReplyList(int bid) {
		List<Reply> list = replyDao.getReplyList(bid);
		return list;
	}

	@Override
	public void insertReply(Reply reply) {
		replyDao.insertReply(reply);
	}

}
