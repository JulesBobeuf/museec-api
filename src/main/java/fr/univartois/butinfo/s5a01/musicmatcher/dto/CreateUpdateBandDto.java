package fr.univartois.butinfo.s5a01.musicmatcher.dto;

public class CreateUpdateBandDto {

	private String name;
	private String description;
	private int owner;
	private String profilePicture;
	private String videoLink;
	
	public CreateUpdateBandDto() {
		
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
