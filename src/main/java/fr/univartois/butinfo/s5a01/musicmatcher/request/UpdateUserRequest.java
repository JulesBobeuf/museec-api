package fr.univartois.butinfo.s5a01.musicmatcher.request;

import java.util.Set;

import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UpdateUserRequest {

	@NotNull
	@Size(min = 2,max = 30)
	private String firstName;
	@NotNull
	@Size(min = 2,max = 30)
	private String lastName;
	@NotNull
	@Min(18)
	@Max(100)
	private int age;
	@NotNull
	private Gender gender;
	@NotNull
	private String profilePicture;
	@NotNull
	@Size(min = 2,max = 1000)
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
	
	public UpdateUserRequest() {
		// Should be empty : default constructor
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
	
}
