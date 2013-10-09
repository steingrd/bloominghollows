package com.github.steingrd.bloominghollows.auth;

import org.hibernate.Criteria;

import com.github.steingrd.bloominghollows.system.Specification;

import static org.hibernate.criterion.Restrictions.eq;

public class AuthTokenSpecification implements Specification<AuthToken> {

	private String tokenString;

	@Override
	public Class<AuthToken> getType() {
		return AuthToken.class;
	}

	@Override
	public void populate(Criteria criteria) {
		if (tokenString != null) {
			criteria.add(eq("token", tokenString));
		}
	}

	public static AuthTokenSpecification tokenByString(String tokenString) {
		AuthTokenSpecification specification = new AuthTokenSpecification();
		specification.tokenString = tokenString;
		return specification;
	}

}
