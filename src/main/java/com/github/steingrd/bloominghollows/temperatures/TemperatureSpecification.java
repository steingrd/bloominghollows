package com.github.steingrd.bloominghollows.temperatures;

import org.hibernate.Criteria;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import com.github.steingrd.bloominghollows.system.Specification;

import static org.hibernate.criterion.Restrictions.ge;
import static org.hibernate.criterion.Restrictions.le;

public class TemperatureSpecification implements Specification<Temperature> {

	private LocalDate date;

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
	}

	public static TemperatureSpecification allTemperatures() {
		return new TemperatureSpecification();
	}
	
	public static TemperatureSpecification allTemperaturesForDate(LocalDate date) {
		TemperatureSpecification spec = new TemperatureSpecification();
		spec.date = date;
		return spec;
	}

}
