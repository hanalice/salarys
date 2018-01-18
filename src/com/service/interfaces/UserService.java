package com.service.interfaces;

import java.util.Map;

public interface UserService {

	public Map<String, Object> queryUser(String account, String password);
}
