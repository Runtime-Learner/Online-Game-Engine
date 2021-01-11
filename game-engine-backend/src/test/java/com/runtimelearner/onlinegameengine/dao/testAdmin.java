package com.runtimelearner.onlinegameengine.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.runtimelearner.onlinegameengine.model.Admin;
import com.runtimelearner.onlinegameengine.model.User;
import com.runtimelearner.onlinegameengine.service.AdminService;
import com.runtimelearner.onlinegameengine.service.UserService;
import com.runtimelearner.onlinegameengine.testResources.TestResources;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration
public class testAdmin {

	@Autowired
    private AdminRepository adminRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AdminService adminService;
	@Autowired
	private UserService userService;
	
    /**
     * Deletes all information from addressRepository, cartRepository and
     * userRepository
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
    	adminRepository.deleteAll();
    	userRepository.deleteAll();
    }
	
    @Test
    public void test_01_createAdmin_legalPassword_legalEmail() {
    	Admin originalAdmin;
    	Admin retrievedAdmin;

    	originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	retrievedAdmin = adminService.getAdminByEmail_eager(originalAdmin.getEmail());
    	assertEquals(originalAdmin.getEmail(), retrievedAdmin.getEmail());
    	assertEquals(originalAdmin.getPassword(), retrievedAdmin.getPassword());
    	assertEquals(originalAdmin.getUsers(), retrievedAdmin.getUsers());
    }
    
    @Test
    public void test_02_createAdmin_legalPassword_EmailAlreadyExists() {
    	
    	adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	
    	try {
    		adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is already associated with account!");
    	}
    }
    
    @Test
    public void test_03_createAdmin_illegalPassword_legalEmail() {
    	
    	for (String illegalPassword : TestResources.illegalPasswords) {
        	try {
        		adminService.createAdmin(TestResources.legalEmails[0], illegalPassword);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_04_createAdmin_legalPassword_illegalEmail() {
    	
    	for (String illegalEmail : TestResources.illegalEmails) {
        	try {
        		adminService.createAdmin(illegalEmail, TestResources.legalPasswords[0]);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_05_createAdmin_NullPassword_legalEmail() {
    	
    	for (String legalEmail : TestResources.legalEmails) {
        	try {
        		adminService.createAdmin(legalEmail, null);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_06_createAdmin_legalPassword_NullEmail() {
    	
    	for (String legalPassword : TestResources.legalPasswords) {
        	try {
        		adminService.createAdmin(null, legalPassword);
        		fail();
        		
        	} catch (IllegalArgumentException e) {
        		
        	}
    	}
    }
    
    @Test
    public void test_07_getAdmin_eager_validEmail() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	Admin retrievedAdmin = adminService.getAdminByEmail_eager(originalAdmin.getEmail());
    	assertEquals(originalAdmin.getEmail(), retrievedAdmin.getEmail());
    	assertEquals(originalAdmin.getPassword(), retrievedAdmin.getPassword());
    	assertEquals(originalAdmin.getUsers(), retrievedAdmin.getUsers());
    }
    
    @Test
    public void test_08_getAdmin_lazy_validEmail() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	Admin retrievedAdmin = adminService.getAdminByEmail_lazy(originalAdmin.getEmail());
    	assertEquals(originalAdmin.getEmail(), retrievedAdmin.getEmail());
    	assertEquals(originalAdmin.getPassword(), retrievedAdmin.getPassword());
    }
    
    @Test
    public void test_09_getAdmin_eager_invalidEmail() { 	
    	try {
    		adminService.getAdminByEmail_eager(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_10_getAdmin_lazy_invalidEmail() {
    	try {
    		adminService.getAdminByEmail_lazy(TestResources.illegalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_11_getAdmin_eager_nullEmail() {
    	try {
    		adminService.getAdminByEmail_eager(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_12_getAdmin_lazy_nullEmail() {
    	try {
    		adminService.getAdminByEmail_lazy(null);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_13_getAdmin_eager_userDoesNotExist() {
    	try {
    		adminService.getAdminByEmail_eager(TestResources.legalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Admin cannot be null!");
    	}
    }
    
    @Test
    public void test_14_getAdmin_lazy_userDoesNotExist() {
    	try {
    		adminService.getAdminByEmail_lazy(TestResources.legalEmails[0]);
    		fail();
    		
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Admin cannot be null!");
    	}
    }
    
    @Test
    public void test_15_deleteAdminFromEmail_adminExists() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	adminService.deleteAdmin(originalAdmin.getEmail());
    	try {
    		adminService.getAdminByEmail_lazy(TestResources.legalEmails[0]);
    		fail();
    	} catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Admin cannot be null!");
    	}
    }
    
    @Test
    public void test_16_deleteAdminFromEmail_adminDoesNotExist() {
    	try {
    		adminService.deleteAdmin(TestResources.legalEmails[0]);
    		fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Admin cannot be null!");
    	}
    }

    @Test
    public void test_17_deleteAdminFromEmail_nullEmail() {
    	try {
    		adminService.deleteAdmin(null);
    		fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Email is null or has wrong format!");
    	}
    }
    
    @Test
    public void test_18_changePassword_correctCurrentPassword_validNewPassword() {
    	Admin originalAdmin;
    	Admin retrievedAdmin;

    	originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	retrievedAdmin = adminService.getAdminByEmail_eager(originalAdmin.getEmail());
    	assertEquals(originalAdmin.getPassword(), TestResources.legalPasswords[0]);
    	assertEquals(originalAdmin.getPassword(), retrievedAdmin.getPassword());
    	
    	originalAdmin = adminService.changePassword(originalAdmin.getEmail(), originalAdmin.getPassword(), TestResources.legalPasswords[1]);
    	retrievedAdmin = adminService.getAdminByEmail_eager(originalAdmin.getEmail());
    	assertEquals(originalAdmin.getPassword(), TestResources.legalPasswords[1]);
    	assertEquals(originalAdmin.getPassword(), retrievedAdmin.getPassword());
    }
    
    @Test
    public void test_19_changePassword_incorrectCurrentPassword_validNewPassword() {
    	
    	Admin originalAdmin;
    	
    	try {
        	originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
        	
        	adminService.changePassword(originalAdmin.getEmail(), TestResources.legalPasswords[1], TestResources.legalPasswords[1]);
        	fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Incorrect password!");
    	}
    }
    
    @Test
    public void test_20_changePassword_correctCurrentPassword_invalidNewPassword() {
    	Admin originalAdmin;
    	
    	try {
        	originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
        	
        	adminService.changePassword(originalAdmin.getEmail(), originalAdmin.getPassword(), TestResources.illegalPasswords[0]);
        	fail();
    	}
    	catch (IllegalArgumentException e) {
    		
    	}
    }
    
    @Test
    public void test_21_changePassword_userDoesNotExist() {
    	try {
    		adminService.changePassword(TestResources.legalEmails[0], TestResources.legalPasswords[0], TestResources.legalPasswords[0]);
        	fail();
    	}
    	catch (IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "Admin cannot be null!");
    	}
    }
    
    @Test
    public void test_22_addUser() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	User a = userService.createUser(TestResources.legalEmails[1], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	originalAdmin = adminService.addUser(originalAdmin.getEmail(), a);
    	
    	assertEquals(1, originalAdmin.getUsers().size());
    	assert(originalAdmin.getUsers().contains(a));
    }
    
    @Test
    public void test_23_addUser_nullUser() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	User a = null;
    	try {
    	originalAdmin = adminService.addUser(originalAdmin.getEmail(), a);
    	fail();
    	} catch(IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_24_addMultipleUsers() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	User a = userService.createUser(TestResources.legalEmails[1], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User b = userService.createUser(TestResources.legalEmails[2], TestResources.legalPasswords[0], TestResources.legalUsernames[1]);
    	User c = userService.createUser(TestResources.legalEmails[3], TestResources.legalPasswords[0], TestResources.legalUsernames[2]);
    	HashSet<User> userSet = new HashSet<User>();
    	userSet.add(a);
    	userSet.add(b);
    	userSet.add(c);
    	
    	originalAdmin = adminService.addUser(originalAdmin.getEmail(), userSet);
    	
    	assertEquals(3, originalAdmin.getUsers().size());
    	assert(originalAdmin.getUsers().contains(a));
    	assert(originalAdmin.getUsers().contains(b));
    	assert(originalAdmin.getUsers().contains(c));
    }
    
    @Test
    public void test_25_addMultipleUsers_nullUsers() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	HashSet<User> userSet = null;
    	
    	try {
    		originalAdmin = adminService.addUser(originalAdmin.getEmail(), userSet);
    		fail();
    	} catch(IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User set cannot be null!");
    	}
    }
    
    @Test
    public void test_26_removeUser() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	User a = userService.createUser(TestResources.legalEmails[1], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	originalAdmin = adminService.addUser(originalAdmin.getEmail(), a);
    	
    	assertEquals(1, originalAdmin.getUsers().size());
    	assert(originalAdmin.getUsers().contains(a));
    	
    	originalAdmin = adminService.removeUser(originalAdmin.getEmail(), a);
    	assertEquals(0, originalAdmin.getUsers().size());
    }
    
    @Test
    public void test_27_removeUser_nullUser() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	User a = null;
    	try {
    	originalAdmin = adminService.removeUser(originalAdmin.getEmail(), a);
    	fail();
    	} catch(IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User cannot be null!");
    	}
    }
    
    @Test
    public void test_28_removeMultipleUsers() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	User a = userService.createUser(TestResources.legalEmails[1], TestResources.legalPasswords[0], TestResources.legalUsernames[0]);
    	User b = userService.createUser(TestResources.legalEmails[2], TestResources.legalPasswords[0], TestResources.legalUsernames[1]);
    	User c = userService.createUser(TestResources.legalEmails[3], TestResources.legalPasswords[0], TestResources.legalUsernames[2]);
    	HashSet<User> userSet = new HashSet<User>();
    	userSet.add(a);
    	userSet.add(b);
    	userSet.add(c);
    	
    	originalAdmin = adminService.addUser(originalAdmin.getEmail(), userSet);
    	
    	assertEquals(3, originalAdmin.getUsers().size());
    	assert(originalAdmin.getUsers().contains(a));
    	assert(originalAdmin.getUsers().contains(b));
    	assert(originalAdmin.getUsers().contains(c));
    	
    	originalAdmin = adminService.removeUser(originalAdmin.getEmail(), userSet);
    	assertEquals(0, originalAdmin.getUsers().size());
    }
    
    @Test
    public void test_29_removeMultipleUsers_nullUsers() {
    	Admin originalAdmin = adminService.createAdmin(TestResources.legalEmails[0], TestResources.legalPasswords[0]);
    	HashSet<User> userSet = null;
    	
    	try {
    		originalAdmin = adminService.removeUser(originalAdmin.getEmail(), userSet);
    		fail();
    	} catch(IllegalArgumentException e) {
    		assertEquals(e.getMessage(), "User set cannot be null!");
    	}
    }
}
