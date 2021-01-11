package com.runtimelearner.onlinegameengine.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.runtimelearner.onlinegameengine.model.User;
import com.runtimelearner.onlinegameengine.model.Webpage;

import java.util.List;
import java.util.UUID;

public interface WebpageRepository extends CrudRepository<Webpage, Long>{
	List<Webpage> findAll();
	Webpage findWebpageById(UUID Id);
	Webpage findWebpageByNameAndLinkedUser(String name, User linkedUser);
	
	@Query(nativeQuery =true,value = "select * from webpage where name=:pageName and linked_user_email=:userEmail")
    Webpage findByNameAndLinkedUserEmail(@Param("pageName") String name, @Param("userEmail") String email);
}
