package kr.co.sapyoung.utils;

public class RegularExUtils {
	public static String getNumber(String str) {
		if ( str == null ) return "";
		 
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < str.length(); i++){
		if( Character.isDigit( str.charAt(i) ) ) {
		sb.append( str.charAt(i) );
		}
		}
		return sb.toString();

	}
}
