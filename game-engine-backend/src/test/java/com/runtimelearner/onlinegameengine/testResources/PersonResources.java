package com.runtimelearner.onlinegameengine.testResources;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PersonResources {

	//testing resources
	public static String[] legalEmails = {"a@b.c", "abb@baa.com", "a1@b1.com", 
	                                      "a1.abb@aab.b2.com"};
	public static String[] illegalEmails = {"a", "a1", "aaa", "aaa1.aa", "@bbb", "@bbb.com", "@bb.asdf2.com", 
											"@.com", ".@.", "a@.com", "asdf@asdf", "abb@ba a.com"};
	
	public static String[] legalPasswords = {"Asdflkj1", "4sdlnGsdeu", "wirneDsk99", "888888888Qq", "Abcdefg1!",
			"!@#$%^*()_-=[]{}|+1Bb"};
	
	public static String[] illegalPasswords = {"noNumber", "alllowercasenonumber", "alllowercasenumber1", 
			"12345678", "ALLUPPERCASENONUMBER","ALLUPPERCASENUMBER1", "4sdlnG sdeu", "Aa1!", "Asdflkj1&"};
	
	public static String[] legalUsernames = {"a", "A", "a1", "A1", "a_", "A_", "a1_", "A1_", "aA1_"};
	
	public static String[] illegalUsernames = {"1", "_", "sfdk!", "AnythingIncluding@", "Anything$", "a space"};
	
}
