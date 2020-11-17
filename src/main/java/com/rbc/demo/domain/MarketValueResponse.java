package com.rbc.demo.domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class MarketValueResponse  extends ResponseEntity {
	public MarketValueResponse(final Object body, final HttpStatus status) {
		super(body, status);
	}
	public List<MarketValue> marketValueSet = new ArrayList<>();
}
