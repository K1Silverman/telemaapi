package com.example.telemaapi.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class FileUpload {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String fileName;

	@Column(nullable = false)
	private String fileType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "uploader_id", nullable = false)
	private User uploader;

	@Column(nullable = false)
	private LocalDateTime uploadDateTime;

	@Column(nullable = false)
	private String filePath;
}
