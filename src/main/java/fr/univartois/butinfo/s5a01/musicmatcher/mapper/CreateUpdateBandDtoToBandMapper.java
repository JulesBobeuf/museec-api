package fr.univartois.butinfo.s5a01.musicmatcher.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateBandDto;

@Mapper
public interface CreateUpdateBandDtoToBandMapper {

	CreateUpdateBandDtoToBandMapper INSTANCE = Mappers.getMapper(CreateUpdateBandDtoToBandMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
	public Band createUpdateBandDtoToBand(CreateUpdateBandDto createUpdateBandDto);
	
	public CreateUpdateBandDto bandToCreateUpdateBandDto(Band band);
}
