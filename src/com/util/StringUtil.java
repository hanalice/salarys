package com.util;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

/**
 * 字符串处理及转换工具类
 * @author Administrator
 */
@SuppressWarnings("rawtypes")
public class StringUtil extends StringUtils {
	private static Pattern numericPattern = Pattern.compile("^[0-9\\-]+$");
	private static Pattern numericStringPattern = Pattern.compile("^[0-9\\-\\-]+$");
	private static Pattern floatNumericPattern = Pattern.compile("^[0-9\\-\\.]+$");
	private static Pattern abcPattern = Pattern.compile("^[a-z|A-Z]+$");
	public static final String splitStrPattern = ",|，|;|；|、|\\.|。|-|_|\\(|\\)|\\[|\\]|\\{|\\}|\\\\|/| |　|\"";

	/**
	 * @Description:Json串封装为对象
	 * @param json
	 * @param beanClass
	 * @return
	 */
	public static <T> T obj2JsonObject(String json, Class<T> beanClass) {
		return JSON.parseObject(json, beanClass);
	}
	
	/**
	 * @Description:Json串封装为对象集合
	 * @param json
	 * @param beanClass
	 * @return
	 */
	public static <T> List<T> obj2JsonArray(String json, Class<T> beanClass) {
		return JSON.parseArray(json, beanClass);
	}
	
	/**
	 * @Description:对象转换为串
	 * @param obj
	 * @return
	 */
	public static String obj2JsonString(Object obj) {
		return JSON.toJSONString(obj);
	}
	
