package com.kapil.covid19.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kapil.covid19.domain.State;

public interface StateRepository extends MongoRepository<State, String>{
	
    State findByName(String name);

}
