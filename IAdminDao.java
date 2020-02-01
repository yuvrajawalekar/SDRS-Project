package com.example.student_doubt_system.dao;

import com.example.student_doubt_system.pojos.UserPojo;

public interface IAdminDao {
	
	public  void deleteUser(UserPojo u);

	public UserPojo updateUser(UserPojo u);
	
}
