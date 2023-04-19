package com.agileProject.ppmtool.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.agileProject.ppmtool.domain.User;
import com.agileProject.ppmtool.exceptions.UsernameAlreadyExistsException;
import com.agileProject.ppmtool.repositories.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User saveUser(User newUser) {
		try {
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			//username has to be unique
			newUser.setUsername(newUser.getUsername());
			//make sure that password and confirm password match
			
			//we dont persist or show the confirmPassword
			newUser.setConfirmPassword("");
			return userRepository.save(newUser);
		} catch (Exception e) {
			throw new UsernameAlreadyExistsException("Username '"+newUser.getUsername()+"' already exists");
		}
		
	}
}
