package fr.univartois.butinfo.s5a01.musicmatcher.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.OfferDto;

@Mapper
public interface OfferToOfferDtoMapper {

	OfferToOfferDtoMapper INSTANCE = Mappers.getMapper(OfferToOfferDtoMapper.class);

	public Offer offerDtoToOffer(OfferDto offerDto);
	
	public OfferDto offerToOfferDto(Offer offer);
	
	public List<OfferDto> listOfferToListOfferDto(List<Offer> offers);

}
