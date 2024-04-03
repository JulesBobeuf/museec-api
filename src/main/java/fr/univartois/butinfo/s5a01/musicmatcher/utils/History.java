package fr.univartois.butinfo.s5a01.musicmatcher.utils;

import java.io.Serializable;
import java.time.LocalDateTime;

public class History implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 465061184632927851L;

	private int bandId;
	
	private LocalDateTime joinDate;
	
	private LocalDateTime leftDate;

	public int getBandId() {
		return bandId;
	}

	public void setBandId(int bandId) {
		this.bandId = bandId;
	}

	public LocalDateTime getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDateTime joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDateTime getLeftDate() {
		return leftDate;
	}

	public void setLeftDate(LocalDateTime leftDate) {
		this.leftDate = leftDate;
	}
	
}
