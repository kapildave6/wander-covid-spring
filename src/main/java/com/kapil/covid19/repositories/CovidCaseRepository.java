package com.kapil.covid19.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.kapil.covid19.domain.CovidCase;

public interface CovidCaseRepository extends MongoRepository<CovidCase, String> {

}
