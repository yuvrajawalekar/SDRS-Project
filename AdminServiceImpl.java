package com.example.student_doubt_system.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student_doubt_system.dao.IAdminDao;
import com.example.student_doubt_system.dao.ISubjectDao;
import com.example.student_doubt_system.dao.IUserDao;
import com.example.student_doubt_system.pojos.SubjectPojo;
import com.example.student_doubt_system.pojos.UserPojo;

@Service
@Transactional
public class AdminServiceImpl implements IAdminService {

	@Autowired
	IUserDao udao;
	@Autowired
	IAdminDao adao;
	@Autowired
	ISubjectDao sdao;
	
	@Override
	public List<UserPojo> getAllUsers() {
		
		return udao.getAllUser();
	}
	
	@Override
	public void deleteUser(int uid) {
		boolean b;
		UserPojo u = udao.getUserDetailsById(uid);
		if(u!=null)
		{		
			adao.deleteUser(u);
		}
				
	}

	@Override
	public UserPojo updateUser(UserPojo u) {
		return adao.updateUser(u);
		 
	}

	@Override
	public SubjectPojo addNewSubject(SubjectPojo subject) {
		if(sdao.getSubjectDetailsByName(subject.getSubjectName())==null)
		{
			return sdao.addNewSubject(subject);
			//return "subject added successfully";
		}
		else
		{
			return null;
		}

	}

	@Override
	public void deleteSubject(int subjectId) {
		boolean b;
		SubjectPojo s= sdao.getSubjectDetailsById(subjectId);
		System.out.println(s);
		if(s!=null)
		{		
			sdao.deleteSubject(s);
		}
				
	}
}
