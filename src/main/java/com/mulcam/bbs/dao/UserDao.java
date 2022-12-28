package com.mulcam.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mulcam.bbs.entity.User;

@Mapper
public interface UserDao {

	@Select("select * from users where uid=#{uid}")
	public User getUser(String uid);
	
	@Select("SELECT * FROM users WHERE isDeleted=0 "
			+ "	ORDER BY regDate DESC, uid"
			+ "	LIMIT 10 OFFSET #{offset}")
	public List<User> getUserList(int offset);
	
	@Insert("INSERT INTO users VALUES (#{uid}, #{pwd}, #{uname}, #{email}, DEFAULT, DEFAULT)")
	public void insertUser(User user);
	
	@Update("UPDATE users SET pwd=#{pwd}, uname=#{uname}, email=#{email} WHERE uid=#{uid}")
	public void updateUser(User user);
	
	@Update("UPDATE users SET isDeleted=1 WHERE uid=#{uid}")
	public void deleteUser(String uid);
	
	@Select("SELECT COUNT(uid) FROM users WHERE isDeleted=0")
	public int getUserCount();
	
}
