package com.example.student_doubt_system.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.student_doubt_system.pojos.QuestionPojo;
import com.example.student_doubt_system.pojos.SubjectPojo;



public interface ISubjectDao 
{
	String addNewQuestion(QuestionPojo question, String subjectName,String email);
    SubjectPojo getSubjectDetailsByName(String subjectName);
    SubjectPojo getSubjectDetailsById(int subjectId);
    List<SubjectPojo> getAllSubjects();
    SubjectPojo addNewSubject(SubjectPojo subject);
	void deleteSubject(SubjectPojo subject);
	ArrayList<ArrayList<String>> getQuestionperSubject() ;
	
}
