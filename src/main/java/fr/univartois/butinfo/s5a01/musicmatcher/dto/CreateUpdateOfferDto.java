package fr.univartois.butinfo.s5a01.musicmatcher.dto;

import java.util.Set;

import fr.univartois.butinfo.s5a01.musicmatcher.utils.Country;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Gender;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Instrument;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.MusicStyle;
import fr.univartois.butinfo.s5a01.musicmatcher.utils.Skill;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
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
	private Set<MusicStyle> musicStyles;
	@NotNull
	private Set<Instrument> instruments;
	@NotNull
	private Set<Skill> skills;
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
	public Set<MusicStyle> getMusicStyles() {
		return musicStyles;
	}
	public void setMusicStyles(Set<MusicStyle> musicStyles) {
		this.musicStyles = musicStyles;
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
