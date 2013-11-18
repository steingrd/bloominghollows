package com.github.steingrd.bloominghollows.brews;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.steingrd.bloominghollows.system.Repository;

import static com.github.steingrd.bloominghollows.brews.BrewSpecification.allBrews;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@Transactional
@RequestMapping("/brews")
public class BrewController {
	
	private final Logger log = LoggerFactory.getLogger(BrewController.class);
	
	@Autowired
	private Repository repository;
	
	@RequestMapping(method = GET, produces = "application/json")
	public @ResponseBody List<Brew> listBrews() {
		return repository.find(allBrews());
	}
	
	@RequestMapping(method = POST, consumes = "application/json")
	public void createBrew(HttpServletResponse response, @RequestHeader("X-Auth-Token") String token, @RequestBody Brew brew) {
		log.info("Creating new brew [{}].", brew);
		repository.store(brew);
		response.setStatus(HttpServletResponse.SC_CREATED);
	}
	
}
