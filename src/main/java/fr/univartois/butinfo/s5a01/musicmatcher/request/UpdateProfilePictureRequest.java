package fr.univartois.butinfo.s5a01.musicmatcher.request;

public class UpdateProfilePictureRequest {

	private int userId;
	
	private String path;
	
	public UpdateProfilePictureRequest() {
		super();
	}

	public UpdateProfilePictureRequest(int userId, String path) {
		super();
		this.userId = userId;
		this.path = path;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
