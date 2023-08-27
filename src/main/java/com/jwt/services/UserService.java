package com.jwt.services;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jwt.models.User;

//	This class is used to store temporary Users to Avoid configuring the Data Base

@Service
public class UserService {
	
	private List<User> users = new ArrayList<>();
	
	public UserService() {
		users.add(new User(UUID.randomUUID().toString(),"Haris", "haris@gmail.com"));
		users.add(new User(UUID.randomUUID().toString(),"Furqan", "furqan@gmail.com"));
		users.add(new User(UUID.randomUUID().toString(),"Khubaib", "khubaib@gmail.com"));
		users.add(new User(UUID.randomUUID().toString(),"Shahzaib", "shahzaib@gmail.com"));
	}
	
	public List<User> getUsers()
	{
		return users;
	}

}
