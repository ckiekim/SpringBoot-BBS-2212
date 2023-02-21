package com.mulcam.bbs.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mulcam.bbs.entity.Profile;

@Mapper
public interface ProfileDao {

	@Insert("insert into profile values (#{uid}, #{github}, #{instagram}, "
			+ "  #{facebook}, #{twitter}, #{homepage}, #{blog}, #{addr}, "
			+ "  #{image}, #{size}, #{filename})")
	void insert(Profile profile);
	
	@Select("select * from profile where uid=#{uid}")
	Profile getProfile(String uid);
	
	@Update("update profile set github=#{github}, instagram=#{instagram},"
			+ "  facebook=#{facebook}, twitter=#{twitter}, homepage=#{homepage}, blog=#{blog}, "
			+ "  addr=#{addr}, image=#{image}, size=#{size}, filename=#{filename}"
			+ "  where uid=#{uid}")
	void update(Profile profile);
	
	@Update("update profile set github=#{github}, instagram=#{instagram},"
			+ "  facebook=#{facebook}, twitter=#{twitter}, homepage=#{homepage}, blog=#{blog}, "
			+ "  addr=#{addr} where uid=#{uid}")
	void updateWithoutImage(Profile profile);
	
}
