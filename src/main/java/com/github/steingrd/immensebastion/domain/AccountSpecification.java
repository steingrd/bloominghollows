package com.github.steingrd.immensebastion.domain;

import org.hibernate.Criteria;

public class AccountSpecification implements Specification<Account> {

	public static Specification<Account> allAccounts() {
		return new AccountSpecification();
	}

	@Override
	public Class<Account> getType() {
		return Account.class;
	}

	@Override
	public void populate(Criteria criteria) {
	}

}
