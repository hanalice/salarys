package com.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.constants.Globals;
import com.dao.beans.User;

/**
 * 项目参数工具类
 * @author Administrator
 */
public class ResourceUtil {

//	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("");

//	/**
//	 * 获取session定义名称
//	 * @return
//	 */
//	public static final String getSessionattachmenttitle(String sessionName) {
//		return bundle.getString(sessionName);
//	}
	
	/**
	 * 获取session内登入的用户
	 * @return
	 */
	public static final User getSessionUser() {
		HttpSession session = ContextHolderUtils.getSession();
		session.setMaxInactiveInterval(-1);
		if (session.getAttributeNames().hasMoreElements()) {
			User user = (User) session.getAttribute(Globals.USER_SESSION);
			return user != null ? user : null;
		} else {
			return null;
		}
	}
	
	/**
	 * 删除session内用户信息
	 */
	public static final void rmSessionUser() {
		HttpSession session = ContextHolderUtils.getSession();
		session.removeAttribute(Globals.USER_SESSION);
	}
	
	/**
	 * 获得请求路径
	 * @param request
	 * @return
	 */
	public static String getRequestPath(HttpServletRequest request) {
		String requestPath = request.getRequestURI() + "?" + request.getQueryString();
		if (requestPath.indexOf("&") > -1) {// 去掉其他参数
			requestPath = requestPath.substring(0, requestPath.indexOf("&"));
		}
		requestPath = requestPath.substring(request.getContextPath().length() + 1);// 去掉项目路径
		return requestPath;
	}
}
