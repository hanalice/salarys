package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.dao.beans.User;
import com.dao.interfaces.SalaryDAO;
import com.dao.interfaces.SalarySubsidyDAO;
import com.service.beans.PageVBean;
import com.service.beans.SearchVBean;
import com.service.interfaces.SalaryService;

public class SalaryServiceImpl implements SalaryService {
	
	@Autowired
	private SalaryDAO salaryDAO;
	@Autowired
	private SalarySubsidyDAO salarySubsidyDAO;


	@Override
	public PageVBean searchSalary(User user, SearchVBean search, int start, int max) {
		PageVBean result = new PageVBean();
		result.setResult(salaryDAO.search(user, search, start, max));
		result.setTotal(salaryDAO.count(user, search));
		return result;
	}

	@Override
	public PageVBean searchSubsidy(User user, SearchVBean search, int start, int max) {
		PageVBean result = new PageVBean();
		result.setResult(salarySubsidyDAO.search(user, search, start, max));
		result.setTotal(salarySubsidyDAO.count(user, search));
		return result;
	}


}
