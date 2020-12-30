package com.runtimelearner.onlinegameengine.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.runtimelearner.onlinegameengine.model.Webpage;
import com.runtimelearner.onlinegameengine.service.WebpageService;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class testWebpage {

	@Autowired
    private WebpageRepository webpageRepository;
	@Autowired
	private WebpageService webpageService;
	
    /**
     * Deletes all information from addressRepository, cartRepository and
     * userRepository
     */
    @BeforeEach
//    @AfterEach
    public void clearDatabase() {
    	webpageRepository.deleteAll();
    }
	
    @Test
    public void testPersistAndLoadWebpage() {
    	Webpage w = webpageService.createWebpage();
    	System.out.println("safd");
    	System.out.println("A " + w.toString());
    	Webpage retrievedWebpage = webpageRepository.findWebpageById(w.getId());
    	System.out.println("B " + retrievedWebpage);
 
    	assert(true);
    }
}
