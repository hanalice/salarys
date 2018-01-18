package com.basic;

/**
 * Description: This class implements BaseDAO interface.
 *
 * @author Shao, Song Nian
 * @created at 2010-5-17 14:41:08
 *
 */
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;

@SuppressWarnings("unchecked")
public class BaseDAOImpl<T> implements BaseDAO<T> {

	private SessionFactory sessionFactory;
	protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void deleteAll() {
		String hql = "delete from " + entityClass.getName();
		Query query = getSession().createQuery(hql);
		query.executeUpdate();
	}

	public void delete(String id) {
		T obj = this.load(id);
		getSession().delete(obj);
	}

	public T get(String id) {
		return (T) getSession().get(entityClass, id);
	}

	public String save(T obj) {
		return (String) getSession().save(obj);
	}

	public void update(T obj) {
		getSession().update(obj);
	}

	public void delete(T obj) {
		getSession().delete(obj);
	}

	public T load(String id) {
		return (T) getSession().load(entityClass, id);
	}

	public List<T> loadAll() {
		Criteria criteria = getSession().createCriteria(entityClass);
		return criteria.list();
	}

	public int countByCriteria(final DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		Long count = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		return count.intValue();
	}

	public List<T> findByCriteria(DetachedCriteria detachedCriteria) {
		return findByCriteria(detachedCriteria, -1, -1);
	}

	public List<T> findByCriteria(DetachedCriteria detachedCriteria, int first, int max) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (first >= 0) {
			criteria.setFirstResult(first);
		}
		if (max >= 0) {
			criteria.setMaxResults(max);
		}
		List<T> result = criteria.list();
		for (T t : result) {
			PojoMapper.toPlain(t);
		}
		return result;
	}

	public List<T> findByHql(String hql, Object[] params) {
		Query query = getSession().createQuery(hql);
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}

	public List<T> findBySql(String sql, Object[] params) {
		Query query = getSession().createSQLQuery(sql);
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.list();
	}

	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public int updateByHql(String hql, Object[] params) {
		Query query = getSession().createQuery(hql);
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int updateByHql(String hql, Map<String, Object> params) {
		Query query = getSession().createQuery(hql);
		if (null != params && params.size() > 0) {
			for (Entry<String, Object> param : params.entrySet()) {
				if (param.getValue() instanceof Collection<?>) {
					query.setParameterList(param.getKey(), (Collection<?>) param.getValue());
				} else {
					query.setParameter(param.getKey(), param.getValue());
				}
			}
		}
		return query.executeUpdate();
	}

	@Override
	public int updateBySql(String sql, Object[] params) {
		Query query = getSession().createSQLQuery(sql);
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query.executeUpdate();
	}

	@Override
	public DetachedCriteria getDetachedCriteria() {
		return DetachedCriteria.forClass(entityClass, "t0");
	}

	@Override
	public int updateColumns(String id, String[] columns, Object[] params) {
		if (null != columns && columns.length > 0 && null != params && columns.length == params.length) {
			StringBuilder sb = new StringBuilder();
			sb.append("update ").append(entityClass.getName()).append(" set ");
			for (int i = 0; i < columns.length; i++) {
				sb.append(columns[i]).append("=?");
				if (i < columns.length - 1) {
					sb.append(",");
				}
			}
			sb.append(" where id=?");
			Query query = getSession().createQuery(sb.toString());
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
			query.setParameter(columns.length, id);
			return query.executeUpdate();
		}
		return -1;
	}

	public T getOne(List<T> list) {
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<T> findVOByHQL(String hql, Object[] params) {
		Query query = getSession().createQuery(hql);
		if (null != params && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				if (params[i] instanceof Object[]) {
					query.setParameterList("L" + String.valueOf(i), (Object[]) params[i]);
				} else if (params[i] instanceof Collection) {
					query.setParameterList("L" + String.valueOf(i), (Collection<?>) params[i]);
				} else {
					query.setParameter(i, params[i]);
				}
			}
		}
		query.setResultTransformer(Transformers.aliasToBean(entityClass));
		return query.list();
	}

	@Override
	public List<T> list(String[] column) {
		DetachedCriteria criteria = getDetachedCriteria();
		ProjectionList projs = Projections.projectionList();
		for (String c : column) {
			projs.add(Projections.property(c), c);
		}
		criteria.setProjection(projs);
		criteria.setResultTransformer(Transformers.aliasToBean(entityClass));
		return findByCriteria(criteria, -1, -1);
	}

	public static void main(String[] args) {
		String a = "getA";
		System.out.println(a.substring(3));
	}

}
