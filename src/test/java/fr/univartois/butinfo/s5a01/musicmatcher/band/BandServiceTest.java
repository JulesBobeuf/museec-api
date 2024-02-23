package fr.univartois.butinfo.s5a01.musicmatcher.band;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.document.Band;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUpdateBandDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.BandRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.BandService;

@SpringBootTest
public class BandServiceTest {
	
	@Autowired
	private BandService bandService;
	
	@MockBean
	private BandRepository bandRepository;
	
	@MockBean
	private UserRepository userRepository;


	@Test
	public void TestUpdateBand() {
		String email = "toto@example.com";

		CreateUpdateBandDto bandDto = new CreateUpdateBandDto();
		bandDto.setName("hey");
		bandDto.setDescription("hey");
		bandDto.setOwner(0); //change the owner, so executing the request twice won't work
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
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		when(bandRepository.findById(1)).thenReturn(Optional.of(band));
		
		assertThat(bandService.updateBand(1, bandDto, email)).isEqualTo(true);
		
        assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	assertThat(bandService.updateBand(1, bandDto, email)).withFailMessage("Forbidden");
            }
        });
        
        user.setRole(Role.ADMINISTRATOR); // an admin won't get a forbidden error.
		assertThat(bandService.updateBand(1, bandDto, email)).isEqualTo(true);
        
	}
	
	@Test
	public void TestDeleteBand() {
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
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		when(bandRepository.findById(1)).thenReturn(Optional.of(band));
		
		assertThat(bandService.deleteBand(1, email)).isEqualTo(true);
		
		user.setId(0);

        assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	assertThat(bandService.deleteBand(1, email)).withFailMessage("Forbidden");
            }
        });
        
        user.setRole(Role.ADMINISTRATOR);
		assertThat(bandService.deleteBand(1, email)).isEqualTo(true);
        
	}
}
