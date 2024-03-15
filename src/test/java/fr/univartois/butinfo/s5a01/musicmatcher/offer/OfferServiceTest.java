package fr.univartois.butinfo.s5a01.musicmatcher.offer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Offer;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateOfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.OfferDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.OfferRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.OfferService;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;

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
	void acceptOfferTest() {
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
		
		assertThat(offerService.acceptOffer(1,1)).isTrue();
		assertThat(offerService.acceptOffer(0,1)).isFalse();
		assertThat(offerService.acceptOffer(1,0)).isFalse();
		assertThat(offerService.acceptOffer(2,1)).isFalse();
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
		offer3.setIdBand(1);
		when(offerRepository.findById(3)).thenReturn(Optional.of(offer3));
		
		Band band1 = new Band();
		band1.setId(1);
		band1.setOwner(1);

		when(bandRepository.findById(0)).thenReturn(Optional.empty());
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
		assertThat(offerService.acceptMusician(email1,3,2)).isFalse();
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
		when(userRepository.findByEmail(email1)).thenReturn(Optional.of(owner1));
		
		ApiUser owner2 = new ApiUser();
		owner2.setId(2);
		owner2.setIdBand(-1);
		String email2 = "Jules2@gmail.com";
		owner2.setEmail(email2);
		owner2.setRole(Role.ADMINISTRATOR);
		when(userRepository.findById(2)).thenReturn(Optional.of(owner2));
		when(userRepository.findByEmail(email2)).thenReturn(Optional.of(owner2));
		
		ApiUser musician1 = new ApiUser();
		musician1.setId(2);
		musician1.setIdBand(-1);
		when(userRepository.findById(2)).thenReturn(Optional.of(musician1));
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer1));
		
		Offer offer3 = new Offer();
		offer3.setId(3);
		offer3.setAwaitingMembers(Set.of(2));
		HashSet<Integer> set = new HashSet<>();
		set.addAll(List.of(99,98,97));
		offer3.setUsersThatRejected(set);
		offer3.setIdBand(1);
		offer3.setActive(true);
		when(offerRepository.findById(3)).thenReturn(Optional.of(offer3));
		
		Band band1 = new Band();
		band1.setId(1);
		band1.setOwner(1);
		
		when(bandRepository.findById(0)).thenReturn(Optional.empty());
		when(bandRepository.findById(band1.getId())).thenReturn(Optional.of(band1));
		
		assertThat(offerService.rejectMusician("un",0,0)).isFalse();
		assertThat(offerService.rejectMusician(email1,0,0)).isFalse();
		assertThat(offerService.rejectMusician("deux",1,0)).isFalse();
		assertThat(offerService.rejectMusician("trois",0,1)).isFalse();
		assertThat(offerService.rejectMusician(email1,1,0)).isFalse();
		assertThat(offerService.rejectMusician("soleil",1,1)).isFalse();
		assertThat(offerService.rejectMusician(email1,0,1)).isFalse();
		assertThat(offerService.rejectMusician(email1,1,1)).isFalse();
		assertThat(offerService.rejectMusician("Thomas@gmail.com",3,1)).isFalse();
		assertThat(offerService.rejectMusician("Thomas@gmail.com",3,4)).isFalse();
		assertThat(offerService.rejectMusician(email1,1,2)).isFalse();
		assertThat(offerService.rejectMusician(email1,3,2)).isTrue();
		assertThat(offerService.rejectMusician(email2,3,2)).isTrue();
	}
	
	@Test
	void createOfferTest() {
		CreateUpdateOfferDto offer = new CreateUpdateOfferDto();
		offer.setName("AAA");
		offer.setDescription("Bonjour");
		offer.setMusicStyles(Set.of(MusicStyle.ACOUSTIC));
		offer.setInstruments(Set.of(Instrument.CELLO));
		offer.setSkills(Set.of(Skill.AUDIO_MIXING));
		offer.setCountry(Country.ANTIGUA_DEPS);
		offer.setAgeMin(20);
		offer.setAgeMax(50);
		offer.setGender(Gender.ANY);
		
		String email = "toto@example.com";
		String email2 = "admin@example.com";
		
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setIdBand(-1);
		user.setRole(Role.USER);
		
		ApiUser user2 = new ApiUser();
		user2.setEmail(email2);
		user2.setId(2);
		user2.setRole(Role.ADMINISTRATOR);
		user2.setIdBand(1);
		
		Band band = new Band();
		band.setId(1);
		band.setName("hey");
		band.setDescription("the coolest description");
		band.setOwner(2);
		band.setProfilePicture("./img.png");
		band.setVideoLink("https://youtube.com");
		
		
		assertThat(offerService.createOffer(offer, email)).isFalse();
		
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		when(userRepository.findByEmail(email2)).thenReturn(Optional.of(user2));
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.findById(2)).thenReturn(Optional.of(user2));
		when(bandRepository.findById(1)).thenReturn(Optional.of(band));
		
		assertThat(offerService.createOffer(offer, email)).isFalse();
		
		user.setEmail(email);
		
		assertThat(offerService.createOffer(offer, email)).isFalse();
		
		user.setIdBand(1);
		
		assertThrows(IllegalArgumentException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				assertThat(offerService.createOffer(offer, email)).isFalse();
			}
		});

		assertThat(offerService.createOffer(offer, email2)).isTrue();

		band.setOwner(1);

		assertThat(offerService.createOffer(offer, email)).isTrue();

		user2.setRole(Role.ADMINISTRATOR);
		
		assertThat(offerService.createOffer(offer, email2)).isTrue();
		
	}
	
	@Test
	void updateOfferTest() {
		
		CreateUpdateOfferDto offer = new CreateUpdateOfferDto();
		offer.setName("AAA");
		offer.setDescription("Bonjour");
		offer.setMusicStyles(Set.of(MusicStyle.ACOUSTIC));
		offer.setInstruments(Set.of(Instrument.CELLO));
		offer.setSkills(Set.of(Skill.AUDIO_MIXING));
		offer.setCountry(Country.ANTIGUA_DEPS);
		offer.setAgeMin(20);
		offer.setAgeMax(50);
		offer.setGender(Gender.ANY);
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		offer1.setName("BBB");
		offer1.setDescription("Bonjour");
		offer1.setMusicStyles(Set.of(MusicStyle.AMBIENT));
		offer1.setInstruments(Set.of(Instrument.GUITAR));
		offer1.setSkills(Set.of(Skill.AUDIO_MIXING));
		offer1.setCountry(Country.ANTIGUA_DEPS);
		offer1.setAgeMin(20);
		offer1.setAgeMax(50);
		offer1.setGender(Gender.FEMALE);
		
		String email = "toto@example.com";
		String email2 = "admin@example.com";
		
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setRole(Role.USER);
		
		ApiUser user2 = new ApiUser();
		user2.setEmail(email2);
		user2.setId(2);
		user2.setRole(Role.ADMINISTRATOR);
		user2.setIdBand(1);
		
		Band band = new Band();
		band.setId(1);
		band.setName("hey");
		band.setDescription("the coolest description");
		band.setOwner(2);
		band.setProfilePicture("./img.png");
		band.setVideoLink("https://youtube.com");
		
		when(userRepository.findByEmail(email2)).thenReturn(Optional.of(user2));
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.findById(2)).thenReturn(Optional.of(user2));
		when(bandRepository.findById(1)).thenReturn(Optional.of(band));
		
		assertThat(offerService.updateOffer(1, offer, email)).isFalse();
		
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		
		assertThat(offerService.updateOffer(1, offer, email)).isFalse();
		
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer1));
		
		assertThat(offerService.updateOffer(1, offer, email)).isFalse();
		
		user.setIdBand(1);
		
		assertThrows(IllegalArgumentException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				assertThat(offerService.updateOffer(1, offer, email)).isFalse();
			}
		});
		
		assertThat(offerService.updateOffer(1, offer, email2)).isTrue();
		
		user.setRole(Role.ADMINISTRATOR);
		
		assertThat(offerService.updateOffer(1, offer, email)).isTrue();
		
	}
	
	@Test
	void deleteOfferTest() {
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		offer1.setName("BBB");
		offer1.setDescription("Bonjour");
		offer1.setMusicStyles(Set.of(MusicStyle.AMBIENT));
		offer1.setInstruments(Set.of(Instrument.GUITAR));
		offer1.setSkills(Set.of(Skill.AUDIO_MIXING));
		offer1.setCountry(Country.ANTIGUA_DEPS);
		offer1.setAgeMin(20);
		offer1.setAgeMax(50);
		offer1.setGender(Gender.FEMALE);
		
		String email = "toto@example.com";
		String email2 = "admin@example.com";
		
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setRole(Role.USER);
		
		ApiUser user2 = new ApiUser();
		user2.setEmail(email2);
		user2.setId(2);
		user2.setRole(Role.ADMINISTRATOR);
		user2.setIdBand(1);
		
		Band band = new Band();
		band.setId(1);
		band.setName("hey");
		band.setDescription("the coolest description");
		band.setOwner(2);
		band.setProfilePicture("./img.png");
		band.setVideoLink("https://youtube.com");
		
		when(userRepository.findByEmail(email2)).thenReturn(Optional.of(user2));
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.findById(2)).thenReturn(Optional.of(user2));
		
		assertThat(offerService.deleteOffer(1, email)).isFalse();
		
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		
		assertThat(offerService.deleteOffer(1, email)).isFalse();
		
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer1));
		
		assertThat(offerService.deleteOffer(1, email)).isFalse();
		
		when(bandRepository.findById(1)).thenReturn(Optional.of(band));
		
		assertThat(offerService.deleteOffer(1, email)).isFalse();
		
		user.setIdBand(1);
		
		assertThrows(IllegalArgumentException.class, new Executable() {

			@Override
			public void execute() throws Throwable {
				assertThat(offerService.deleteOffer(1, email)).isFalse();
			}
		});
		
		assertThat(offerService.deleteOffer(1, email2)).isTrue();
		
		user.setRole(Role.ADMINISTRATOR);
		
		assertThat(offerService.deleteOffer(1, email)).isTrue();
	}
	
	@Test
	void getOfferTest() {

		LocalDateTime now = LocalDateTime.now();
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		offer1.setName("BBB");
		offer1.setDescription("Bonjour");
		offer1.setMusicStyles(Set.of(MusicStyle.AMBIENT));
		offer1.setInstruments(Set.of(Instrument.GUITAR));
		offer1.setSkills(Set.of(Skill.AUDIO_MIXING));
		offer1.setCountry(Country.ANTIGUA_DEPS);
		offer1.setAgeMin(20);
		offer1.setAgeMax(50);
		offer1.setGender(Gender.FEMALE);
		offer1.setIdBand(1);
		offer1.setUsersThatRejected(new HashSet<>());
		offer1.setAwaitingMembers(new HashSet<>());
		offer1.setDateCreation(now);
		offer1.setDateUpdate(now);
		offer1.setActive(true);
		
		when(offerRepository.findById(1)).thenReturn(Optional.of(offer1));
		
		OfferDto resultOffers = offerService.getOffer(1);
		
		assertThat(resultOffers.getId()).isEqualTo(offer1.getId());
		assertThat(resultOffers.getName()).isEqualTo(offer1.getName());
		assertThat(resultOffers.getDescription()).isEqualTo(offer1.getDescription());
		assertTrue(resultOffers.getMusicStyles().contains(MusicStyle.AMBIENT.name()));
		assertTrue(resultOffers.getInstruments().contains(Instrument.GUITAR.name()));
		assertTrue(resultOffers.getSkills().contains(Skill.AUDIO_MIXING.name()));
		assertThat(resultOffers.getCountry()).isEqualTo(offer1.getCountry());
		assertThat(resultOffers.getAgeMin()).isEqualTo(offer1.getAgeMin());
		assertThat(resultOffers.getAgeMax()).isEqualTo(offer1.getAgeMax());
		assertThat(resultOffers.getGender()).isEqualTo(offer1.getGender());
		assertThat(resultOffers.getIdBand()).isEqualTo(offer1.getIdBand());
		assertThat(resultOffers.getUsersThatRejected()).isEqualTo(offer1.getUsersThatRejected());
		assertThat(resultOffers.getAwaitingMembers()).isEqualTo(offer1.getAwaitingMembers());
		assertThat(resultOffers.getDateCreation()).isEqualTo(offer1.getDateCreation());
		assertThat(resultOffers.getDateUpdate()).isEqualTo(offer1.getDateUpdate());
		assertThat(resultOffers.isActive()).isEqualTo(offer1.isActive());
		
	}
	
	@Test
	void getOffersTest() {
		
		LocalDateTime now = LocalDateTime.now();
		
		Offer offer1 = new Offer();
		offer1.setId(1);
		offer1.setName("BBB");
		offer1.setDescription("Bonjour");
		offer1.setMusicStyles(Set.of(MusicStyle.AMBIENT));
		offer1.setInstruments(Set.of(Instrument.GUITAR));
		offer1.setSkills(Set.of(Skill.AUDIO_MIXING));
		offer1.setCountry(Country.ANTIGUA_DEPS);
		offer1.setAgeMin(20);
		offer1.setAgeMax(50);
		offer1.setGender(Gender.FEMALE);
		offer1.setIdBand(1);
		offer1.setUsersThatRejected(new HashSet<>());
		offer1.setAwaitingMembers(new HashSet<>());
		offer1.setDateCreation(now);
		offer1.setDateUpdate(now);
		offer1.setActive(true);
		
		Offer offer2 = new Offer();
		offer2.setId(1);
		offer2.setName("BBB");
		offer2.setDescription("Bonjour");
		offer2.setMusicStyles(Set.of(MusicStyle.AMBIENT));
		offer2.setInstruments(Set.of(Instrument.GUITAR));
		offer2.setSkills(Set.of(Skill.CLIP_REALISATION));
		offer2.setCountry(Country.ANTIGUA_DEPS);
		offer2.setAgeMin(20);
		offer2.setAgeMax(50);
		offer2.setGender(Gender.FEMALE);
		offer1.setIdBand(2);
		offer1.setUsersThatRejected(new HashSet<>());
		offer1.setAwaitingMembers(new HashSet<>());
		offer1.setDateCreation(now);
		offer1.setDateUpdate(now);
		offer1.setActive(false);
	
		when(offerRepository.findAll()).thenReturn(List.of(offer1, offer2));
		
		List<OfferDto> resultOffers = offerService.getAllOffers();
		
		assertThat(resultOffers.get(0).getId()).isEqualTo(offer1.getId());
		assertThat(resultOffers.get(0).getName()).isEqualTo(offer1.getName());
		assertThat(resultOffers.get(0).getDescription()).isEqualTo(offer1.getDescription());
		assertTrue(resultOffers.get(0).getMusicStyles().contains(MusicStyle.AMBIENT.name()));
		assertTrue(resultOffers.get(0).getInstruments().contains(Instrument.GUITAR.name()));
		assertTrue(resultOffers.get(0).getSkills().contains(Skill.AUDIO_MIXING.name()));
		assertThat(resultOffers.get(0).getCountry()).isEqualTo(offer1.getCountry());
		assertThat(resultOffers.get(0).getAgeMin()).isEqualTo(offer1.getAgeMin());
		assertThat(resultOffers.get(0).getAgeMax()).isEqualTo(offer1.getAgeMax());
		assertThat(resultOffers.get(0).getGender()).isEqualTo(offer1.getGender());
		assertThat(resultOffers.get(0).getIdBand()).isEqualTo(offer1.getIdBand());
		assertThat(resultOffers.get(0).getUsersThatRejected()).isEqualTo(offer1.getUsersThatRejected());
		assertThat(resultOffers.get(0).getAwaitingMembers()).isEqualTo(offer1.getAwaitingMembers());
		assertThat(resultOffers.get(0).getDateCreation()).isEqualTo(offer1.getDateCreation());
		assertThat(resultOffers.get(0).getDateUpdate()).isEqualTo(offer1.getDateUpdate());
		assertThat(resultOffers.get(0).isActive()).isEqualTo(offer1.isActive());
		
		assertThat(resultOffers.get(1).getId()).isEqualTo(offer2.getId());
		assertThat(resultOffers.get(1).getName()).isEqualTo(offer2.getName());
		assertThat(resultOffers.get(1).getDescription()).isEqualTo(offer2.getDescription());
		assertTrue(resultOffers.get(1).getMusicStyles().contains(MusicStyle.AMBIENT.name()));
		assertTrue(resultOffers.get(1).getInstruments().contains(Instrument.GUITAR.name()));
		assertTrue(resultOffers.get(1).getSkills().contains(Skill.CLIP_REALISATION.name()));
		assertThat(resultOffers.get(1).getCountry()).isEqualTo(offer2.getCountry());
		assertThat(resultOffers.get(1).getAgeMin()).isEqualTo(offer2.getAgeMin());
		assertThat(resultOffers.get(1).getAgeMax()).isEqualTo(offer2.getAgeMax());
		assertThat(resultOffers.get(1).getGender()).isEqualTo(offer2.getGender());
		assertThat(resultOffers.get(1).getIdBand()).isEqualTo(offer2.getIdBand());
		assertThat(resultOffers.get(1).getUsersThatRejected()).isEqualTo(offer2.getUsersThatRejected());
		assertThat(resultOffers.get(1).getAwaitingMembers()).isEqualTo(offer2.getAwaitingMembers());
		assertThat(resultOffers.get(1).getDateCreation()).isEqualTo(offer2.getDateCreation());
		assertThat(resultOffers.get(1).getDateUpdate()).isEqualTo(offer2.getDateUpdate());
		assertThat(resultOffers.get(1).isActive()).isEqualTo(offer2.isActive());
	}
	
}
