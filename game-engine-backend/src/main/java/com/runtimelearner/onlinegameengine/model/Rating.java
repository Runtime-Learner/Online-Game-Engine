package com.runtimelearner.onlinegameengine.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;

@Entity
public class Rating {
    @Id
    @GeneratedValue
	private UUID Id;
    
	private byte rating;
	private String review;
	
	@ManyToOne
	private Game game;
	
	@ManyToOne
	private User user;
	
	public Rating() {
		
	}
	
	public Rating(byte rating) {
		this.rating = rating;
	}
	
	public Rating(byte rating, String review) {
		this.rating = rating;
		this.review = review;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return Id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		Id = id;
	}

	/**
	 * @return the rating
	 */
	public byte getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(byte rating) {
		this.rating = rating;
	}

	/**
	 * @return the review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
