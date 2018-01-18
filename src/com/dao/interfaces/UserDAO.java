package com.dao.interfaces;

import com.basic.BaseDAO;
import com.dao.beans.User;

public interface UserDAO extends BaseDAO<User>{

	public User getByNameAndPasswd(String name, String password);
}
