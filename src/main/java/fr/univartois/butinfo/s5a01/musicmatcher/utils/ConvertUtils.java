package fr.univartois.butinfo.s5a01.musicmatcher.utils;

public class ConvertUtils {

	/**
	 * This method casts an object into type T (gotten from the variable
	 * There is no type checks. Be careful when using this method.
	 * @param <T>
	 * @param o : the object you want to cast
	 * @return the casted object
	 */
	@SuppressWarnings("unchecked")
	public static <T> T toT(Object o) {
		return (T) o;
	}
	
	private ConvertUtils() {
		// sonar rule : hide public constructor if not needed
		super();
	}
}
