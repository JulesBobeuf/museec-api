package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateOfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.OfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.CreateUpdateOfferDtoToOfferMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.mapper.OfferToOfferDtoMapper;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.History;

@Service
public class OfferService {
	
	private static final String FORBIDDEN_MESSAGE = "Forbidden";

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private BandRepository bandRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceService;
    /**
     * Method that allows a user to accept an offer
     */
    public boolean acceptOffer(int idUser, int idOffer) {
    	Optional<ApiUser> user = userRepository.findById(idUser);
    	Optional<Offer> offer = offerRepository.findById(idOffer);
    	if (user.isEmpty() || offer.isEmpty()) {
    		return false;
    	}
    	ApiUser realUser = user.get();
    	Offer realOffer = offer.get();
    	if(realUser.getIdBand() != -1)
    		return false;
    	realOffer.addAwaitingMembers(realUser.getId());
    	offerRepository.save(realOffer);
    	return true;
    }
    
    /**
     * Method that allows a user to reject an offer
     */
    public Boolean rejectOffer(int idUser, int idOffer) {
    	
    	Optional<ApiUser> user = userRepository.findById(idUser);
    	Optional<Offer> offer = offerRepository.findById(idOffer);
    	if (user.isEmpty() || offer.isEmpty()) {
    		return false;
    	}
    	ApiUser realUser = user.get();
    	Offer realOffer = offer.get();
    	realOffer.addUsersThatRejected(realUser.getId());
    	offerRepository.save(realOffer);
    	return true;
    }
  
    /**
     * Method that allows an owner to accept a musician
     */
	public boolean acceptMusician(String email, int idOffer, int idMusician) {
		Optional<ApiUser> musician = userRepository.findById(idMusician);
    	Optional<Offer> offer = offerRepository.findById(idOffer);
		Optional<ApiUser> owner = userRepository.findByEmail(email);
		
    	if (musician.isEmpty() || offer.isEmpty() || owner.isEmpty()) {
    		return false;
    	}
    	
    	ApiUser realMusician = musician.get();
    	ApiUser realOwner = owner.get();
    	Offer realOffer = offer.get();
    	Optional<Band> band = bandRepository.findById(realOffer.getIdBand());
    	
    	if (band.isEmpty()) {
    		return false;
    	}
    	
    	Band realBand = band.get();
    	
    	if (realBand.getOwner() != realOwner.getId() &&  (realOwner.getRole() != Role.ADMINISTRATOR)) {
	        throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}
    	
    	if (realMusician.isLookingForAGroup() && realMusician.getIdBand() == -1 && realOffer.getAwaitingMembers().contains(realMusician.getId())) {
    		
    		History history = new History();
    		history.setJoinDate(LocalDateTime.now());
    		history.setBandId(realOffer.getIdBand());
    		realMusician.addHistory(history);
    		
    		realMusician.setIdBand(realOwner.getIdBand());
    		realMusician.setLookingForAGroup(false);
    		realOffer.setActive(false);
    		userRepository.save(realMusician);
    		offerRepository.save(realOffer);
    		return true;
    	}
    	return false;
	}
	
    /**
     * Method that allows an owner to accept a musician
     */
	public boolean rejectMusician(String email, int idOffer, int idMusician) {
		Optional<ApiUser> musician = userRepository.findById(idMusician);
    	Optional<Offer> offer = offerRepository.findById(idOffer);
		Optional<ApiUser> owner = userRepository.findByEmail(email);
		
    	if (musician.isEmpty() || offer.isEmpty() || owner.isEmpty()) {
    		return false;
    	}
    	
    	ApiUser realMusician = musician.get();
    	ApiUser realOwner = owner.get();
    	Offer realOffer = offer.get();
    	Optional<Band> band = bandRepository.findById(realOffer.getIdBand());
    	
    	if (band.isEmpty()) {
    		return false;
    	}
    	
    	Band realBand = band.get();
    	
    	if (realBand.getOwner() != realOwner.getId() && realOwner.getRole() != Role.ADMINISTRATOR) {
	        throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}
    	
    	if (realOffer.getAwaitingMembers().contains(realMusician.getId())) {
    		realOffer.addUsersThatRejected(realMusician.getId());
    		offerRepository.save(realOffer);
    		return true;
    	}
    	return false;
	}
	
