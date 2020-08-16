package com.kapil.covid19.services;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kapil.covid19.domain.CovidCase;
import com.kapil.covid19.domain.CovidSummary;
import com.kapil.covid19.domain.State;
import com.kapil.covid19.repositories.CovidCaseRepository;
import com.kapil.covid19.repositories.StateRepository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.fields;


@Service
public class CovidCaseServiceImpl implements CovidCaseService {
	
	private CovidCaseRepository covidCaseRepository;
	private final MongoTemplate mongoTemplate;
	private StateRepository stateRepository;

	public CovidCaseServiceImpl(CovidCaseRepository covidCaseRepository,
			MongoTemplate mongoTemplate,
			StateRepository stateRepository) {
		super();
		this.covidCaseRepository = covidCaseRepository;
		this.mongoTemplate = mongoTemplate;
		this.stateRepository = stateRepository;
	}


	@Override
	public List<CovidCase> getCovidCases(JSONObject filterObject,
			JSONObject sortObject,
			JSONObject pageObject) {
				
		Query query = new Query();
		
		if((filterObject != null) && 
				(filterObject.has("state")) && 
				(!filterObject.getString("state").equalsIgnoreCase(""))) {
			query.addCriteria(Criteria.where("state.$id").is(new ObjectId(filterObject.getString("state"))));
		}
		
		if((filterObject != null) && 
				(filterObject.has("name")) && 
				(!filterObject.getString("name").equalsIgnoreCase(""))) {
			
			Criteria regex = Criteria.where("name").regex(".*" + filterObject.getString("name") + ".*", "i");      

			query.addCriteria(regex);
		}
		
		if((filterObject != null) && 
				(filterObject.has("gender")) && 
				(!filterObject.getString("gender").equalsIgnoreCase(""))) {
			query.addCriteria(Criteria.where("gender").is(filterObject.getString("gender")));
		}
		
		Pageable paging = PageRequest.of(0, 10, Sort.by("id").ascending());
		
		
		if(sortObject != null && pageObject != null && sortObject.getString("sort_type").equalsIgnoreCase( "asc")) {
			paging = PageRequest.of(pageObject.getInt("pageNumber"), 
					pageObject.getInt("pageSize"), 
					Sort.by(sortObject.getString("sort")).ascending());
		}else if(sortObject != null && pageObject != null && sortObject.getString("sort_type").equalsIgnoreCase("desc")) {
			paging = PageRequest.of(pageObject.getInt("pageNumber"), 
					pageObject.getInt("pageSize"), 
					Sort.by(sortObject.getString("sort")).descending());
		}
		
		query.with(paging);
				
		List<CovidCase> listCovidCases = mongoTemplate.find(query, CovidCase.class);

		return listCovidCases;
	}


	@Override
	public Document getCovidSummary(JSONObject filterObject) {
		
		Criteria matchCriteria = new Criteria();
		
		if((filterObject != null) && 
				(filterObject.has("state")) && 
				(!filterObject.getString("state").equalsIgnoreCase(""))) {
			matchCriteria = Criteria.where("state.$id").is(new ObjectId(filterObject.getString("state")));
		}
			
		if((filterObject != null) && 
				(filterObject.has("gender")) && 
				(!filterObject.getString("gender").equalsIgnoreCase(""))) {
			matchCriteria.andOperator(Criteria.where("gender").is(filterObject.getString("gender")));
		}
		
		ProjectionOperation projectStage = project()
				.and("gender.$id").as("gender")
				.and("status").as("status")
				.and("gender").as("gender")
				.and("identifiedDate").extractYear().as("year")
				.and("identifiedDate").extractMonth().as("month")
				.and("identifiedDate").extractDayOfMonth().as("day");

		
		Aggregation agg = newAggregation(
				match(matchCriteria),
				projectStage,
				group("gender", "status", "year", "month", "day").count().as("count")
				,project()
				.and("$_id.year").as("year")
				.and("$_id.month").as("month")
				.and("$_id.day").as("day")
				.and("$_id.gender").as("gender")
				.and("$_id.status").as("status")
				.and("$count").as("count")
			);
		
		Document document  = mongoTemplate.aggregate(agg, "covidCase", CovidSummary.class).getRawResults();
		
		System.out.println(document);
		
		return document;
	}
}
