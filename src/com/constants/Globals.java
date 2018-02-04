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
	
	public static String UPLOAD_SUCCESS = "200";
	public static String UPLOAD_ERROR = "500";
	
	/**
	 * 信息交互成功失败code
	 */
	public static String loginSucJsonStr = "{\"successFlag\": 200,\"status\" : \"success\"}";
	public static String loginFailJsonStr = "{\"successFlag\": 200,\"status\" : \"fail\"}";
	public static String errorJsonString = "{\"successFlag\": 500}";
	
	public final static String successJsonStr = "{\"status\" : \"success\"}";
	public final static String failJsonStr = "{\"status\" : \"fail\"}";
	
	public static final String[] SALARY_COLUMES = { "name", "year", "month",
		"gangwei", "xinji", "jiaotong", "one_child", "liangyou", "poison",
		"jiaxiang", "increase", "annual", "yanglao", "gongjijin", "yiliao", "shiye",
		"personal_income_tax", "gonghui", "kouxiang", "decrease",
		"total", "remark" };
	public static final String[] SUBSIDY_COLUMES = { "name", "year", "month",
		"gangwei", "gongzuoliang", "jiaxiang1", "jiaxiang2", "holiday",
		"increase", "personal_income_tax", "kouxiang1", "kouxiang2",
		"decrease", "total", "remark" };
	
}
