package com.example.telemaapi.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.example.telemaapi.dto.FileUploadDto;
import com.example.telemaapi.entity.FileUpload;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FileUploadMapper {

	@Mapping(source = "id", target = "id")
	@Mapping(source = "fileName", target = "fileName")
	@Mapping(source = "fileType", target = "fileType")
	@Mapping(source = "uploadDateTime", target = "uploadDateTime")
	@Mapping(source = "metadata", target = "metadata")
	FileUploadDto toDto(FileUpload fileUpload);

	List<FileUploadDto> toDtos(List<FileUpload> fileUploads);
}
