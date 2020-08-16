package com.kapil.covid19.services;

import java.util.List;

import org.bson.Document;
import org.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;

import com.kapil.covid19.domain.CovidCase;
import com.kapil.covid19.domain.CovidSummary;

public interface CovidCaseService {
	
	public List<CovidCase> getCovidCases(JSONObject filterObject,
			JSONObject sortObject,
			JSONObject pageObject);

	public Document getCovidSummary(JSONObject filterObject);
	
}