    /**
     * Method that allows an owner to create an offer
     */
	public boolean createOffer(CreateUpdateOfferDto request, String email) {
		Offer offer = CreateUpdateOfferDtoToOfferMapper.INSTANCE.createUpdateOfferDtoToOffer(request);
		Optional<ApiUser> owner = userRepository.findByEmail(email);
		
    	if (owner.isEmpty()) {
    		return false;
    	}
    	
    	ApiUser realOwner = owner.get();
    	Optional<Band> band = bandRepository.findById(realOwner.getIdBand());
    	
    	if (band.isEmpty()) {
    		return false;
    	}
    	
    	Band realBand = band.get();
    	
    	if (realBand.getOwner() != realOwner.getId() && realOwner.getRole() != Role.ADMINISTRATOR) {
    		throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}
    	
    	offer.setId(sequenceService.generateSequence(Offer.SEQUENCE_NAME));
    	offer.setIdBand(realBand.getId());
    	LocalDateTime dateNow = LocalDateTime.now();
		offer.setDateCreation(dateNow);
		offer.setDateUpdate(dateNow);
		offer.setActive(true);
		offer.setAwaitingMembers(new HashSet<>());
		offer.setUsersThatRejected(new HashSet<>());
		offerRepository.save(offer);
    	return true;
    }
	
    /**
     * Method that allows an owner to update an offer
     */
	public boolean updateOffer(int id, CreateUpdateOfferDto request, String email) {
		Optional<Offer> optionalOffer = offerRepository.findById(id);
		Optional<ApiUser> optionalOwner = userRepository.findByEmail(email);
		
    	if (optionalOwner.isEmpty() || optionalOffer.isEmpty()) {
    		return false;
    	}
    	
    	ApiUser owner = optionalOwner.get();
    	Offer offer = optionalOffer.get();
    	Optional<Band> band = bandRepository.findById(owner.getIdBand());
    	
    	if (band.isEmpty()) {
    		return false;
    	}
    	
    	Band realBand = band.get();
    	
    	if (realBand.getOwner() != owner.getId() && owner.getRole() != Role.ADMINISTRATOR) {
    		throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
		}
    	
    	offer.setName(request.getName());
		offer.setDescription(request.getDescription());
		offer.setMusicStyles(request.getMusicStyles());
		offer.setInstruments(request.getInstruments());
		offer.setSkills(request.getSkills());
		offer.setCountry(request.getCountry());
		offer.setAgeMin(request.getAgeMin());
		offer.setAgeMax(request.getAgeMax());
		offer.setGender(request.getGender());
		offer.setDateUpdate(LocalDateTime.now());
		offerRepository.save(offer);
    	return true;
	}
	
    /**
     * Method that allows an owner to delete an offer
     */
	public boolean deleteOffer(int id, String email) {
		Optional<Offer> optionalOffer = offerRepository.findById(id);
		Optional<ApiUser> optionalOwner = userRepository.findByEmail(email);
		
    	if (optionalOwner.isEmpty() || optionalOffer.isEmpty()) {
    		return false;
    	}
    	
    	ApiUser owner = optionalOwner.get();
    	Offer offer = optionalOffer.get();
    	Optional<Band> band = bandRepository.findById(owner.getIdBand());
    	
    	if (band.isEmpty()) {
    		return false;
    	}
    	
    	Band realBand = band.get();
		// if the user is trying to delete a band that is not his, make sure it's an administrator.
		if (realBand.getOwner() != owner.getId() &&  (owner.getRole() != Role.ADMINISTRATOR)) {
    		throw new IllegalArgumentException(FORBIDDEN_MESSAGE);
			
		}
		offerRepository.delete(offer);
		return true;
	}
	
    /**
     * Method that allows an administrator to get all offers
     */
	public List<OfferDto> getAllOffers() {
		List<Offer> allOffers = offerRepository.findAll();
		return OfferToOfferDtoMapper.INSTANCE.listOfferToListOfferDto(allOffers);
	}
	
    /**
     * Method that allows to get an offer
     */
	public OfferDto getOffer(int id) {
		Optional<Offer> optionalOffer = offerRepository.findById(id);
		if (optionalOffer.isPresent()) {
			return OfferToOfferDtoMapper.INSTANCE.offerToOfferDto(optionalOffer.get());
		}
		return null;
	}

}
