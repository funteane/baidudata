package com.cubead.jinjili.common.dao;

import java.io.Serializable;
import java.util.List;

import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface ICommonService {
	/**
	 * 保存对象
	 * 
	 * @param obj
	 */
	public <T> void save(T obj);

	/**
	 * 更新对象
	 * 
	 * @param obj
	 */
	public <T> void update(T obj);

	/**
	 * 删除对象
	 * 
	 * @param obj
	 */
	public <T> void delete(T obj);

	/**
	 * 按hql查询
	 * 
	 * @param hql
	 * @return
	 */
	public <T> List<T> find(String hql);

	/**
	 * 按Hql查询，带可变参数
	 * 
	 * @param hql
	 *            可变参数用?表示
	 * @param value
	 *            参数值数组
	 * @return
	 */
	public <T> List<T> find(String hql, Object... value);

	/**
	 * 根据id读取对象
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityClass, Serializable id);

	/**
	 * 根据id读取对象
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T load(Class<T> entityClass, Serializable id);

	/**
	 * 分页查询
	 * 
	 * @param hql
	 *            可变参数用？表示
	 * @param pageObj
	 *            分页对象，包含查询结果
	 * @param values
	 *            参数值数组
	 * @return
	 */
	public void find(String hql, PageBean pageObj, Object... values);

	/**
	 * 查询，可变参数用命名参数表示，形式为<:参数名>
	 * 
	 * @param hql
	 * @param paramNames
	 *            命名参数数组
	 * @param values
	 *            参数值数组
	 * @return 查询结果
	 */
	public <T> List<T> find(String hql, String[] paramNames, Object[] values);

	/**
	 * 分页查询，可变参数用命名参数表示，形式为<:参数名>
	 * 
	 * @param hql
	 * @param pageObj
	 * @param paramNames
	 * @param values
	 * @return
	 */
	public void find(String hql, PageBean pageObj, String[] paramNames, Object[] values);

	/**
	 * 从数据库读取数据更新持久化对象状态
	 * 
	 * @param obj
	 */
	public <T> void refresh(T obj);

	/**
	 * 按hql删除对象
	 * 
	 * @param hql
	 *            可变参数用？表示
	 * @param value
	 *            参数值
	 */
	public void deleteAll(String hql, Object... value);

	/**
	 * 按hql更新对象
	 * 
	 * @param hql
	 *            可变参数用？表示
	 * @param value
	 *            参数值
	 */
	public void bulkUpdate(String hql, Object... value);

	/**
	 * 初始化持久化对象
	 * 
	 * @param obj
	 */
	public void initialize(Object obj);

	/**
	 * 将某对象移出session
	 * 
	 * @param obj
	 */
	public void evict(Object obj);

	/**
	 * 将某列表移出session
	 * 
	 * @param list
	 */
	public <T> void evictList(List<T> list);

	/**
	 * 将缓存的对象保存到数据库中
	 * 
	 */
	public void flush();

	/**
	 * 清除缓存和取消未提交的插入、更新和删除操作
	 * 
	 */
	public void clear();

	/**
	 * 执行原生sql
	 */
	public void findBySQL(String sql, PageBean pageBean);

	/**
	 * 执行原生sql
	 */
	public List<Object[]> findBySQL(String sql, Object[] params);

	public <T> List<T> findBySQL(String sql, Object[] params, Class<T> entityClass);

	/**
	 * insert or update data by hql
	 * 
	 * @param hql
	 * @param params
	 */
	public void executeHQL(String hql, Object[] params);

	public void executeSQL(String sql, Object[] params);

	/**
	 * 查找前多少条信息，和分页方法的区别是不计算总条数
	 * 
	 * @param hql
	 * @param num
	 * @param value
	 * @return
	 */
	public <T> List<T> findTop(String hql, int topNum, Object... value);

	/**
	 * 获取session
	 */
	//public Session getSessions();

	public HibernateTemplate getHibertemplate();

	/**
	 * 保存对象或者新建
	 * 
	 * @param obj
	 */
	public <T> void saveOrupdate(T obj);
	
	public void execute(HibernateCallback<?> callback);
}
