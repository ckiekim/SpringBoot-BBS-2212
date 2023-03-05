package com.mulcam.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mulcam.bbs.entity.Board;
import com.mulcam.bbs.entity.Like;

@Mapper
public interface BoardDao {

	@Select("SELECT b.bid, b.uid, b.title, b.modTime, "
			+ "	b.viewCount, b.replyCount, b.likeCount, u.uname FROM board AS b"
			+ "	JOIN users AS u"
			+ "	ON b.uid=u.uid"
			+ "	WHERE b.isDeleted=0 AND ${field} LIKE #{query}"
			+ "	ORDER BY bid DESC"
			+ "	LIMIT 10 OFFSET #{offset}")
	public List<Board> getBoardList(int offset, String field, String query);

	@Select("SELECT COUNT(bid) FROM board AS b"
			+ "	JOIN users AS u"
			+ "	ON b.uid=u.uid"
			+ "	WHERE b.isDeleted=0 AND ${field} LIKE #{query}")
	public int getBoardCount(String field, String query);
	
	@Select("SELECT b.bid, b.uid, b.title, b.content, b.modTime, b.viewCount,"
			+ "	b.replyCount, b.files, b.likeCount, u.uname FROM board AS b"
			+ "	JOIN users AS u"
			+ "	ON b.uid=u.uid"
			+ "	WHERE b.bid=#{bid}")
	public Board getBoard(int bid);
	
	@Update("UPDATE board SET ${field}=${field}+1 WHERE bid=#{bid}")
	public void increaseCount(int bid, String field);

	@Insert("INSERT INTO board VALUES(DEFAULT, #{uid}, #{title}, #{content},"
			+ " DEFAULT, DEFAULT, DEFAULT, DEFAULT, #{files}, DEFAULT)")
	public void insertBoard(Board board);

	@Update("UPDATE board SET title=#{title}, content=#{content}, "
			+ " modTime=NOW(), files=#{files} WHERE bid=#{bid}")
	public void updateBoard(Board board);

	@Update("UPDATE board SET isDeleted=1 WHERE bid=#{bid}")
	public void deleteBoard(int bid);
	
	@Select("select likeCount from board where bid=#{bid}")
	public int getLikeCount(int bid);
	
	@Insert("insert into likeTable values(default, #{bid}, #{uid}, default)")
	public void insertLike(Like like);
	
	@Select("select * from likeTable where bid=#{bid} and uid=#{uid}")
	public Like getLikeEntry(int bid, String uid);
	
}
