package com.rbc.demo.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import org.springframework.web.multipart.MultipartFile;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public List<String> findAll(final String stock) {
		return searchByStockName(stock);
	}

	@Override
	public void updateRecord(final String fileName, final String newRecord) {
		openFileToUpdate(fileName, newRecord);
	}

	private void openFileToUpdate(String fileName, String newRecord) {
		try{
			Files.write(load(fileName), newRecord.getBytes(), StandardOpenOption.APPEND);
		}catch(IOException e){
			throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}

	//Note: we actually want this to return a list of MarketValues
	public List<String> searchByStockName(String stock){
		int count = 0;
		double countBuffer=0;
		List<File> myFiles= new ArrayList<>();
		List<String> setOfResponses = new ArrayList<>(); // Normally this should be a list of MarketValues
		BufferedReader reader;
		String inputSearch = stock;
		String line = "";

		try (Stream<Path> paths = Files.walk(Paths.get("upload-dir"))) {
				paths.filter(Files::isRegularFile).forEach(file -> myFiles.add(file.toFile()));
				}
		catch (IOException ioException) {
			ioException.printStackTrace();
		}
		count = myFiles.size();
		for(int i=0;i < count;i++){
			try {
				reader = new BufferedReader(new FileReader(myFiles.get(i)));
				while((line = reader.readLine()) != null) {

					System.out.println(line);
					String[] words = line.split(",");

					for (String word : words) {
						if (word.equals(inputSearch)) {

							countBuffer++;
							//get from the list
							setOfResponses.add(line);
						}
					}
					if(countBuffer > 0)	{
						countBuffer = 0;
					}
				}
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return  setOfResponses;
	}

}
