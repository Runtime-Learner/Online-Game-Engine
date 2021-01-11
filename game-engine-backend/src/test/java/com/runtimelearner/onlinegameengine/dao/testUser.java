package com.runtimelearner.onlinegameengine.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Set;

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
import com.runtimelearner.onlinegameengine.testResources.TestResources;

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
    public void test_01_createUser_legalPassword_legalEmail_legalUsername() {
    	User originalUser;
    	User retrievedUser;

    	originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser.getEmail(), retrievedUser.getEmail());
    	assertEquals(originalUser.getPassword(), retrievedUser.getPassword());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    	assertEquals(originalUser.getRatings(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_02_createUser_legalPassword_legalEmail_illegalUsername() {
    	
    	for (String illegalUsername : TestResources.illegalUsernames) {
    		try {
		    	userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], illegalUsername);
		    	fail();
    		} catch(IllegalArgumentException e) {
    			
    		}
    	}
    }
    
    @Test
    public void test_03_createUser_legalPassword_EmailAlreadyExists_legalUsername() {
    	
    	userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);

    	try {
    		userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[1]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is already associated with account!");
    	}
    }
    
    @Test
    public void test_04_createUser_legalPassword_legalEmail_UsernameAlreadyExists() {
    	
    	userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	
    	try {
    		userService.createUser(TestResources.legalEmails[1], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Username is already taken!");
    	}
    }
    
    @Test
    public void test_05_createUser_illegalPassword_legalEmail_legalUsername() {
    	
    	for (String illegalPassword : TestResources.illegalPasswords) {
        	try {
        		userService.createUser(TestResources.legalEmails[0], illegalPassword, TestResources.legalUsernames[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_06_createUser_legalPassword_illegalEmail_legalUsername() {
    	
    	for (String illegalEmail : TestResources.illegalEmails) {
        	try {
        		userService.createUser(illegalEmail, TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_07_createUser_NullPassword_legalEmail_legalUsername() {
    	
    	for (String legalEmail : TestResources.legalEmails) {
        	try {
        		userService.createUser(legalEmail, null, TestResources.legalUsernames[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_08_createUser_legalPassword_NullEmail_legalUsername() {
    	
    	for (String legalPassword : TestResources.legalPasswords) {
        	try {
        		userService.createUser(null, legalPassword, TestResources.legalUsernames[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_09_createUser_legalPassword_legalEmail_nullUsername() {
		try {
	    	userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], null);
	    	fail();
		} catch(IllegalArgumentException e) {
			
		}
    }
    
    @Test
    public void test_10_getUser_eager_validEmail() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    	assertEquals(originalUser.getRatings(), retrievedUser.getRatings());
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_11_getUser_eager_game_validEmail() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Games_only(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    }
    
    @Test
    public void test_12_getUser_eager_rating_validEmail() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Ratings_only(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getRatings(), retrievedUser.getRatings());
    }
    
    @Test
    public void test_13_getUser_eager_webpage_validEmail() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Webpages_only(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_14_getUser_eager_game_rating_validEmail() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Games_Ratings(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    	assertEquals(originalUser.getRatings(), retrievedUser.getRatings());
    }
    
    @Test
    public void test_15_getUser_eager_game_webpage_validEmail() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Games_Webpages(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getGames(), retrievedUser.getGames());
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_16_getUser_eager_rating_webpage_validEmail() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_eager_Ratings_Webpages(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(originalUser.getRatings(), retrievedUser.getRatings());
    	assertEquals(originalUser.getWebpages(), retrievedUser.getWebpages());
    }
    
    @Test
    public void test_17_getUser_lazy_validEmail() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByEmail_lazy(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    }
    
    @Test
    public void test_18_getUser_eager_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_19_getUser_eager_game_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_only(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_20_getUser_eager_rating_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Ratings_only(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_21_getUser_eager_webpage_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Webpages_only(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_22_getUser_eager_game_rating_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_Ratings(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_23_getUser_eager_game_webpage_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_Webpages(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_24_getUser_eager_rating_webpage_invalidEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Ratings_Webpages(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_25_getUser_lazy_invalidEmail() {
    	try {
    		userService.getUserByEmail_lazy(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_26_getUser_eager_nullEmail() {
    	try {
    		userService.getUserByEmail_eager(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }

    @Test
    public void test_27_getUser_eager_game_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_only(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_28_getUser_eager_rating_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Ratings_only(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_29_getUser_eager_webpage_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Webpages_only(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_30_getUser_eager_game_rating_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_Ratings(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_31_getUser_eager_game_webpage_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Games_Webpages(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_32_getUser_eager_rating_webpage_nullEmail() { 	
    	try {
    		userService.getUserByEmail_eager_Ratings_Webpages(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_33_getUser_lazy_nullEmail() {
    	try {
    		userService.getUserByEmail_lazy(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_34_getUser_eager_validEmail_userDoesNotExist() {
    	try {
        	userService.getUserByEmail_eager(TestResources.legalEmails[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_35_getUser_eager_game_validEmail_userDoesNotExist() {
    	try {
    		userService.getUserByEmail_eager_Games_only(TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_36_getUser_eager_rating_validEmail_userDoesNotExist() {
    	try {
        	userService.getUserByEmail_eager_Ratings_only(TestResources.legalEmails[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_37_getUser_eager_webpage_validEmail_userDoesNotExist() {
    	try {
        	userService.getUserByEmail_eager_Webpages_only(TestResources.legalEmails[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_38_getUser_eager_game_rating_validEmail_userDoesNotExist() {
    	try {
        	userService.getUserByEmail_eager_Games_Ratings(TestResources.legalEmails[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_39_getUser_eager_game_webpage_validEmail_userDoesNotExist() {
    	try {
        	userService.getUserByEmail_eager_Games_Webpages(TestResources.legalEmails[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_40_getUser_eager_rating_webpage_validEmail_userDoesNotExist() {
    	try {
        	userService.getUserByEmail_eager_Ratings_Webpages(TestResources.legalEmails[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_41_getUser_lazy_validEmail_userDoesNotExist() {
    	try {
        	userService.getUserByEmail_lazy(TestResources.legalEmails[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_42_deleteUserFromEmail_userExists() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	userService.deleteUser(originalUser.getEmail());
    	try {
    		userService.getUserByEmail_lazy(TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_43_deleteUserFromEmail_userDoesNotExist() {
    	try {
    		userService.deleteUser(TestResources.legalEmails[0]);
    		fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_44_deleteUserFromEmail_invalidEmail() {
    	try {
    		userService.deleteUser(TestResources.illegalEmails[0]);
    		fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_45_deleteUserFromEmail_nullEmail() {
    	try {
    		userService.deleteUser(null);
    		fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_46_changePassword_correctCurrentPassword_validNewPassword() {
    	User originalUser;
    	User retrievedUser;

    	originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser.getPassword(), TestResources.legalPasswords[0]);
    	assertEquals(originalUser.getPassword(), retrievedUser.getPassword());
    	
    	originalUser = userService.changePassword(originalUser.getEmail(), originalUser.getPassword(), TestResources.legalPasswords[1]);
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser.getPassword(), TestResources.legalPasswords[1]);
    	assertEquals(originalUser.getPassword(), retrievedUser.getPassword());
    }
    
    @Test
    public void test_47_changePassword_incorrectCurrentPassword_validNewPassword() {
    	
    	User originalUser;
    	
    	try {
        	originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
        	
        	userService.changePassword(originalUser.getEmail(), TestResources.legalPasswords[1], TestResources.legalPasswords[1]);
        	fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Incorrect password!");
    	}
    }
    
    @Test
    public void test_48_changePassword_correctCurrentPassword_invalidNewPassword() {
    	User originalUser;
    	
    	try {
        	originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
        	
        	userService.changePassword(originalUser.getEmail(), originalUser.getPassword(), TestResources.illegalPasswords[0]);
        	fail();
    	}
    	catch (IllegalArgumentException e) {
    		
    	}
    }
    
    @Test
    public void test_49_changePassword_userDoesNotExist() {
    	try {
        	userService.changePassword(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalPasswords[0]);
        	fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	} 
    }

    @Test
    public void test_50_changeBio() {
    	User originalUser;
    	User retrievedUser;

    	originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	originalUser = userService.changeBio(originalUser.getEmail(), "new bio");
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(retrievedUser.getBio(), "new bio");
    }
    
    @Test
    public void test_51_changeBio_userDoesNotExist() {
    	try {
    		userService.changeBio(TestResources.legalEmails[0], "new bio");
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_52_changeProfilePictureURL() {
    	User originalUser;
    	User retrievedUser;

    	originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	originalUser = userService.changeProfilePictureURL(originalUser.getEmail(), "new url");
    	retrievedUser = userService.getUserByEmail_eager(originalUser.getEmail());
    	assertEquals(originalUser, retrievedUser);
    	assertEquals(retrievedUser.getProfilePictureUrl(), "new url");
    }
    
    @Test
    public void test_53_changeProfilePictureURL_userDoesNotExist() {
    	try {
    		userService.changeProfilePictureURL(TestResources.legalEmails[0], "new url");
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_54_getUser_lazy_validUsername_userDoesNotExist() {
    	try {
        	userService.getUserByUsername_lazy(TestResources.legalUsernames[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_55_getUser_lazy_nullUsername() {
    	try {
    		userService.getUserByUsername_lazy(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Username cannot be empty!");
    	}
    }
    
    @Test
    public void test_56_getUser_lazy_invalidUsername() {
    	
    	for (String illegalUsername : TestResources.illegalUsernames) {
	    	try {
	    		userService.getUserByUsername_lazy(illegalUsername);
	    		fail();
	    		
	    	} catch (IllegalArgumentException e) {
	    		
	    	}
    	}
    }
    
    @Test
    public void test_57_getUser_lazy_validUsername() {
    	User originalUser = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User retrievedUser = userService.getUserByUsername_lazy(originalUser.getUsername());
    	assertEquals(originalUser, retrievedUser);
    }
    
    @Test
    public void test_58_getUsers_usernameContains_lazy_validUsername_userDoesNotExist() {
    	try {
        	userService.getUsers_usernameContains_lazy(TestResources.legalUsernames[0]);
        	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "No registered usernames contain the given string!");
    	}
    }
    
    @Test
    public void test_59_getUser_usernameContains_lazy_nullUsername() {
    	try {
    		userService.getUsers_usernameContains_lazy(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Username cannot be empty!");
    	}
    }
    
    @Test
    public void test_60_getUser_usernameContains_lazy_invalidUsername() {
    	
    	for (String illegalUsername : TestResources.illegalUsernames) {
	    	try {
	    		userService.getUsers_usernameContains_lazy(illegalUsername);
	    		fail();
	    		
	    	} catch (IllegalArgumentException e) {
	    		
	    	}
    	}
    }
    
    @Test
    public void test_61_getUser_usernameContains_lazy_validUsername() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0] + "abc3");
    	User user2 = userService.createUser(TestResources.legalEmails[1], TestResources.legalPasswords[1], TestResources.legalUsernames[1] + "abc3");
    	User user3 = userService.createUser(TestResources.legalEmails[2], TestResources.legalPasswords[2], TestResources.legalUsernames[2]);
    	
    	Set<User> retrievedUser = userService.getUsers_usernameContains_lazy("abc3");
    	assert(retrievedUser.contains(user));
    	assert(retrievedUser.contains(user2));
    	assert(!retrievedUser.contains(user3));
    }
    
    @Test
    public void moreTestsToWrite() {
    	fail();
    }

  //TODO: addGame
  //TODO: deleteGame
  //TODO: addRating
  //TODO: deleteRating
  //TODO: addWebpage
  //TODO: deleteWebpage
}
