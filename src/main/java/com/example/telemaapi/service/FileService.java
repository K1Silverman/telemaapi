package com.example.telemaapi.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.example.telemaapi.dto.FileUploadDto;
import com.example.telemaapi.dto.UserFilesDto;
import com.example.telemaapi.entity.FileUpload;
import com.example.telemaapi.entity.User;
import com.example.telemaapi.mapper.FileUploadMapper;
import com.example.telemaapi.mapper.UserFilesMapper;
import com.example.telemaapi.repository.FileUploadRepository;
import com.example.telemaapi.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;

@Service
public class FileService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileUploadRepository fileRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private UserFilesMapper userFilesMapper;

	@Autowired
	private FileUploadMapper fileUploadMapper;

	@Autowired
	private CacheManager cacheManager;

	@Value("${METADATA_URL}")
	private String metadataUrl;

	@Transactional
	public UserFilesDto uploadFile(String username, MultipartFile file) throws IOException {

		Optional<User> user = getUser(username);
		FileUpload fileToUpload = setFileReadyForUpload(file, user.get());
		FileUpload uploadedFile = fileRepository.save(fileToUpload);
		FileUploadDto fileUploadDto = fileUploadMapper.toDto(uploadedFile);

		return updateCache(username, fileUploadDto);
	}

	@Transactional
	@Cacheable(value = "userFiles", key = "#username")
	public UserFilesDto getUserFiles(String username) throws IllegalArgumentException {

		Optional<User> user = getUser(username);
		List<FileUpload> userFiles = fileRepository.findByUploader(user.get());
		UserFilesDto userFilesDto = userFilesMapper.toDto(user.get());
		List<FileUploadDto> fileUploadDtos = fileUploadMapper.toDtos(userFiles);
		userFilesDto.setFiles(fileUploadDtos);

		return userFilesDto;
	}

	private Optional<User> getUser(String username) {

		Optional<User> user = userRepository.getUserByUsername(username);

		if (user.isEmpty()) {
			throw new IllegalArgumentException("User not found");
		}

		return user;
	}

	private FileUpload setFileReadyForUpload(MultipartFile fileToUpload, User user) throws IOException {

		FileUpload file = new FileUpload();
		file.setFileName(fileToUpload.getOriginalFilename());
		file.setFileType(fileToUpload.getContentType());
		file.setUploader(user);
		file.setFile(fileToUpload.getBytes());
		file.setMetadata(getMetadata());

		return file;
	}

	private Map<String, Object> getMetadata() throws RuntimeException {

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> objectResponse = restTemplate.getForEntity(metadataUrl, String.class);

		if (objectResponse.getStatusCode().is2xxSuccessful()) {
			try {
				Map<String, Object> metadata;
				metadata = objectMapper.readValue(objectResponse.getBody(),
						new TypeReference<Map<String, Object>>() {
						});

				return metadata;

			} catch (JsonProcessingException e) {
				throw new RuntimeException("Error parsing public API response", e);
			}
		} else {
			throw new RuntimeException("Public API call failed with status: " + objectResponse.getStatusCode());
		}
	}

	private UserFilesDto updateCache(String username, FileUploadDto newFile) {

		Cache cache = cacheManager.getCache("userFiles");
		UserFilesDto cachedUser = cache.get(username, UserFilesDto.class);

		if (cachedUser == null) {
			User user = userRepository.getUserByUsername(username).get();
			cachedUser = userFilesMapper.toDto(user);
		}

		List<FileUploadDto> filesList = cachedUser.getFiles();
		filesList.add(newFile);
		cachedUser.setFiles(filesList);
		cache.put(username, cachedUser);

		return cachedUser;
	}
}
