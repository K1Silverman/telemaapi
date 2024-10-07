package com.example.telemaapi.dto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import lombok.Data;

@Data
public class UserFilesDto implements Serializable {
	private UUID id;
	private String username;
	private List<FileUploadDto> files;
}
