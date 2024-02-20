package fr.univartois.butinfo.s5a01.musicmatcher.dto;

import jakarta.validation.constraints.NotNull;

public class ChangePasswordDto {

	@NotNull
	private String email;
	
	@NotNull
	private String oldPassword;
	
	@NotNull
	private String newPassword;
	
	@NotNull
	private String confirmationPassword;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmationPassword() {
		return confirmationPassword;
	}

	public void setConfirmationPassword(String confirmationPassword) {
		this.confirmationPassword = confirmationPassword;
	}
	
	
}
