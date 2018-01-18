package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.constants.Globals;
import com.dao.beans.User;

@SuppressWarnings("serial")
public class LoginFilter extends HttpServlet implements Filter {
	protected FilterConfig filterConfig;

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		System.out.println(request.getRequestURI());
		User user = (User) request.getSession().getAttribute(Globals.USER_SESSION);
		if (user != null) {
			arg2.doFilter(arg0, arg1);
		} else {
			String noLogin = filterConfig.getInitParameter("noLogin");
			response.sendRedirect(request.getContextPath() + noLogin);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

}
