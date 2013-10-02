package com.github.steingrd.bloominghollows.rest;

import org.junit.Test;

import com.sun.jersey.api.client.ClientResponse;

import static org.fest.assertions.Assertions.assertThat;



public class UploadTemperaturesTest extends AbstractSpringContextJerseyTest {

	@Test
	public void shouldReturnBadRequestIfMissingRequestEntity() throws Exception {
		ClientResponse response = resource().path("/temperatures").post(ClientResponse.class);
		
		assertThat(response.getStatus()).isEqualTo(400);
	}
	
}
