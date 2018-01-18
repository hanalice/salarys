package com.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constants.Globals;
import com.dao.beans.User;
import com.service.beans.PageVBean;
import com.service.beans.SearchVBean;
import com.service.interfaces.SalaryService;
import com.util.StringUtil;

@Controller
public class SalaryAction extends BaseAction{
	
	@Autowired
	private SalaryService salaryService;
	
	@RequestMapping(value = "salaryAction", params = "querySalary")
	public @ResponseBody PageVBean querySalary(HttpServletRequest request, HttpServletResponse response){
		User user = (User) request.getSession().getAttribute(Globals.USER_SESSION);
		SearchVBean search = StringUtil.obj2JsonObject(getParameter("condition", request), SearchVBean.class);
		int[] page = getPageParameter(request);
		PageVBean result = salaryService.searchSalary(user, search, (page[1] - 1) * page[0], page[0]);
		result.setPage(page);
		return result;
	}
	
	@RequestMapping(value = "salaryAction", params = "querySubsidy")
	public @ResponseBody PageVBean querySubsidy(HttpServletRequest request, HttpServletResponse response){
		User user = (User) request.getSession().getAttribute(Globals.USER_SESSION);
		SearchVBean search = StringUtil.obj2JsonObject(getParameter("condition", request), SearchVBean.class);
		int[] page = getPageParameter(request);
		PageVBean result = salaryService.searchSubsidy(user, search, (page[1] - 1) * page[0], page[0]);
		result.setPage(page);
		return result;
	}
	
	private int[] getPageParameter(HttpServletRequest request) {
		int max = getParameter(request, "pageBean.pageSize");
		Integer pageNo = getParameter(request, "pageBean.pageNo");
		if (pageNo == null) {
			pageNo = 1;
		}
		return new int[] { max, pageNo };
	}

	private Integer getParameter(HttpServletRequest request, String key) {
		String val = request.getParameter(key);
		if (val != null) {
			return Integer.parseInt(val);
		}
		return null;
	}
	
	
}
