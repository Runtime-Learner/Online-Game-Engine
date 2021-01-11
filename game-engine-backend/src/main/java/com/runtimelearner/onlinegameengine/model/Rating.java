package com.runtimelearner.onlinegameengine.model;


import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;

@Entity
@Table(name = "rating")
public class Rating {
    @Id
    @GeneratedValue
	private UUID Id;
    
    @Basic(optional = false)
	private byte rating;
    
	private String review;
	
	@Basic(optional = false)
	private Date creationDate;
	
	@ManyToOne(optional=false)
	private Game ratedGame;
	
	@ManyToOne(optional=false)
	private User ratingAuthor;
	
	
	
	public Rating() {
		
	}
	
	public Rating(byte rating, User author, Game ratedGame) {
		this.rating = rating;
		this.ratingAuthor = author;
		this.ratedGame = ratedGame;
		this.creationDate = new Date();
	}
	
	public Rating(byte rating, String review, User author, Game ratedGame) {
		this.rating = rating;
		this.review = review;
		this.ratingAuthor = author;
		this.ratedGame = ratedGame;
		this.creationDate = new Date();
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return Id;
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
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the ratedGame
	 */
	public Game getRatedGame() {
		return ratedGame;
	}

	/**
	 * @param ratedGame the ratedGame to set
	 */
	public void setRatedGame(Game ratedGame) {
		this.ratedGame = ratedGame;
	}

	/**
	 * @return the ratingAuthor
	 */
	public User getRatingAuthor() {
		return ratingAuthor;
	}

	/**
	 * @param ratingAuthor the ratingAuthor to set
	 */
	public void setRatingAuthor(User ratingAuthor) {
		this.ratingAuthor = ratingAuthor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Id == null) ? 0 : Id.hashCode());
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
		Rating other = (Rating) obj;
		if (Id == null) {
			if (other.Id != null)
				return false;
		} else if (!Id.equals(other.Id))
			return false;
		return true;
	}

	
	
	
}
