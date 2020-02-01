package com.example.student_doubt_system.dao;

import java.util.List;
import java.io.*;
import java.time.LocalDateTime;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.example.student_doubt_system.pojos.AnswerPojo;
import com.example.student_doubt_system.pojos.QuestionPojo;
import com.example.student_doubt_system.pojos.SubjectPojo;
import com.example.student_doubt_system.pojos.UserPojo;
@Repository
public class QuestionDaoImpl implements IQuestionDao {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<QuestionPojo> getAllQuestions(String subjectName) {
		String jpql = "select s from SubjectPojo s join fetch s.questions where s.subjectName=:subjectName";
		//String jpql="select q from QuestionPojo q  join fetch q.where ";
		
		List<QuestionPojo> questions=null;
		try {
			SubjectPojo s=em.unwrap(Session.class).createQuery(jpql,SubjectPojo.class).setParameter("subjectName", subjectName).getSingleResult();
			
			questions=s.getQuestions();
		}catch (Exception e) {
			System.out.print(e);

		}
		
		return questions;
	}


	
	@Override
	public QuestionPojo getQuestionDetailsById(int questionId) {
		return em.unwrap(Session.class).get(QuestionPojo.class, questionId);
	}

	@Override
	public void deleteQuestion(QuestionPojo u) {
		em.unwrap(Session.class).delete(u);

		
	}



	@Override
	public List<QuestionPojo> getAllUserQuestions(String LoggedInUser) {
		String jpql = "select u from UserPojo u join fetch u.questions where u.email=:email";
		List<QuestionPojo> questions=null;
		try {
			UserPojo s=em.unwrap(Session.class).createQuery(jpql,UserPojo.class).setParameter("email", LoggedInUser).getSingleResult();
			questions=s.getQuestions();
			
		}catch (Exception e) {
			System.out.print(e);
		}
		
		return questions;
		
		
	}
	
	
	@Override
	public List<QuestionPojo> getTrendingQuestions() {
		
		String jpql = "select q from QuestionPojo q join q.answers ans group by q.questionId,q.questionName, "
				+ "q.description,q.questionTime order by count(ans) DESC";
		List<QuestionPojo> questions=null;
		try {
			questions=em.unwrap(Session.class).createQuery(jpql,QuestionPojo.class).getResultList();
		}catch (Exception e) {
			System.out.print(e);
		}
		
		return questions;
		
	}
}
