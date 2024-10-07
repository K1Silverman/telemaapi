package com.example.telemaapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.example.telemaapi.dto.UserFilesDto;
import com.example.telemaapi.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserFilesMapper {

	UserFilesMapper INSTANCE = Mappers.getMapper(UserFilesMapper.class);

	@Mapping(source = "id", target = "id")
	@Mapping(source = "username", target = "username")
	UserFilesDto toDto(User user);

}
