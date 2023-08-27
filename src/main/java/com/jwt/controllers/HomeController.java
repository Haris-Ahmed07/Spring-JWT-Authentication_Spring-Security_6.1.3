package com.jwt.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.models.User;
import com.jwt.services.UserService;

//Main Controller for checking the Authentication & Authorization
@RestController
@RequestMapping("/home")
public class HomeController {
	
//	UserService Bean to retrieve data from the customUserService
	@Autowired
	private UserService userService;

//	Returns all the users
	@GetMapping("/getAll")
	public List<User> getAll()
	{
		return userService.getUsers();
	}
	
//	For testing the Admin's Authorization
	@GetMapping("/admin")
	public String Admin()
	{
		return "You Are An Admin";
	}
	
//	For testing the User's Authorization
	@GetMapping("/user")
	public String user()
	{
		return "You Are An User";
	}
	
	
	
}

