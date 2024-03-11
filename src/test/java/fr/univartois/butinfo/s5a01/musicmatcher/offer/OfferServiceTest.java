package fr.univartois.butinfo.s5a01.musicmatcher.offer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
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
	
	@MockBean
	private BandRepository bandRepository;

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
	
	@Test
	void acceptMusicianTest() {
		ApiUser owner1 = new ApiUser();
		owner1.setId(1);
		owner1.setIdBand(1);
		String email1 = "Jules@gmail.com";
		owner1.setEmail(email1);
		when(userRepository.findById(1)).thenReturn(Optional.of(owner1));
		when(userRepository.findByEmail("Jules@gmail.com")).thenReturn(Optional.of(owner1));
		
		ApiUser musician1 = new ApiUser();
		musician1.setId(2);
		musician1.setIdBand(-1);
		when(userRepository.findById(2)).thenReturn(Optional.of(musician1));
		
		ApiUser musician2 = new ApiUser();
		musician2.setId(4);
		musician2.setIdBand(-1);
		musician2.setLookingForAGroup(true);
		when(userRepository.findById(4)).thenReturn(Optional.of(musician2));
		
		ApiUser PasOwner1 = new ApiUser();
		PasOwner1.setId(3);
		PasOwner1.setEmail("Thomas@gmail.com");
		when(userRepository.findById(3)).thenReturn(Optional.of(PasOwner1));
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer1));
		
		Offer offer2 = new Offer();
		offer2.setId(2);
		offer2.setAwaitingMembers(Set.of(2));
		when(offerRepository.findById(2)).thenReturn(Optional.of(offer2));
		
		Offer offer3 = new Offer();
		offer3.setId(3);
		offer3.setAwaitingMembers(Set.of(4));
		offer3.setActive(true);
		when(offerRepository.findById(3)).thenReturn(Optional.of(offer3));
		
		Band band1 = new Band();
		band1.setId(1);
		band1.setOwner(1);
		when(bandRepository.findById(1)).thenReturn(Optional.of(band1));
		
		assertThat(offerService.acceptMusician("un",0,0)).isFalse();
		assertThat(offerService.acceptMusician(email1,0,0)).isFalse();
		assertThat(offerService.acceptMusician("deux",1,0)).isFalse();
		assertThat(offerService.acceptMusician("trois",0,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,1,0)).isFalse();
		assertThat(offerService.acceptMusician("soleil",1,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,0,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,1,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,2,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,2,2)).isFalse();
		assertThat(offerService.acceptMusician("Thomas@gmail.com",3,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,3,4)).isTrue();
	}
	
	@Test
	void rejectMusicianTest() {
		ApiUser owner1 = new ApiUser();
		owner1.setId(1);
		owner1.setIdBand(1);
		String email1 = "Jules@gmail.com";
		owner1.setEmail(email1);
		when(userRepository.findById(1)).thenReturn(Optional.of(owner1));
		when(userRepository.findByEmail("Jules@gmail.com")).thenReturn(Optional.of(owner1));
		
		ApiUser musician1 = new ApiUser();
		musician1.setId(2);
		musician1.setIdBand(-1);
		when(userRepository.findById(2)).thenReturn(Optional.of(musician1));
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer1));
		
		Offer offer3 = new Offer();
		offer3.setId(3);
		offer3.setAwaitingMembers(Set.of(4));
		offer3.setActive(true);
		when(offerRepository.findById(3)).thenReturn(Optional.of(offer3));
		
		Band band1 = new Band();
		band1.setId(1);
		band1.setOwner(1);
		when(bandRepository.findById(1)).thenReturn(Optional.of(band1));
		
		assertThat(offerService.acceptMusician("un",0,0)).isFalse();
		assertThat(offerService.acceptMusician(email1,0,0)).isFalse();
		assertThat(offerService.acceptMusician("deux",1,0)).isFalse();
		assertThat(offerService.acceptMusician("trois",0,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,1,0)).isFalse();
		assertThat(offerService.acceptMusician("soleil",1,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,0,1)).isFalse();
		assertThat(offerService.acceptMusician(email1,1,1)).isFalse();
		assertThat(offerService.acceptMusician("Thomas@gmail.com",3,1)).isFalse();
		assertThat(offerService.acceptMusician("Thomas@gmail.com",3,4)).isFalse();
		assertThat(offerService.acceptMusician(email1,3,4)).isFalse();
	}
	
}
