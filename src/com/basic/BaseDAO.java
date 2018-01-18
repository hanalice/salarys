package com.basic;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Description: This class provides some common operations for DAO layer.
 * 
 * @author Shao, Song Nian
 * @created at 2010-5-17 14:41:08
 * 
 */
public interface BaseDAO<T> {

	/**
	 * Description: Save the bean to database.
	 * 
	 * @param obj
	 *            to be saved bean
	 * @return String identity id
	 */
	public String save(T obj);

	/**
	 * Description: Get the bean by its identity id.
	 * 
	 * @param id
	 *            identity id
	 * @return Bean
	 */
	public T get(String id);

	/**
	 * Description: Load the bean by its identity id.
	 * 
	 * @param id
	 *            identity id
	 * @return Bean
	 */
	public T load(String id);

	/**
	 * Description: Update the bean.
	 * 
	 * @param obj
	 *            to be updated bean
	 */
	public void update(T obj);

	public void deleteAll();

	/**
	 * Description: Delete the bean by its identity id.
	 * 
	 * @param id
	 *            identity id
	 */
	public void delete(String id);

	/**
	 * Description: Delete the bean.
	 * 
	 * @param id
	 *            identity id
	 */
	public void delete(T obj);

	/**
	 * Description: Load all beans.
	 * 
	 * @return List
	 * 
	 */
	public List<T> loadAll();

	public List<T> list(String[] column);

	/**
	 * Description: find all the entries according to query criteria.
	 * 
	 * @return List
	 * 
	 */
	public List<T> findByCriteria(DetachedCriteria detachedCriteria);

	/**
	 * Description: find all the entries according to query criteria.
	 * 
	 * @return List
	 * 
	 */
	public List<T> findByCriteria(DetachedCriteria detachedCriteria, int first, int max);

	/**
	 * Description: find all the entries according to hql.
	 * 
	 * @return List
	 * 
	 */
	public List<T> findByHql(String hql, Object[] params);
	
	public List<T> findBySql(String sql, Object[] params);

	/**
	 * Description: update by hql
	 * 
	 * @return
	 * 
	 */
	public int updateByHql(String hql, Object[] params);
	
	public int updateByHql(String hql, Map<String, Object> params);

	/**
	 * Description: update by sql
	 * 
	 * @return
	 * 
	 */
	public int updateBySql(String sql, Object[] params);
	
	/**
	 * Description: update the entry's columns by id.
	 * 
	 * @return
	 * 
	 */
	public int updateColumns(String id, String[] columns, Object[] params);

	/**
	 * Description: count all the entries according to query criteria.
	 * 
	 * @return count
	 * 
	 */
	public int countByCriteria(DetachedCriteria detachedCriteria);

	/**
	 * Description: get hibernate session.
	 * 
	 * @return count
	 * 
	 */
	public Session getSession();

	public DetachedCriteria getDetachedCriteria();

	public List<T> findVOByHQL(String hql, Object[] params);

}
