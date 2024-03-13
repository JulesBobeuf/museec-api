package fr.univartois.butinfo.s5a01.musicmatcher.dto;

import java.util.Set;

import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public class CreateUpdateOfferDto {
	
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
	
	public CreateUpdateOfferDto() {
		
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
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
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

}
