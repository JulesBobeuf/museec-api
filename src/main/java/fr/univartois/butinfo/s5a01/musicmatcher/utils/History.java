package fr.univartois.butinfo.s5a01.musicmatcher.utils;

import java.io.Serializable;
import java.time.LocalDate;

public class History implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 465061184632927851L;

	private int bandId;
	
	private LocalDate joinDate;
	
	private LocalDate leftDate;

	public int getBandId() {
		return bandId;
	}

	public void setBandId(int bandId) {
		this.bandId = bandId;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getLeftDate() {
		return leftDate;
	}

	public void setLeftDate(LocalDate leftDate) {
		this.leftDate = leftDate;
	}
	
	
}
