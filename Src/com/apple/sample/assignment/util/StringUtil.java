package com.apple.sample.assignment.util;

import java.util.StringTokenizer;

public class StringUtil {

	private static StringTokenizer stk=null;
	
	 public static String[] getTokens(String str, String delim)
	    {
		 
		  stk = new StringTokenizer(str, delim);
		String[] strings = new String[stk.countTokens()];
		while(stk.hasMoreTokens())
		for (int i = 0; stk.hasMoreTokens(); ++i)
		   strings[i] = stk.nextToken();
		  return strings;
	    }
	 
}
