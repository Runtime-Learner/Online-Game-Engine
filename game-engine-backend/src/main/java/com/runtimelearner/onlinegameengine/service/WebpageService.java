package com.runtimelearner.onlinegameengine.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.runtimelearner.onlinegameengine.dao.WebpageRepository;
import com.runtimelearner.onlinegameengine.model.Webpage;

@Service
public class WebpageService {
	@Autowired
	private WebpageRepository webpageRepo;
	
	@Transactional
	public Webpage createWebpage() {
		Webpage w = new Webpage("hello");
		w = webpageRepo.save(w);
		
		Hibernate.initialize(w.getWebpage());
		System.out.println(w.getWebpage().toString());
		return w;
	}
}
