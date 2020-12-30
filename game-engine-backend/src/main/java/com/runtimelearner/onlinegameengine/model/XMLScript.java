package com.runtimelearner.onlinegameengine.model;

import java.sql.Clob;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.context.annotation.Lazy;

@Entity
public class XMLScript {
    @Id
    @GeneratedValue
	private UUID Id;
    
    @OneToMany(fetch = FetchType.LAZY)
    private Set<XMLScript> referencedScripts;
    
    @Lazy
    private Clob script;
    
	public XMLScript() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return Id;
	}


	/**
	 * @return the referencedScripts
	 */
	public Set<XMLScript> getReferencedScripts() {
		return referencedScripts;
	}

	/**
	 * @param referencedScripts the referencedScripts to set
	 */
	public void setReferencedScripts(Set<XMLScript> referencedScripts) {
		this.referencedScripts = referencedScripts;
	}

	/**
	 * @return the script
	 */
	public Clob getScript() {
		return script;
	}

	/**
	 * @param script the script to set
	 */
	public void setScript(Clob script) {
		this.script = script;
	}

	
}
