package fr.univartois.butinfo.s5a01.musicmatcher.request;

import fr.univartois.butinfo.s5a01.musicmatcher.utils.ImageStyle;

public class ImageGenerationRequest {
	
	private int id;
	
	private ImageStyle style;
	
	private String prompt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ImageStyle getStyle() {
		return style;
	}

	public void setStyle(ImageStyle style) {
		this.style = style;
	}

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

}
