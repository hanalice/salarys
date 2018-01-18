package com.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSONObject;
import com.util.ContextUtils;
import com.util.StringUtil;

public class BaseAction {
	
	
	protected <T> T getParamsObj(Class<T> c, HttpServletRequest request) {
		return StringUtil.obj2JsonObject(getParameter("params", request), c);
	}
	
	protected String getParameter(String paraName, HttpServletRequest request) {
		String parameter = request.getParameter(paraName);
		return (parameter != null && !parameter.equals("undefined")) ? parameter.trim() : "";
	}
	
	/**
	 * 把指定的JSON字符流写到client
	 * @param json 要写入的字符
	 * @param response
	 */
	protected void writeJSONObjectToClient(Object object, HttpServletResponse response) {
		writeJSONToClient(StringUtil.obj2JsonString(object != null ? object : new Object()), response);
	}

	/**
	 * 把指定的JSON字符流写到client
	 * @param jsonStr 要写入的字符
	 * @param contentType 输出格式
	 */
	protected void writeJSONToClient(String jsonStr, HttpServletResponse response) {
		PrintWriter os = null;
		try {
			byte[] json = (StringUtil.isNotBlank(jsonStr) ? jsonStr : StringUtil.obj2JsonString(new JSONObject())).getBytes("UTF-8");
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
			}
		}
	}
	
	/**
	 * 读取js文件脚本（多用于读取页面布局所使用的javaScript脚本，控制前台调用显示）
	 * @param names 多个js文件名组合
	 * @return
	 */
	protected void writeScriptToClient(String names, HttpServletResponse response) {
		PrintWriter os = null;
		try {
			String[] nameArr = names.split(",");
			StringBuffer sb = new StringBuffer();
			for (String name : nameArr) {
				sb.append(FileUtils.readFileToString(new File(getRealPath() + "\\script\\view\\button\\" + name + ".js"), "utf-8"));
			}
			response.setContentType("application/text;charset=utf-8");
			os = response.getWriter();
			os.println(sb.toString());
			os.flush();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	
	}
	
	/**
	 * web服务绝对路径
	 * @return
	 */
	protected String getRealPath() {
		return ContextUtils.getSession().getServletContext().getRealPath("");
	}
	
	/**
	 * 页面显示图片或者文件
	 * @param fileName 本地文件名
	 * @param response
	 */
	protected void writeFileToClient(String fileName, HttpServletResponse response) {
		OutputStream os = null;
		try {
			byte[] fileBytes = FileUtils.readFileToByteArray(new File("F:\\mydomain\\" + fileName));
			response.setContentType("text/html");
			os = response.getOutputStream();
			os.write(fileBytes);
			os.flush();
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
