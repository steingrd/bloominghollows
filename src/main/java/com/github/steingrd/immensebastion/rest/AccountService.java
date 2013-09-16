package com.github.steingrd.immensebastion.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.steingrd.immensebastion.domain.Account;

@Component
@Path("/accounts")
public class AccountService {
	
	@Autowired
	private SessionFactory sessionFactory;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response accounts() {
		List<Account> accounts = new ArrayList<>();
		accounts.add(new Account("foobar"));
		accounts.add(new Account("zotbar"));
		accounts.add(new Account("session " + (sessionFactory != null)));
		
		return Response.status(200).entity(accounts).build();
	}
	
}
