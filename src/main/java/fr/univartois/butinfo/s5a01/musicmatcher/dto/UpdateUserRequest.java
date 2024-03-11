package fr.univartois.butinfo.s5a01.musicmatcher.dto;

import java.util.Set;

import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;
import jakarta.validation.constraints.NotNull;

public class UpdateUserRequest {

	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private int age;
	@NotNull
	private Gender gender;
	@NotNull
	private String profilePicture;
	@NotNull
	private String description;
	@NotNull
	private boolean isLookingForAGroup;
	@NotNull
	private Set<Instrument> instruments;
	@NotNull
	private Set<Skill> skills;
	@NotNull
	private Set<MusicStyle> musicStyles;
	@NotNull
	private Country country;
	@NotNull
	private int idBand;
	
	public UpdateUserRequest() {
		
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isLookingForAGroup() {
		return isLookingForAGroup;
	}
	public void setLookingForAGroup(boolean isLookingForAGroup) {
		this.isLookingForAGroup = isLookingForAGroup;
	}
	public Set<Instrument> getInstruments() {
		return instruments;
	}
	public void setInstruments(Set<Instrument> instruments) {
		this.instruments = instruments;
	}
	public Set<Skill> getSkills() {
		return skills;
	}
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	public Set<MusicStyle> getMusicStyles() {
		return musicStyles;
	}
	public void setMusicStyles(Set<MusicStyle> musicStyles) {
		this.musicStyles = musicStyles;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}

	public int getIdBand() {
		return idBand;
	}

	public void setIdBand(int idBand) {
		this.idBand = idBand;
	}
	
	
}
