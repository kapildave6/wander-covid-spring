package com.kapil.covid19.bootstrap;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import org.bson.types.ObjectId;
import org.springframework.boot.CommandLineRunner;import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;
import com.kapil.covid19.domain.CovidCase;
import com.kapil.covid19.domain.CovidStatus;
import com.kapil.covid19.domain.Gender;
import com.kapil.covid19.domain.State;
import com.kapil.covid19.repositories.CovidCaseRepository;
import com.kapil.covid19.repositories.StateRepository;
import com.kapil.covid19.repositories.UserRepository;

@Component
@Profile(value = "development")
public class BootStrapData implements CommandLineRunner{
	
	private final StateRepository stateRepository;
	private final CovidCaseRepository covidCaseRepository;
	private final UserRepository userReporsitory;
	
	
	public BootStrapData(StateRepository stateRepository, 
			CovidCaseRepository covidCaseRepository,
			UserRepository userReporsitory) {
		super();
		this.stateRepository = stateRepository;
		this.covidCaseRepository = covidCaseRepository;
		this.userReporsitory = userReporsitory;
	}


	public static Date between(Date startInclusive, Date endExclusive) {
	    long startMillis = startInclusive.getTime();
	    long endMillis = endExclusive.getTime();
	    long randomMillisSinceEpoch = ThreadLocalRandom
	      .current()
	      .nextLong(startMillis, endMillis);
	 
	    return new Date(randomMillisSinceEpoch);
	}
	
	@Override
	public void run(String... args) throws Exception {
		
		covidCaseRepository.deleteAll();
		stateRepository.deleteAll();
//		userReporsitory.deleteAll();
			
		
		Faker faker = new Faker();
			
		String[] stateList = new String[] { "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming" };
		
		for(int i=0; i< stateList.length ;i++) {
			
			State state = new State(stateList[i]);
			
			stateRepository.save(state);
		}
		
		long aDay = TimeUnit.DAYS.toMillis(1);
		long now = new Date().getTime();
		
		Date startDate = new Date(now - aDay * 15);
		Date endDate = new Date();
		
		for (int i = 0; i < 10; i++) {
			
			Gender gender = Gender.values()[new Random().nextInt(Gender.values().length)];
			CovidStatus activeStatus = CovidStatus.values()[new Random().nextInt(CovidStatus.values().length)]; 

			CovidCase covidCase1 = new CovidCase(faker.name().fullName() ,
						gender, 
						between(startDate, endDate), 
						activeStatus , 
						stateRepository.findByName(faker.address().state()));
			
			covidCaseRepository.save(covidCase1);
		}
		
		
		System.out.println("Started in bootstrap");
		System.out.println("Covid Case Count " + covidCaseRepository.count());
	}

}
