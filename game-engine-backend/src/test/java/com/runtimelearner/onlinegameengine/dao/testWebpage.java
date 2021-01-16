package com.runtimelearner.onlinegameengine.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.security.InvalidAlgorithmParameterException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.runtimelearner.onlinegameengine.model.User;
import com.runtimelearner.onlinegameengine.model.Webpage;
import com.runtimelearner.onlinegameengine.service.UserService;
import com.runtimelearner.onlinegameengine.service.WebpageService;
import com.runtimelearner.onlinegameengine.testResources.TestResources;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class testWebpage {

	@Autowired
    private WebpageRepository webpageRepository;
	@Autowired
	private WebpageService webpageService;
	@Autowired
	private UserRepository userRepo;
	@Autowired 
	UserService userService;
	
    /**
     * Deletes all information from addressRepository, cartRepository and
     * userRepository
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
    	webpageRepository.deleteAll();
    	userRepo.deleteAll();
    }
	
    @Test
    public void test_01_createWebpage_validPageName__validUserEmail_userExists() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	User retrievedUser = userService.getUserByEmail_eager_Webpages_only(user.getEmail());
    	assert(retrievedUser.getWebpages().contains(webpage));
    	assertEquals(webpage.getLinkedUser(), retrievedUser);
    	assertEquals(webpage.getName(), TestResources.legalWebpageNames[0]);
    }
    
    @Test
    public void test_02_createWebpage_webpageNameAlreadyTaken() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	for (String legalPageName : TestResources.legalWebpageNames) {
	    	webpageService.createWebpage(legalPageName, user.getEmail());
	    	try {
	    		webpageService.createWebpage(legalPageName, user.getEmail());
	    		fail();
	    	} catch (IllegalArgumentException e) {
	    		assertEquals(e.getMessage(), "You already have a webpage with that name!");
	    	}
    	}
    }
    
    @Test
    public void test_03_createWebpage_validPageName__validUserEmail_userDoesNotExists() {
    	try {
    		webpageService.createWebpage(TestResources.legalWebpageNames[0], TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_04_createWebpage_invalidPageName__validUserEmail() {
    	for (String invalidPageName : TestResources.illegalWebpageNames) {
	    	try {
	    		webpageService.createWebpage(invalidPageName, TestResources.legalEmails[0]);
	    		fail();
	    	} catch (IllegalArgumentException e) {
	    		
	    	}
    	}
    }
    
    @Test
    public void test_05_createWebpage_validPageName__invalidUserEmail() {
    	for (String invalidEmail : TestResources.illegalEmails) {
	    	try {
	    		webpageService.createWebpage(TestResources.legalWebpageNames[0], invalidEmail);
	    		fail();
	    	} catch (IllegalArgumentException e) {
	    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
	    	}
    	}
    }
    
    @Test
    public void test_06_createWebpage_validPageName__nullEmail() {
    	try {
    		webpageService.createWebpage(TestResources.legalWebpageNames[0], null);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_07_createWebpage_nullPageName__validUserEmail() {
    	try {
    		webpageService.createWebpage(null, TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage name cannot be empty!");
    	}
    }

    @Test
    public void test_08_getWebpageByNameAndUserEmail_validPageName__userExists_pageExists() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	Webpage webpage2 = webpageService.createWebpage(TestResources.legalWebpageNames[2], user.getEmail());
    	Webpage retrievedPage = webpageService.getWebpageByNameAndUserEmail(webpage.getName(), user.getEmail());
    	Webpage retrievedPage2 = webpageService.getWebpageByNameAndUserEmail(webpage2.getName(), user.getEmail());
    	assertEquals(webpage, retrievedPage);
    	assertEquals(webpage2, retrievedPage2);
    }
    
    @Test
    public void test_09_getWebpageByNameAndUserEmail_validPageName__userDoesNotExist_pageDoesNotExist() {
    	try {
    		webpageService.getWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_10_getWebpageByNameAndUserEmail_validPageName__userExists_pageDoesNotExist() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	try {
    		webpageService.getWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], user.getEmail());
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_11_getWebpageByNameAndUserEmail_invalidPageName_validUserEmail() {
    	try {
    		webpageService.getWebpageByNameAndUserEmail(TestResources.illegalWebpageNames[0], TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {

    	}
    }
    
    @Test
    public void test_12_getWebpageByNameAndUserEmail_validPageName_invalidUserEmail() {
    	try {
    		webpageService.getWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], TestResources.illegalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_13_getWebpageByNameAndUserEmail_nullPageName_validUserEmail() {
    	try {
    		webpageService.getWebpageByNameAndUserEmail(null, TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage name cannot be empty!");
    	}
    }
    
    @Test
    public void test_14_getWebpageByNameAndUserEmail_validPageName__nullUserEmail() {
    	try {
    		webpageService.getWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], null);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_15_getWebpageByNameAndUsername_validPageName__userExists_pageExists() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	Webpage webpage2 = webpageService.createWebpage(TestResources.legalWebpageNames[2], user.getEmail());
    	Webpage retrievedPage = webpageService.getWebpageByNameAndUsername(webpage.getName(), user.getUsername());
    	Webpage retrievedPage2 = webpageService.getWebpageByNameAndUsername(webpage2.getName(), user.getUsername());
    	assertEquals(webpage, retrievedPage);
    	assertEquals(webpage2, retrievedPage2);
    }
    
    @Test
    public void test_16_getWebpageByNameAndUsername_validPageName__userDoesNotExist_pageDoesNotExist() {
    	try {
    		webpageService.getWebpageByNameAndUsername(TestResources.legalWebpageNames[0], TestResources.legalUsernames[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_17_getWebpageByNameAndUsername_validPageName__userExists_pageDoesNotExist() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	try {
    		webpageService.getWebpageByNameAndUsername(TestResources.legalWebpageNames[0], user.getUsername());
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_18_getWebpageByNameAndUsername_invalidPageName_validUsername() {
    	try {
    		webpageService.getWebpageByNameAndUsername(TestResources.illegalWebpageNames[0], TestResources.legalUsernames[0]);
    		fail();
    	} catch (IllegalArgumentException e) {

    	}
    }
    
    @Test
    public void test_19_getWebpageByNameAndUsername_validPageName_invalidUsername() {
    	try {
    		webpageService.getWebpageByNameAndUsername(TestResources.legalWebpageNames[0], TestResources.illegalUsernames[0]);
    		fail();
    	} catch (IllegalArgumentException e) {

    	}
    }
    
    @Test
    public void test_20_getWebpageByNameAndUsername_nullPageName_validUsername() {
    	try {
    		webpageService.getWebpageByNameAndUsername(null, TestResources.legalUsernames[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage name cannot be empty!");
    	}
    }
    
    @Test
    public void test_21_getWebpageByNameAndUsername_validPageName__nullUsername() {
    	try {
    		webpageService.getWebpageByNameAndUsername(TestResources.legalWebpageNames[0], null);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Username cannot be empty!");
    	}
    }

    @Test
    public void test_22_getWebpageById_validId_pageExists() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	Webpage retrievedWebpage = webpageService.getWebpageById(webpage.getId());
    	assertEquals(retrievedWebpage, webpage);
    }
    
    @Test
    public void test_23_getWebpageById_validId_pageDoesNotExists() {
    	try {
    		webpageService.getWebpageById(UUID.randomUUID());
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_24_getWebpageById_nullId() {
    	try {
    		webpageService.getWebpageById(null);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "id cannot be null!");
    	}
    }
 
    @Test
    public void test_25_deleteWebpageByNameAndUserEmail_validPageName__validUserEmail_userExists() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	User retrievedUser = userService.getUserByEmail_eager_Webpages_only(user.getEmail());
    	assert(retrievedUser.getWebpages().contains(webpage));
    	assertEquals(webpage.getLinkedUser(), retrievedUser);
    	assertEquals(webpage.getName(), TestResources.legalWebpageNames[0]);
    	
    	webpageService.deleteWebpageByNameAndUserEmail(webpage.getName(), user.getEmail());
    	
    	try {
    		webpageService.getWebpageById(webpage.getId());
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_26_deleteWebpageByNameAndUserEmail_validPageName__userDoesNotExist_pageDoesNotExist() {	
    	
    	try {
    		webpageService.deleteWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_27_deleteWebpageByNameAndUserEmail_validPageName_userExists_pageDoesNotExist() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);

    	try {
    		webpageService.deleteWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], user.getEmail());
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_28_deleteWebpageByNameAndUserEmail_invalidPageName_validUserEmail() {
    	try {
    		webpageService.deleteWebpageByNameAndUserEmail(TestResources.illegalWebpageNames[0], TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {

    	}
    }
    
    @Test
    public void test_29_deleteWebpageByNameAndUserEmail_validPageName_invalidUserEmail() {
    	try {
    		webpageService.deleteWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], TestResources.illegalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_30_deleteWebpageByNameAndUserEmail_nullPageName_validUserEmail() {
    	try {
    		webpageService.deleteWebpageByNameAndUserEmail(null, TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {

    	}
    }
    
    @Test
    public void test_31_deleteWebpageByNameAndUserEmail_validPageName_nullUserEmail() {
    	try {
    		webpageService.deleteWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], null);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_32_deleteWebpageByIdAndUserEmail_validPageId__validUserEmail_userExists() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	User retrievedUser = userService.getUserByEmail_eager_Webpages_only(user.getEmail());
    	assert(retrievedUser.getWebpages().contains(webpage));
    	assertEquals(webpage.getLinkedUser(), retrievedUser);
    	assertEquals(webpage.getName(), TestResources.legalWebpageNames[0]);
    	
    	webpageService.deleteWebpageByIdAndUserEmail(webpage.getId(), user.getEmail());
    	
    	try {
    		webpageService.getWebpageById(webpage.getId());
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_33_deleteWebpageByIdAndUserEmail_validPageId__userDoesNotExist_pageDoesNotExist() {	
    	
    	try {
    		webpageService.deleteWebpageByIdAndUserEmail(UUID.randomUUID(), TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_34_deleteWebpageByIdAndUserEmail_validPageId_userExists_pageDoesNotExist() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);

    	try {
    		webpageService.deleteWebpageByIdAndUserEmail(UUID.randomUUID(), user.getEmail());
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_35_deleteWebpageByIdAndUserEmail_validPageId_invalidUserEmail() {
    	try {
    		webpageService.deleteWebpageByIdAndUserEmail(UUID.randomUUID(), TestResources.illegalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_36_deleteWebpageByIdAndUserEmail_nullPageId_validUserEmail() {
    	try {
    		webpageService.deleteWebpageByIdAndUserEmail(null, TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "id cannot be null!");
    	}
    }
    
    @Test
    public void test_37_deleteWebpageByIdAndUserEmail_validPageId_nullUserEmail() {
    	try {
    		webpageService.deleteWebpageByIdAndUserEmail(UUID.randomUUID(), null);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_38_deleteWebpageByPageAndUser_validPage_validUser_UserOwnsPage() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	User retrievedUser = userService.getUserByEmail_eager_Webpages_only(user.getEmail());
    	assert(retrievedUser.getWebpages().contains(webpage));
    	assertEquals(webpage.getLinkedUser(), retrievedUser);
    	assertEquals(webpage.getName(), TestResources.legalWebpageNames[0]);
    	
    	webpageService.deleteWebpage(webpage, user);
    	
    	try {
    		webpageService.getWebpageById(webpage.getId());
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_39_deleteWebpageByPageAndUser_validPage_validUser_UserDoesNotOwnPage() {	
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	User retrievedUser = userService.getUserByEmail_eager_Webpages_only(user.getEmail());
    	assert(retrievedUser.getWebpages().contains(webpage));
    	assertEquals(webpage.getLinkedUser(), retrievedUser);
    	assertEquals(webpage.getName(), TestResources.legalWebpageNames[0]);
    	
    	User user2 = userService.createUser(TestResources.legalEmails[1], TestResources.legalPasswords[0], TestResources.legalUsernames[1]);
    	
    	try {
    		webpageService.deleteWebpage(webpage, user2);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "This webpage does not belong to this user!");
    	}
    }
    
    @Test
    public void test_40_deleteWebpageByPageAndUser_nullPage_validUser() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);

    	try {
    		webpageService.deleteWebpage(null, user);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_41_deleteWebpageByPageAndUser_validPage_nullUser() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	User retrievedUser = userService.getUserByEmail_eager_Webpages_only(user.getEmail());
    	assert(retrievedUser.getWebpages().contains(webpage));
    	assertEquals(webpage.getLinkedUser(), retrievedUser);
    	assertEquals(webpage.getName(), TestResources.legalWebpageNames[0]);
    	
    	try {
    		webpageService.deleteWebpage(webpage, null);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_08_updateWebpageByNameAndUserEmail_validPageName__userExists_pageExists_htmlValid() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	assertEquals(webpage.getHtmlData(), "");
    	webpage = webpageService.updateWebpageByNameAndUserEmail(webpage.getName(), user.getEmail(), "new html data!");
    	Webpage retrievedPage = webpageService.getWebpageByNameAndUserEmail(webpage.getName(), user.getEmail());
    	assertEquals(webpage, retrievedPage);
    	assertEquals(webpage.getHtmlData(), "new html data!");
    }
    
    @Test
    public void test_08_updateWebpageByNameAndUserEmail_validPageName__userExists_pageExists_htmlNull() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	Webpage webpage = webpageService.createWebpage(TestResources.legalWebpageNames[0], user.getEmail());
    	assertEquals(webpage.getHtmlData(), "");
    	try {
    	webpage = webpageService.updateWebpageByNameAndUserEmail(webpage.getName(), user.getEmail(), null);
    	fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "cannot save null data!");
    	}
    }
    
    @Test
    public void test_09_updateWebpageByNameAndUserEmail_validPageName__userDoesNotExist_pageDoesNotExist() {
    	try {
    		webpageService.updateWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], TestResources.legalEmails[0], "");
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!");
    	}
    }
    
    @Test
    public void test_10_updateWebpageByNameAndUserEmail_validPageName__userExists_pageDoesNotExist() {
    	User user = userService.createUser(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	try {
    		webpageService.updateWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], user.getEmail(), "");
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage is null!"); 
    	}
    }
    
    @Test
    public void test_11_updateWebpageByNameAndUserEmail_invalidPageName_validUserEmail() {
    	try {
    		webpageService.updateWebpageByNameAndUserEmail(TestResources.illegalWebpageNames[0], TestResources.legalEmails[0], "");
    		fail();
    	} catch (IllegalArgumentException e) {

    	}
    }
    
    @Test
    public void test_12_updateWebpageByNameAndUserEmail_validPageName_invalidUserEmail() {
    	try {
    		webpageService.updateWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], TestResources.illegalEmails[0], "");
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_13_updateWebpageByNameAndUserEmail_nullPageName_validUserEmail() {
    	try {
    		webpageService.updateWebpageByNameAndUserEmail(null, TestResources.legalEmails[0], "");
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Webpage name cannot be empty!");
    	}
    }
    
    @Test
    public void test_14_updateWebpageByNameAndUserEmail_validPageName__nullUserEmail() {
    	try {
    		webpageService.updateWebpageByNameAndUserEmail(TestResources.legalWebpageNames[0], null, "");
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }

}
