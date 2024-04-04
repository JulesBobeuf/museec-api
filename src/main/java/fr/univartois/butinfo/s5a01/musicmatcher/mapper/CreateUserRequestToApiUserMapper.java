package fr.univartois.butinfo.s5a01.musicmatcher.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUserRequest;

@Mapper
public interface CreateUserRequestToApiUserMapper {

	CreateUserRequestToApiUserMapper INSTANCE = Mappers.getMapper(CreateUserRequestToApiUserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idBand", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "history", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    @Mapping(target = "authorities", ignore = true)
	public ApiUser createUserRequestToApiUser(CreateUserRequest createUserRequest);
	
    @Mapping(target = "confirmPassword", ignore = true)
	public CreateUserRequest apiUserToCreateUserRequest(ApiUser apiUser);
}
