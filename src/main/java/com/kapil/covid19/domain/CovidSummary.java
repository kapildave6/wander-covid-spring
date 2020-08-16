package com.kapil.covid19.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class CovidSummary {
    private String _id;
    private int count;
}
