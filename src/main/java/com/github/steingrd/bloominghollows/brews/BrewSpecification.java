package com.github.steingrd.bloominghollows.brews;

import org.hibernate.Criteria;

import com.github.steingrd.bloominghollows.system.Specification;

import static org.hibernate.criterion.Restrictions.eq;

public class BrewSpecification implements Specification<Brew> {

	private Long brewId;

	@Override
	public Class<Brew> getType() {
		return Brew.class;
	}

	@Override
	public void populate(Criteria criteria) {
		if (brewId != null) {
			criteria.add(eq("id", brewId));
		}
	}

	public static Specification<Brew> allBrews() {
		return new BrewSpecification();
	}

	public static Specification<Brew> brewWithId(Long brewId) {
		BrewSpecification specification = new BrewSpecification();
		specification.brewId = brewId;
		return specification;
	}

}
