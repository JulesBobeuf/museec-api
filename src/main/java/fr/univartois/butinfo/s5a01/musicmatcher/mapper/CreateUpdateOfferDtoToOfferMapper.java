package fr.univartois.butinfo.s5a01.musicmatcher.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateOfferDto;

@Mapper
public interface CreateUpdateOfferDtoToOfferMapper {

	CreateUpdateOfferDtoToOfferMapper INSTANCE = Mappers.getMapper(CreateUpdateOfferDtoToOfferMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idBand", ignore = true)
    @Mapping(target = "usersThatRejected", ignore = true)
    @Mapping(target = "awaitingMembers", ignore = true)
    @Mapping(target = "dateCreation", ignore = true)
    @Mapping(target = "dateUpdate", ignore = true)
    @Mapping(target = "active", ignore = true)
	public Offer createUpdateOfferDtoToOffer(CreateUpdateOfferDto createUpdateOfferDto);
	
	public CreateUpdateOfferDto offerToCreateUpdateOfferDto(Offer offer);
	
}
