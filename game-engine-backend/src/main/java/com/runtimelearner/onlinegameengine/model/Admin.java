package com.runtimelearner.onlinegameengine.model;


import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Admin extends Person{
	@OneToMany
	Set<User> users;

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
	public Set<User> getUsers() {
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}
}
