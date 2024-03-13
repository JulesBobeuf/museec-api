package fr.univartois.butinfo.s5a01.musicmatcher.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Role {
	USER(List.of(Privilege.READ)),
	ADMINISTRATOR(List.of(Privilege.values()));
	
	private List<Privilege> privileges;
	
	private Role(List<Privilege> privileges) {
		this.privileges = privileges;
	}

	public List<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> result = new ArrayList<>();
		this.privileges.forEach(privilege -> 
			result.add(new SimpleGrantedAuthority(String.format("ROLE_%s", privilege.toString())))
		);
		return result;
	}
}
