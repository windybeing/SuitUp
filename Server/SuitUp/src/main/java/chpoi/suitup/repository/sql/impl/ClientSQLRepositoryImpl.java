package chpoi.suitup.repository.sql.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.sql.ClientSQL;
import chpoi.suitup.repository.sql.ClientSQLRepository;

/**
 * ClientSQL Repository base implementation for MySQL.
 * 
 * @author Dong Zhiyuan
 */
@Repository("clientSQLRepositoryImpl")
public class ClientSQLRepositoryImpl implements ClientSQLRepository{
	
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ClientSQLRepository#save(chpoi.suitup.entity.sql.ClientSQL)
	 */
	public ClientSQL save(ClientSQL client){
		hibernateTemplate.saveOrUpdate(client);
		return client;
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ClientSQLRepository#delete(java.lang.String)
	 */
	public void delete(String _id){
		ClientSQL client = hibernateTemplate.get(ClientSQL.class, _id);
		hibernateTemplate.delete(client);
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ClientSQLRepository#getById(java.lang.String)
	 */
	public ClientSQL getById(String _id){
		DetachedCriteria criteria = DetachedCriteria.forClass(ClientSQL.class).add(Restrictions.eq("_id", _id));
		List<ClientSQL> client_List = (List<ClientSQL>) hibernateTemplate.findByCriteria(criteria);
		if(client_List.size() == 0) {
			return null;
		}  else {
			return client_List.get(0);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ClientSQLRepository#getOneByExample(chpoi.suitup.entity.sql.ClientSQL)
	 */
	public ClientSQL getOneByExample(ClientSQL client){
		List<ClientSQL> client_List = hibernateTemplate.findByExample(client);
		if(client_List.size() == 0) {
			return null;
		}  else {
			return client_List.get(0);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ClientSQLRepository#getCountByExample(chpoi.suitup.entity.sql.ClientSQL)
	 */
	public int getCountByExample(ClientSQL client){
		DetachedCriteria criteria = DetachedCriteria.forClass(ClientSQL.class).setProjection(Projections.projectionList().add(Projections.rowCount())).add(Example.create(client));
		List results = hibernateTemplate.findByCriteria(criteria);
		return ((Long) results.get(0)).intValue();
	}
}
