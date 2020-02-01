package com.example.student_doubt_system.dao;



import java.util.List;

import com.example.student_doubt_system.pojos.UserPojo;

public interface IUserDao {
	List<UserPojo> getAllUser();
	UserPojo getUserDetailsById(int userId);
	UserPojo addNewUserDetails(UserPojo u);
	void deleteUser(UserPojo u);
	UserPojo updateUserDetails(UserPojo u);
	UserPojo getUserDetailsByEmail(String email) ;
	
}
