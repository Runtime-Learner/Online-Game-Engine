package com.runtimelearner.onlinegameengine.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Game {
	@Id
	private String name;
	
	private String description;
	private GameCategory genre;
	private GameStatus status;
	private boolean featured;
	
	@ManyToOne(optional=false)
	private User author;
	
	@OneToOne
	private XMLScript mainScript;
	
	public Game() {
		// TODO Auto-generated constructor stub
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the genre
	 */
	public GameCategory getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(GameCategory genre) {
		this.genre = genre;
	}

	/**
	 * @return the status
	 */
	public GameStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(GameStatus status) {
		this.status = status;
	}

	/**
	 * @return the featured
	 */
	public boolean isFeatured() {
		return featured;
	}

	/**
	 * @param featured the featured to set
	 */
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}

	/**
	 * @return the mainScript
	 */
	public XMLScript getMainScript() {
		return mainScript;
	}

	/**
	 * @param mainScript the mainScript to set
	 */
	public void setMainScript(XMLScript mainScript) {
		this.mainScript = mainScript;
	}

	
	
}
