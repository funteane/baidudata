package com.cubead.jinjili.common.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;


public class CommonService implements ICommonService{
	
	private ICommonDAO commonDAO;

	public ICommonDAO getCommonDAO() {
		return commonDAO;
	}

	public void setCommonDAO(ICommonDAO commonDAO) {
		this.commonDAO = commonDAO;
	}

	@Override
	public <T> void save(T obj) {
		commonDAO.save(obj);
		
	}

	@Override
	public <T> void update(T obj) {
		commonDAO.update(obj);
	}

	@Override
	public <T> void delete(T obj) {
		commonDAO.delete(obj);
	}

	@Override
	public <T> List<T> find(String hql) {
		return commonDAO.find(hql);
	}

	@Override
	public <T> List<T> find(String hql, Object... value) {
		return commonDAO.find(hql,value);
	}

	@Override
	public <T> T get(Class<T> entityClass, Serializable id) {
		return commonDAO.get(entityClass, id);
	}

	@Override
	public <T> T load(Class<T> entityClass, Serializable id) {
		return commonDAO.load(entityClass, id);
	}

	@Override
	public void find(String hql, PageBean pageObj, Object... values) {
		commonDAO.find(hql, pageObj, values);
	}

	@Override
	public <T> List<T> find(String hql, String[] paramNames, Object[] values) {
		return commonDAO.find(hql, paramNames, values);
	}

	@Override
	public void find(String hql, PageBean pageObj, String[] paramNames,
			Object[] values) {
		commonDAO.find(hql, pageObj, paramNames, values);
	}

	@Override
	public <T> void refresh(T obj) {
		commonDAO.refresh(obj);
	}

	@Override
	public void deleteAll(String hql, Object... value) {
		commonDAO.deleteAll(hql, value);
	}

	@Override
	public void bulkUpdate(String hql, Object... value) {
		commonDAO.bulkUpdate(hql, value);
	}

	@Override
	public void initialize(Object obj) {
		commonDAO.initialize(obj);
	}

	@Override
	public void evict(Object obj) {
		commonDAO.evict(obj);
	}

	@Override
	public <T> void evictList(List<T> list) {
		commonDAO.evictList(list);
	}

	@Override
	public void flush() {
		commonDAO.flush();
	}

	@Override
	public void clear() {
		commonDAO.clear();
	}

	@Override
	public void findBySQL(String sql, PageBean pageBean) {
		commonDAO.findBySQL(sql, pageBean);
	}

	@Override
	public List<Object[]> findBySQL(String sql, Object[] params) {
		return findBySQL(sql, params);
	}

	@Override
	public <T> List<T> findBySQL(String sql, Object[] params,
			Class<T> entityClass) {
		return commonDAO.findBySQL(sql, params, entityClass);
	}

	@Override
	public void executeHQL(String hql, Object[] params) {
		commonDAO.executeHQL(hql, params);
	}

	@Override
	public void executeSQL(String sql, Object[] params) {
		commonDAO.executeSQL(sql, params);
	}

	@Override
	public <T> List<T> findTop(String hql, int topNum, Object... value) {
		return commonDAO.findTop(hql, topNum, value);
	}

	@Override
	public HibernateTemplate getHibertemplate() {
		return commonDAO.getHibertemplate();
	}

	@Override
	public <T> void saveOrupdate(T obj) {
		commonDAO.saveOrupdate(obj);
	}

	@Override
	public void execute(HibernateCallback<?> callback) {
		commonDAO.execute(callback);
	}
	
}
