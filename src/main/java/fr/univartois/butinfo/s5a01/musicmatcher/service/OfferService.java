package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;

@Service
public class OfferService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private BandRepository bandRepository;

    /**
     * Method that allows a user to accept an offer
     */
    public boolean accepteOffer(int idUser, int idOffer) {
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
    public boolean rejectOffer(int idUser, int idOffer) {
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
    	
    	if (realBand.getOwner() != realOwner.getId()) {
			if (realOwner.getRole() != Role.ADMINISTRATOR) {
	            throw new IllegalArgumentException("Forbidden");
			}
		}
    	
    	if (realMusician.isLookingForAGroup() && realMusician.getIdBand() == -1 && realOffer.getAwaitingMembers().contains(realMusician.getId())) {
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
    	
    	if (realBand.getOwner() != realOwner.getId()) {
			if (realOwner.getRole() != Role.ADMINISTRATOR) {
	            throw new IllegalArgumentException("Forbidden");
			}
		}
    	
    	if (realOffer.getAwaitingMembers().contains(realMusician.getId())) {
    		realOffer.addUsersThatRejected(realMusician.getId());
    		offerRepository.save(realOffer);
    		return true;
    	}
    	return false;
	}
}
