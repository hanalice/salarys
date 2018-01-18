package com.basic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.proxy.HibernateProxy;

public class PojoMapper {

	private static Map<String, Class<?>> processClassMap = new HashMap<String, Class<?>>();

	private static Map<String, List<Object[]>> mappingMethod = new HashMap<String, List<Object[]>>();

	public static void init() {
		SessionFactory a = (SessionFactory) SpringFactory.getBean("sessionFactory");
		Map<String, ClassMetadata> c = a.getAllClassMetadata();
		Set<String> classNames = c.keySet();
		for (String className : classNames) {
			ClassMetadata classMetadata = c.get(className);
			Class<?> clazz = classMetadata.getMappedClass();
			if (className.endsWith(REQUEST_SUFFIX) && !className.endsWith("." + REQUEST_SUFFIX)) {
				String processName = className.substring(className.lastIndexOf(".") + 1,
						className.lastIndexOf("Request"));
				processName = processName.substring(0, 1).toLowerCase() + processName.substring(1);
				processClassMap.put(processName, clazz);
			}
			List<Object[]> value = new ArrayList<Object[]>();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				if (Pojo.class.isAssignableFrom(field.getType())) {
					value.add(new Object[] { field.getName(), field.getType() });
				}
			}
			if (value.size() > 0) {
				mappingMethod.put(className, value);
			}
		}
	}

	public static void toPlain(Object obj) {
		Class<?> clazz = obj.getClass();
		List<Object[]> fields = mappingMethod.get(clazz.getName());
		if (fields != null) {
			for (Object[] field : fields) {
				try {
					String fieldName = (String) field[0];
					String name = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
					Method get = clazz.getMethod("get" + name);
					Pojo value = (Pojo) get.invoke(obj);
					if (value != null) {
						if (value instanceof HibernateProxy) {
							Class<?> fieldClass = (Class<?>) field[1];
							Pojo newValue = (Pojo) fieldClass.newInstance();
							newValue.setId(value.getId());
							Method set = clazz.getMethod("set" + name, fieldClass);
							set.invoke(obj, newValue);
						} else {
							toPlain(value);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static final String REQUEST_SUFFIX = "Request";

	public static void main(String[] args) {
		init();
	}

}
