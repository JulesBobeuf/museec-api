package fr.univartois.butinfo.s5a01.musicmatcher.utils;

import java.util.Date;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.Document;

import fr.univartois.butinfo.s5a01.generatedata.utils.Gender;
import fr.univartois.butinfo.s5a01.generatedata.utils.Instrument;
import fr.univartois.butinfo.s5a01.generatedata.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.generatedata.utils.Region;
import fr.univartois.butinfo.s5a01.generatedata.utils.Role;
import fr.univartois.butinfo.s5a01.generatedata.utils.Skill;

@Document("Users")
public class DummyUser {
	
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
	private Region region;
	private int idBand;
	private Role role;
	private String email;
	private String password;
	private boolean isLocked;
	private Date dateCreation;
	private Date dateUpdate;
	
	public DummyUser() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public int getIdBand() {
		return idBand;
	}

	public void setIdBand(int idBand) {
		this.idBand = idBand;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
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
	
	
}
