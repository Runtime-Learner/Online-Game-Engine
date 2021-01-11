package com.runtimelearner.onlinegameengine.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Game {
	@Id
	private String name;
	
	private String description;
	private GameCategory genre;
	private GameStatus status;
	private boolean featured;
	
	@ManyToOne(optional=false)
	private User gameDeveloper;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "linkedGame")
	private Set<GameScript> gameScripts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ratedGame")
	private Set<Rating> ratings;
	
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
	 * @return the gameDeveloper
	 */
	public User getGameDeveloper() {
		return gameDeveloper;
	}

	/**
	 * @param gameDeveloper the gameDeveloper to set
	 */
	public void setGameDeveloper(User gameDeveloper) {
		this.gameDeveloper = gameDeveloper;
	}

	/**
	 * @return the gameScripts
	 */
	public Set<GameScript> getGameScripts() {
		return gameScripts;
	}

	/**
	 * @param gameScripts the gameScripts to set
	 */
	public void setGameScripts(Set<GameScript> gameScripts) {
		this.gameScripts = gameScripts;
	}

	/**
	 * @return the ratings
	 */
	public Set<Rating> getRatings() {
		return ratings;
	}

	/**
	 * @param ratings the ratings to set
	 */
	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + (featured ? 1231 : 1237);
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Game other = (Game) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (featured != other.featured)
			return false;
		if (genre != other.genre)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	
	
}
