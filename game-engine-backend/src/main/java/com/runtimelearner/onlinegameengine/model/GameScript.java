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
public class GameScript {
    @Id
    @GeneratedValue
	private UUID Id;
    
    @Basic(optional = false)
    private String name;
    
    @ManyToOne(optional=false)
    private Game linkedGame;
    
    @Lob
    @Column(columnDefinition="TEXT")
    private String xmlData;
    
	public GameScript() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return Id;
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
	 * @return the linkedGame
	 */
	public Game getLinkedGame() {
		return linkedGame;
	}


	/**
	 * @param linkedGame the linkedGame to set
	 */
	public void setLinkedGame(Game linkedGame) {
		this.linkedGame = linkedGame;
	}


	/**
	 * @return the xmlData
	 */
	public String getXmlData() {
		return xmlData;
	}


	/**
	 * @param xmlData the xmlData to set
	 */
	public void setXmlData(String xmlData) {
		this.xmlData = xmlData;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
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
		GameScript other = (GameScript) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	

	
}
