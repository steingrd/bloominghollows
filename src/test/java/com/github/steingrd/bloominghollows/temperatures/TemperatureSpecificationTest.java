package com.github.steingrd.bloominghollows.temperatures;

import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.github.steingrd.bloominghollows.brews.Brew;
import com.github.steingrd.bloominghollows.config.DbConfiguration;
import com.github.steingrd.bloominghollows.config.PackageConfiguration;
import com.github.steingrd.bloominghollows.system.Repository;

import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperatures;
import static com.github.steingrd.bloominghollows.temperatures.TemperatureSpecification.allTemperaturesForBrewByDate;

import static org.fest.assertions.Assertions.assertThat;

import static org.joda.time.LocalDate.parse;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { DbConfiguration.class, PackageConfiguration.class })
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class TemperatureSpecificationTest {

	@Autowired
	private Repository repository;
	
	@Test
	public void shouldFindZeroTemperatures() throws Exception {
		assertThat(repository.find(allTemperatures())).hasSize(0);
	}
	
	@Test
	public void shouldStoreAndFindOneTemperature() throws Exception {
		Brew brew = new Brew("shouldStoreAndFindOneTemperature");
		repository.store(brew);
		
		repository.store(t(brew, "2013-10-01T01:00:00", 20));
		assertThat(repository.find(allTemperatures())).hasSize(1);
	}
	
	@Test
	public void shouldFindTemperatureForSingleDate() throws Exception {
		Brew brew = new Brew("shouldFindTemperatureForSingleDate");
		repository.store(brew);
		
		repository.store(t(brew, "2013-10-01T01:00:00", 20));
		repository.store(t(brew, "2013-10-02T01:00:00", 20));
		assertThat(repository.find(allTemperaturesForBrewByDate(brew, parse("2013-10-01")))).hasSize(1);
	}
	
	private static Temperature t(Brew brew, String date, int temp) {
		return new Temperature(brew, DateTime.parse(date), temp);
	}
	
}
