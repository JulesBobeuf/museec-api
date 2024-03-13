package fr.univartois.butinfo.s5a01.musicmatcher.document;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Document
public class Band {
	
	/**
	 * Name of the sequence in the database.
	 */
	public static final String SEQUENCE_NAME = "bandsequence";
	
	@Id
	private int id;
	@NotNull
	@Size(min = 2,max = 30)
	private String name;
	@NotNull
	@Size(min = 2,max = 1000)
	private String description;
	@NotNull
	@Size(min = 2,max = 30)
	private int owner;
	@NotNull
	private String profilePicture;
	@NotNull
	private String videoLink;
	@NotNull
	@DateTimeFormat
	private LocalDateTime dateCreation;
	@NotNull
	@DateTimeFormat
	private LocalDateTime dateUpdate;
	
	public Band() {
		
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
	public String getVideoLink() {
		return videoLink;
	}
	public void setVideoLink(String videoLink) {
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
