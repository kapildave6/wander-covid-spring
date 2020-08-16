package com.kapil.covid19.services;

import org.springframework.stereotype.Service;

import com.kapil.covid19.domain.User;
import com.kapil.covid19.repositories.UserRepository;

@Service
public class UserDetailServiceImpl implements UserDetailService {
		
	public UserRepository userRepository; 
	
	public UserDetailServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@Override
	public User getByEmailAddress(String emailAddress) {
		return this.userRepository.findByEmailAddress(emailAddress);
	}
}
