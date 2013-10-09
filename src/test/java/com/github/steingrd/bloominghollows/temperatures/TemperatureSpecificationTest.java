package com.github.steingrd.bloominghollows.temperatures;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.steingrd.bloominghollows.config.AppConfiguration;
import com.github.steingrd.bloominghollows.system.Repository;

import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperatures;
import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperaturesForDate;

import static org.fest.assertions.Assertions.assertThat;

import static org.joda.time.LocalDate.parse;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfiguration.class })
@TransactionConfiguration(defaultRollback = true)
@Transactional
@WebAppConfiguration // TODO remove this by import DbConfiguration only
public class TemperatureSpecificationTest {

	@Autowired
	private Repository repository;
	
	@Test
	public void shouldFindZeroTemperatures() throws Exception {
		assertThat(repository.find(allTemperatures())).hasSize(0);
	}
	
	@Test
	public void shouldStoreAndFindOneTemperature() throws Exception {
		repository.store(t("2013-10-01T01:00:00", 20));
		assertThat(repository.find(allTemperatures())).hasSize(1);
	}
	
	@Test
	public void shouldFindTemperatureForSingleDate() throws Exception {
		repository.store(t("2013-10-01T01:00:00", 20));
		repository.store(t("2013-10-02T01:00:00", 20));
		assertThat(repository.find(allTemperaturesForDate(parse("2013-10-01")))).hasSize(1);
	}
	
	private static Temperature t(String date, int temp) {
		return new Temperature(DateTime.parse(date), temp);
	}
	
}
