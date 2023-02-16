package com.mulcam.bbs.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.mulcam.bbs.entity.Profile;

@Mapper
public interface ProfileDao {

	@Insert("insert into profile values (#{uid}, #{github}, #{instagram}, "
			+ "  #{facebook}, #{twitter}, #{homepage}, #{blog}, #{addr}, "
			+ "  #{image}, #{size}, #{filename})")
	void insert(Profile profile);
	
	@Select("select * from profile where uid=#{uid}")
	Profile getProfile(String uid);
	
}
