package fr.univartois.butinfo.s5a01.musicmatcher.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ApiUserDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.AuthenticationRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ChangePasswordDto;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.CreateUserRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.AuthService;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;

@SpringBootTest
public class AuthServiceTest {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	void loadUserByUsernameWorksTest() {
		String email = "toto@example.com";

		ApiUser user = new ApiUser();
		user.setEmail(email);
		user.setId(1);

		when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		UserDetails user2 = authService.loadUserByUsername(email);
		assertEquals(email, user2.getUsername());
	}
	
	@Test
	void loadUserByUsernameDoesntWorkTest() {

		assertThrows(UsernameNotFoundException.class, new Executable() {
            
            @Override
            public void execute() throws Throwable {
        		String email = "toto@example.com";
				when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        		assertThat(authService.loadUserByUsername(email)).isEqualTo(null);
        	}
        });
	}
	
	@Test
	void changePasswordWorksTest() {
		ChangePasswordDto request = new ChangePasswordDto();
		request.setEmail("toto@example.com");
		request.setOldPassword("toto");
		request.setNewPassword("toto2");
		request.setConfirmationPassword("toto2");
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(0);
		apiUser.setEmail("toto@example.com");
		apiUser.setPassword(passwordEncoder.encode("toto"));
		
		Optional<ApiUser> optionalUser = Optional.of(apiUser);
		
		when(userRepository.findByEmail("toto@example.com")).thenReturn(optionalUser);
		
		assertThat(authService.changePassword(request)).isEqualTo("Password changed sucessfully");
	}
	
	@Test
    void changePasswordWrongPasswordTest() {
         
        assertThrows(IllegalArgumentException.class, new Executable() {
             
            @Override
            public void execute() throws Throwable {
        		ChangePasswordDto request = new ChangePasswordDto();
        		request.setEmail("toto@example.com");
        		request.setOldPassword("yoyo");
        		request.setNewPassword("toto2");
        		request.setConfirmationPassword("toto2");
        		
        		ApiUser apiUser = new ApiUser();
        		apiUser.setId(0);
        		apiUser.setEmail("toto@example.com");
        		apiUser.setPassword(passwordEncoder.encode("toto"));
        		
        		Optional<ApiUser> optionalUser = Optional.of(apiUser);
        		
        		when(userRepository.findByEmail("toto@example.com")).thenReturn(optionalUser);
        		
        		assertThat(authService.changePassword(request)).isEqualTo("Password changed sucessfully");
        	}
        });
    }
	
	
	@Test
    void changePasswordWrongConfirmationPasswordTest() {
         
        assertThrows(IllegalArgumentException.class, new Executable() {
             
            @Override
            public void execute() throws Throwable {
        		ChangePasswordDto request = new ChangePasswordDto();
        		request.setEmail("toto@example.com");
        		request.setOldPassword("yoyo");
        		request.setNewPassword("toto2");
        		request.setConfirmationPassword("toto3");
        		
        		ApiUser apiUser = new ApiUser();
        		apiUser.setId(0);
        		apiUser.setEmail("toto@example.com");
        		apiUser.setPassword(passwordEncoder.encode("toto"));
        		
        		Optional<ApiUser> optionalUser = Optional.of(apiUser);
        		
        		when(userRepository.findByEmail("toto@example.com")).thenReturn(optionalUser);
        		
        		assertThat(authService.changePassword(request)).isEqualTo("Password changed sucessfully");
        	}
        });
    }
	
	@Test
	void loginTest() {
		String email = "toto@example.com";
		String password = "string";
		AuthenticationRequest request = new AuthenticationRequest();
		request.setEmail(email);
		request.setPassword(password);
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(0);
		apiUser.setEmail("toto@example.com");
		apiUser.setPassword(passwordEncoder.encode(password));
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(apiUser));
		assertThat(authService.login(request)).isInstanceOf(String.class);
		
		apiUser.setPassword("sdcnsdkcsn");
		assertThat(authService.login(request)).isEqualTo("Can't login : wrong credentials");
		
	}
	
	@Test
	void createUserTest() {
		String email = "toto@example.com";
		String password = "string";
		
		CreateUserRequest request = new CreateUserRequest();
		
		request.setEmail(email);
		request.setPassword(password);
		request.setConfirmPassword(password);
		request.setAge(20);
		request.setCountry(Country.FRANCE);
		request.setFirstName("Jules");
		request.setLastName("Bobeuf");
		request.setGender(Gender.MALE);
		request.setInstruments(Set.of(Instrument.GUITAR, Instrument.PIANO));
		request.setMusicStyles(Set.of(MusicStyle.ACOUSTIC, MusicStyle.FOLK));
		request.setDescription("this is my wonderful description.");
		request.setLookingForAGroup(false);
		request.setSkills(Set.of(Skill.MUSIC_THEORY));
		request.setProfilePicture("./mypfp");
		request.setRole(Role.USER);
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		assertThat(authService.createUser(request)).isTrue();
	}
	
	@Test
	void createUserDoesntWorkTest() {
		String email = "toto@example.com";
		String password = "string";
		
		CreateUserRequest request = new CreateUserRequest();
		
		request.setEmail(email);
		request.setPassword(password);
		request.setConfirmPassword("sqdqsdq");
		request.setAge(20);
		request.setCountry(Country.FRANCE);
		request.setFirstName("Jules");
		request.setLastName("Bobeuf");
		request.setGender(Gender.MALE);
		request.setInstruments(Set.of(Instrument.GUITAR, Instrument.PIANO));
		request.setMusicStyles(Set.of(MusicStyle.ACOUSTIC, MusicStyle.FOLK));
		request.setDescription("this is my wonderful description.");
		request.setLookingForAGroup(false);
		request.setSkills(Set.of(Skill.MUSIC_THEORY));
		
		ApiUser apiUser = new ApiUser();
		apiUser.setId(0);
		apiUser.setEmail(email);
		apiUser.setPassword(passwordEncoder.encode(password));
		
		when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(apiUser));
		
		// confirm password != password
		assertThat(authService.createUser(request)).isFalse();
		
		request.setConfirmPassword(password);
		
		// user already exists
		assertThat(authService.createUser(request)).isFalse();

	}
}
