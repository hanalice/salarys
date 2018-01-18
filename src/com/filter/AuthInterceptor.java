package com.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.dao.beans.User;
import com.util.ResourceUtil;

/**
 * 权限拦截器
 * @author Administrator
 */
public class AuthInterceptor implements HandlerInterceptor {
	 
	private List<String> excludeUrls;

	public List<String> getExcludeUrls() {
		return excludeUrls;
	}

	public void setExcludeUrls(List<String> excludeUrls) {
		this.excludeUrls = excludeUrls;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
	}

	/**
	 * 在controller前拦截
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		String requestPath = ResourceUtil.getRequestPath(request);// 用户访问的资源地址
		User user = ResourceUtil.getSessionUser();
		 
		if (excludeUrls.contains(requestPath)) {
			//存在免拦截的请求直接进行后续操作
			return true;
		} else if (user != null) {
			//session保存了登入用户信息可做后续操作
			return true;
		} else {
			//登入后的超时用户请求拦截
			JSONObject jsonData = new JSONObject();
			jsonData.put("invalidSession", true);
			PrintWriter os = null;
			try {
				byte[] json = jsonData.toString().getBytes("UTF-8");
				response.setContentType("application/json;charset=utf-8");
				os = response.getWriter();
				os.println(new String(json, "UTF-8"));
				os.flush();
				response.flushBuffer();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (os != null) {
					os.close();
					os = null;
				}
			}
			return false;
		}
	}

}
