package fr.univartois.butinfo.s5a01.musicmatcher.dto;

public class CreateUserRequest {

	private String username;
	private String email;
	private String password;
	// this parameter is only here to demonstrate the purpose of the admin role
	// do not do this in a real world scenario haha
	private boolean isAdmin;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	
}
