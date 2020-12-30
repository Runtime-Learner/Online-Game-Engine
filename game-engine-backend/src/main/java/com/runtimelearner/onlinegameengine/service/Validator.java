package com.runtimelearner.onlinegameengine.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
	private static final String invalidPasswordCharactersRegex = "[^\\w!@#$()\\-+]";
	private static final String validPasswordRegex = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])";
	private static final String emailRegex = "([A-Za-z0-9]+\\.)*[A-Za-z0-9]+@([A-Za-z0-9]+\\.)+[A-Za-z0-9]+";

	public static void validateEmail(String email){
		if (email == null || email.trim().length() == 0 || !Pattern.matches(emailRegex, email)) {
			throw new IllegalArgumentException("Email is null or has wrong format!");
		}
	}
	public static void validatePassword(String password) {
		//check that password is long enough and doesn't contain illegal characters
		if (password == null || password.trim().length() < 8) {
			throw new IllegalArgumentException("Password is too short!");
		}
		
		Pattern p = Pattern.compile(invalidPasswordCharactersRegex);
		Matcher m = p.matcher(password);
		if (m.find()) {
			throw new IllegalArgumentException("Password contains illegal characters!");
		}
		
		//check if password contains at least one lowercase letter, one uppercase letter and one number
		p = Pattern.compile(validPasswordRegex);
		m = p.matcher(password);
		if (!m.find()) {
			throw new IllegalArgumentException("Password must contain one lowercase letter, one uppercase letter, and one number!");
		}
	}
	
	public static void validateUsername(String username) {
		if (username == null) {
			throw new IllegalArgumentException("Username cannot be empty!");
		}
		
		Pattern p = Pattern.compile("\\s");
		Matcher m = p.matcher(username);
		if (m.find()) {
			throw new IllegalArgumentException("Username cannot include spaces!");
		}
		
		p = Pattern.compile("[^\\w]");
		m = p.matcher(username);
		if (m.find()) {
			throw new IllegalArgumentException("Username can only contain letters, numbers, and the character \'_\'!");
		}
		
		p = Pattern.compile("^[a-zA-Z].*");
		m = p.matcher(username);
		if (!m.find()) {
			throw new IllegalArgumentException("Username must begin with a letter!");
		}
	}
}
