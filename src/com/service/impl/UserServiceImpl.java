package com.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.constants.Globals;
import com.dao.beans.User;
import com.dao.interfaces.UserDAO;
import com.service.interfaces.UserService;
import com.util.StringUtil;

public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;

	@Override
	public Map<String, Object> queryUser(String account, String password) {
		Map<String, Object> map = new HashMap<>();
		if (StringUtil.isNotEmpty(account) && StringUtil.isNotEmpty(password)){
			User user = userDAO.getByNameAndPasswd(account, password);
			if (StringUtil.isNotEmpty(user)) {
				map.put("status", Globals.SUCCESS);
				map.put("data", user);
			}else {
				map.put("status", Globals.ERROR);
				map.put("data", null);
			}
		}else{
			map.put("status", Globals.ERROR);
			map.put("data", null);
		}
		return map;
	}

}
