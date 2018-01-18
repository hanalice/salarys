package com.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.basic.BaseDAOImpl;
import com.dao.beans.Salary;
import com.dao.beans.User;
import com.dao.interfaces.SalaryDAO;
import com.service.beans.SearchVBean;
import com.util.StringUtil;

public class SalaryDAOImpl extends BaseDAOImpl<Salary> implements SalaryDAO {

	@Override
	public List<Salary> search(User user, SearchVBean search, int start, int max) {
		DetachedCriteria criteria = getDetachedCriteria();
		if (search != null) {
			if (StringUtils.isNotEmpty(search.getYear())) {
				criteria.add(Restrictions.eq("year", search.getYear()));
			}else{
				criteria.add(Restrictions.eq("year", StringUtil.dateParseStringY(new Date())));
			}
			if (StringUtils.isNotEmpty(search.getMonth())) {
				criteria.add(Restrictions.eq("month", search.getMonth()));
			}
			if (StringUtils.isNotEmpty(search.getName())) {
				criteria.add(Restrictions.like("name", search.getName()+"%"));
			}
		}
		if ("admin".equals(user.getAccount())){
			
		}else{
			criteria.add(Restrictions.eq("name", user.getName()));
		}
		criteria.addOrder(Order.asc("name"));
		criteria.addOrder(Order.asc("year"));
		criteria.addOrder(Order.asc("month"));
		return findByCriteria(criteria, start, max);
	}

	@Override
	public int count(User user, SearchVBean search) {
		DetachedCriteria criteria = getDetachedCriteria();
		if (search != null) {
			if (StringUtils.isNotEmpty(search.getYear())) {
				criteria.add(Restrictions.eq("year", search.getYear()));
			}else{
				criteria.add(Restrictions.eq("year", StringUtil.dateParseStringY(new Date())));
			}
			if (StringUtils.isNotEmpty(search.getMonth())) {
				criteria.add(Restrictions.eq("month", search.getMonth()));
			}
			if (StringUtils.isNotEmpty(search.getName())) {
				criteria.add(Restrictions.like("name", search.getName()+"%"));
			}
		}
		if ("admin".equals(user.getAccount())){
			
		}else{
			criteria.add(Restrictions.eq("name", user.getName()));
		}
		criteria.addOrder(Order.asc("name"));
		criteria.addOrder(Order.asc("year"));
		criteria.addOrder(Order.asc("month"));
		return countByCriteria(criteria);
	}

	@Override
	public List<Salary> getByUserName(String name, String year, String month) {
		DetachedCriteria criteria = getDetachedCriteria();
		criteria.add(Restrictions.eq("name", name));
		criteria.add(Restrictions.eq("year", year));
		criteria.add(Restrictions.eq("month", month));
		return findByCriteria(criteria);
	}


}
