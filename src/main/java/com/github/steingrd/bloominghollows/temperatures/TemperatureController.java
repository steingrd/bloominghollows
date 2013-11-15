package com.github.steingrd.bloominghollows.temperatures;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.steingrd.bloominghollows.auth.AuthTokenService;
import com.github.steingrd.bloominghollows.brews.Brew;
import com.github.steingrd.bloominghollows.system.Repository;

import static com.github.steingrd.bloominghollows.brews.BrewSpecification.brewWithId;
import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperaturesForBrew;
import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperaturesForBrewByDate;
import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Transactional
@RequestMapping("/brews/{brewId}/temperatures")
public class TemperatureController {
	
	private final Logger logger = LoggerFactory.getLogger(TemperatureController.class);
	
	@Autowired
	private Repository repository;
	
	@Autowired
	private AuthTokenService authToken;
	
	@RequestMapping(method = POST, consumes = "application/json")
	public void postTemperature(HttpServletResponse response,
			@RequestHeader("X-Auth-Token") String token, 
			@PathVariable("brewId") Long brewId, 
			@RequestBody Temperature temperature) {
		
		if (temperature == null) {
			logger.info("RequestBody is null, no Temperature to store -> 400/Bad Request");
			response.setStatus(SC_BAD_REQUEST);
			return;
		}

		if (!authToken.isValid(token)) {
			logger.info("Token [{}] is not valid -> 401/Forbidden", token);
			response.setStatus(SC_FORBIDDEN);
			return;
		}

		Brew brew = repository.getOrNull(brewWithId(brewId));
		if (brew == null) {
			logger.info("Brew [{}] is not valid -> 404/Not Found", brewId);
			response.setStatus(SC_NOT_FOUND);
			return;
		}
		
		temperature.setBrew(brew);
		repository.store(temperature);
		response.setStatus(SC_ACCEPTED);
	}
	
	@RequestMapping(method = GET, produces = "application/json")
	public @ResponseBody List<Temperature> listTemperatures(HttpServletResponse response,
			@PathVariable("brewId") Long brewId,
			@RequestParam(required=false) String date) {

		Brew brew = repository.getOrNull(brewWithId(brewId));
		if (brew == null) {
			logger.info("Brew [{}] is not valid -> 404/Not Found", brewId);
			response.setStatus(SC_NOT_FOUND);
			return null;
		}
		
		// TODO is it possible to get a LocalDate instead of a String
		if (date != null) {
			return repository.find(allTemperaturesForBrewByDate(brew, LocalDate.parse(date)));
		} 
		
		return repository.find(allTemperaturesForBrew(brew));
	}
	
}
