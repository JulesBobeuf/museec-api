package fr.univartois.butinfo.s5a01.musicmatcher.band;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.document.DatabaseSequence;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.BandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateBandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.BandService;
import fr.univartois.butinfo.s5a01.musicmatcher.service.SequenceGeneratorService;

@SpringBootTest
class BandServiceTest {
	
	@Autowired
	private BandService bandService;
	
	@MockBean
	private BandRepository bandRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	@MockBean
	private SequenceGeneratorService sequenceService;
	
	@Test
	void testGetBand() {
		LocalDateTime now = LocalDateTime.now();

		Band band = new Band();
		band.setName("hey");
		band.setDescription("the coolest description");
		band.setOwner(1);
		band.setProfilePicture("./img.png");
		band.setVideoLink("https://youtube.com");
		band.setId(1);
		band.setDateCreation(now);
		band.setDateUpdate(now);
		
		when(bandRepository.findById(1)).thenReturn(Optional.of(band));
		
		BandDto resultBand = bandService.getBand(1);
		assertThat(resultBand.getName()).isEqualTo(band.getName());
		assertThat(resultBand.getDescription()).isEqualTo(band.getDescription());
		assertThat(resultBand.getOwner()).isEqualTo(band.getOwner());
		assertThat(resultBand.getProfilePicture()).isEqualTo(band.getProfilePicture());
		assertThat(resultBand.getVideoLink()).isEqualTo(band.getVideoLink());
		assertThat(resultBand.getId()).isEqualTo(band.getId());
		assertThat(resultBand.getDateCreation()).isEqualTo(band.getDateCreation());
		assertThat(resultBand.getDateUpdate()).isEqualTo(band.getDateUpdate());
		
		BandDto resultEmptyBand = bandService.getBand(986713);
		assertNull(resultEmptyBand);
	}
	
	@Test
	void testGetAllBands() {
		LocalDateTime now = LocalDateTime.now();

		Band band = new Band();
		band.setName("hey");
		band.setDescription("a great description");
		band.setOwner(1);
		band.setProfilePicture("./img.png");
		band.setVideoLink("https://youtube.com");
		band.setId(1);
		band.setDateCreation(now);
		band.setDateUpdate(now);
		
		LocalDateTime now2 = LocalDateTime.now();
		
		Band band2 = new Band();
		band2.setName("hey2");
		band2.setDescription("a wonderful description");
		band2.setOwner(2);
		band2.setProfilePicture("./img.png");
		band2.setVideoLink("https://youtube.com");
		band2.setId(2);
		band2.setDateCreation(now2);
		band2.setDateUpdate(now2);
		
		when(bandRepository.findAll()).thenReturn(List.of(band, band2));
		
		List<BandDto> resultBands = bandService.getAllBands();

		assertThat(resultBands.get(0).getName()).isEqualTo(band.getName());
		assertThat(resultBands.get(0).getDescription()).isEqualTo(band.getDescription());
		assertThat(resultBands.get(0).getOwner()).isEqualTo(band.getOwner());
		assertThat(resultBands.get(0).getProfilePicture()).isEqualTo(band.getProfilePicture());
		assertThat(resultBands.get(0).getVideoLink()).isEqualTo(band.getVideoLink());
		assertThat(resultBands.get(0).getId()).isEqualTo(band.getId());
		assertThat(resultBands.get(0).getDateCreation()).isEqualTo(band.getDateCreation());
		assertThat(resultBands.get(0).getDateUpdate()).isEqualTo(band.getDateUpdate());
		
		assertThat(resultBands.get(1).getName()).isEqualTo(band2.getName());
		assertThat(resultBands.get(1).getDescription()).isEqualTo(band2.getDescription());
		assertThat(resultBands.get(1).getOwner()).isEqualTo(band2.getOwner());
		assertThat(resultBands.get(1).getProfilePicture()).isEqualTo(band2.getProfilePicture());
		assertThat(resultBands.get(1).getVideoLink()).isEqualTo(band2.getVideoLink());
		assertThat(resultBands.get(1).getId()).isEqualTo(band2.getId());
		assertThat(resultBands.get(1).getDateCreation()).isEqualTo(band2.getDateCreation());
		assertThat(resultBands.get(1).getDateUpdate()).isEqualTo(band2.getDateUpdate());
	}
	
