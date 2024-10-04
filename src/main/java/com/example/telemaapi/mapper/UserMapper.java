package com.example.telemaapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.example.telemaapi.dto.UserDto;
import com.example.telemaapi.entity.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {

	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(source = "username", target = "username")
	@Mapping(source = "password", target = "password")
	User toEntity(UserDto user);

}
