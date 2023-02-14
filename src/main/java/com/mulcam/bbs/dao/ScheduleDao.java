package com.mulcam.bbs.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.mulcam.bbs.entity.Schedule;

@Mapper
public interface ScheduleDao {

	@Select("SELECT * FROM schedule"
			+ "  WHERE uid=#{uid} and sdate >= #{startDate} and sdate <= #{endDate}"
			+ "  ORDER BY startTime")
	List<Schedule> getSchedList(String uid, String startDate, String endDate);
	
	@Insert("INSERT INTO schedule VALUES"
			+ " (DEFAULT, #{uid}, #{sdate}, #{title}, #{place},"
			+ " #{startTime}, #{endTime}, #{isImportant}, #{memo})")
	void insert(Schedule schedule);
	
	@Select("select * from schedule where sid=#{sid}")
	Schedule getSchedule(int sid);
	
	@Update("update schedule set uid=#{uid}, sdate=#{sdate}, title=#{title}, place=#{place},"
			+ "  startTime=#{startTime}, endTime=#{endTime}, isImportant=#{isImportant}, memo=#{memo}"
			+ "  where sid=#{sid}")
	void update(Schedule schedule);
	
	@Delete("delete from schedule where sid=#{sid}")
	void delete(int sid);
	
	@Select("SELECT * FROM schedule"
			+ "  WHERE uid=#{uid} and sdate >= #{startDate}"
			+ "  ORDER BY startTime LIMIT 15 OFFSET #{offset}")
	List<Schedule> getSchedListByPage(String uid, String startDate, int offset);
	
	@Select("SELECT COUNT(sid) FROM schedule"
			+ "  WHERE uid=#{uid} and sdate >= #{startDate}"
			+ "  ORDER BY startTime")
	int getSchedCount(String uid, String startDate);
	
}
