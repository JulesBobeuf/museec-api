package fr.univartois.butinfo.s5a01.musicmatcher.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Roles {
	USER(List.of(Privileges.READ)),
	ADMINISTRATOR(List.of(Privileges.values()));
	
	private List<Privileges> privileges;
	
	private Roles(List<Privileges> privileges) {
		this.privileges = privileges;
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> result = new ArrayList<>();
		this.privileges.forEach((privilege) -> {
			result.add(new SimpleGrantedAuthority(String.format("ROLE_%s", privilege.toString())));
		});
		return result;
	}
}
