package fr.univartois.butinfo.s5a01.musicmatcher.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ChangePasswordDto {

	@NotNull
	@Size(min = 2,max = 100)
	private String email;
	
	@NotNull
	@Size(min = 2,max = 30)
	private String oldPassword;
	
	@NotNull
	@Size(min = 2,max = 30)
	private String newPassword;
	
	@NotNull
	@Size(min = 2,max = 30)
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
