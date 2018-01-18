package com.dao.impl;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.basic.BaseDAOImpl;
import com.dao.beans.User;
import com.dao.interfaces.UserDAO;

public class UserDAOImpl extends BaseDAOImpl<User> implements UserDAO{

	@Override
	public User getByNameAndPasswd(String account, String password) {
		DetachedCriteria criteria = getDetachedCriteria();
		criteria.add(Restrictions.eq("account", account));
		criteria.add(Restrictions.eq("password", password));
		return getOne(findByCriteria(criteria));
	}

}
