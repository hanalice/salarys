package com.service.interfaces;

import com.dao.beans.User;
import com.service.beans.PageVBean;
import com.service.beans.SearchVBean;

public interface SalaryService{
	
	public PageVBean searchSalary(User user, SearchVBean search, int start, int max);
	
	public PageVBean searchSubsidy(User user, SearchVBean search, int start, int max);
	
}
