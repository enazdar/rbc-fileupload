package com.rbc.demo.controllers;

import java.util.List;
import com.rbc.demo.core.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class FileSearchController {
	private final StorageService marketValueService;

	@Autowired
	public FileSearchController(StorageService marketValueService) {
		this.marketValueService = marketValueService;
	}

	@GetMapping("/stock")
	public ResponseEntity<List<String>> searchForStocks(@RequestParam("stockId") String stockId ) {
		return new ResponseEntity<>(marketValueService.findAll(stockId), HttpStatus.OK);
	}
}
