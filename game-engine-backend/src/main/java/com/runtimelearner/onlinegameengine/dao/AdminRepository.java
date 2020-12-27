package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.repository.CrudRepository;

import com.runtimelearner.onlinegameengine.model.Admin;

import java.util.List;

public interface AdminRepository extends CrudRepository<Admin, Long>{
	List<Admin> findAll();
}
