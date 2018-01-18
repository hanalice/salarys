package com.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.constants.Globals;
import com.dao.beans.User;
import com.service.interfaces.UserService;

@Controller
public class UserAction extends BaseAction{
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "userAction", params = "queryUser")
	public @ResponseBody Map<String, Object> queryUser(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> map = new HashMap<>();
		String account = getParameter("account", request);
		String password = getParameter("password", request);
		Map<String, Object> result = userService.queryUser(account, password);
		if ((int)result.get("status") == Globals.SUCCESS){
			User user = (User)result.get("data");
			request.getSession().setAttribute(Globals.USER_SESSION, user);
			map = userService.queryUser(account, password);
		}else{
			map.put("status", Globals.ERROR);
			map.put("data", null);
		}
		return map;
	}

}
