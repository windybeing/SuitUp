package chpoi.suitup.repository.sql.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.sql.SellerSQL;
import chpoi.suitup.repository.sql.SellerSQLRepository;

/**
 * SellerSQL Repository base implementation for MySQL.
 * 
 * @author Dong Zhiyuan
 */
@Repository("sellerSQLRepositoryImpl")
public class SellerSQLRepositoryImpl implements SellerSQLRepository{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SellerSQLRepository#save(chpoi.suitup.entity.sql.SellerSQL)
	 */
	public SellerSQL save(SellerSQL seller){
		hibernateTemplate.saveOrUpdate(seller);
		return seller;
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SellerSQLRepository#delete(java.lang.String)
	 */
	public void delete(String _id){
		SellerSQL seller = hibernateTemplate.get(SellerSQL.class, _id);
		hibernateTemplate.delete(seller);
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SellerSQLRepository#getById(java.lang.String)
	 */
	public SellerSQL getById(String _id){
		DetachedCriteria criteria = DetachedCriteria.forClass(SellerSQL.class).add(Restrictions.eq("_id", _id));
		List<SellerSQL> seller_List = (List<SellerSQL>) hibernateTemplate.findByCriteria(criteria);
		if(seller_List.size() == 0) {
			return null;
		}  else {
			return seller_List.get(0);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SellerSQLRepository#getOneByExample(chpoi.suitup.entity.sql.SellerSQL)
	 */
	public SellerSQL getOneByExample(SellerSQL seller){
		List<SellerSQL> seller_List = hibernateTemplate.findByExample(seller);
		if(seller_List.size() == 0) {
			return null;
		}  else {
			return seller_List.get(0);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SellerSQLRepository#getCountByExample(chpoi.suitup.entity.sql.SellerSQL)
	 */
	public int getCountByExample(SellerSQL seller){
		DetachedCriteria criteria = DetachedCriteria.forClass(SellerSQL.class).setProjection(Projections.projectionList().add(Projections.rowCount())).add(Example.create(seller));
		List results = hibernateTemplate.findByCriteria(criteria);
		return ((Long) results.get(0)).intValue();
	}
}
