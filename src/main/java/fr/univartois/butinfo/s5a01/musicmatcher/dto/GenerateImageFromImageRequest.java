package fr.univartois.butinfo.s5a01.musicmatcher.dto;

public class GenerateImageFromImageRequest {

	private int id;
	
	private String prompt;
	
	private String path;
	
	public GenerateImageFromImageRequest() {
		
	}
	
	public GenerateImageFromImageRequest(int id, String prompt, String path) {
		super();
		this.id = id;
		this.prompt = prompt;
		this.path = path;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
