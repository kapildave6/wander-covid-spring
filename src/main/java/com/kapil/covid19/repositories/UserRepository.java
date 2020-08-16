package com.kapil.covid19.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kapil.covid19.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	User findByEmailAddress(String emailAddress);
}
