package com.dao.interfaces;

import java.util.List;

import com.basic.BaseDAO;
import com.dao.beans.Salary;
import com.dao.beans.User;
import com.service.beans.SearchVBean;

public interface SalaryDAO extends BaseDAO<Salary>{

	public List<Salary> search(User user, SearchVBean search, int start, int max);
	
	public int count(User user, SearchVBean search);
	
	public List<Salary> getByUserName(String name, String year, String month);
}
