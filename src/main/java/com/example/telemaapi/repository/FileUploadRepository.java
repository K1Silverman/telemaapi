package com.example.telemaapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.telemaapi.entity.FileUpload;
import com.example.telemaapi.entity.User;

public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {

	@Query("select f from FileUpload f where f.uploader = ?1")
	List<FileUpload> findByUploader(User user);

}
