package com.github.steingrd.immensebastion.rest;

import static com.github.steingrd.immensebastion.domain.AccountSpecification.accountWithId;
import static com.github.steingrd.immensebastion.domain.AccountSpecification.allAccounts;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.ok;
import static javax.ws.rs.core.Response.seeOther;
import static javax.ws.rs.core.Response.Status.OK;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.steingrd.immensebastion.domain.Account;
import com.github.steingrd.immensebastion.domain.Repository;

@Component
@Path("/accounts")
@Transactional
public class AccountService {
	
	@Autowired
	private Repository repository;
	
	@GET
	@Produces(APPLICATION_JSON)
	public Response accounts() {
		List<Account> accounts = repository.find(allAccounts());
		return ok().entity(accounts).build();
	}
	
	@PUT
	@Consumes(APPLICATION_JSON)
	public Response createAccount(Account newAccount) {
		repository.store(newAccount);
		return seeOther(path(newAccount)).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(APPLICATION_JSON)
	public Response account(@PathParam("id") Long id) {
		Account account = repository.get(accountWithId(id));
		return ok().entity(account).build();
	}
	
	@POST
	@Path("/{id}")
	@Consumes(APPLICATION_JSON)
	public Response updateAccount(@PathParam("id") Long id, Account newValues) {
		Account account = repository.get(accountWithId(id));
		account.update(newValues);
		repository.store(account);
		return seeOther(path(account)).build();
	}

	private URI path(Account account) {
		return URI.create("/accounts/" + account.id);
	}
	
}