	/**
	 * 判断是否数字表示
	 * @param src 源字符串
	 * @return 是否数字的标志
	 */
	public static boolean isNumeric(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = numericPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * 判断是否数字表示
	 * @param src 源字符串
	 * @return 是否数字的标志
	 */
	public static boolean isNumericString(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = numericStringPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * 判断是否纯字母组合
	 * @param src 源字符串
	 * @return 是否纯字母组合的标志
	 */
	public static boolean isABC(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = abcPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * 判断是否浮点数字表示
	 * @param src 源字符串
	 * @return 是否数字的标志
	 */
	public static boolean isFloatNumeric(String src) {
		boolean return_value = false;
		if (src != null && src.length() > 0) {
			Matcher m = floatNumericPattern.matcher(src);
			if (m.find()) {
				return_value = true;
			}
		}
		return return_value;
	}

	/**
	 * 把string array or list用给定的符号symbol连接成一个字符串
	 * @param array
	 * @param symbol
	 * @return
	 */
	public static String joinString(List array, String symbol) {
		String result = "";
		if (array != null) {
			for (int i = 0; i < array.size(); i++) {
				String temp = array.get(i).toString();
				if (temp != null && temp.trim().length() > 0)
					result += (temp + symbol);
			}
			if (result.length() > 1)
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	public static String subStringNotEncode(String subject, int size) {
		if (subject != null && subject.length() > size) {
			subject = subject.substring(0, size) + "...";
		}
		return subject;
	}

	/**
	 * 截取字符串　超出的字符用symbol代替 　　
	 * @param len 字符串长度　长度计量单位为一个utf-8汉字　　两个英文字母计算为一个单位长度
	 * @param str
	 * @param symbol
	 * @return
	 */
	public static String getLimitLengthString(String str, int len, String symbol) {
		int iLen = len * 2;
		int counterOfDoubleByte = 0;
		String strRet = "";
		try {
			if (str != null) {
				byte[] b = str.getBytes("utf-8");
				if (b.length <= iLen) {
					return str;
				}
				for (int i = 0; i < iLen; i++) {
					if (b[i] < 0) {
						counterOfDoubleByte++;
					}
				}
				if (counterOfDoubleByte % 2 == 0) {
					strRet = new String(b, 0, iLen, "utf-8") + symbol;
					return strRet;
				} else {
					strRet = new String(b, 0, iLen - 1, "utf-8") + symbol;
					return strRet;
				}
			} else {
				return "";
			}
		} catch (Exception ex) {
			return str.substring(0, len);
		} finally {
			strRet = null;
		}
	}

	/**
	 * 截取字符串　超出的字符用symbol代替 　　
	 * @param len 字符串长度　长度计量单位为一个utf-8汉字　　两个英文字母计算为一个单位长度
	 * @param str
	 * @param symbol
	 * @return12
	 */
	public static String getLimitLengthString(String str, int len) {
		return getLimitLengthString(str, len, "...");
	}

	/**
	 * 截取字符，不转码
	 * @param subject
	 * @param size
	 * @return
	 */
	public static String subStrNotEncode(String subject, int size) {
		if (subject.length() > size) {
			subject = subject.substring(0, size);
		}
		return subject;
	}

	/**
	 * 把string array or list用给定的符号symbol连接成一个字符串
	 * @param array
	 * @param symbol
	 * @return
	 */
	public static String joinString(String[] array, String symbol) {
		String result = "";
		if (array != null) {
			for (int i = 0; i < array.length; i++) {
				String temp = array[i];
				if (temp != null && temp.trim().length() > 0)
					result += (temp + symbol);
			}
			if (result.length() > 1)
				result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * 取得字符串的实际长度（考虑了汉字的情况）
	 * @param SrcStr 源字符串
	 * @return 字符串的实际长度
	 */
	public static int getStringLen(String SrcStr) {
		int return_value = 0;
		if (SrcStr != null) {
			char[] theChars = SrcStr.toCharArray();
			for (int i = 0; i < theChars.length; i++) {
				return_value += (theChars[i] <= 255) ? 1 : 2;
			}
		}
		return return_value;
	}

	/**
	 * 检查数据串中是否包含非法字符集
	 * @param str
	 * @return [true]|[false] 包含|不包含
	 */
	public static boolean check(String str) {
		String sIllegal = "'\"";
		int len = sIllegal.length();
		if (null == str)
			return false;
		for (int i = 0; i < len; i++) {
			if (str.indexOf(sIllegal.charAt(i)) != -1)
				return true;
		}

		return false;
	}

	/***************************************************************************
	 * getHideEmailPrefix - 隐藏邮件地址前缀。
	 * @param email - EMail邮箱地址 例如: tangmin@ibm.com 等等...
	 * @return 返回已隐藏前缀邮件地址, 如 *********@ibm.com.
	 **************************************************************************/
	public static String getHideEmailPrefix(String email) {
		if (null != email) {
			int index = email.lastIndexOf('@');
			if (index > 0) {
				email = repeat("*", index).concat(email.substring(index));
			}
		}
		return email;
	}

	/***************************************************************************
	 * repeat - 通过源字符串重复生成N次组成新的字符串。
	 * @param src - 源字符串 例如: 空格(" "), 星号("*"), "浙江" 等等...
	 * @param num - 重复生成次数
	 * @return 返回已生成的重复字符串
	 **************************************************************************/
	public static String repeat(String src, int num) {
		StringBuffer s = new StringBuffer();
		for (int i = 0; i < num; i++)
			s.append(src);
		return s.toString();
	}

	/**
	 * 根据指定的字符把源字符串分割成一个数组
	 * @param src
	 * @return
	 */
	public static List<String> parseString2ListByCustomerPattern(String pattern, String src) {

		if (src == null)
			return null;
		List<String> list = new ArrayList<String>();
		String[] result = src.split(pattern);
		for (int i = 0; i < result.length; i++) {
			list.add(result[i]);
		}
		return list;
	}

	/**
	 * 根据指定的字符把源字符串分割成一个数组
	 * @param src
	 * @return
	 */
	public static List<String> parseString2ListByPattern(String src) {
		String pattern = "，|,|、|。";
		return parseString2ListByCustomerPattern(pattern, src);
	}

	/**
	 * 判断是否是空字符串 null和"" 都返回 true
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (s != null && !s.equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 自定义的分隔字符串函数 例如: 1,2,3 =>[1,2,3] 3个元素 ,2,3=>[,2,3] 3个元素 ,2,3,=>[,2,3,] 4个元素 ,,,=>[,,,] 4个元素
	 * 5.22算法修改，为提高速度不用正则表达式 两个间隔符,,返回""元素
	 * @param split 分割字符 默认,
	 * @param src 输入字符串
	 * @return 分隔后的list
	 * @author Robin
	 */
	public static List<String> splitToList(String split, String src) {
		// 默认,
		String sp = ",";
		if (split != null && split.length() == 1) {
			sp = split;
		}
		List<String> r = new ArrayList<String>();
		int lastIndex = -1;
		int index = src.indexOf(sp);
		if (-1 == index && src != null) {
			r.add(src);
			return r;
		}
		while (index >= 0) {
			if (index > lastIndex) {
				r.add(src.substring(lastIndex + 1, index));
			} else {
				r.add("");
			}

			lastIndex = index;
			index = src.indexOf(sp, index + 1);
			if (index == -1) {
				r.add(src.substring(lastIndex + 1, src.length()));
			}
		}
		return r;
	}

	/**
	 * 把 名=值 参数表转换成字符串 (a=1,b=2 =>a=1&b=2)
	 * @param map
	 * @return
	 */
	public static String linkedHashMapToString(LinkedHashMap<String, String> map) {
		if (map != null && map.size() > 0) {
			String result = "";
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String name = (String) it.next();
				String value = (String) map.get(name);
				result += (result.equals("")) ? "" : "&";
				result += String.format("%s=%s", name, value);
			}
			return result;
		}
		return null;
	}

	/**
	 * 解析字符串返回 名称=值的参数表 (a=1&b=2 => a=1,b=2)
	 * @see test.koubei.util.StringUtilTest#testParseStr()
	 * @param str
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> toLinkedHashMap(String str) {
		if (str != null && !str.equals("") && str.indexOf("=") > 0) {
			LinkedHashMap result = new LinkedHashMap();

			String name = null;
			String value = null;
			int i = 0;
			while (i < str.length()) {
				char c = str.charAt(i);
				switch (c) {
				case 61: // =
					value = "";
					break;
				case 38: // &
					if (name != null && value != null && !name.equals("")) {
						result.put(name, value);
					}
					name = null;
					value = null;
					break;
				default:
					if (value != null) {
						value = (value != null) ? (value + c) : "" + c;
					} else {
						name = (name != null) ? (name + c) : "" + c;
					}
				}
				i++;

			}

			if (name != null && value != null && !name.equals("")) {
				result.put(name, value);
			}

			return result;

		}
		return null;
	}

	/**
	 * 根据输入的多个解释和下标返回一个值
	 * @param captions 例如:"无,爱干净,一般,比较乱"
	 * @param index 1
	 * @return 一般
	 */
	public static String getCaption(String captions, int index) {
		if (index > 0 && captions != null && !captions.equals("")) {
			String[] ss = captions.split(",");
			if (ss != null && ss.length > 0 && index < ss.length) {
				return ss[index];
			}
		}
		return null;
	}

	/**
	 * 在sou中是否存在finds 如果指定的finds字符串有一个在sou中找到,返回true;
	 * @param sou
	 * @param find
	 * @return
	 */
	public static boolean strPos(String sou, String... finds) {
		if (sou != null && finds != null && finds.length > 0) {
			for (int i = 0; i < finds.length; i++) {
				if (sou.indexOf(finds[i]) > -1)
					return true;
			}
		}
		return false;
	}

	public static boolean strPos(String sou, List<String> finds) {
		if (sou != null && finds != null && finds.size() > 0) {
			for (String s : finds) {
				if (sou.indexOf(s) > -1)
					return true;
			}
		}
		return false;
	}

	public static boolean strPos(String sou, String finds) {
		List<String> t = splitToList(",", finds);
		return strPos(sou, t);
	}

	/**
	 * 判断两个字符串是否相等 如果都为null则判断为相等,一个为null另一个not null则判断不相等 否则如果s1=s2则相等
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean equals(String s1, String s2) {
		if (StringUtil.isEmpty(s1) && StringUtil.isEmpty(s2)) {
			return true;
		} else if (!StringUtil.isEmpty(s1) && !StringUtil.isEmpty(s2)) {
			return s1.equals(s2);
		}
		return false;
	}

	/**
	 * 把xml转为object
	 * @param xml
	 * @return Object
	 */
	@SuppressWarnings("resource")
	public static Object xmlToObject(String xml) {
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(xml.getBytes("UTF8"));
			XMLDecoder decoder = new XMLDecoder(new BufferedInputStream(in));
			return decoder.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * 字符串替换
	 * 
	 * @param str 源字符串
	 * @param sr 正则表达式样式
	 * @param sd 替换文本
	 * @return 结果串
	 */
	public static String stringReplace(String str, String sr, String sd) {
		String regEx = sr;
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(str);
		str = m.replaceAll(sd);
		return str;
	}

	/**
	 * 
	 * 根据正则表达式分割字符串
	 * @param str 源字符串
	 * @param ms 正则表达式
	 * @return 目标字符串组
	 */
	public static String[] splitString(String str, String ms) {
		String regEx = ms;
		Pattern p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		String[] sp = p.split(str);
		return sp;
	}

	/**
	 * 纯粹文本替换
	 * @param s 源字符串
	 * @param sf 子字符串
	 * @param sb 替换字符串
	 * @return 替换后的字符串
	 */
	public static String replaceAll(String s, String sf, String sb) {
		int i = 0, j = 0;
		int l = sf.length();
		boolean b = true;
		boolean o = true;
		String str = "";
		do {
			j = i;
			i = s.indexOf(sf, j);
			if (i > j) {
				str += s.substring(j, i);
				str += sb;
				i += l;
				o = false;
			} else {
				str += s.substring(j);
				b = false;
			}
		} while (b);
		if (o) {
			str = s;
		}
		return str;
	}

	/**
	 * 判断是否与给定字符串样式匹配
	 * @param str 字符串
	 * @param pattern 正则表达式样式
	 * @return 是否匹配是true,否false
	 */
	public static boolean isMatch(String str, String pattern) {
		Pattern pattern_hand = Pattern.compile(pattern);
		Matcher matcher_hand = pattern_hand.matcher(str);
		boolean b = matcher_hand.matches();
		return b;
	}

	/**
	 * 截取字符串
	 * @param s 源字符串
	 * @param jmp 跳过jmp
	 * @param sb 取在sb
	 * @param se 于se
	 * @return 之间的字符串
	 */
	public static String subStringExe(String s, String jmp, String sb, String se) {
		if (isEmpty(s)) {
			return "";
		}
		int i = s.indexOf(jmp);
		if (i >= 0 && i < s.length()) {
			s = s.substring(i + 1);
		}
		i = s.indexOf(sb);
		if (i >= 0 && i < s.length()) {
			s = s.substring(i + 1);
		}
		if (se == "") {
			return s;
		} else {
			i = s.indexOf(se);
			if (i >= 0 && i < s.length()) {
				s = s.substring(i + 1);
			}
			return s;
		}
	}

	/**
	 *************************************************************************
	 * 用要通过URL传输的内容进行编码
	 * @param 源字符串
	 * @return 经过编码的内容
	 *************************************************************************
	 */
	public static String URLEncode(String src) {
		String return_value = "";
		try {
			if (src != null) {
				return_value = URLEncoder.encode(src, "utf-8");

			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return_value = src;
		}

		return return_value;
	}

	/**
	 * yahoo首页中切割字符串.
	 * @param str
	 * @return
	 */
	public static String subYhooString(String subject, int size) {
		subject = subject.substring(1, size);
		return subject;
	}

	public static String subYhooStringDot(String subject, int size) {
		subject = subject.substring(1, size) + "...";
		return subject;
	}

	/**
	 * 泛型方法(通用)，把list转换成以“,”相隔的字符串 调用时注意类型初始化（申明类型） 如：List<Integer> intList = new ArrayList<Integer>(); 调用方法：StringUtil.listTtoString(intList); 效率：list中4条信息，1000000次调用时间为850ms左右
	 * @param <T> 泛型
	 * @param list列表
	 * @return 以“,”相隔的字符串
	 */
	public static <T> String listTtoString(List<T> list) {
		if (list == null || list.size() < 1)
			return "";
		Iterator<T> i = list.iterator();
		if (!i.hasNext())
			return "";
		StringBuilder sb = new StringBuilder();
		for (;;) {
			T e = i.next();
			sb.append(e);
			if (!i.hasNext())
				return sb.toString();
			sb.append(",");
		}
	}

	/**
	 * 把整形数组转换成以“,”相隔的字符串
	 * @param a 数组a
	 * @return 以“,”相隔的字符串
	 */
	public static String intArraytoString(int[] a) {
		if (a == null)
			return "";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "";
		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.toString();
			b.append(",");
		}
	}
	
	/**
	 * 把字符串数组转换成以“,”相隔的字符串
	 * @param a 数组a
	 * @return 以“,”相隔的字符串
	 */
	public static String charArraytoString(String[] a) {
		if (a == null)
			return "";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "";
		StringBuilder b = new StringBuilder();
		for (int i = 0;; i++) {
			b.append(a[i]);
			if (i == iMax)
				return b.toString();
			b.append(",");
		}
	}

	/**
	 * 判断文字内容重复
	 */
	public static boolean isContentRepeat(String content) {
		int similarNum = 0;
		int forNum = 0;
		int subNum = 0;
		int thousandNum = 0;
		String startStr = "";
		String nextStr = "";
		boolean result = false;
		float endNum = (float) 0.0;
		if (content != null && content.length() > 0) {
			if (content.length() % 1000 > 0)
				thousandNum = (int) Math.floor(content.length() / 1000) + 1;
			else
				thousandNum = (int) Math.floor(content.length() / 1000);
			if (thousandNum < 3)
				subNum = 100 * thousandNum;
			else if (thousandNum < 6)
				subNum = 200 * thousandNum;
			else if (thousandNum < 9)
				subNum = 300 * thousandNum;
			else
				subNum = 3000;
			for (int j = 1; j < subNum; j++) {
				if (content.length() % j > 0)
					forNum = (int) Math.floor(content.length() / j) + 1;
				else
					forNum = (int) Math.floor(content.length() / j);
				if (result || j >= content.length())
					break;
				else {
					for (int m = 0; m < forNum; m++) {
						if (m * j > content.length() || (m + 1) * j > content.length() || (m + 2) * j > content.length())
							break;
						startStr = content.substring(m * j, (m + 1) * j);
						nextStr = content.substring((m + 1) * j, (m + 2) * j);
						if (startStr.equals(nextStr)) {
							similarNum = similarNum + 1;
							endNum = (float) similarNum / forNum;
							if (endNum > 0.4) {
								result = true;
								break;
							}
						} else
							similarNum = 0;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 判断是否是空字符串 null和"" null返回result,否则返回字符串
	 * @param s
	 * @return
	 */
	public static String isEmpty(String s, String result) {
		if (s != null && !s.equals("")) {
			return s;
		}
		return result;
	}

	/**
	 * 判断对象是否为空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(Object str) {
		if (str != null) {
			if (str instanceof Collection) {
				if (((Collection) str).isEmpty()) {
					return false;
				}
				return true;
			}
			if (str instanceof Map) {
				if (((Map) str).isEmpty()) {
					return false;
				}
				return true;
			} 
			if (str.getClass().isArray()) {
				if (Array.getLength(str) == 0) {
					return false;
				}
				return true;
			}
			if (str.equals("")) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	/**
	 * 全角字符变半角字符
	 * @param str
	 * @return
	 */
	public static String full2Half(String str) {
		if (str == null || "".equals(str))
			return "";
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);

			if (c >= 65281 && c < 65373)
				sb.append((char) (c - 65248));
			else
				sb.append(str.charAt(i));
		}

		return sb.toString();

	}

	/**
	 * 全角括号转为半角
	 * @param str
	 * @return
	 */
	public static String replaceBracketStr(String str) {
		if (str != null && str.length() > 0) {
			str = str.replaceAll("（", "(");
			str = str.replaceAll("）", ")");
		}
		return str;
	}

	/**
	 * 解析字符串返回map键值对(例：a=1&b=2 => a=1,b=2)
	 * @param query 源参数字符串
	 * @param split1 键值对之间的分隔符（例：&）
	 * @param split2 key与value之间的分隔符（例：=）
	 * @param dupLink 重复参数名的参数值之间的连接符，连接后的字符串作为该参数的参数值，可为null null：不允许重复参数名出现，则靠后的参数值会覆盖掉靠前的参数值。
	 * @return map
	 * @author sky
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> parseQuery(String query, char split1, char split2, String dupLink) {
		if (!isEmpty(query) && query.indexOf(split2) > 0) {
			Map<String, String> result = new HashMap();

			String name = null;
			String value = null;
			String tempValue = "";
			int len = query.length();
			for (int i = 0; i < len; i++) {
				char c = query.charAt(i);
				if (c == split2) {
					value = "";
				} else if (c == split1) {
					if (!isEmpty(name) && value != null) {
						if (dupLink != null) {
							tempValue = result.get(name);
							if (tempValue != null) {
								value += dupLink + tempValue;
							}
						}
						result.put(name, value);
					}
					name = null;
					value = null;
				} else if (value != null) {
					value += c;
				} else {
					name = (name != null) ? (name + c) : "" + c;
				}
			}

			if (!isEmpty(name) && value != null) {
				if (dupLink != null) {
					tempValue = result.get(name);
					if (tempValue != null) {
						value += dupLink + tempValue;
					}
				}
				result.put(name, value);
			}

			return result;
		}
		return null;
	}

	/**
	 * 将list 用传入的分隔符组装为String
	 * @param list
	 * @param slipStr
	 * @return String
	 */
	public static String listToStringSlipStr(List list, String slipStr) {
		StringBuffer returnStr = new StringBuffer();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				returnStr.append(list.get(i)).append(slipStr);
			}
		}
		if (returnStr.toString().length() > 0)
			return returnStr.toString().substring(0, returnStr.toString().lastIndexOf(slipStr));
		else
			return "";
	}

	/**
	 * 获取从start开始用*替换len个长度后的字符串
	 * @param str 要替换的字符串
	 * @param start 开始位置
	 * @param len 长度
	 * @return 替换后的字符串
	 */
	public static String getMaskStr(String str, int start, int len) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}
		if (str.length() < start) {
			return str;
		}
		// 获取*之前的字符串
		String ret = str.substring(0, start);
		// 获取最多能打的*个数
		int strLen = str.length();
		if (strLen < start + len) {
			len = strLen - start;
		}
		// 替换成*
		for (int i = 0; i < len; i++) {
			ret += "*";
		}
		// 加上*之后的字符串
		if (strLen > start + len) {
			ret += str.substring(start + len);
		}
		return ret;
	}

	/**
	 * 根据传入的分割符号,把传入的字符串分割为List字符串
	 * @param slipStr 分隔的字符串
	 * @param src 字符串
	 * @return 列表
	 */
	public static List<String> stringToStringListBySlipStr(String slipStr, String src) {
		if (src == null)
			return null;
		List<String> list = new ArrayList<String>();
		String[] result = src.split(slipStr);
		for (int i = 0; i < result.length; i++) {
			list.add(result[i]);
		}
		return list;
	}

	/**
	 * 截取字符串
	 * @param str 原始字符串
	 * @param len 要截取的长度
	 * @param tail 结束加上的后缀
	 * @return 截取后的字符串
	 */
	public static String getHtmlSubString(String str, int len, String tail) {
		if (str == null || str.length() <= len) {
			return str;
		}
		int length = str.length();
		char c = ' ';
		String tag = null;
		String name = null;
		int size = 0;
		String result = "";
		boolean isTag = false;
		List<String> tags = new ArrayList<String>();
		int i = 0;
		for (int end = 0, spanEnd = 0; i < length && len > 0; i++) {
			c = str.charAt(i);
			if (c == '<') {
				end = str.indexOf('>', i);
			}
			if (end > 0) {
				// 截取标签
				tag = str.substring(i, end + 1);
				int n = tag.length();
				if (tag.endsWith("/>")) {
					isTag = true;
				} else if (tag.startsWith("</")) { // 结束符
					name = tag.substring(2, end - i);
					size = tags.size() - 1;
					// 堆栈取出html开始标签
					if (size >= 0 && name.equals(tags.get(size))) {
						isTag = true;
						tags.remove(size);
					}
				} else { // 开始符
					spanEnd = tag.indexOf(' ', 0);
					spanEnd = spanEnd > 0 ? spanEnd : n;
					name = tag.substring(1, spanEnd);
					if (name.trim().length() > 0) {
						// 如果有结束符则为html标签
						spanEnd = str.indexOf("</" + name + ">", end);
						if (spanEnd > 0) {
							isTag = true;
							tags.add(name);
						}
					}
				}
				// 非html标签字符
				if (!isTag) {
					if (n >= len) {
						result += tag.substring(0, len);
						break;
					} else {
						len -= n;
					}
				}

				result += tag;
				isTag = false;
				i = end;
				end = 0;
			} else { // 非html标签字符
				len--;
				result += c;
			}
		}
		// 添加未结束的html标签
		for (String endTag : tags) {
			result += "</" + endTag + ">";
		}
		if (i < length) {
			result += tail;
		}
		return result;
	}

	public static String getProperty(String property) {
		if (property.contains("_")) {
			return property.replaceAll("_", "\\.");
		}
		return property;
	}

	/**
	 * 解析前台encodeURIComponent编码后的参数
	 * @param encodeURIComponent (encodeURIComponent(no))
	 * @return
	 */
	public static String getEncodePra(String property) {
		String trem = "";
		if (isNotEmpty(property)) {
			try {
				trem = URLDecoder.decode(property, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return trem;
	}

	// 判断一个字符串是否都为数字
	public boolean isDigit(String strNum) {
		Pattern pattern = Pattern.compile("[0-9]{1,}");
		Matcher matcher = pattern.matcher((CharSequence) strNum);
		return matcher.matches();
	}

	// 截取数字
	public String getNumbers(String content) {
		Pattern pattern = Pattern.compile("\\d+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	// 截取非数字
	public String splitNotNumber(String content) {
		Pattern pattern = Pattern.compile("\\D+");
		Matcher matcher = pattern.matcher(content);
		while (matcher.find()) {
			return matcher.group(0);
		}
		return "";
	}

	/**
	 * 判断某个字符串是否存在于数组中
	 * @param stringArray 原数组
	 * @param source 查找的字符串
	 * @return 是否找到
	 */
	public static boolean contains(String[] stringArray, String source) {
		// 转换为list
		List<String> tempList = Arrays.asList(stringArray);
		// 利用list的包含方法,进行判断
		if (tempList.contains(source)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description:把数组转换为一个用逗号分隔的字符串 ，以便于用in+String 查询
	 */
	public static String converToString(String[] ig) {
		String str = "";
		if (ig != null && ig.length > 0) {
			for (int i = 0; i < ig.length; i++) {
				str += ig[i] + ",";
			}
		}
		str = str.substring(0, str.length() - 1);
		return str;
	}

	/**
	 * @Description:把list转换为一个用逗号分隔的字符串
	 */
	public static String listToString(List list) {
		StringBuilder sb = new StringBuilder();
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (i < list.size() - 1) {
					sb.append(list.get(i) + ",");
				} else {
					sb.append(list.get(i));
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * 时间转换
	 * @param inDay
	 * @return
	 */
	public static String dateParseStringYMD(java.util.Date inDay)
	{
		String newDate=null;
		DateFormat df = (DateFormat)new SimpleDateFormat("yyyy-MM-dd");
		try{
			newDate = df.format(inDay);
		}
		catch(Exception e){
			
		}
		return newDate;
	
	}
	public static String dateParseStringYMDH(java.util.Date inDay)
	{
		String newDate=null;
		DateFormat df = (DateFormat)new SimpleDateFormat("yyyy-MM-dd HH");
		try{
			newDate = df.format(inDay);
		}
		catch(Exception e){
			
		}
		return newDate;
	
	}
	public static String dateParseStringMDHM(java.util.Date inDay){
		String newDate=null;
		DateFormat df = (DateFormat)new SimpleDateFormat("MM-dd HH:mm");
		try{
			newDate = df.format(inDay);
		}
		catch(Exception e){
			
		}
		return newDate;
	
	}
	public static String dateParseStringYMDHM(java.util.Date inDay)
	{
		String newDate=null;
		DateFormat df = (DateFormat)new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			newDate = df.format(inDay);
		}
		catch(Exception e){
			
		}
		return newDate;
	
	}
	public static String dateParseStringYMDHMS(java.util.Date inDay)
	{
		String newDate=null;
		DateFormat df = (DateFormat)new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			newDate = df.format(inDay);
		}
		catch(Exception e){
			
		}
		return newDate;
	
	}
	public static String dateParseStringY(java.util.Date inDay)
	{
		String newDate=null;
		DateFormat df = (DateFormat)new SimpleDateFormat("yyyy");
		try{
			newDate = df.format(inDay);
		}
		catch(Exception e){
			
		}
		return newDate;
	
	}
	public static String dateParseStringM(java.util.Date inDay)
	{
		String newDate=null;
		DateFormat df = (DateFormat)new SimpleDateFormat("MM");
		try{
			newDate = df.format(inDay);
		}
		catch(Exception e){
			
		}
		return newDate;
	
	}
	public static String dateParseStringD(java.util.Date inDay)
	{
		String newDate=null;
		DateFormat df = (DateFormat)new SimpleDateFormat("dd");
		try{
			newDate = df.format(inDay);
		}
		catch(Exception e){
			
		}
		return newDate;
	
	}

}
