package com.example.student_doubt_system.services;

import java.util.List;

import com.example.student_doubt_system.pojos.SubjectPojo;
import com.example.student_doubt_system.pojos.UserPojo;

public interface IAdminService {

	public List<UserPojo> getAllUsers();
	public void deleteUser(int uid);
	public UserPojo updateUser(UserPojo u);
	//public List<SubjectPojo> getAllSubjects();
	SubjectPojo addNewSubject(SubjectPojo subject);
	public void deleteSubject(int subjectId);
}
