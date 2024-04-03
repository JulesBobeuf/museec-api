package fr.univartois.butinfo.s5a01.musicmatcher.dto;

import fr.univartois.butinfo.s5a01.musicmatcher.utils.ImageStyle;

public class GenerateImageFromImageRequest {

	private int id;
	
	private ImageStyle style;
	
	private String path;
	
	public GenerateImageFromImageRequest() {
		
	}
	
	public GenerateImageFromImageRequest(int id, ImageStyle style, String path) {
		super();
		this.id = id;
		this.style = style;
		this.path = path;
	}

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
