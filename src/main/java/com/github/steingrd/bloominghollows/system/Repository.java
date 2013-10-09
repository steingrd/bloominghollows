package com.github.steingrd.bloominghollows.system;

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
		return criteriaFor(specification).list();
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Specification<T> specification) {
		return (T) criteriaFor(specification).uniqueResult();
	}	

	public <T> void store(@SuppressWarnings("unchecked") T... objects) {
		for (T o : objects) {
			sessionFactory.getCurrentSession().saveOrUpdate(o);
		}
	}
	
	private <T> Criteria criteriaFor(Specification<T> specification) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(specification.getType());
		specification.populate(criteria);
		return criteria;
	}

}
