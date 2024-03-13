package fr.univartois.butinfo.s5a01.musicmatcher.document;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Document
@Builder
public class Offer {

	/**
	 * Name of the sequence in the database.
	 */
	public static final String SEQUENCE_NAME = "offersequence";
	
	@Id
	private int id;
	@NotNull
	@Size(min = 2,max = 30)
	private String name;
	@NotNull
	@Size(min = 2,max = 1000)
	private String description;
	@NotNull
	private Set<String> musicStyles;
	@NotNull
	private Set<String> instruments;
	@NotNull
	private Set<String> skills;
	@NotNull
	private Country country;
	@NotNull
	@Max(100)
	@Min(18)
	private int ageMin;
	@NotNull
	@Min(18)
	@Max(100)
	private int ageMax;
	@NotNull
	private Gender gender;
	@NotNull
	private int idBand;
	@NotNull
	private Set<Integer> usersThatRejected;
	@NotNull
	private Set<Integer> awaitingMembers;
	@NotNull
	@DateTimeFormat
	private LocalDateTime dateCreation;
	@NotNull
	@DateTimeFormat
	private LocalDateTime dateUpdate;
	@NotNull
	private boolean isActive;
	
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
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
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
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	
}
