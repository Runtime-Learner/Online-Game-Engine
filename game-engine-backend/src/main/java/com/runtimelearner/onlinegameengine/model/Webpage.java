package com.runtimelearner.onlinegameengine.model;

import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Webpage {
    @Id
    @GeneratedValue
	private UUID id;
    
    @Basic(optional = false)
    private String name;
    
    @Lob
    @Column(columnDefinition="TEXT")
    private String htmlData;
    
    @ManyToOne(optional=false)
	private User linkedUser;
    
    
	public Webpage() {
	}
	
	public Webpage(String name, User user) {
		this.htmlData = "";
		this.name = name;
		this.linkedUser = user;
	}


	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the htmlData
	 */
	public String getHtmlData() {
		return htmlData;
	}

	/**
	 * @param htmlData the htmlData to set
	 */
	public void setHtmlData(String htmlData) {
		this.htmlData = htmlData;
	}

	/**
	 * @return the linkedUser
	 */
	public User getLinkedUser() {
		return linkedUser;
	}

	/**
	 * @param linkedUser the linkedUser to set
	 */
	public void setLinkedUser(User linkedUser) {
		this.linkedUser = linkedUser;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Webpage other = (Webpage) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}
