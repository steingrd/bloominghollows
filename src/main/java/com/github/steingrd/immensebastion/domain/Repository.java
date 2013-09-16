package com.github.steingrd.immensebastion.domain;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Repository {

	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public <T> List<T> find(Specification<T> specification) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(specification.getType());
		specification.populate(criteria);
		return criteria.list();
	}
	
}