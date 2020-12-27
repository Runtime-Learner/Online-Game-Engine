package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.repository.CrudRepository;

import com.runtimelearner.onlinegameengine.model.Webpage;

import java.util.List;

public interface WebpageRepository extends CrudRepository<Webpage, Long>{
	List<Webpage> findAll();
}
