package com.github.steingrd.bloominghollows.temperatures;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.steingrd.bloominghollows.system.Repository;

import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperatures;
import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperaturesForDate;
import static javax.servlet.http.HttpServletResponse.SC_ACCEPTED;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Transactional
@RequestMapping("/temperatures")
public class TemperatureController {
	
	private final Logger logger = LoggerFactory.getLogger(TemperatureController.class);
	
	@Autowired
	private Repository repository;
	
	@RequestMapping(method = POST, consumes = "application/json")
	public void postTemperature(HttpServletResponse response, @RequestBody Temperature temperature) {
		if (temperature == null) {
			logger.info("RequestBody is null, no Temperature to store -> 400/Bad Request");
			response.setStatus(SC_BAD_REQUEST);
		}
		
		
		repository.store(temperature);
		response.setStatus(SC_ACCEPTED);
	}
	
	@RequestMapping(method = GET, produces = "application/json")
	public @ResponseBody List<Temperature> listTemperatures(@RequestParam(required=false) String date) {
		
		// TODO is it possible to get a LocalDate instead of a String
		if (date != null) {
			return repository.find(allTemperaturesForDate(LocalDate.parse(date)));
		} 
		
		return repository.find(allTemperatures());
	}
	
}
