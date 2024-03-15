package fr.univartois.butinfo.s5a01.musicmatcher.utils;

public enum ImageStyle {

    POLAROID("polaroid"),
    TILT_SHIFT_EFFECT("tilt shift effect"),
    PRODUCT_SHOT("product shot"),
    LONG_EXPOSURE("long exposure"),
    PORTRAIT("portrait"),
    COLOR_SPLASH("color-splash"),
    MONOCHROME("monochrome"),
    SATELLITE("satellite");
    
    private String name;
    
    ImageStyle(String name) {
    	this.setName(name);
    }

	public String getName() {
		return name;
	}

	void setName(String name) {
		this.name = name;
	}
}
