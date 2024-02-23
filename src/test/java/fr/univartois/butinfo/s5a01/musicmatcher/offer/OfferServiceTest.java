package fr.univartois.butinfo.s5a01.musicmatcher.offer;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.OfferService;
import fr.univartois.butinfo.s5a01.musicmatcher.service.UserService;

class OfferServiceTest {
	
	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private OfferService offerService;
	
	@MockBean
	private OfferRepository offerRepository;

	@Test
	void accepteOfferTest() {
		ApiUser user = new ApiUser();
		user.setEmail("toto@example.com");
		user.setId(1);
		
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		
		Offer offer = new Offer();
		offer.setId(1);
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer));
	}

	@Test
	void rejectOfferTest() {
		
	}
	
}
