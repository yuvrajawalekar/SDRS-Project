package com.example.student_doubt_system.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.example.student_doubt_system.pojos.UserPojo;

@Repository
public class AdminDaoImpl implements IAdminDao {

	@PersistenceContext
	private EntityManager mgr;
	
	@Override
	public void deleteUser(UserPojo u) {
		System.out.println(u);
		
		mgr.unwrap(Session.class).delete(u);

	}

	@Override
	public UserPojo updateUser(UserPojo u) {

		mgr.unwrap(Session.class).update(u);
		return u;
	}

}
