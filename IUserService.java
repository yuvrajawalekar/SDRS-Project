package com.example.student_doubt_system.services;

import java.util.ArrayList;
import java.util.List;

import com.example.student_doubt_system.pojos.AnswerPojo;
import com.example.student_doubt_system.pojos.QuestionPojo;
import com.example.student_doubt_system.pojos.SubjectPojo;
import com.example.student_doubt_system.pojos.UserPojo;

public interface IUserService {

	public UserPojo VerifyUser(String email, String password) ;
	

	public UserPojo RegisterUser(UserPojo user);
	
	public String AddQuestion(QuestionPojo question,String subjectName,String LoggedInUser);
	
	public List<QuestionPojo> ShowQuestions(String subjectName);
	
	public List<AnswerPojo> ShowAnswers(int question_id);
	
	public boolean AddNewAnswer(AnswerPojo answer,int quetionId,String LoggedInUser);
	
	public ArrayList<ArrayList<String>> getQuestionperSubject() ;
	
	public String get_questions(String question_subject);
	
	public String send_mail(String recipent,String Subject,String text) ;
	
	List<QuestionPojo> getAllUserQuestions(String LoggedInUser);
	
	List<SubjectPojo> getAllSubjects();
	
	public UserPojo getUserDetailsById(int userId);
	List<QuestionPojo> getTrendingQuestions();
	
}
