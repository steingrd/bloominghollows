package com.github.steingrd.bloominghollows.domain;

import org.hibernate.Criteria;


public interface Specification<T> {

	Class<T> getType();
	
	void populate(Criteria criteria);
	
}
