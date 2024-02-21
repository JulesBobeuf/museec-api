package fr.univartois.butinfo.s5a01.musicmatcher.auth;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ChangePasswordDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.AuthService;

@SpringBootTest
public class AuthServiceTest {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@MockBean
	private UserRepository userRepository;
	
	@Test
	public void changePasswordWorksTest() {
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
    public void changePasswordWrongPasswordTest() {
         
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
    public void changePasswordWrongConfirmationPasswordTest() {
         
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
}
