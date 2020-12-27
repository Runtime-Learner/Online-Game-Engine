package com.runtimelearner.onlinegameengine.model;


import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Admin extends Person{
	@OneToMany
	List<User> users;	//admin has list of users it is able to manage. User data is loaded when admins select them. This decreases the amount of network traffic

	public Admin() {
		super();
		users = null;
	}

	public Admin(String email, String password) {
		super(email, password);
		users = null;
	}
	
	/**
	 * @return the users
	 */
	public List<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
