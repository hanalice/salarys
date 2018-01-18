package com.dao.interfaces;

import java.util.List;

import com.basic.BaseDAO;
import com.dao.beans.SalarySubsidy;
import com.dao.beans.User;
import com.service.beans.SearchVBean;

public interface SalarySubsidyDAO extends BaseDAO<SalarySubsidy> {

	public List<SalarySubsidy> search(User user, SearchVBean search, int start, int max);
	
	public int count(User user, SearchVBean search);
	
	public List<SalarySubsidy> getByUserName(String name, String year, String month);
}
