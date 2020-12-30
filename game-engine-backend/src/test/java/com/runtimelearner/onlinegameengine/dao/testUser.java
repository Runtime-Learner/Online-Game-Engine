package com.runtimelearner.onlinegameengine.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.runtimelearner.onlinegameengine.model.User;
import com.runtimelearner.onlinegameengine.service.UserService;
import com.runtimelearner.onlinegameengine.testResources.PersonResources;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class testUser {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
    /**
     * Deletes all information from addressRepository, cartRepository and
     * userRepository
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
    	userRepository.deleteAll();
    }
	
    @Test
    public void test_01_createUser_legalPassword_legalEmail() {
    	User originalUser;
    	User retrievedUser;

    	originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser.getEmail(), retrievedUser.getEmail());
    	assertEquals(originalUser.getPassword(), retrievedUser.getPassword());
    }
    
    @Test
    public void test_01_createUser_legalPassword_legalEmail_illegalUsername() {
    	
    	for (String illegalUsername : PersonResources.illegalUsernames) {
    		try {
		    	userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], illegalUsername);
		    	fail();
    		} catch(IllegalArgumentException e) {
    			
    		}
    	}
    }
    
    @Test
    public void test_01_createUser_legalPassword_legalEmail_nullUsername() {
		try {
	    	userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], null);
	    	fail();
		} catch(IllegalArgumentException e) {
			
		}
    }
    
    @Test
    public void test_02_createUser_legalPassword_EmailAlreadyExists() {
    	
    	userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);

    	try {
    		userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[1]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is already associated with account!");
    	}
    }
    
    @Test
    public void test_02_createUser_legalPassword_legalEmail_UsernameAlreadyExists() {
    	
    	userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	
    	try {
    		userService.createUser(PersonResources.legalEmails[1], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Username is already taken!");
    	}
    }
    
    @Test
    public void test_03_createUser_illegalPassword_legalEmail() {
    	
    	for (String illegalPassword : PersonResources.illegalPasswords) {
        	try {
        		userService.createUser(PersonResources.legalEmails[0], illegalPassword, PersonResources.legalUsernames[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_04_createUser_legalPassword_illegalEmail() {
    	
    	for (String illegalEmail : PersonResources.illegalEmails) {
        	try {
        		userService.createUser(illegalEmail, PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_05_createUser_NullPassword_legalEmail() {
    	
    	for (String legalEmail : PersonResources.legalEmails) {
        	try {
        		userService.createUser(legalEmail, null, PersonResources.legalUsernames[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_06_createUser_legalPassword_NullEmail() {
    	
    	for (String legalPassword : PersonResources.legalPasswords) {
        	try {
        		userService.createUser(null, legalPassword, PersonResources.legalUsernames[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_07_getUser_eager_validEmail() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    	assertEquals(originalUser.getRatings(), retrievedUser.getRatings());
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_07_getUser_eager_game_validEmail() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Games_only(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    }
    
    @Test
    public void test_08_getUser_eager_rating_validEmail() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Ratings_only(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getRatings(), retrievedUser.getRatings());
    }
    
    @Test
    public void test_09_getUser_eager_webpage_validEmail() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Webpages_only(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_10_getUser_eager_game_rating_validEmail() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Games_Ratings(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    	assertEquals(originalUser.getRatings(), retrievedUser.getRatings());
    }
    
    @Test
    public void test_11_getUser_eager_game_webpage_validEmail() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Games_Webpages(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_12_getUser_eager_rating_webpage_validEmail() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Ratings_Webpages(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getRatings(), retrievedUser.getRatings());
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_13_getUser_lazy_validEmail() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_lazy(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    }
    
    @Test
    public void test_14_getUser_eager_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager(PersonResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_15_getUser_eager_game_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_only(PersonResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_16_getUser_eager_rating_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Ratings_only(PersonResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_17_getUser_eager_webpage_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Webpages_only(PersonResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_18_getUser_eager_game_rating_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_Ratings(PersonResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_19_getUser_eager_game_webpage_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_Webpages(PersonResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_20_getUser_eager_rating_webpage_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Ratings_Webpages(PersonResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_21_getUser_lazy_invalidEmail() {
    	try {
    		userService.getUserByEmail_lazy(PersonResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_22_getUser_eager_nullEmail() {
    	try {
    		userService.getUserByEmail_eager(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }

    @Test
    public void test_23_getUser_eager_game_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_only(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_24_getUser_eager_rating_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Ratings_only(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_25_getUser_eager_webpage_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Webpages_only(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_26_getUser_eager_game_rating_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_Ratings(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_27_getUser_eager_game_webpage_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_Webpages(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_28_getUser_eager_rating_webpage_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Ratings_Webpages(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_29_getUser_lazy_nullEmail() {
    	try {
    		userService.getUserByEmail_lazy(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_30_deleteUserFromEmail_userExists() {
    	User originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	userService.deleteUser(originalUser.getEmail());
    	try {
    		userService.getUserByEmail_lazy(PersonResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_31_deleteUserFromEmail_userDoesNotExist() {
    	try {
    		userService.deleteUser(PersonResources.legalEmails[0]);
    		fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }

    @Test
    public void test_32_deleteUserFromEmail_nullEmail() {
    	try {
    		userService.deleteUser(null);
    		fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_33_changePassword_correctCurrentPassword_validNewPassword() {
    	User originalUser;
    	User retrievedUser;

    	originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser.getPassword(), PersonResources.legalPasswords[0]);
    	assertEquals(originalUser.getPassword(), retrievedUser.getPassword());
    	
    	originalUser = userService.changePassword(originalUser.getEmail(), originalUser.getPassword(), PersonResources.legalPasswords[1]);
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser.getPassword(), PersonResources.legalPasswords[1]);
    	assertEquals(originalUser.getPassword(), retrievedUser.getPassword());
    }
    
    @Test
    public void test_34_changePassword_incorrectCurrentPassword_validNewPassword() {
    	
    	User originalUser;
    	
    	try {
        	originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
        	
        	userService.changePassword(originalUser.getEmail(), PersonResources.legalPasswords[1], PersonResources.legalPasswords[1]);
        	fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Incorrect password!");
    	}
    }
    
    @Test
    public void test_35_changePassword_correctCurrentPassword_invalidNewPassword() {
    	User originalUser;
    	
    	try {
        	originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
        	
        	userService.changePassword(originalUser.getEmail(), originalUser.getPassword(), PersonResources.illegalPasswords[0]);
        	fail();
    	}
    	catch (IllegalArgumentException e) {
    		
    	}
    }

    @Test
    public void test_01_changeBio() {
    	User originalUser;
    	User retrievedUser;

    	originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	originalUser = userService.changeBio(originalUser.getEmail(), "new bio");
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(retrievedUser.getBio(), "new bio");
    }
    
    @Test
    public void test_01_changeProfilePictureURL() {
    	User originalUser;
    	User retrievedUser;

    	originalUser = userService.createUser(PersonResources.legalEmails[0], PersonResources.legalPasswords[0], PersonResources.legalUsernames[0]);
    	originalUser = userService.changeProfilePictureURL(originalUser.getEmail(), "new url");
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(retrievedUser.getProfilePictureUrl(), "new url");
    }
}
