package com.runtimelearner.onlinegameengine.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

import org.springframework.context.annotation.Lazy;
import org.apache.commons.io.IOUtils;
@Entity
public class Webpage {
    @Id
    @GeneratedValue
	private UUID id;
    private String name;
    
    @Lob
    @Column(columnDefinition="BLOB")
    private byte[] webpage;
    
	public Webpage() {
		String theString = "";

		File file = new File("/home/matt/test.xml");
		
		InputStream inputStream = this.getClass()
				  .getClassLoader()
				  .getResourceAsStream("/home/matt/test.xml");
		
		try {
		webpage = (IOUtils.toByteArray(inputStream));
		} catch (Exception e) {System.out.println("error2 " + e.getMessage());}
	}
	
	public Webpage(String name) {
		InputStream inputStream = this.getClass()
				  .getClassLoader()
				  .getResourceAsStream("/home/matt/test.xml");
		
		try {
		webpage = (IOUtils.toByteArray(inputStream));
		} catch (Exception e) { System.out.println("error " +e.getMessage());}
		
		this.name = name;
	}

	/**
	 * @return the webpage
	 */
	public byte[] getWebpage() {
		return webpage;
	}

	/**
	 * @param webpage the webpage to set
	 */
	public void setWebpage(byte[] webpage) {
		this.webpage = webpage;
	}

	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Webpage [id=" + id + ", name=" + name + "webpage=" + webpage.toString() + "]";
	}
	
	
	
}
