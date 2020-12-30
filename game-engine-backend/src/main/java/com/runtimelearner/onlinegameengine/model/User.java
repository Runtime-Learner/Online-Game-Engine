package com.runtimelearner.onlinegameengine.model;



import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "app_user")
public class User extends Person {

	private String username;
	private String bio;
	private String profilePictureUrl;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "gameAuthor")
	private Set<Game> games;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ratingAuthor")
	private Set<Rating> ratings;
	
	@OneToMany(fetch = FetchType.LAZY)
	private Set<Webpage> webpages;
	
	public User(String email, String password, String username, String profilePictureUrl) {
		super(email, password);
		this.username = username;
		this.profilePictureUrl = profilePictureUrl;
		this.bio = "";
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
	public Set<Game> getGames() {
		return games;
	}

	/**
	 * @param games the games to set
	 */
	public void setGames(Set<Game> games) {
		this.games = games;
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

	/**
	 * @return the webpages
	 */
	public Set<Webpage> getWebpages() {
		return webpages;
	}

	/**
	 * @param webpages the webpages to set
	 */
	public void setWebpages(Set<Webpage> webpages) {
		this.webpages = webpages;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bio == null) ? 0 : bio.hashCode());
		result = prime * result + ((profilePictureUrl == null) ? 0 : profilePictureUrl.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (bio == null) {
			if (other.bio != null)
				return false;
		} else if (!bio.equals(other.bio))
			return false;
		if (profilePictureUrl == null) {
			if (other.profilePictureUrl != null)
				return false;
		} else if (!profilePictureUrl.equals(other.profilePictureUrl))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	
}
