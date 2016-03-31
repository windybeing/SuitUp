package chpoi.suitup.repository.sql.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.sql.ManufacturerSQL;
import chpoi.suitup.repository.sql.ManufacturerSQLRepository;

/**
 * ManufacturerSQL Repository base implementation for MySQL.
 * 
 * @author Dong Zhiyuan
 */
@Repository("manufacturerSQLRepositoryImpl")
public class ManufacturerSQLRepositoryImpl implements ManufacturerSQLRepository{

	@Autowired
	private HibernateTemplate hibernateTemplate;

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ManufacturerSQLRepository#save(chpoi.suitup.entity.sql.ManufacturerSQL)
	 */
	public ManufacturerSQL save(ManufacturerSQL manufacturer){
		hibernateTemplate.saveOrUpdate(manufacturer);
		return manufacturer;
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ManufacturerSQLRepository#delete(java.lang.String)
	 */
	public void delete(String _id){
		ManufacturerSQL manufacturer = hibernateTemplate.get(ManufacturerSQL.class, _id);
		hibernateTemplate.delete(manufacturer);
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ManufacturerSQLRepository#getById(java.lang.String)
	 */
	public ManufacturerSQL getById(String _id){
		DetachedCriteria criteria = DetachedCriteria.forClass(ManufacturerSQL.class).add(Restrictions.eq("_id", _id));
		List<ManufacturerSQL> manufacturer_List = (List<ManufacturerSQL>) hibernateTemplate.findByCriteria(criteria);
		if(manufacturer_List.size() == 0) {
			return null;
		}  else {
			return manufacturer_List.get(0);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ManufacturerSQLRepository#getById(java.lang.String)
	 */
	public ManufacturerSQL getByIdentification(String identification){
		DetachedCriteria criteria = DetachedCriteria.forClass(ManufacturerSQL.class).add(Restrictions.eq("identification", identification));
		List<ManufacturerSQL> manufacturer_List = (List<ManufacturerSQL>) hibernateTemplate.findByCriteria(criteria);
		if(manufacturer_List.size() == 0) {
			return null;
		}  else {
			return manufacturer_List.get(0);
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ManufacturerSQLRepository#getByExample(chpoi.suitup.entity.sql.ManufacturerSQL, int, int)
	 */
	public List<ManufacturerSQL> getByExample(ManufacturerSQL manufacturer, int page, int size){
		List<ManufacturerSQL> manufacturer_List = (List<ManufacturerSQL>) hibernateTemplate.findByExample(manufacturer, page*size, size);
		if(manufacturer_List.size() == 0) {
			return null;
		}  else {
			return manufacturer_List;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.ManufacturerSQLRepository#getAll(int, int)
	 */
	public List<ManufacturerSQL> getAll(int page, int size){
		DetachedCriteria criteria = DetachedCriteria.forClass(ManufacturerSQL.class);
		List<ManufacturerSQL> manufacturer_List = (List<ManufacturerSQL>) hibernateTemplate.findByCriteria(criteria, page*size, size);
		if(manufacturer_List.size() == 0) {
			return null;
		}  else {
			return manufacturer_List;
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.repository.sql.SuitSQLRepository#getByIdList(java.util.List)
	 */
	public List<ManufacturerSQL> getByIdList(List<String> _id_list){
		DetachedCriteria criteria = DetachedCriteria.forClass(ManufacturerSQL.class).add(Restrictions.in("_id", _id_list.toArray()));
		List<ManufacturerSQL> manufacturerSQL_List = (List<ManufacturerSQL>) hibernateTemplate.findByCriteria(criteria);
		return manufacturerSQL_List;
	}
}
