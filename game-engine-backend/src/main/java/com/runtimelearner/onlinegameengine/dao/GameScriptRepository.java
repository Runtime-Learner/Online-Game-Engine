package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.repository.CrudRepository;

import com.runtimelearner.onlinegameengine.model.GameScript;

import java.util.List;

public interface GameScriptRepository extends CrudRepository<GameScript, Long>{
	List<GameScript> findAll();
}
