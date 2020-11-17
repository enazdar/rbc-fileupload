package com.rbc.demo.core;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

import com.rbc.demo.domain.MarketValue;

public interface StorageService {

	void init();

	void store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename);
	MarketValue getMarketRecord(String marketValue);

	List<String> findAll(String stock);

	void updateRecord(String fileName, String newRecord);

	void deleteAll();

}
