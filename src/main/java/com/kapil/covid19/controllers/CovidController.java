package com.kapil.covid19.controllers;

import java.util.List;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.kapil.covid19.domain.CovidCase;
import com.kapil.covid19.domain.CovidSummary;
import com.kapil.covid19.services.CovidCaseService;

import org.bson.Document;
import org.json.JSONObject;

@CrossOrigin
@RestController
@RequestMapping(path = "/covid")
public class CovidController {

	private CovidCaseService covidCaseService;

	public CovidController(CovidCaseService covidCaseService) {
		this.covidCaseService= covidCaseService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/list")
	private List<CovidCase> covidCases(@RequestBody String requestObject) {

		JSONObject jsonRequest = new JSONObject(requestObject);

		JSONObject pageObject = null;
		if(jsonRequest != null && jsonRequest.has("paging")) {
			pageObject = (JSONObject) jsonRequest.get("paging");

		}
		JSONObject sortObject = null;
		if(jsonRequest != null && jsonRequest.has("sorting")) {
			sortObject = (JSONObject) jsonRequest.get("sorting");

		}
		JSONObject filterObject = null;
		if(jsonRequest != null && jsonRequest.has("filter")) {
			filterObject = (JSONObject) jsonRequest.get("filter");
		}
		
		return covidCaseService.getCovidCases(filterObject, sortObject, pageObject);  
	}


	@RequestMapping(method = RequestMethod.POST, path = "/summary")
	private Document covidSummary(
			@RequestBody String requestObject) {

		JSONObject jsonRequest = new JSONObject(requestObject);

		int graphType = 1;
		
		JSONObject filterObject = null;
		
		if(jsonRequest != null && jsonRequest.has("filter")) {
			filterObject = (JSONObject) jsonRequest.get("filter");
		}
		
		return covidCaseService.getCovidSummary(filterObject);  
	}

}
