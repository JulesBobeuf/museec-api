package fr.univartois.butinfo.s5a01.musicmatcher.document;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.univartois.butinfo.s5a01.musicmatcher.auth.Roles;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Region;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Document
@Builder
public class ApiUser implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Name of the sequence in the database.
	 */
	public static final String SEQUENCE_NAME = "usersequence";
	
	
	@Id
	private int id;
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
	private Region region;
	
	private int idBand;
	@NotNull
	private Roles role;
	@NotNull
	private String email;
	@NotNull
	private String password;
	@NotNull
	private boolean isLocked;
	@NotNull
	private LocalDateTime dateCreation = LocalDateTime.now();
	@NotNull
	private LocalDateTime dateUpdate = LocalDateTime.now();
	@NotNull

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return role.getAuthorities();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setRole(Roles role) {
		this.role=role;
	}
	
	public Roles getRole() {
		return role;
	}
	
	public void setPassword(String newPass) {
		this.password=newPass;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	// dont do this, it's for the sake of the project.
	public void isAnAdmin(boolean isAdmin) {
		if (isAdmin) {
			setRole(Roles.ADMINISTRATOR);
		}
		else {
			setRole(Roles.USER);
		}
	}

	@Override
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !isLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return !isLocked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
