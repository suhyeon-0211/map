package com.map.user.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.map.user.model.User;

@Repository
public interface UserDAO {
	public boolean existLoginId(String loginId);
	public int insertUser(User user);
	public User selectUserByLoginIdPassword(
			@Param("loginId") String loginId, 
			@Param("password") String password);
}
