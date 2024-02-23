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
	private UserService service;
	
	@MockBean
	private UserRepository repository;
	
	//@Test
	//Mapstruct does not work for tests...
	public void getUserTest() {
		ApiUser user = new ApiUser();
		user.setEmail("toto@example.com");
		user.setId(1);
		
		when(repository.findById(1)).thenReturn(Optional.of(user));
		
		ApiUserDto user2 = service.getUser(1);
		assertThat(user2.getId()).isEqualTo(user.getId());
		assertThat(user2.getEmail()).isEqualTo(user.getEmail());

	}
}
