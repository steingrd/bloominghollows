package com.github.steingrd.bloominghollows.brews;

import org.hibernate.Criteria;

import com.github.steingrd.bloominghollows.system.Specification;

public class BrewSpecification implements Specification<Brew> {

	@Override
	public Class<Brew> getType() {
		return Brew.class;
	}

	@Override
	public void populate(Criteria criteria) {
	}

	public static Specification<Brew> allBrews() {
		return new BrewSpecification();
	}

}
