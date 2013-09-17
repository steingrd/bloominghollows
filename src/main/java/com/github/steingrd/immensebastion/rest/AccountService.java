package com.github.steingrd.immensebastion.rest;

import static com.github.steingrd.immensebastion.domain.AccountSpecification.accountWithId;
import static com.github.steingrd.immensebastion.domain.AccountSpecification.allAccounts;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.OK;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	@Produces(APPLICATION_JSON)
	public Response accounts() {
		List<Account> accounts = repository.find(allAccounts());
		return Response.status(OK).entity(accounts).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(APPLICATION_JSON)
	public Response account(@PathParam("id") Long id) {
		Account account = repository.get(accountWithId(id));
		return Response.status(OK).entity(account).build();
	}
	
}
