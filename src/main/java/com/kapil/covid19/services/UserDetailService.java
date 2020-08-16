package com.kapil.covid19.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.kapil.covid19.domain.CovidCase;
import com.kapil.covid19.domain.User;
import com.kapil.covid19.repositories.UserRepository;

public interface UserDetailService {
		
	public User getByEmailAddress(String emailAddress);
}
