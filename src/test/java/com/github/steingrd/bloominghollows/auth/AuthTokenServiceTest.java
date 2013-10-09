package com.github.steingrd.bloominghollows.auth;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.steingrd.bloominghollows.config.DbConfiguration;
import com.github.steingrd.bloominghollows.config.PackageConfiguration;
import com.github.steingrd.bloominghollows.system.Repository;

import static org.fest.assertions.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DbConfiguration.class, PackageConfiguration.class })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class AuthTokenServiceTest {

	@Autowired
	private Repository repository;
	
	@Autowired
	private AuthTokenService authTokenService;
	
	@Test
	public void shouldAcceptValidToken() throws Exception {
		repository.store(new AuthToken("test.token"));
		assertThat(authTokenService.isValidToken("test.token")).isTrue();
	}
	
	@Test
	public void shouldNotAcceptInvalidTokens() throws Exception {
		assertThat(authTokenService.isValidToken("test.token")).isFalse();
	}
	
	@Test
	public void shouldCreateValidTokens() throws Exception {
		String token = authTokenService.create();
		assertThat(token).isNotNull();
		assertThat(authTokenService.isValidToken(token)).isTrue();
	}
	
}
