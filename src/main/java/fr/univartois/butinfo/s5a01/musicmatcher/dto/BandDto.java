package fr.univartois.butinfo.s5a01.musicmatcher.dto;

import java.time.LocalDateTime;

public class BandDto {
	
	private int id;
	private String name;
	private String description;
	private int owner;
	private String profilePicture;
	private int videoLink;
	private LocalDateTime dateCreation;
	private LocalDateTime dateUpdate;
	
	public BandDto() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public int getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(int videoLink) {
		this.videoLink = videoLink;
	}
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	public LocalDateTime getDateUpdate() {
		return dateUpdate;
	}
	public void setDateUpdate(LocalDateTime dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	

}