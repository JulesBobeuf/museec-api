package fr.univartois.butinfo.s5a01.musicmatcher.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.BandDto;

@Mapper
public interface BandToBandDtoMapper {

	BandToBandDtoMapper INSTANCE = Mappers.getMapper(BandToBandDtoMapper.class);

	public Band bandDtoToBand(BandDto bandDto);
	
	public BandDto bandToBandDto(Band band);
	
	public List<BandDto> listBandToListBandDto(List<Band> bands);

	
}
