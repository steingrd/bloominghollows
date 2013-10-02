package com.github.steingrd.bloominghollows.temperatures;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static javax.ws.rs.core.Response.status;

@Service
@Path("/temperatures")
@Transactional
public class TemperaturesService {

	@POST
	public Response uploadTemperatureReading() {
		return status(Status.BAD_REQUEST).build();
	}
	
}
