package com.runtimelearner.onlinegameengine.service;

import java.util.HashSet;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.runtimelearner.onlinegameengine.dao.UserRepository;
import com.runtimelearner.onlinegameengine.model.User;
import com.runtimelearner.onlinegameengine.model.Game;
import com.runtimelearner.onlinegameengine.model.Rating;
import com.runtimelearner.onlinegameengine.model.Webpage;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private GameService gameService;
	@Autowired
	private RatingService ratingService;
	@Autowired
	private WebpageService webpageService;
	
	
	@Transactional
	public User createUser(String email, String password, String username) {	
		//validate email
		Validator.validateEmail(email);
		
		//validate password
		Validator.validatePassword(password);
		
		//validate username
		Validator.validateUsername(username);
		
		//check if username is already being used
		if (userRepo.findUserByUsername(username) != null) {
			throw new IllegalArgumentException("Username is already taken!");
		}
		
		//check if email is aready being used
		if (userRepo.findUserByEmail(email) != null || userRepo.findUserByEmail(email) != null) {
			throw new IllegalArgumentException("Email is already associated with account!");
		}
		
		User user = new User(email, password, username, "");
		user.setBio("");
		user.setGames(new HashSet<Game>());
		user.setRatings(new HashSet<Rating>());
		user.setWebpages(new HashSet<Webpage>());
		user = userRepo.save(user);
		return user;
	}
	
	public User changePassword(String email, String currentPassword, String newPassword) {
		Validator.validatePassword(newPassword);
		Validator.validateEmail(email);
		
		User retrievedUser = getUserByEmail_lazy(email);
		if (! retrievedUser.getPassword().equals(currentPassword)) {
			throw new IllegalArgumentException("Incorrect password!");
		}
		retrievedUser.setPassword(newPassword);
		retrievedUser = userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	public User changeBio(String email, String newBio) {
		Validator.validateEmail(email);
		
		User retrievedUser = getUserByEmail_lazy(email);
		retrievedUser.setBio(newBio);
		retrievedUser = userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	public User changeProfilePictureURL(String email, String profilePicture) {
		Validator.validateEmail(email);
		
		User retrievedUser = getUserByEmail_lazy(email);
		retrievedUser.setProfilePictureUrl(profilePicture);
		retrievedUser = userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_lazy(String email) {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager(String email) {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getGames());
		Hibernate.initialize(retrievedUser.getRatings());
		Hibernate.initialize(retrievedUser.getWebpages());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Games_only(String email) {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getGames());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Ratings_only(String email) {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getRatings());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Webpages_only(String email) {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getWebpages());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Games_Ratings(String email) {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getGames());
		Hibernate.initialize(retrievedUser.getRatings());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Games_Webpages(String email) {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getGames());
		Hibernate.initialize(retrievedUser.getWebpages());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Ratings_Webpages(String email) {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getRatings());
		Hibernate.initialize(retrievedUser.getWebpages());
		return retrievedUser;
	}
	
	//TODO: deleteUser should deleteRating, deleteGame, deleteWebpage
	@Transactional
	public void deleteUser(String email) {
		User retrievedUser = getUserByEmail_eager(email);
		
//		for (Game userGame : retrievedUser.getGames()) {
//			gameService.deleteGame(userGame);
//		}
//		for (Rating userRating : retrievedUser.getRatings()) {
//			ratingService.deleteRating(userRating);
//		}
//		for (Webpage userWebpage : retrievedUser.getWebpages()) {
//			webpageService.deleteWebpage(userWebpage);
//		}
		userRepo.delete(retrievedUser);
	}
	
	public User addGame(String email, Game game) {
		Validator.validateEmail(email);
		checkGameNotNull(game);
		
		User retrievedUser = getUserByEmail_eager_Games_only(email);
		if (retrievedUser.getGames().contains(game))
		{
			throw new IllegalArgumentException("User already contains Game!");
		}
		retrievedUser.getGames().add(game);
		userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	public User deleteGame(String email, Game game) {
		Validator.validateEmail(email);
		checkGameNotNull(game);
		
		User retrievedUser = getUserByEmail_eager_Games_only(email);
		if (!retrievedUser.getGames().contains(game))
		{
			throw new IllegalArgumentException("User does not contain Game!");
		}
		retrievedUser.getGames().remove(game);
		userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	public User addRating(String email, Rating rating) {
		Validator.validateEmail(email);
		checkRatingNotNull(rating);
		
		User retrievedUser = getUserByEmail_eager_Ratings_only(email);
		if (retrievedUser.getRatings().contains(rating))
		{
			throw new IllegalArgumentException("User already contains Rating!");
		}
		retrievedUser.getRatings().add(rating);
		userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	public User deleteRating(String email, Rating rating) {
		Validator.validateEmail(email);
		checkRatingNotNull(rating);
		
		User retrievedUser = getUserByEmail_eager_Ratings_only(email);
		if (!retrievedUser.getRatings().contains(rating))
		{
			throw new IllegalArgumentException("User does not contain Rating!");
		}
		retrievedUser.getRatings().add(rating);
		userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	public User addWebpage(String email, Webpage webpage) {
		Validator.validateEmail(email);
		checkWebpageNotNull(webpage);
		
		User retrievedUser = getUserByEmail_eager_Webpages_only(email);
		if (retrievedUser.getWebpages().contains(webpage))
		{
			throw new IllegalArgumentException("User already contains Webpage!");
		}
		retrievedUser.getWebpages().add(webpage);
		userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	public User deleteWebpage(String email, Webpage webpage) {
		Validator.validateEmail(email);
		checkWebpageNotNull(webpage);
		
		User retrievedUser = getUserByEmail_eager_Webpages_only(email);
		if (!retrievedUser.getWebpages().contains(webpage))
		{
			throw new IllegalArgumentException("User does not contain Webpage!");
		}
		retrievedUser.getWebpages().add(webpage);
		userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	private void checkUserNotNull(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null!");
		}
	}
	
	private void checkGameNotNull(Game game) {
		if (game == null) {
			throw new IllegalArgumentException("Game cannot be null!");
		}
	}
	
	private void checkRatingNotNull(Rating rating) {
		if (rating == null) {
			throw new IllegalArgumentException("Rating cannot be null!");
		}
	}
	
	private void checkWebpageNotNull(Webpage webpage) {
		if (webpage == null) {
			throw new IllegalArgumentException("Webpage cannot be null!");
		}
	}

}
