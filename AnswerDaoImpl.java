package com.example.student_doubt_system.dao;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.example.student_doubt_system.pojos.AnswerPojo;
import com.example.student_doubt_system.pojos.QuestionPojo;
import com.example.student_doubt_system.pojos.UserPojo;
@Repository
public class AnswerDaoImpl implements IAnswerDao{

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	UserDaoImpl udao;
	
	@Autowired
	QuestionDaoImpl qdao;
	
	@Override
	public List<AnswerPojo> getAllAnswers(int questionId) {
		//String jpql = "select a from AnswerPojo a where a.selectedAnswer=:questionId";
		String jpql = "select q from QuestionPojo q join fetch q.answers where q.questionId=:questionId";
		List<AnswerPojo> answers=null;
		try {
			
			 QuestionPojo q =em.unwrap(Session.class).createQuery(jpql,QuestionPojo.class).setParameter("questionId", questionId).getSingleResult();
			 answers=q.getAnswers();
		}catch (Exception e) {
			System.out.print(e);

		}
		
		return answers;
	}
	@Override
	public boolean AddNewAnswer(AnswerPojo answer, int questionId,String LoggedInUser) {
		
		String mesg="answer addition failed";
		
		String jpql = "select u from UserPojo u where u.email=:email";
				
		try
		{
		
			
			UserPojo user_answered =em.unwrap(Session.class).createQuery(jpql, UserPojo.class)
					.setParameter("email",LoggedInUser).getSingleResult();
			
			user_answered.addAnswer(answer);
			answer.setSelectedAnswerByUser(user_answered);
			
			
		jpql ="select q from QuestionPojo q where q.questionId=:questionId";
		
		QuestionPojo que=em.unwrap(Session.class).createQuery(jpql, QuestionPojo.class)
		.setParameter("questionId",questionId).getSingleResult();
		
		que.addAnswer(answer);
		answer.setSelectedAnswer(que);
		
		//que.setSelectedQuestion("dsd");
		//System.out.println(que.getDescription());
		UserPojo user_asked_question = que.getSelectedQuestion();
		System.out.println(qdao.getQuestionDetailsById(questionId).getSelectedQuestion().getEmail());
		
		
		String answerd_by_first_name=user_answered.getFname();
		String answerd_by_last_name=user_answered.getLname();
		
		if(user_asked_question==null) {
			System.out.println("nullllllllllll ");
		}
		
		String asked_by_user_email=user_asked_question.getEmail();
		String asked_by_user_fname=user_asked_question.getFname();
		String asked_by_user_lname=user_asked_question.getLname();
		
		String Subject=answerd_by_first_name+" "+answerd_by_last_name+" answered to your question";
		String text=answerd_by_first_name+" "+answerd_by_last_name+" answered\n "+answer.getAnswer();
		sendEmail(asked_by_user_email, Subject, text);
		
		
		
		mesg="answer addition successfull";
		}
		catch (Exception e) {
			System.out.println(e);
			//return mesg;
			return false;
		}
		
		return true;
	}
	
	@Autowired
    private JavaMailSender javaMailSender;
	
	void sendEmail(String sent_to,String Subject,String text) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(sent_to);

        msg.setSubject(Subject);
        msg.setText(text);

        javaMailSender.send(msg);

    }
	

}
