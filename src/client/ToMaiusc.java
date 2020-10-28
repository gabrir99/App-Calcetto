package client;

public class ToMaiusc {

	public static String conv(String s) {
		char[] str = s.toCharArray();
		
		for(int i = 0; i < str.length; ++i) {
			if(i != 0 && Character.isLetter(str[i]) && Character.isUpperCase(str[i]))
				str[i] += 32;
			
			if((i == 0 && Character.isLetter(str[i]) && Character.isLowerCase(str[i])
					|| (i != 0 && Character.isWhitespace(str[i - 1]))) ){
				str[i] -= 32;
			}
		}
		
		return new String(str);
	}

}
