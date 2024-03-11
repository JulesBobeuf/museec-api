package fr.univartois.butinfo.s5a01.musicmatcher.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
class UserServiceTest {

	@Autowired
	private UserService userService;

	@MockBean
	private UserRepository userRepository;

	@Test
	void getUserTest() {
		ApiUser user = new ApiUser();
		user.setEmail("toto@example.com");
		user.setId(1);

		when(userRepository.findById(1)).thenReturn(Optional.of(user));
		when(userRepository.findById(10)).thenReturn(Optional.empty());

		ApiUserDto user2 = userService.getUser(1);
		assertThat(user2.getId()).isEqualTo(user.getId());
		assertThat(user2.getEmail()).isEqualTo(user.getEmail());
		
		ApiUserDto user10 = userService.getUser(10);
		assertNull(user10);

	}

	@Test
	void getAllUsersTest() {
		LocalDateTime now = LocalDateTime.now();

		ApiUser user0 = new ApiUser();
		user0.setEmail("toto0@example.com");
		user0.setId(2);

		ApiUser user1 = new ApiUser();
		user1.setEmail("toto@example.com");
		user1.setId(1);
		user1.setAge(20);
		user1.setCountry(Country.FRANCE);
		user1.setFirstName("Jules");
		user1.setLastName("Bobeuf");
		user1.setDateCreation(now);
		user1.setDateUpdate(now);
		user1.setGender(Gender.MALE);
		user1.setIdBand(4);
		user1.setInstruments(Set.of(Instrument.GUITAR, Instrument.PIANO));
		user1.setMusicStyles(Set.of(MusicStyle.ACOUSTIC, MusicStyle.FOLK));
		user1.setDescription("this is my wonderful description.");
		user1.setLookingForAGroup(false);
		user1.setSkills(Set.of(Skill.MUSIC_THEORY));

		ApiUser user2 = new ApiUser();
		user1.setEmail("toto2@example.com");
		user1.setId(2);

		ApiUser user3 = new ApiUser();
		user1.setEmail("toto3@example.com");
		user1.setId(3);

		when(userRepository.findAll()).thenReturn(List.of(user0, user1, user2, user3));

		List<ApiUserDto> users = userService.getUsers();
		assertThat(user1.getId()).isEqualTo(users.get(1).getId());
		assertThat(user1.getEmail()).isEqualTo(users.get(1).getEmail());
		assertThat(user1.getAge()).isEqualTo(users.get(1).getAge());

		assertThat(user1.getCountry().getCountryName()).isEqualTo(users.get(1).getCountry().getCountryName());
		assertThat(user1.getFirstName()).isEqualTo(users.get(1).getFirstName());
		assertThat(user1.getLastName()).isEqualTo(users.get(1).getLastName());

		assertThat(user1.getDateCreation()).isEqualTo(users.get(1).getDateCreation());
		assertThat(user1.getDateUpdate()).isEqualTo(users.get(1).getDateUpdate());
		assertThat(user1.getGender()).isEqualTo(users.get(1).getGender());

		assertTrue(users.get(1).getInstruments().containsAll(Set.of(Instrument.GUITAR, Instrument.PIANO)));
		assertTrue(users.get(1).getMusicStyles().containsAll(Set.of(MusicStyle.ACOUSTIC, MusicStyle.FOLK)));
		assertTrue(users.get(1).getSkills().contains(Skill.MUSIC_THEORY));

		assertThat(user1.getDescription()).isEqualTo(users.get(1).getDescription());
		assertThat(user1.isLookingForAGroup()).isEqualTo(users.get(1).isLookingForAGroup());
		assertThat(user1.getIdBand()).isEqualTo(users.get(1).getIdBand());

		assertThat(user2.getId()).isEqualTo(users.get(2).getId());
		assertThat(user2.getEmail()).isEqualTo(users.get(2).getEmail());

		assertThat(user3.getId()).isEqualTo(users.get(3).getId());
		assertThat(user3.getEmail()).isEqualTo(users.get(3).getEmail());
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

	@Test
	void TestUpdateUser() {
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
	void TestDeleteUser() {
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
