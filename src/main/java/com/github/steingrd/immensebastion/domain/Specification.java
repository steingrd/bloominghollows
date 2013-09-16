package com.github.steingrd.immensebastion.domain;

import org.hibernate.Criteria;


public interface Specification<T> {

	Class<T> getType();
	
	void populate(Criteria criteria);
	
}
