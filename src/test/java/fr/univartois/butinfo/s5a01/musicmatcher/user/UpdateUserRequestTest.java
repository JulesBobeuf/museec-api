package fr.univartois.butinfo.s5a01.musicmatcher.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;
import fr.univartois.butinfo.s5a01.musicmatcher.dto.UpdateUserRequest;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;

@SpringBootTest
class UpdateUserRequestTest {
	
	@Test
	void attributeTest() {
		UpdateUserRequest user = new UpdateUserRequest();
		int age = 20;
		Country country = Country.FRANCE;
		String firstName = "Jules";
		String lastName = "Bobeuf";
		Gender gender = Gender.ANY;
		int idBand = 4;
		Set<Instrument> instruments = Set.of(Instrument.GUITAR, Instrument.PIANO);
		Set<MusicStyle> musicStyles = Set.of(MusicStyle.ACOUSTIC, MusicStyle.FOLK);
		Set<Skill> skills = Set.of(Skill.MUSIC_THEORY);
		String description = "this is my wonderful description.";

		user.setAge(age);
		user.setCountry(country);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		
		user.setInstruments(instruments);
		user.setMusicStyles(musicStyles);
		user.setDescription(description);
		user.setLookingForAGroup(false);
		user.setSkills(skills);
		
		assertEquals(user.getAge(), age);
		assertThat(country).isEqualTo(user.getCountry());
		assertThat(firstName).isEqualTo(user.getFirstName());

		assertThat(lastName).isEqualTo(user.getLastName());
		assertThat(description).isEqualTo(user.getDescription());
		assertThat(gender).isEqualTo(user.getGender());

		assertTrue(user.getInstruments().containsAll(instruments));
		assertTrue(user.getMusicStyles().containsAll(musicStyles));
		assertTrue(user.getSkills().containsAll(skills));
		
		assertFalse(user.isLookingForAGroup());
	}

}
