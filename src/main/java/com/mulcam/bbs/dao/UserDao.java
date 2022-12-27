package com.mulcam.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mulcam.bbs.entity.User;

@Mapper
public interface UserDao {

	@Select("select * from users where uid=#{uid}")
	public User getUser(String uid);
	
	@Select("SELECT * FROM users WHERE isDeleted=0 "
			+ "	ORDER BY regDate DESC, uid"
			+ "	LIMIT 10 OFFSET #{offset}")
	public List<User> getUserList(int offset);
	
	@Insert("")
	public void registerUser(User u);
	
}
