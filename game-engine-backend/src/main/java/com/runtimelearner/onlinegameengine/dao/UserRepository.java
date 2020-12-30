package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.repository.CrudRepository;

import com.runtimelearner.onlinegameengine.model.User;
import com.runtimelearner.onlinegameengine.projection.UserUsername;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long>{
	List<User> findAll();
	List<UserUsername> findAllBy();
	User findUserByEmail(String email);
	User findUserByUsername(String username);
}
