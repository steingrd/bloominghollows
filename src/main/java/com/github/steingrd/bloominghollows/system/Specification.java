package com.github.steingrd.bloominghollows.system;

import org.hibernate.Criteria;


public interface Specification<T> {

	Class<T> getType();
	
	void populate(Criteria criteria);
	
}
