package fr.univartois.butinfo.s5a01.musicmatcher.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ApiUserDto;

@Mapper
public interface ApiUserToApiUserDtoMapper {
	
	ApiUserToApiUserDtoMapper INSTANCE = Mappers.getMapper(ApiUserToApiUserDtoMapper.class);

	public ApiUser apiUserDtoToApiUser(ApiUserDto apiUserDto);
	
	public ApiUserDto apiUserToApiUserDto(ApiUser apiUser);
	
	public List<ApiUserDto> listApiUserToListApiUser(List<ApiUser> listApiUser);

}
