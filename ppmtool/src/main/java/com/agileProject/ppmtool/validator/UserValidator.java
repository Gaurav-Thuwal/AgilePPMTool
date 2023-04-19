package com.agileProject.ppmtool.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.agileProject.ppmtool.domain.User;

@Component
public class UserValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return User.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		// TODO Auto-generated method stub
		
		User user = (User)object;
		if(user.getPassword().length() < 6) {
			errors.rejectValue("password", " Length", "Password must contain atleast 6 characters");
		}
		
		if(!user.getPassword().equals(user.getConfirmPassword())) {
			errors.rejectValue("confirmPassword", " Match", "Passwords does not match");
		}
		
		//confirmPassword
		
		
		
	}
	
}
