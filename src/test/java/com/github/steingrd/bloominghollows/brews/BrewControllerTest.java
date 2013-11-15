package com.github.steingrd.bloominghollows.brews;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.github.steingrd.bloominghollows.auth.AuthToken;
import com.github.steingrd.bloominghollows.config.AppConfiguration;
import com.github.steingrd.bloominghollows.system.Repository;

import static com.github.steingrd.bloominghollows.brews.BrewSpecification.allBrews;

import static org.fest.assertions.Assertions.assertThat;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfiguration.class })
@TransactionConfiguration(defaultRollback = true)
@Transactional
@WebAppConfiguration
public class BrewControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext appContext;
	
	@Autowired
	private Repository repository;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();
        repository.store(new AuthToken("valid.token"));
    }
	
	@Test
	public void shouldReturnEmptyListOfBrews() throws Exception {
		mockMvc.perform(get("/brews"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	public void shouldReturnListOfBrews() throws Exception {
		repository.store(new Brew("test 1"));
		repository.store(new Brew("test 2"));
		repository.store(new Brew("test 3"));
		
		mockMvc.perform(get("/brews"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void shouldReturnStatusBadRequestIfAuthTokenIsNotPresented() throws Exception {
		String json = "{\"name\": \"test brew!\"}";
		
		assertThat(repository.find(allBrews())).hasSize(0);
		
		mockMvc.perform(post("/brews").content(json).contentType(APPLICATION_JSON))
			.andExpect(status().isBadRequest());
		
		assertThat(repository.find(allBrews())).hasSize(0);
	}
	
	@Test
	public void shouldCreateNewBrewAndReturnStatusCreated() throws Exception {
		String json = "{\"name\": \"test brew!\"}";
		
		assertThat(repository.find(allBrews())).hasSize(0);
		
		mockMvc.perform(post("/brews").content(json).contentType(APPLICATION_JSON).header("X-Auth-Token", "valid.token"))
			.andExpect(status().isCreated());
		
		List<Brew> brews = repository.find(allBrews());
		assertThat(brews).hasSize(1);
		assertThat(brews.get(0).getName()).isEqualTo("test brew!");
	}
	
	
}
