package com.github.steingrd.immensebastion.domain;

import static org.hibernate.criterion.Restrictions.eq;

import org.hibernate.Criteria;

public class AccountSpecification implements Specification<Account> {

	public static Specification<Account> allAccounts() {
		return new AccountSpecification();
	}

	public static Specification<Account> accountWithId(Long id) {
		AccountSpecification specification = new AccountSpecification();
		specification.id = id;
		return specification;
	}

	private Long id;
	
	@Override
	public Class<Account> getType() {
		return Account.class;
	}

	@Override
	public void populate(Criteria criteria) {
		if (id != null) {
			criteria.add(eq("id", id));
		}
	}

}
