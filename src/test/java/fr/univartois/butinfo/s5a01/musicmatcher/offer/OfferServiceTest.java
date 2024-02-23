package fr.univartois.butinfo.s5a01.musicmatcher.offer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.OfferService;

@SpringBootTest
class OfferServiceTest {

	@MockBean
	private UserRepository userRepository;
	
	@Autowired
	private OfferService offerService;
	
	@MockBean
	private OfferRepository offerRepository;

	@Test
	void accepteOfferTest() {
		ApiUser user1 = new ApiUser();
		user1.setId(1);
		user1.setIdBand(-1);
		when(userRepository.findById(1)).thenReturn(Optional.of(user1));
		
		ApiUser user2 = new ApiUser();
		user2.setId(2);
		user2.setIdBand(2);
		when(userRepository.findById(2)).thenReturn(Optional.of(user2));
		
		Offer offer = new Offer();
		offer.setId(1);
		offer.setAwaitingMembers(new HashSet<>());
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer));
		
		assertThat(offerService.accepteOffer(1,1)).isTrue();
		assertThat(offerService.accepteOffer(0,1)).isFalse();
		assertThat(offerService.accepteOffer(1,0)).isFalse();
		assertThat(offerService.accepteOffer(2,1)).isFalse();
	}

	@Test
	void rejectOfferTest() {
		ApiUser user1 = new ApiUser();
		user1.setId(1);
		when(userRepository.findById(1)).thenReturn(Optional.of(user1));
		
		Offer offer = new Offer();
		offer.setId(1);
		offer.setUsersThatRejected(new HashSet<>());
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer));
		
		assertThat(offerService.rejectOffer(1,1)).isTrue();
		assertThat(offerService.rejectOffer(0,1)).isFalse();
		assertThat(offerService.rejectOffer(1,0)).isFalse();
	}
	
}
