package fr.univartois.butinfo.s5a01.musicmatcher.mapper;

import org.mapstruct.factory.Mappers;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateOfferDto;

public interface CreateUpdateOfferDtoToOfferMapper {

	CreateUpdateOfferDtoToOfferMapper INSTANCE = Mappers.getMapper(CreateUpdateOfferDtoToOfferMapper.class);

	public Offer createUpdateOfferDtoToOffer(CreateUpdateOfferDto createUpdateOfferDto);
	
	public CreateUpdateOfferDto offerToCreateUpdateOfferDto(Offer offer);
	
}
