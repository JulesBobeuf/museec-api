package fr.univartois.butinfo.s5a01.musicmatcher.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ApiUserDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.UpdateUserRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.UserService;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;

@SpringBootTest()
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@MockBean
	private UserRepository userRepository;
	
	//@Test
	//Mapstruct does not work for tests...
	public void getUserTest() {
		ApiUser user = new ApiUser();
		user.setEmail("toto@example.com");
		user.setId(1);
		
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		
		ApiUserDto user2 = userService.getUser(1);
		assertThat(user2.getId()).isEqualTo(user.getId());
		assertThat(user2.getEmail()).isEqualTo(user.getEmail());

	}
	
	@Test
	public void TestUpdateUser() {
		String email = "toto@example.com";
		
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setEmail(email);
		user.setIdBand(1);
		user.setAge(18);
		user.setRole(Role.USER);
		
		UpdateUserRequest request = new UpdateUserRequest();
		
		request.setAge(19);
		request.setCountry(Country.FRANCE);
		request.setDescription("test desc");
		request.setLookingForAGroup(true);
		request.setGender(Gender.MALE);
		request.setInstruments(Set.of(Instrument.GUITAR, Instrument.CELLO));
		request.setSkills(Set.of(Skill.AUDIO_MIXING, Skill.MUSIC_THEORY));
		request.setMusicStyles(Set.of(MusicStyle.ACOUSTIC, MusicStyle.ROCK));
		request.setFirstName("a name");
		request.setLastName("a last name");
		request.setProfilePicture("/images/test.png");
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));
		
		assertThat(userService.updateUser(1, request, email)).isTrue();
		
        assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	assertThat(userService.updateUser(0, request, "toto2@example.com")).withFailMessage("Forbidden");
            }
        });
        
        user.setRole(Role.ADMINISTRATOR); // an admin won't get a forbidden error.
		assertThat(userService.updateUser(0, request, "toto2@example.com")).isTrue();
        
	}
	
	@Test
	public void TestDeleteUser() {
		String email = "toto@example.com";
		
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setEmail(email);
		user.setIdBand(1);
		
		ApiUser user2 = new ApiUser();
		user2.setId(2);
		user2.setEmail("toto2@€xample.org");
		user2.setIdBand(2);
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.findById(2)).thenReturn(Optional.of(user2));

		assertThat(userService.deleteUser(1, email)).isEqualTo(true);
		
		user.setId(0);

        assertThrows(IllegalArgumentException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
            	assertThat(userService.deleteUser(1, "toto313@€xample.org")).withFailMessage("Forbidden");
            }
        });
        
        user.setRole(Role.ADMINISTRATOR);
		assertThat(userService.deleteUser(1, "toto313@€xample.org")).isEqualTo(true);
        
	}
}
