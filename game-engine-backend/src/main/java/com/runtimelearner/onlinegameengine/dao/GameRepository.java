package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.repository.CrudRepository;

import com.runtimelearner.onlinegameengine.model.Game;
import com.runtimelearner.onlinegameengine.model.GameStatus;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long>{
	List<Game> findAll();
	List<Game> findByFeaturedAndStatusNot(boolean featured, GameStatus isNot);
}
