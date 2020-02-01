package com.example.student_doubt_system.dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.example.student_doubt_system.pojos.UserPojo;


@Repository
public class UserDaoImpl implements IUserDao {
	@PersistenceContext
	private EntityManager mgr;

	@Override
	public List<UserPojo> getAllUser() {
		String jpql = "select u from UserPojo u";
		return mgr.unwrap(Session.class).
				createQuery(jpql,UserPojo.class).getResultList();
	}

	@Override
	public UserPojo getUserDetailsById(int userId) {
		// TODO Auto-generated method stub
		return mgr.unwrap(Session.class).get(UserPojo.class, userId);
	}

	@Override
	public UserPojo getUserDetailsByEmail(String email) {
		// TODO Auto-generated method stub
		String jpql="select u from UserPojo u where u.email=:email";
		try
		{
		return mgr.unwrap(Session.class).createQuery(jpql, UserPojo.class)
				.setParameter("email", email).getSingleResult();
		}catch(Exception e)
		{
			return null;
		}
	}

	
	@Override
	public UserPojo addNewUserDetails(UserPojo u) {
		//e --transient
		mgr.unwrap(Session.class).persist(u);//persistent
		return u;
	}

	@Override
	public void deleteUser(UserPojo u) {
		mgr.unwrap(Session.class).delete(u);
		
	}

	@Override
	public UserPojo updateUserDetails(UserPojo u) {
		mgr.unwrap(Session.class).update(u);
		return u;
	}
	
	
	

}
