package com.example.student_doubt_system.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.student_doubt_system.pojos.QuestionPojo;
import com.example.student_doubt_system.pojos.SubjectPojo;
import com.example.student_doubt_system.pojos.UserPojo;

@Repository
public class SubjectDaoImpl implements ISubjectDao {
	
	@Autowired
	EntityManager em;

	@Override
	public String addNewQuestion(QuestionPojo question, String subjectName,String LoggedInUser) {
		
		String mesg="question addition failed";
		String jpql = "select s from SubjectPojo s where s.subjectName=:subjectName";
		try
		{
		SubjectPojo sub=em.unwrap(Session.class).createQuery(jpql, SubjectPojo.class)
		.setParameter("subjectName",subjectName).getSingleResult();
		question.setSelectedSubject(sub);
		sub.addQuestion(question);

		jpql = "select u from UserPojo u where u.email=:emailId";
		UserPojo user=em.unwrap(Session.class).createQuery(jpql, UserPojo.class)
				.setParameter("emailId",LoggedInUser).getSingleResult();
		question.setSelectedQuestion(user);
		user.addQuestion(question);
		
		System.out.print(user.getEmail());
			
		mesg="question addition successfull";
		
		}
		catch (Exception e) {
			System.out.println(e);
			//return mesg;
		}

		
		return mesg;
	}
	
	
	
	
	@Override
	public SubjectPojo getSubjectDetailsByName(String subjectName) {
		
		String jpql="select s from SubjectPojo s where s.subjectName=:subjectName";
		try
		{
		return em.unwrap(Session.class).createQuery(jpql, SubjectPojo.class)
				.setParameter("subjectName", subjectName).getSingleResult();
		}catch(Exception e)
		{
			return null;
		}
	}

	@Override
	public List<SubjectPojo> getAllSubjects() {
		String jpql = "select s from SubjectPojo s";
		return em.unwrap(Session.class).
				createQuery(jpql,SubjectPojo.class).getResultList();
	}

	@Override
	public SubjectPojo addNewSubject(SubjectPojo subject) {
		em.unwrap(Session.class).persist(subject);//persistent
		return subject;
	}

	@Override
	public void deleteSubject(SubjectPojo subject) {
		System.out.println(subject);
		em.unwrap(Session.class).delete(subject);
	}




	@Override
	public SubjectPojo getSubjectDetailsById(int subjectId) {
		
		return em.unwrap(Session.class).get(SubjectPojo.class, subjectId);	}
	
	@Override
	public ArrayList<ArrayList<String>> getQuestionperSubject() {
		String mesg="question addition failed";
		String jpql = "select count(ques) ,s.subjectName from SubjectPojo s join s.questions ques"
				+ " group by s.subjectId , s.subjectName";
		try
		{
		List l = em.createQuery(jpql).getResultList();
		ArrayList<String> subjects=new ArrayList<String>();
		ArrayList<String> no_of_ques=new ArrayList<String>();
		for (Object p : l) {
			Object[] row = (Object[]) p;
			 
			 
			no_of_ques.add(Long.toString((Long)row[0]));
			 subjects.add((String)row[1]);
			 
		    }
		ArrayList<ArrayList<String>> returned=new ArrayList<ArrayList<String>>();
		returned.add(subjects);
		returned.add(no_of_ques);
		
		return returned;
		}
		catch (Exception e) {
			System.out.println(e);
			//return mesg;
		}
		return null;
		
	}

}
