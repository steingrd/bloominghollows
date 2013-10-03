package com.github.steingrd.bloominghollows.temperatures;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.steingrd.bloominghollows.system.Repository;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.status;

@Service
@Path("/temperatures")
@Transactional
public class TemperaturesService {

	@Resource
	Repository repository;
	
	@POST
	@Consumes(APPLICATION_JSON)
	public Response uploadTemperatureReading(Temperature temperature) {
		repository.store(temperature);
		return status(Status.ACCEPTED).build();
	}
	
}
