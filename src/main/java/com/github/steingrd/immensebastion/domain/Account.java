package com.github.steingrd.immensebastion.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.GenericGenerator;

@Entity
@XmlRootElement
public class Account {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@XmlElement
	public Long id;
	
	@XmlElement
	public String name;

	private Account() {
		// hibernate
	}
	
	public Account(String name) {
		this();
		this.name = name;
	}

	public void update(Account newValues) {
		this.name = newValues.name;
	}

}
