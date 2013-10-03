package com.github.steingrd.bloominghollows.temperatures;

import org.hibernate.Criteria;

import com.github.steingrd.bloominghollows.system.Specification;

public class TemperatureSpecification implements Specification<Temperature> {

	@Override
	public Class<Temperature> getType() {
		return Temperature.class;
	}

	@Override
	public void populate(Criteria criteria) {
	}

	public static TemperatureSpecification allTemperatures() {
		return new TemperatureSpecification();
	}

}