	@Test
	void testCreateBand() {
		CreateUpdateBandDto band = new CreateUpdateBandDto();
		band.setName("hey");
		band.setDescription("the coolest description");
		band.setOwner(1);
		band.setProfilePicture("./img.png");
		band.setVideoLink("https://youtube.com");
		
		String email = "toto@example.com";
		String email2 = "admin@example.com";
		
		ApiUser user = new ApiUser();
		user.setEmail(email);
		user.setId(1);
		user.setIdBand(-1);
		user.setRole(Role.USER);

		
		ApiUser user2 = new ApiUser();
		user2.setEmail(email2);
		user2.setId(2);
		user2.setRole(Role.ADMINISTRATOR);
		user2.setIdBand(-1);
		
		// repo returns null, so false
		assertThat(bandService.createBand(band, email)).isFalse();
		
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		when(userRepository.findByEmail(email2)).thenReturn(Optional.of(user2));
		
		int i = 0;
		when(sequenceService.generateSequence(anyString())).thenReturn(++i);
		
		assertThat(bandService.createBand(band, email)).isTrue();
		assertThat(bandService.createBand(band, email2)).isTrue();
		
		user.setId(4);

		// An admin can create a band for anyone. a User can create a band only for himself
		assertThat(bandService.createBand(band, email)).isFalse();
	}


	@Test
	void testUpdateBand() {
		String email = "toto@example.com";

		CreateUpdateBandDto bandDto = new CreateUpdateBandDto();
		bandDto.setName("hey");
		bandDto.setDescription("hey");
		bandDto.setOwner(1);
		bandDto.setProfilePicture("./img.png");
		bandDto.setVideoLink("https://youtube.com");
		
		Band band = new Band();
		band.setName("hey");
		band.setDescription("hey");
		band.setOwner(1);
		band.setProfilePicture("./img.png");
		band.setVideoLink("https://youtube.com");
		band.setId(1);
		band.setDateCreation(LocalDateTime.now());
		band.setDateUpdate(LocalDateTime.now());
		
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setEmail(email);
		user.setIdBand(1);
		
		// repo returns nothing so false
		assertThat(bandService.updateBand(1, bandDto, email)).isFalse();
		
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(bandRepository.findById(1)).thenReturn(Optional.of(band));
		
		assertThat(bandService.updateBand(1, bandDto, email)).isTrue();
		
		band.setOwner(1093871);
		
        assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	assertThat(bandService.updateBand(1, bandDto, email));
            }
        });
        
        user.setRole(Role.ADMINISTRATOR); // an admin won't get a forbidden error.
		assertThat(bandService.updateBand(1, bandDto, email)).isTrue();
		
		bandDto.setOwner(67169);
		// user does not exists, so the following is false.
		assertThat(bandService.updateBand(1, bandDto, email)).isFalse();

	}
	
	@Test
	void testDeleteBand() {
		String email = "toto@example.com";
		
		Band band = new Band();
		band.setName("hey");
		band.setDescription("hey");
		band.setOwner(1);
		band.setProfilePicture("./img.png");
		band.setVideoLink("https://youtube.com");
		band.setId(1);
		band.setDateCreation(LocalDateTime.now());
		band.setDateUpdate(LocalDateTime.now());
		
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setEmail(email);
		user.setIdBand(1);
		
		// repo returns nothing, so should be false
		assertThat(bandService.deleteBand(1, email)).isFalse();
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		when(bandRepository.findById(1)).thenReturn(Optional.of(band));
		
		assertThat(bandService.deleteBand(1, email)).isTrue();
		
		user.setId(0);

        assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	assertThat(bandService.deleteBand(1, email));
            }
        });
        
        user.setRole(Role.ADMINISTRATOR);
		assertThat(bandService.deleteBand(1, email)).isTrue();
        
	}
}
