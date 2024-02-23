package fr.univartois.butinfo.s5a01.musicmatcher.document;

import java.util.Date;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Document
@Builder
public class Offer {

	@Id
	private int id;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private Set<String> musicStyles;
	@NotNull
	private Set<String> instruments;
	@NotNull
	private Set<String> skills;
	@NotNull
	private String region;
	@NotNull
	private int ageMin;
	@NotNull
	private int ageMax;
	@NotNull
	private String gender;
	@NotNull
	private int idBand;
	@NotNull
	private Set<Integer> usersThatRejected;
	@NotNull
	private Set<Integer> awaitingMembers;
	@NotNull
	private Date dateCreation;
	@NotNull
	private Date dateUpdate;
	
	public Offer() {
		
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
	public Set<String> getMusicStyles() {
		return musicStyles;
	}
	public void setMusicStyles(Set<String> musicStyles) {
		this.musicStyles = musicStyles;
	}
	public Set<String> getInstruments() {
		return instruments;
	}
	public void setInstruments(Set<String> instruments) {
		this.instruments = instruments;
	}
	public Set<String> getSkills() {
		return skills;
	}
	public void setSkills(Set<String> skills) {
		this.skills = skills;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public int getAgeMin() {
		return ageMin;
	}
	public void setAgeMin(int ageMin) {
		this.ageMin = ageMin;
	}
	public int getAgeMax() {
		return ageMax;
	}
	public void setAgeMax(int ageMax) {
		this.ageMax = ageMax;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getIdBand() {
		return idBand;
	}
	public void setIdBand(int idBand) {
		this.idBand = idBand;
	}
	public Set<Integer> getUsersThatRejected() {
		return usersThatRejected;
	}
	public void setUsersThatRejected(Set<Integer> usersThatRejected) {
		this.usersThatRejected = usersThatRejected;
	}
	public Set<Integer> getAwaitingMembers() {
		return awaitingMembers;
	}
	public void setAwaitingMembers(Set<Integer> awaitingMembers) {
		this.awaitingMembers = awaitingMembers;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Date getDateUpdate() {
		return dateUpdate;
	}
	public void setDateUpdate(Date dateUpdate) {
		this.dateUpdate = dateUpdate;
	}
	
	// method which allows to add Awaiting members
	public Set<Integer> addAwaitingMembers(int idAwaitingMember) {
		this.awaitingMembers.add(idAwaitingMember);
		return awaitingMembers;
	}
	
	// method which allows to add users in the rejected list
	public Set<Integer> addUsersThatRejected(int idUsersThatRejected) {
		this.usersThatRejected.add(idUsersThatRejected);
		return usersThatRejected;
	}

}
