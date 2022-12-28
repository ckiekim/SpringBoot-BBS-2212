package com.mulcam.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mulcam.bbs.entity.Reply;

@Mapper
public interface ReplyDao {

	@Select("SELECT r.rid, r.content, r.regDate, r.isMine, r.uid, r.bid, u.uname "
			+ "	FROM reply AS r"
			+ "	JOIN users AS u"
			+ "	ON r.uid=u.uid"
			+ "	WHERE bid=#{bid}")
	public List<Reply> getReplyList(int bid);
	
	@Insert("INSERT INTO reply"
			+ " VALUES (default, #{content}, default, #{isMine}, #{uid}, #{bid})")
	public void insertReply(Reply reply);
	
}
