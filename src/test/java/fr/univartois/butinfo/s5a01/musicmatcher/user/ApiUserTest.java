package fr.univartois.butinfo.s5a01.musicmatcher.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Privilege;
import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.document.ApiUser;

@SpringBootTest()
public class ApiUserTest {

	@Test
	void testUserDetailsInterface() {
		String email = "toto@€xample.org";
		String password = "mypass";
		
		ApiUser apiUser = new ApiUser();
		apiUser.setEmail(email);
		apiUser.setPassword(password);
		apiUser.setRole(Role.USER);
		apiUser.setLocked(false);
		
		assertTrue(apiUser.isEnabled());
		assertTrue(apiUser.isAccountNonLocked());
		assertTrue(apiUser.isAccountNonExpired());
		assertFalse(apiUser.isCredentialsNonExpired());
		
		assertEquals(email, apiUser.getEmail());
		assertEquals(password, apiUser.getPassword());
		assertEquals(Role.USER, apiUser.getRole());
		assertTrue(apiUser.getAuthorities().contains(new SimpleGrantedAuthority(String.format("ROLE_%s", Privilege.READ.toString()))));
		
		// test administrator privileges
		apiUser.setRole(Role.ADMINISTRATOR);
		assertEquals(Role.ADMINISTRATOR, apiUser.getRole());
		
		assertTrue(apiUser.getAuthorities().contains(new SimpleGrantedAuthority(String.format("ROLE_%s", Privilege.READ.toString()))));
		assertTrue(apiUser.getAuthorities().contains(new SimpleGrantedAuthority(String.format("ROLE_%s", Privilege.ADD.toString()))));
		assertTrue(apiUser.getAuthorities().contains(new SimpleGrantedAuthority(String.format("ROLE_%s", Privilege.UPDATE.toString()))));
		assertTrue(apiUser.getAuthorities().contains(new SimpleGrantedAuthority(String.format("ROLE_%s", Privilege.DELETE.toString()))));
	
		apiUser.setLocked(true);
		assertFalse(apiUser.isEnabled());

	}
	
}
