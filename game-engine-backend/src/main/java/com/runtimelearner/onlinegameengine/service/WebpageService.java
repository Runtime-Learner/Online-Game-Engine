package com.runtimelearner.onlinegameengine.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.runtimelearner.onlinegameengine.dao.WebpageRepository;
import com.runtimelearner.onlinegameengine.model.User;
import com.runtimelearner.onlinegameengine.model.Webpage;

@Service
public class WebpageService {
	@Autowired
	private WebpageRepository webpageRepo;
	@Autowired 
	UserService userService;
	
	@Transactional
	public Webpage createWebpage(String nameOfPage, String userEmail) {
		Validator.validateWebpageName(nameOfPage);
		User linkedUser = userService.getUserByEmail_lazy(userEmail);
		
		//verify that user does not already have a webpage with the name 'nameOfPage'
		pageNameIsUnique(nameOfPage, linkedUser);
		
		Webpage webpage = new Webpage(nameOfPage, linkedUser);
		webpage = webpageRepo.save(webpage);
		return webpage;
	}
	
	@Transactional
	public Webpage getWebpageByNameAndUserEmail(String nameOfPage, String userEmail) {
		Validator.validateWebpageName(nameOfPage);
		Validator.validateEmail(userEmail);

		Webpage page = webpageRepo.findByNameAndLinkedUserEmail(nameOfPage, userEmail);
		checkWebpageNotNull(page);
		return page;
	}
	
	@Transactional
	public Webpage getWebpageByNameAndUsername(String nameOfPage, String username) {
		Validator.validateWebpageName(nameOfPage);
		User linkedUser = userService.getUserByUsername_lazy(username);
		
		Webpage page = webpageRepo.findWebpageByNameAndLinkedUser(nameOfPage, linkedUser);
		checkWebpageNotNull(page);
		return page;
	}
	
	@Transactional
	public Webpage getWebpageById(UUID id) {
		if (id == null) {
			throw new IllegalArgumentException("id cannot be null!");
		}
		
		Webpage webpage = webpageRepo.findWebpageById(id);
		
		checkWebpageNotNull(webpage);
		
		return webpage;
	}
	
	@Transactional
	public void deleteWebpageByNameAndUserEmail(String nameOfPage, String userEmail) {
		Webpage page = getWebpageByNameAndUserEmail(nameOfPage, userEmail);
		webpageRepo.delete(page);
	}
	
	@Transactional
	public void deleteWebpageByIdAndUserEmail(UUID id, String userEmail) {
		Webpage page = getWebpageById(id);
		
		verifyPageBelongsToUser(page, userEmail);
		
		webpageRepo.delete(page);
	}
	
	@Transactional
	public void deleteWebpage(Webpage page, User user) {
		checkUserNotNull(user);
		checkWebpageNotNull(page);
		
		verifyPageBelongsToUser(page, user);
		webpageRepo.delete(page);
	}
	
	@Transactional
	public void updateWebpageByNameAndUserEmail(String nameOfPage, String userEmail, String html) {
		Webpage page = getWebpageByNameAndUserEmail(nameOfPage, userEmail);
		page.setHtmlData(html);
		webpageRepo.save(page);
	}
	
	@Transactional
	public void updateWebpageByIdAndUserEmail(UUID id, String userEmail, String html) {
		Webpage page = getWebpageById(id);
		
		verifyPageBelongsToUser(page, userEmail);
		
		page.setHtmlData(html);
		webpageRepo.save(page);
	}
	

	
	@Transactional
	private void pageNameIsUnique(String name, User user) {
		if (webpageRepo.findWebpageByNameAndLinkedUser(name, user) != null) {
			throw new IllegalArgumentException("You already have a webpage with that name!");
		}
	}
	
	private void verifyPageBelongsToUser(Webpage page, User user) {
		verifyPageBelongsToUser(page, user.getEmail());
	}
	
	private void verifyPageBelongsToUser(Webpage page, String email) {
		if (!page.getLinkedUser().getEmail().equals(email)) {
			throw new IllegalArgumentException("This webpage does not belong to this user!");
		}
	}
	
	private void checkWebpageNotNull(Webpage page) {
		if (page == null) {
			throw new IllegalArgumentException("Webpage is null!");
		}
	}
	
	private void checkUserNotNull(User user) throws IllegalArgumentException {
		if (user == null) {
			throw new IllegalArgumentException("User cannot be null!");
		}
	}
}
