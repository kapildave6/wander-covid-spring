package com.kapil.covid19.domain;


import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CovidCase {
	
	@Id
	private String id;
		
	private String name;
	
	private Gender gender;
	
	private Date identifiedDate;
	
	private CovidStatus status;
	
	@DBRef
	private State state;
	
	public CovidCase( String name, Gender gender, Date identifiedDate, CovidStatus status, State state) {
		super();
		this.name = name;
		this.gender = gender;
		this.identifiedDate = identifiedDate;
		this.status = status;
		this.state = state;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getIdentifiedDate() {
		return identifiedDate;
	}

	public void setIdentifiedDate(Date identifiedDate) {
		this.identifiedDate = identifiedDate;
	}

	public CovidStatus getStatus() {
		return status;
	}

	public void setStatus(CovidStatus status) {
		this.status = status;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	
	@Override
	public String toString() {
		return "StateCovidCase [id=" + id + ", name=" + name + ", gender=" + gender + ", identifiedDate="
				+ identifiedDate + ", status=" + status + ", state=" + state.toString() + "]";
	}
	
	
	
}
