package com.runtimelearner.onlinegameengine.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class testRating {

	@Autowired
    private RatingRepository ratingRepository;
	
	
    /**
     * Deletes all information from addressRepository, cartRepository and
     * userRepository
     */

    @AfterEach
    public void clearDatabase() {
    	ratingRepository.deleteAll();
    }
	
    @Test
    public void testPersistAndLoadRating() {
    	assert(true);
    }
}
