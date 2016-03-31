package chpoi.suitup.repository.sql.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.sql.SuitSQL;
import chpoi.suitup.repository.sql.SuitSQLRepository;

/**
 * SuitSQL Repository base implementation for MySQL.
 * 
 * @author Dong Zhiyuan
 */
@Repository("suitSQLRepositoryImpl")
public class SuitSQLRepositoryImpl implements SuitSQLRepository{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SuitSQLRepository#save(chpoi.suitup.entity.sql.SuitSQL)
	 */
	public SuitSQL save(SuitSQL suit){
		hibernateTemplate.saveOrUpdate(suit);
		return suit;
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SuitSQLRepository#delete(java.lang.String)
	 */
	public void delete(String _id){
		SuitSQL suit = hibernateTemplate.get(SuitSQL.class, _id);
		hibernateTemplate.delete(suit);
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SuitSQLRepository#getById(java.lang.String)
	 */
	public SuitSQL getById(String _id){
		DetachedCriteria criteria = DetachedCriteria.forClass(SuitSQL.class).add(Restrictions.eq("_id", _id));
		List<SuitSQL> suit_List = (List<SuitSQL>) hibernateTemplate.findByCriteria(criteria);
		if(suit_List.size() == 0) {
			return null;
		}  else {
			return suit_List.get(0);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SuitSQLRepository#getByExample(chpoi.suitup.entity.sql.SuitSQL, int, int)
	 */
	public List<SuitSQL> getByExample(SuitSQL suit, int page, int size){
		List<SuitSQL> suit_List = (List<SuitSQL>) hibernateTemplate.findByExample(suit, page*size, size);
		if(suit_List.size() == 0) {
			return null;
		}  else {
			return suit_List;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SuitSQLRepository#getByExample(chpoi.suitup.entity.sql.SuitSQL, int, int)
	 */
	public List<SuitSQL> getBySearchSuitname(String suitname, int page, int size){
		DetachedCriteria criteria = DetachedCriteria.forClass(SuitSQL.class).add(Restrictions.like("suitname", suitname, MatchMode.ANYWHERE));
		List<SuitSQL> suit_List = (List<SuitSQL>) hibernateTemplate.findByCriteria(criteria, page*size, size);
		if(suit_List.size() == 0) {
			return null;
		}  else {
			return suit_List;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SuitSQLRepository#getAll(int, int)
	 */
	public List<SuitSQL> getAll(int page, int size){
		DetachedCriteria criteria = DetachedCriteria.forClass(SuitSQL.class);
		List<SuitSQL> suit_List = (List<SuitSQL>) hibernateTemplate.findByCriteria(criteria, page*size, size);
		if(suit_List.size() == 0) {
			return null;
		}  else {
			return suit_List;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SuitSQLRepository#getByIdList(java.util.List)
	 */
	public List<SuitSQL> getByIdList(List<String> _id_list){
		DetachedCriteria criteria = DetachedCriteria.forClass(SuitSQL.class).add(Restrictions.in("_id", _id_list.toArray()));
		List<SuitSQL> suitSQL_List = (List<SuitSQL>) hibernateTemplate.findByCriteria(criteria);
		return suitSQL_List;
	}
}
