package com.example.student_doubt_system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.student_doubt_system.pojos.SubjectPojo;
import com.example.student_doubt_system.pojos.UserPojo;
import com.example.student_doubt_system.services.IAdminService;
import com.example.student_doubt_system.services.IUserService;

@RestController
@CrossOrigin(allowedHeaders= "*", origins = "*")
public class AdminController {

	@Autowired
	IAdminService adminService;
	
	@Autowired
	IUserService userService;
	
	@RequestMapping(value="/getAllUsers",method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getAllUsers()
	{
		
		List<UserPojo> user = adminService.getAllUsers();
		if (user.size() == 0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<UserPojo>>(user, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getUserDetailsById/{userId}",method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getUserDetailsById(@PathVariable int userId) {
		System.out.println(userId);
		UserPojo u = userService.getUserDetailsById(userId);
		if(u==null)
		{
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		else
		{
			return new ResponseEntity<UserPojo>(u,HttpStatus.OK);
		}
	}
	
	@RequestMapping(value="/deleteUser/{uid}",method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> deleteUser(@PathVariable String uid)
	{
		 adminService.deleteUser(Integer.parseInt(uid));
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@RequestMapping(value="/updateUser/{userId}",method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateUser(@RequestParam String fname, @RequestParam String lname, @RequestParam String email,
			@RequestParam (value="image",required = false) MultipartFile image,@RequestParam String password, @PathVariable int userId)
	{
		System.out.println(fname+" "+lname+" "+email+" "+password+" "+userId);
		
		UserPojo u = userService.getUserDetailsById(userId);
		
		if(u==null)
		{
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		
			
			u.setFname(fname);
			u.setLname(lname);
			u.setEmail(email);
			u.setPassword(password);
			
			try
			{
		//	UserPojo user=new UserPojo(fname,lname, email,password);
			if(image!=null)
			{
				u.setImage(image.getBytes());
			}
			return new ResponseEntity<UserPojo>(adminService.updateUser(u),HttpStatus.OK);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
	
		}
		

	
	
	
	@RequestMapping(value="/addNewSubject", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> addNewSubject(@RequestBody SubjectPojo subject)
	{
		return new ResponseEntity<SubjectPojo>(adminService.addNewSubject(subject),HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/deleteSubject/{subjectId}",method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<Void> deleteSubject(@PathVariable String subjectId)
	{
		System.out.println(subjectId);
		 adminService.deleteSubject(Integer.parseInt(subjectId));
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	
}
