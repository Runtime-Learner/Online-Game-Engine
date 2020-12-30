package com.runtimelearner.onlinegameengine.service;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.runtimelearner.onlinegameengine.dao.AdminRepository;
import com.runtimelearner.onlinegameengine.dao.UserRepository;
import com.runtimelearner.onlinegameengine.model.Admin;
import com.runtimelearner.onlinegameengine.model.User;

@Service
public class AdminService {

	@Autowired 
	private AdminRepository adminRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Transactional
	public Admin createAdmin(String email, String password) {	
		//validate email
		Validator.validateEmail(email);
		
		//validate password
		Validator.validatePassword(password);
		
		//check if email is aready being used
		if (adminRepo.findAdminByEmail(email) != null || userRepo.findUserByEmail(email) != null) {
			throw new IllegalArgumentException("Email is already associated with account!");
		}
		
		Admin admin = new Admin(email, password);
		admin.setUsers(new HashSet<User>());
		admin = adminRepo.save(admin);
		return admin;
	}
	
	public Admin changePassword(String email, String currentPassword, String newPassword) {
		Validator.validatePassword(newPassword);
		Validator.validateEmail(email);
		
		Admin retrievedAdmin = getAdminByEmail_lazy(email);
		if (! retrievedAdmin.getPassword().equals(currentPassword)) {
			throw new IllegalArgumentException("Incorrect password!");
		}
		retrievedAdmin.setPassword(newPassword);
		retrievedAdmin = adminRepo.save(retrievedAdmin);
		return retrievedAdmin;
	}
	
	@Transactional
	public Admin getAdminByEmail_lazy(String email) {
		Validator.validateEmail(email);
		
		Admin retrievedAdmin = adminRepo.findAdminByEmail(email);
		
		checkAdminNotNull(retrievedAdmin);
		
		return retrievedAdmin;
	}
	
	@Transactional
	public Admin getAdminByEmail_eager(String email) {
		Validator.validateEmail(email);
		
		Admin retrievedAdmin = adminRepo.findAdminByEmail(email);
		
		checkAdminNotNull(retrievedAdmin);
		
		Hibernate.initialize(retrievedAdmin.getUsers());
		return retrievedAdmin;
	}
	
	@Transactional
	public void deleteAdmin(String email) {
		Admin retrievedAdmin = getAdminByEmail_lazy(email);

		adminRepo.delete(retrievedAdmin);
	}
	
	@Transactional
	public Admin addUser(String email, User user) {
		Validator.validateEmail(email);
		checkUserNotNull(user);
		
		Admin fullyLoadedAdmin = getAdminByEmail_eager(email);

		fullyLoadedAdmin.getUsers().add(user);
		
		return adminRepo.save(fullyLoadedAdmin);
	}
	
	@Transactional
	public Admin addUser(String email, Set<User> users) {
		Validator.validateEmail(email);
		checkUserSetNotNull(users);
		
		Admin fullyLoadedAdmin = getAdminByEmail_eager(email);
		
		for (User user : users) {
			fullyLoadedAdmin.getUsers().add(user);
		}
		return adminRepo.save(fullyLoadedAdmin);
	}
	
	@Transactional
	public Admin removeUser(String email, User user) {
		Validator.validateEmail(email);
		checkUserNotNull(user);
		
		Admin fullyLoadedAdmin = getAdminByEmail_eager(email);
		
		fullyLoadedAdmin.getUsers().remove(user);
		return adminRepo.save(fullyLoadedAdmin);
	}
	
	@Transactional
	public Admin removeUser(String email, Set<User> users) {
		Validator.validateEmail(email);
		checkUserSetNotNull(users);
		
		Admin fullyLoadedAdmin = getAdminByEmail_eager(email);
		
		for (User user : users) {
			fullyLoadedAdmin.getUsers().remove(user);
		}
		return adminRepo.save(fullyLoadedAdmin);
	}
	
	private void checkAdminNotNull(Admin admin) {
		if (admin == null) {
			throw new IllegalArgumentException("Admin cannot be null!");
		}
	}
	private void checkUserNotNull(User user) {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null!");
		}
	}
	private void checkUserSetNotNull(Set<User> users) {
		if (users == null) {
			throw new IllegalArgumentException("User set cannot be null!");
		}
	}

}
