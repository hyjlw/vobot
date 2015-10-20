/**
 * 
 */
package com.vobot.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author weilin
 *
 */
public class BaseDao<T> {
	
	@PersistenceContext
	private EntityManager em;
	
	public void save(T obj){
		try {
			em.persist(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
