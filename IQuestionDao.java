package com.example.student_doubt_system.dao;

import java.util.List;

import com.example.student_doubt_system.pojos.AnswerPojo;
import com.example.student_doubt_system.pojos.QuestionPojo;

public interface IQuestionDao {

	List<QuestionPojo> getAllQuestions(String subjectName);
	QuestionPojo getQuestionDetailsById(int questionId);
	List<QuestionPojo> getAllUserQuestions(String LoggedInUser);
	//QuestionPojo addNewUserDetails(QuestionPojo u);
	void deleteQuestion(QuestionPojo u);
	//QuestionPojo updateUserDetails(QuestionPojo u);
	//QuestionPojo getUserDetailsByEmail(String email) ;
	List<QuestionPojo> getTrendingQuestions();
}
