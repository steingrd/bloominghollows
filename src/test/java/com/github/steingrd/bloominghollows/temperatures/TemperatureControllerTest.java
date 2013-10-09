package com.github.steingrd.bloominghollows.temperatures;

import org.joda.time.DateTime;
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

import com.github.steingrd.bloominghollows.config.AppConfiguration;
import com.github.steingrd.bloominghollows.system.Repository;

import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperatures;

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
public class TemperatureControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext appContext;
	
	@Autowired
	private Repository repository;
	
	@Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(appContext).build();
    }

	@Test
	public void shouldStatusReturnBadRequestForEmptyRequestBody() throws Exception {
		mockMvc.perform(post("/temperatures").contentType(APPLICATION_JSON)).andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldReturnStatusAcceptedWhenTemperatureIsPersisted() throws Exception {
		String json = "{\"timestamp\": \"2013-10-01T12:00:00\", \"temperature\": 20}";
		
		mockMvc.perform(post("/temperatures").content(json).contentType(APPLICATION_JSON))
			.andExpect(status().isAccepted());

		assertThat(repository.find(allTemperatures())).hasSize(1);
	}
	
	@Test
	public void shouldListAllTemperatures() throws Exception {
		repository.store(
				t("2013-10-01T01:00:00", 20), 
				t("2013-10-01T01:00:00", 21), 
				t("2013-10-02T01:00:00", 22));
		
		mockMvc.perform(get("/temperatures"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)));
	}
	
	@Test
	public void shouldListAllTemperaturesForGivenDay() throws Exception {
		repository.store(
				t("2013-10-01T01:00:00", 20), 
				t("2013-10-01T01:01:00", 21), 
				t("2013-10-02T01:00:00", 22));
		
		mockMvc.perform(get("/temperatures?date=2013-10-01"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(2)));
	}
	
	private static Temperature t(String date, int temp) {
		return new Temperature(DateTime.parse(date), temp);
	}
	
}
