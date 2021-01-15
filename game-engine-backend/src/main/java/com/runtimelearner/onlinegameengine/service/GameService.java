package com.runtimelearner.onlinegameengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.runtimelearner.onlinegameengine.dao.GameRepository;
import com.runtimelearner.onlinegameengine.model.Game;
import com.runtimelearner.onlinegameengine.model.GameStatus;
import com.runtimelearner.onlinegameengine.model.User;

@Service
public class GameService {
	
	@Autowired 
	UserService userService;
	@Autowired GameRepository gameRepo;
	
	//create game
	public Game createGame(String name, String userEmail) {
		Validator.validateGameName(name);
		checkGameNameIsUniqueAndValid(name);
		
		User linkedUser = userService.getUserByEmail_lazy(userEmail);
		
		Game game = new Game();
		game.setName(name);
		game.setGameDeveloper(linkedUser);
		game.setStatus(GameStatus.unpublished);
		game = gameRepo.save(game);
		return game;
	}
	//edit name
	//edit description
	//edit genre
	//get game by name
	//get games by genre
	//get games name contains
	//get games by featured
	
	public void checkGameNameIsUniqueAndValid(String name) {
		Validator.validateGameName(name);
		
		//check if email is already being used
		if (gameRepo.findGameByName(name) != null) {
			throw new IllegalArgumentException("Name is already used!");
		}
	}
}
