package com.github.steingrd.bloominghollows.auth;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.steingrd.bloominghollows.system.Repository;

import static com.github.steingrd.bloominghollows.auth.AuthTokenSpecification.tokenByString;


@Service
public class AuthTokenService {

	@Autowired
	private Repository repository;
	
	public boolean isValidToken(String token) {
		AuthToken authToken = repository.get(tokenByString(token));
		return authToken != null;
	}

	public String create() {
		String token = UUID.randomUUID().toString();
		repository.store(new AuthToken(token));
		return token;
	}
	
}
