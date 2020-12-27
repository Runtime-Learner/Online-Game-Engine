package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.repository.CrudRepository;

import com.runtimelearner.onlinegameengine.model.XMLScript;

import java.util.List;

public interface XMLScriptRepository extends CrudRepository<XMLScript, Long>{
	List<XMLScript> findAll();
}
