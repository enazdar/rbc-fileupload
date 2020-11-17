package com.rbc.demo.controllers;

import com.rbc.demo.core.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UpdateRecordController {
	private final StorageService storageService;

	@Autowired
	public UpdateRecordController(final StorageService storageService) {
		this.storageService = storageService;
	}

	@PostMapping("/record")
	public ResponseEntity addNewRecord(@RequestParam("fileName") String fileName, @RequestParam("newRecord") String newRecord ) {
		//Resource file = storageService.loadAsResource(fileName);
		storageService.updateRecord(fileName, newRecord);
		return ResponseEntity.ok("Successfully updated file: "+ fileName);
	}
}
