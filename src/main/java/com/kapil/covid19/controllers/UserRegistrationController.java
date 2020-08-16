package com.kapil.covid19.controllers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kapil.covid19.authetication.AuthenticationException;
import com.kapil.covid19.domain.GenericResponse;
import com.kapil.covid19.domain.User;
import com.kapil.covid19.repositories.UserRepository;
import com.kapil.covid19.services.UserDetailService;

@RestController
@CrossOrigin
@RequestMapping(path = "/user")
public class UserRegistrationController {
	
	private UserDetailService userDetailService;
	private UserRepository userRepository;
	
	public UserRegistrationController(UserDetailService userDetailService,
			UserRepository userRepository) {
		this.userDetailService = userDetailService;
		this.userRepository = userRepository;
	}
		
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public GenericResponse createAuthenticationToken(@RequestBody User user)
			throws AuthenticationException {
		
		User existingUser = this.userDetailService.getByEmailAddress(user.getEmailAddress());
		
		if(existingUser != null) {
			return new GenericResponse("", "User with email address alreadsy exists!! Please login with your credentials.");
		}
		
		BCryptPasswordEncoder ecryptor = new BCryptPasswordEncoder();
		user.setPassword(ecryptor.encode(user.getPassword())); 
		
		user = this.userRepository.save(user);
		
		return new GenericResponse("Success");
	}
}
