package com.constants;

/**
 * 类名称：Globals
 * 类描述：  全局变量定义
 * @author Administrator
 */
public final class Globals {
	/**
	 * 保存用户到SESSION标识
	 */
	public static String USER_SESSION = "user";
	
	/**
	 * 通信成功失败code
	 */
	public static int SUCCESS = 200;
	public static int ERROR = 500;
	
	/**
	 * 信息交互成功失败code
	 */
	public static String loginSucJsonStr = "{\"successFlag\": 200,\"status\" : \"success\"}";
	public static String loginFailJsonStr = "{\"successFlag\": 200,\"status\" : \"fail\"}";
	public static String errorJsonString = "{\"successFlag\": 500}";
	
	public final static String successJsonStr = "{\"status\" : \"success\"}";
	public final static String failJsonStr = "{\"status\" : \"fail\"}";
	
}
