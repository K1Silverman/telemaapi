package com.example.telemaapi.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

import lombok.Data;

@Data
public class FileUploadDto implements Serializable {

	private Long id;
	private String fileName;
	private String fileType;
	private LocalDateTime uploadDateTime;
	private Map<String, Object> metadata;

}
