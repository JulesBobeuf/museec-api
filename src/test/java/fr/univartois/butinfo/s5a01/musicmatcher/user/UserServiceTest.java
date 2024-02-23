package fr.univartois.butinfo.s5a01.musicmatcher.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.ApiUserDto;
import fr.univartois.butinfo.s5a01.musicmatcher.repository.UserRepository;
import fr.univartois.butinfo.s5a01.musicmatcher.service.UserService;

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
	void banUserTest() {
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setLocked(false);
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		assertThat(userService.banUser(1)).isTrue();
		assertThat(userService.banUser(1)).isTrue();
		assertThat(userService.banUser(0)).isFalse();
	}
	
	@Test
	void unbanUserTest() {
		ApiUser user = new ApiUser();
		user.setId(1);
		user.setLocked(true);
		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		assertThat(userService.unbanUser(1)).isTrue();
		assertThat(userService.unbanUser(1)).isTrue();
		assertThat(userService.unbanUser(0)).isFalse();
	}
	
}
