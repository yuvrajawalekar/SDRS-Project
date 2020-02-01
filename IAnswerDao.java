package com.example.student_doubt_system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.student_doubt_system.pojos.AnswerPojo;
import com.example.student_doubt_system.pojos.QuestionPojo;

@Repository
public interface IAnswerDao {
	List<AnswerPojo> getAllAnswers(int questionId);
	boolean AddNewAnswer(AnswerPojo answer, int questionId,String LoggedInUser);
	//void save(AnswerPojo answer);
}



