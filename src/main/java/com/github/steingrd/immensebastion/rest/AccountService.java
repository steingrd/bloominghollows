package com.github.steingrd.immensebastion.rest;

import static com.github.steingrd.immensebastion.domain.AccountSpecification.allAccounts;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.steingrd.immensebastion.domain.Account;
import com.github.steingrd.immensebastion.domain.Repository;

@Component
@Path("/accounts")
public class AccountService {
	
	@Autowired
	private Repository repository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response accounts() {
		List<Account> accounts = repository.find(allAccounts());
		return Response.status(200).entity(accounts).build();
	}
	
}
