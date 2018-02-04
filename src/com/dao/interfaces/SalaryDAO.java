package com.dao.interfaces;

import java.util.List;

import com.basic.BaseDAO;
import com.dao.beans.Salary;
import com.dao.beans.User;
import com.service.beans.SearchVBean;

public interface SalaryDAO extends BaseDAO<Salary>{
    /**
     * support month range serach, the month format: 1-3, you can search the month 1,2,3 data
     * @param user
     * @param search
     * @param start
     * @param max
     * @return
     */
	public List<Salary> search(User user, SearchVBean search, int start, int max);
	
	public int count(User user, SearchVBean search);
	
	public List<Salary> getByUserName(String name, String year, String month);
}
