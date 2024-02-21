package fr.univartois.butinfo.s5a01.musicmatcher.document;

import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Roles;
import jakarta.validation.constraints.NotNull;

@Document
public class ApiUser implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String username;
	@NotNull
	private String email;
	@NotNull
	private String password;
	
	@NotNull
	private Roles role = Roles.USER;
	


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return isEnabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isOwner() {
		return isOwner;
	}

	public void setOwner(boolean isOwner) {
		this.isOwner = isOwner;
	}
	
	public void setRole(Roles role) {
		this.role=role;
	}
	
	public Roles getRole() {
		return role;
	}
	
	public void setPassword(String newPass) {
		this.password=newPass;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	
	// dont do this, it's for the sake of the project.
	public void isAnAdmin(boolean isAdmin) {
		if (isAdmin) {
			setRole(Roles.ADMINISTRATOR);
		}
		else {
			setRole(Roles.USER);
		}
	}

}
