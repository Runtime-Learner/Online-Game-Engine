package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.repository.CrudRepository;

import com.runtimelearner.onlinegameengine.model.Webpage;

import java.util.List;
import java.util.UUID;

public interface WebpageRepository extends CrudRepository<Webpage, Long>{
	List<Webpage> findAll();
	Webpage findWebpageById(UUID Id);
}
