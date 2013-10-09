package com.github.steingrd.bloominghollows.auth;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "tokens")
public class AuthToken {

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	private Long id;
	
	@Column
	private String token;
	
	private AuthToken() {
	}

	public AuthToken(String token) {
		this();
		this.token = token;
	}

	public Long getId() {
		return id;
	}
	
	public String getToken() {
		return token;
	}
	
}
