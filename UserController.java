package com.example.student_doubt_system.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.student_doubt_system.pojos.AnswerPojo;
import com.example.student_doubt_system.pojos.QuestionPojo;
import com.example.student_doubt_system.pojos.SubjectPojo;
import com.example.student_doubt_system.pojos.UserPojo;
import com.example.student_doubt_system.services.IUserService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(allowedHeaders= "*", origins = "*")
public class UserController {
	
	

	@Autowired
	IUserService service;
	
	
	//@PostMapping()
	@RequestMapping(value="/verifyuser", method=RequestMethod.POST)
	public ResponseEntity<?> VerifyUser(HttpServletRequest request, Model model, @RequestBody UserPojo user)
	{
		
		
		UserPojo u=service.VerifyUser(user.getEmail(),user.getPassword());
		if(u!=null)
		{
			//hs.setAttribute("LoggedInUser", u);
			model.addAttribute("MY_SESSION_MESSAGES", u.getUserId());
			request.getSession().setAttribute("MY_SESSION_MESSAGES",u.getUserId());
			
			System.out.println(u);
			return new ResponseEntity<UserPojo>(u,HttpStatus.OK);
		}
		return new ResponseEntity<String>("Login failed...",HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	//@ResponseBody
	public ResponseEntity<?> Register(
			
			@RequestParam String fname,
			@RequestParam String lname,
			@RequestParam  String email,
			@RequestParam  String password,
			@RequestParam (value="image",required = false) MultipartFile image
			 )
	{
		try
		{
		UserPojo user=new UserPojo(email, password, fname, lname);
		user.setRole("user");
		System.out.println(user);
		if(image!=null)
		{
			user.setImage(image.getBytes());
		}
		return new ResponseEntity<UserPojo>(service.RegisterUser(user),HttpStatus.OK);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@RequestMapping(value="/addquestion", method=RequestMethod.POST)
	public void AddQuestion( HttpServletRequest request,@RequestParam String question, @RequestParam String subjectName, @RequestParam String LoggedInUser)
	{
		try
		{
			//System.out.print((String)request.getSession().getAttribute("MY_SESSION_MESSAGES")+"scasvasv");
			//UserPojo u=(UserPojo)hs.getAttribute("LoggedInUser"); 
			System.out.println(question);
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			QuestionPojo p=objectMapper. readValue(question, QuestionPojo.class);
			
			
			System.out.println(LoggedInUser);
			System.out.println(p.getQuestionName());
			//p.setSelectedQuestion(u);
			service.AddQuestion(p,subjectName, LoggedInUser);
			
			
		}catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	@RequestMapping(value="/showquestions/{subjectName}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> ShowQuestions(@PathVariable String subjectName)
	{
		System.out.println(subjectName);
		List<QuestionPojo> questions = service.ShowQuestions(subjectName);
		
		if (questions.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<QuestionPojo>>(questions, HttpStatus.OK);
	}
	
	@RequestMapping(value="/showanswers/{questionId}",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> ShowAnswers(@PathVariable String questionId)
	{
		/*
		 * AnswerPojo ans = new AnswerPojo(); ans.setAnswer("dasda");
		 * this.service.AddNewAnswer(ans, 9);
		 */
		//System.out.print(Integer.parseInt(questionId)+"dad");
		List<AnswerPojo> answers = service.ShowAnswers(Integer.parseInt(questionId));
	
		
		if (answers==null || answers.size()==0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<AnswerPojo>>(answers , HttpStatus.OK);
	}
	
	@RequestMapping(value="/addanswer", method=RequestMethod.POST)
	@ResponseBody
	public void addAnswer(@RequestParam String answer,@RequestParam String questionId,@RequestParam String LoggedInUser)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		System.out.print(answer+questionId);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		try {
			AnswerPojo a=objectMapper.readValue(answer, AnswerPojo.class);
			//AnswerPojo a=new AnswerPojo();
			//a.setAnswer(answer);
			System.out.print(a.getAnswer());
			service.AddNewAnswer(a, Integer.parseInt(questionId),LoggedInUser);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/showuserquestions", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> showUserQuestions(@RequestParam String LoggedInUser)
	{
		System.out.println(LoggedInUser);
		List<QuestionPojo> questions= service.getAllUserQuestions(LoggedInUser);
		return new ResponseEntity<List<QuestionPojo>>(questions , HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAllSubjects", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllSubjects()
	{
			List<SubjectPojo> subjects = service.getAllSubjects();
		
		if (subjects.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<SubjectPojo>>(subjects, HttpStatus.OK);
	}
	
	@RequestMapping(value="/showtrendingquestions", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getTrendingQuestions()
	{
		
		List<QuestionPojo> questions= service.getTrendingQuestions();
		return new ResponseEntity<List<QuestionPojo>>(questions , HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/getquestionpersubject",method=RequestMethod.GET )
	@ResponseBody
	public ResponseEntity<?> getQuestionPerSubject()
	{
		return new ResponseEntity<ArrayList<ArrayList<String>>>(service.getQuestionperSubject(),HttpStatus.OK);
	}
}
