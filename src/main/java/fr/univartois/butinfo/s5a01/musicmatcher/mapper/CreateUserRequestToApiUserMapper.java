package fr.univartois.butinfo.s5a01.musicmatcher.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUserRequest;

@Mapper
public interface CreateUserRequestToApiUserMapper {

	CreateUserRequestToApiUserMapper INSTANCE = Mappers.getMapper(CreateUserRequestToApiUserMapper.class);

	public ApiUser createUserRequestToApiUser(CreateUserRequest createUserRequest);
	
	public CreateUserRequest apiUserToCreateUserRequest(ApiUser apiUser);
}
