package com.runtimelearner.onlinegameengine.model;



import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class User extends Person {

	private String username;
	private String bio;
	private String profilePictureUrl;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
	private List<Game> games;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Rating> ratings;
	
	@OneToMany(fetch = FetchType.LAZY)
	private List<Webpage> webpages;
	
	public User(String email, String password, String username, String profilePictureUrl) {
		super(email, password);
		this.username = username;
		this.profilePictureUrl = profilePictureUrl;
	}

	public User() {
		super();
		this.username = "";
		this.profilePictureUrl = "";
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the bio
	 */
	public String getBio() {
		return bio;
	}

	/**
	 * @param bio the bio to set
	 */
	public void setBio(String bio) {
		this.bio = bio;
	}

	/**
	 * @return the profilePictureUrl
	 */
	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	/**
	 * @param profilePictureUrl the profilePictureUrl to set
	 */
	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	/**
	 * @return the games
	 */
	public List<Game> getGames() {
		return games;
	}

	/**
	 * @param games the games to set
	 */
	public void setGames(List<Game> games) {
		this.games = games;
	}

	/**
	 * @return the ratings
	 */
	public List<Rating> getRatings() {
		return ratings;
	}

	/**
	 * @param ratings the ratings to set
	 */
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	/**
	 * @return the webpages
	 */
	public List<Webpage> getWebpages() {
		return webpages;
	}

	/**
	 * @param webpages the webpages to set
	 */
	public void setWebpages(List<Webpage> webpages) {
		this.webpages = webpages;
	}

	
}
