package com.runtimelearner.onlinegameengine.service;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.runtimelearner.onlinegameengine.dao.AdminRepository;
import com.runtimelearner.onlinegameengine.dao.UserRepository;
import com.runtimelearner.onlinegameengine.dao.WebpageRepository;
import com.runtimelearner.onlinegameengine.model.User;
import com.runtimelearner.onlinegameengine.model.Game;
import com.runtimelearner.onlinegameengine.model.Rating;
import com.runtimelearner.onlinegameengine.model.Webpage;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AdminRepository adminRepo;
	@Autowired
	private GameService gameService;
	@Autowired
	private RatingService ratingService;
	@Autowired
	private WebpageService webpageService;
	
	
	@Transactional
	public User createUser(String email, String password, String username) throws IllegalArgumentException {	

		//validate password
		Validator.validatePassword(password);
		
		//check if username is valid and not already being used 
		checkUsernameIsUniqueAndValid(username);
		
		//check if email is valid and not already being used
		checkEmailIsUniqueAndValid(email);
		
		User user = new User(email, password, username, "");
		user.setBio("");
		user.setGames(new HashSet<Game>());
		user.setRatings(new HashSet<Rating>());
		user.setWebpages(new HashSet<Webpage>());
		user = userRepo.save(user);
		return user;
	}
	
	/**
	 * used to validate username dynamically while user is typing it
	 * @param username
	 */
	public void checkUsernameIsUniqueAndValid(String username) {
		Validator.validateUsername(username);
		//check if username is already being used
		if (userRepo.findUserByUsername(username) != null) {
			throw new IllegalArgumentException("Username is already taken!");
		}
	}
	
	/**
	 * used to validate email dynamically while user is typing it
	 * @param email
	 */
	public void checkEmailIsUniqueAndValid(String email) {
		Validator.validateEmail(email);
		
		//check if email is already being used
		if (adminRepo.findAdminByEmail(email) != null || userRepo.findUserByEmail(email) != null) {
			throw new IllegalArgumentException("Email is already associated with account!");
		}
	}
	
	@Transactional
	public User changePassword(String email, String currentPassword, String newPassword) throws IllegalArgumentException {
		Validator.validatePassword(newPassword);	//no need to validate current password since it will be compared to retrieved user password
		
		User retrievedUser = getUserByEmail_lazy(email);
		if (! retrievedUser.getPassword().equals(currentPassword)) {
			throw new IllegalArgumentException("Incorrect password!");
		}
		retrievedUser.setPassword(newPassword);
		retrievedUser = userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	@Transactional
	public User changeBio(String email, String newBio) throws IllegalArgumentException {
		
		User retrievedUser = getUserByEmail_lazy(email);
		retrievedUser.setBio(newBio);
		retrievedUser = userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	@Transactional
	public User changeProfilePictureURL(String email, String profilePicture) throws IllegalArgumentException {
		
		User retrievedUser = getUserByEmail_lazy(email);
		retrievedUser.setProfilePictureUrl(profilePicture);
		retrievedUser = userRepo.save(retrievedUser);
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByUsername_lazy(String username) throws IllegalArgumentException {
		Validator.validateUsername(username);
		
		User retrievedUser = userRepo.findUserByUsername(username);
		
		checkUserNotNull(retrievedUser);
		
		return retrievedUser;
	}
	
	@Transactional
	public Set<User> getUsers_usernameContains_lazy(String username) throws IllegalArgumentException {
		Validator.validateUsername(username);
		
		Set<User> retrievedUser = userRepo.findUserByUsernameContaining(username);
		
		if (retrievedUser == null || retrievedUser.size() == 0) {
			throw new IllegalArgumentException("No registered usernames contain the given string!");
		}
		
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_lazy(String email) throws IllegalArgumentException {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager(String email) throws IllegalArgumentException {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getGames());
		Hibernate.initialize(retrievedUser.getRatings());
		Hibernate.initialize(retrievedUser.getWebpages());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Games_only(String email) throws IllegalArgumentException {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getGames());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Ratings_only(String email) throws IllegalArgumentException {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getRatings());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Webpages_only(String email) throws IllegalArgumentException {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getWebpages());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Games_Ratings(String email) throws IllegalArgumentException {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getGames());
		Hibernate.initialize(retrievedUser.getRatings());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Games_Webpages(String email) throws IllegalArgumentException {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getGames());
		Hibernate.initialize(retrievedUser.getWebpages());
		return retrievedUser;
	}
	
	@Transactional
	public User getUserByEmail_eager_Ratings_Webpages(String email) throws IllegalArgumentException {
		Validator.validateEmail(email);
		
		User retrievedUser = userRepo.findUserByEmail(email);
		
		checkUserNotNull(retrievedUser);
		
		Hibernate.initialize(retrievedUser.getRatings());
		Hibernate.initialize(retrievedUser.getWebpages());
		return retrievedUser;
	}
	
	//TODO: deleteUser should deleteRating, deleteGame, deleteWebpage
	@Transactional
	public void deleteUser(String email) throws IllegalArgumentException {
		User retrievedUser = getUserByEmail_eager(email);
		
//		for (Game userGame : retrievedUser.getGames()) {
//			gameService.deleteGame(userGame);
//		}
//		for (Rating userRating : retrievedUser.getRatings()) {
//			ratingService.deleteRating(userRating);
//		}
		for (Webpage userWebpage : retrievedUser.getWebpages()) {
			webpageService.deleteWebpage(userWebpage, retrievedUser);
		}
		userRepo.delete(retrievedUser);
	}
	
	@Transactional
	public User addGame(String email, Game game) throws IllegalArgumentException {
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
	
	@Transactional
	public User deleteGame(String email, Game game) throws IllegalArgumentException {
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
	
	@Transactional
	public User addRating(String email, Rating rating) throws IllegalArgumentException {
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
	
	@Transactional
	public User deleteRating(String email, Rating rating) throws IllegalArgumentException {
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
	
	@Transactional
	public User addWebpage(String email, Webpage webpage) throws IllegalArgumentException {
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
	
	@Transactional
	public User deleteWebpage(String email, Webpage webpage) throws IllegalArgumentException {
		
		User retrievedUser = getUserByEmail_eager_Webpages_only(email);

		webpageService.deleteWebpage(webpage, retrievedUser);
		retrievedUser.getWebpages().remove(webpage);
		
		return retrievedUser;
	}
	
	private void checkUserNotNull(User user) throws IllegalArgumentException {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null!");
		}
	}
	
	private void checkGameNotNull(Game game) throws IllegalArgumentException {
		if (game == null) {
			throw new IllegalArgumentException("Game cannot be null!");
		}
	}
	
	private void checkRatingNotNull(Rating rating) throws IllegalArgumentException {
		if (rating == null) {
			throw new IllegalArgumentException("Rating cannot be null!");
		}
	}
	
	private void checkWebpageNotNull(Webpage webpage) throws IllegalArgumentException {
		if (webpage == null) {
			throw new IllegalArgumentException("Webpage cannot be null!");
		}
	}

}
