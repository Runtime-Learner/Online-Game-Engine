package com.runtimelearner.onlinegameengine.testResources;

public class TestResources {

	//testing resources
	public static final String[] legalEmails = {"a@b.c", "abb@baa.com", "a1@b1.com", 
	                                      "a1.abb@aab.b2.com"};
	public static final String[] illegalEmails = {"a", "a1", "aaa", "aaa1.aa", "@bbb", "@bbb.com", "@bb.asdf2.com", 
											"@.com", ".@.", "a@.com", "asdf@asdf", "abb@ba a.com"};
	
	public static final String[] legalPasswords = {"Asdflkj1", "4sdlnGsdeu", "wirneDsk99", "888888888Qq", "Abcdefg1!",
			"!@#$%^*()_-=[]{}|+1Bb"};
	
	public static final String[] illegalPasswords = {"noNumber", "alllowercasenonumber", "alllowercasenumber1", 
			"12345678", "ALLUPPERCASENONUMBER","ALLUPPERCASENUMBER1", "4sdlnG sdeu", "Aa1!", "Asdflkj1&"};
	
	public static final String[] legalUsernames = {"a", "A", "a1", "A1", "a_", "A_", "a1_", "A1_", "aA1_"};
	
	public static final String[] illegalUsernames = {"1", "_", "sfdk!", "AnythingIncluding@", "Anything$", "a space"};
	
	public static final String[] legalWebpageNames = legalUsernames;
	
	public static final String[] illegalWebpageNames = illegalUsernames;
}
