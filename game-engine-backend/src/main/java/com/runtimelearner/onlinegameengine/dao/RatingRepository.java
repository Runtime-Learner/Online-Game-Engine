package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.repository.CrudRepository;

import com.runtimelearner.onlinegameengine.model.Rating;

import java.util.List;

public interface RatingRepository extends CrudRepository<Rating, Long>{
	List<Rating> findAll();
}
