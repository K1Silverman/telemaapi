package com.example.telemaapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.telemaapi.dto.UserFilesDto;
import com.example.telemaapi.infrastructure.validation.DisallowedExtensions;
import com.example.telemaapi.service.FileService;
import com.example.telemaapi.utils.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Files", description = "Uploading and listing user's files")
@RequestMapping("api/user/files")
public class FileController {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private FileService fileService;

	@GetMapping("/")
	@Operation(summary = "Get user files", description = "Retrieves user's uploaded files' information.")
	public ResponseEntity<?> getUserFiles(
			HttpServletRequest request)
			throws Exception {
		String username = getUsernameFromJWT(request);
		UserFilesDto asdf = fileService.getUserFiles(username);
		return ResponseEntity.ok().body(asdf);
	}

	@PostMapping(value = "/", consumes = "multipart/form-data")
	@Operation(summary = "Upload file", description = "Uploads user's file.")
	public ResponseEntity<?> uploadFile(
			@Parameter(description = "File to upload", required = true) @RequestParam("File") @Valid @DisallowedExtensions({
					"image/jpeg", "image/png", "image/gif" }) MultipartFile file,
			HttpServletRequest request) throws Exception {

		String username = getUsernameFromJWT(request);
		UserFilesDto fileUploadDto = fileService.uploadFile(username, file);
		return ResponseEntity.ok().body(fileUploadDto);
	}

	private String getUsernameFromJWT(HttpServletRequest request) throws Exception {

		String token = request.getHeader("Authorization");
		token = token.substring(7);
		if (token != null) {
			String username = jwtUtil.extractUsername(token);
			return username;
		} else {
			throw new Exception("Invalid JWT token");
		}

	}

}
