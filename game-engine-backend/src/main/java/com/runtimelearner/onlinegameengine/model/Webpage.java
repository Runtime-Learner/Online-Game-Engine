package com.runtimelearner.onlinegameengine.model;

import java.sql.Clob;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Webpage {
    @Id
    @GeneratedValue
	private UUID Id;
    
    private Clob webpage;
    
	public Webpage() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the webpage
	 */
	public Clob getWebpage() {
		return webpage;
	}

	/**
	 * @param webpage the webpage to set
	 */
	public void setWebpage(Clob webpage) {
		this.webpage = webpage;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return Id;
	}
	
	
}
