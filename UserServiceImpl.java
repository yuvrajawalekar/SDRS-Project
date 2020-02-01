package com.example.student_doubt_system.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.student_doubt_system.dao.IAnswerDao;
import com.example.student_doubt_system.dao.IQuestionDao;
import com.example.student_doubt_system.dao.ISubjectDao;
import com.example.student_doubt_system.dao.IUserDao;
import com.example.student_doubt_system.pojos.AnswerPojo;
import com.example.student_doubt_system.pojos.QuestionPojo;
import com.example.student_doubt_system.pojos.SubjectPojo;
import com.example.student_doubt_system.pojos.UserPojo;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	EntityManager em;
	@Autowired
	IUserDao udao;
	@Autowired
	IQuestionDao qdao;
	@Autowired
	IAnswerDao adao;
	@Autowired
	ISubjectDao sdao;

	@Override
	public UserPojo VerifyUser(String email, String password) {
		UserPojo user = null;
		String jpql = "select u from UserPojo u where u.email=:email and u.password=:password";
		try {
			user = em.unwrap(Session.class).createQuery(jpql, UserPojo.class).setParameter("email", email)
					.setParameter("password", password).getSingleResult();
			return user;
		} catch (Exception e) {
			System.out.println(e);
			return user;
		}

	}
	@Override
	public UserPojo RegisterUser(UserPojo user) {

		if(udao.getUserDetailsByEmail(user.getEmail())==null)
		{
			return udao.addNewUserDetails(user);
			
		}
		else
		{
			return null;
		}
			
	}

	@Override
	public String AddQuestion(QuestionPojo question, String subjectName,String LoggedInUser) {
		return sdao.addNewQuestion(question, subjectName, LoggedInUser);
	}

	@Override
	public String get_questions(String question_subject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String send_mail(String recipent, String Subject, String text) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<QuestionPojo> ShowQuestions(String subjectName) {
		
		return qdao.getAllQuestions(subjectName);
	}
	
	@Override
	public List<AnswerPojo> ShowAnswers(int questionId){
		return adao.getAllAnswers(questionId);
	}
	@Override
	public boolean AddNewAnswer(AnswerPojo answer,int questionId,String LoggedInUser) {
		return adao.AddNewAnswer(answer, questionId,LoggedInUser);
	}
	@Override
	public List<QuestionPojo> getAllUserQuestions(String LoggedInUser) {
		// TODO Auto-generated method stub
		return qdao.getAllUserQuestions(LoggedInUser);
		
	}
	@Override
	public List<SubjectPojo> getAllSubjects() {
		
		return sdao.getAllSubjects();
	}
	
	@Override
	public UserPojo getUserDetailsById(int userId) {
		return udao.getUserDetailsById(userId);
		 
	}
	@Override
	public List<QuestionPojo> getTrendingQuestions() 
	{
		return qdao.getTrendingQuestions();
	}
	
	@Override
	public ArrayList<ArrayList<String>> getQuestionperSubject() 
	{
		return sdao.getQuestionperSubject();
	}
}
