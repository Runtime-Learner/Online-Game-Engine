package com.runtimelearner.onlinegameengine.model;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {
	
	@Id
	private String email;
	private String password;
	
	
	
	public Person(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	public Person() {
		this.email = "";
		this.password = "";
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
}
