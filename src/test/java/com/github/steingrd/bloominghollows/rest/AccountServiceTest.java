package com.github.steingrd.bloominghollows.rest;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import static org.fest.assertions.Assertions.assertThat;



public class AccountServiceTest extends AbstractSpringContextJerseyTest {

	@Test
	public void shouldListAvailableAccounts() throws Exception {
		ClientResponse response = get("/accounts");

		assertThat(response.getStatus()).isEqualTo(200);
	}

	private ClientResponse get(final String path) {
		return resource().path(path).accept(APPLICATION_JSON).get(ClientResponse.class);
	}
	
}
