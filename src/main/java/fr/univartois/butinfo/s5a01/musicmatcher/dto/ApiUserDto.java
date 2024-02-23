package fr.univartois.butinfo.s5a01.musicmatcher.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Role;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.History;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;

public class ApiUserDto {

	private int id;
	private String firstName;
	private String lastName;
	private int age;
	private Gender gender;
	private String profilePicture;
	private String description;
	private boolean isLookingForAGroup;
	private Set<Instrument> instruments;
	private Set<Skill> skills;
	private Set<MusicStyle> musicStyles;
	private List<History> history;
	private Country country;	
	private int idBand;
	private Role role;
	private String email;
	private boolean isLocked;
	private LocalDateTime dateCreation;
	private LocalDateTime dateUpdate;
	
	public List<History> getHistory() {
		return history;
	}

	public void setHistory(List<History> history) {
		this.history = history;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setRole(Role role) {
		this.role=role;
	}
	
	public Role getRole() {
		return role;
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

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	public LocalDateTime getDateCreation() {
		return dateCreation;
	}

	public LocalDateTime getDateUpdate() {
		return dateUpdate;
	}

	public void updateDateUpdate() {
		this.dateUpdate = LocalDateTime.now();
	}

	public int getId() {
		return id;
	}

	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}

	public void setDateUpdate(LocalDateTime dateUpdate) {
		this.dateUpdate = dateUpdate;
	}

	public void setId(int id) {
		this.id = id;
	}
}
