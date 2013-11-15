package com.github.steingrd.bloominghollows.temperatures;

import org.hibernate.Criteria;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.github.steingrd.bloominghollows.brews.Brew;
import com.github.steingrd.bloominghollows.system.Specification;

import static org.hibernate.criterion.Restrictions.eq;
import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Restrictions.le;

public class TemperatureSpecification implements Specification<Temperature> {

	private LocalDate date;
	private Brew brew;

	@Override
	public Class<Temperature> getType() {
		return Temperature.class;
	}

	@Override
	public void populate(Criteria criteria) {
		if (date != null) {
			DateTime startOfDay = date.toDateTime(LocalTime.MIDNIGHT);
			DateTime endOfDay = date.plusDays(1).toDateTime(LocalTime.MIDNIGHT);
			criteria.add(ge("timestamp", startOfDay));
			criteria.add(le("timestamp", endOfDay));
		}
		
		if (brew != null) {
			criteria.add(eq("brew", brew));
		}
	}

	public static TemperatureSpecification allTemperatures() {
		return new TemperatureSpecification();
	}
	
	public static TemperatureSpecification allTemperaturesForBrewByDate(Brew brew, LocalDate date) {
		TemperatureSpecification spec = new TemperatureSpecification();
		spec.brew = brew;
		spec.date = date;
		return spec;
	}

	public static Specification<Temperature> allTemperaturesForBrew(Brew brew) {
		TemperatureSpecification spec = new TemperatureSpecification();
		spec.brew = brew;
		return spec;
	}

}
