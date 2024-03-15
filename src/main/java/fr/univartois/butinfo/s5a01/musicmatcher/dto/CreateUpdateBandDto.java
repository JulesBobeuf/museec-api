package fr.univartois.butinfo.s5a01.musicmatcher.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUpdateBandDto {

	@NotNull
	@Size(min = 2,max = 30)
	private String name;
	@NotNull
	@Size(min = 2,max = 1000)
	private String description;
	@NotNull
	private int owner;
	@NotNull
	private String profilePicture;
	private String videoLink;
	
	public CreateUpdateBandDto() {
		// Should be empty : default constructor
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
		this.videoLink = videoLink;
	}
}
