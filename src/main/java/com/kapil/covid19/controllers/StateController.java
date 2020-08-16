package com.kapil.covid19.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kapil.covid19.authetication.AuthenticationException;
import com.kapil.covid19.domain.State;
import com.kapil.covid19.repositories.StateRepository;

@RestController
@CrossOrigin
public class StateController {
	
	private StateRepository stateRepository;
	
	public StateController(StateRepository stateRepository) {
		this.stateRepository = stateRepository;
	}
	
	@RequestMapping(value = "/states", method = RequestMethod.GET)
	public List<State> createAuthenticationToken()
			throws AuthenticationException {
		
		return stateRepository.findAll();
	}
		
}
