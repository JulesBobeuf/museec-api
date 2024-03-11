package fr.univartois.butinfo.s5a01.musicmatcher.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;

@Service
public class OfferService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;

    /**
     * Method that allows a user to accept an offer
     */
    public Boolean accepteOffer(int idUser, int idOffer) {
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
  
	
}
