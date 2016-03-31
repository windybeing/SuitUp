package chpoi.suitup.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Manufacturer;
import chpoi.suitup.entity.mongo.ManufacturerMongo;
import chpoi.suitup.entity.sql.ManufacturerSQL;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.repository.mongo.ManufacturerMongoRepository;
import chpoi.suitup.repository.sql.ManufacturerSQLRepository;
import chpoi.suitup.service.ManufacturerService;


@Service("manufacturerServiceImpl")
public class ManufacturerServiceImpl implements ManufacturerService{

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ManufacturerSQLRepository manufacturerSQLRepository;
	@Autowired
	private ManufacturerMongoRepository manufacturerMongoRepository;

	/*
	 * (non-Javadoc)
	 * @see chpoi.manufacturerup.service.ManufacturerService#register(chpoi.manufacturerup.entity.Manufacturer)
	 */
	public Manufacturer register(Manufacturer manufacturer) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ManufacturerSQL manufacturerSQL = null;
		ManufacturerMongo manufacturerMongo = null; 
		try {
			transaction = session.beginTransaction();
			manufacturerMongo = new ManufacturerMongo(manufacturer);
			manufacturerMongoRepository.save(manufacturerMongo);
			manufacturerSQL = new ManufacturerSQL(manufacturerMongo.get_id(), manufacturer);
			manufacturerSQLRepository.save(manufacturerSQL);
			transaction.commit();
			return new Manufacturer(manufacturerMongo, manufacturerSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Manufacturer Register");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.manufacturerup.service.ManufacturerService#delete(java.lang.String)
	 */
	public void delete(String _id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			manufacturerSQLRepository.delete(_id);
			manufacturerMongoRepository.delete(_id);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Manufacturer Delete");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.manufacturerup.service.ManufacturerService#getById(java.lang.String)
	 */
	public Manufacturer getById(String _id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ManufacturerSQL manufacturerSQL = null;
		ManufacturerMongo manufacturerMongo = null;
		try {
			transaction = session.beginTransaction();
			manufacturerSQL = manufacturerSQLRepository.getById(_id);
			manufacturerMongo = manufacturerMongoRepository.findOne(_id);
			transaction.commit();
			return new Manufacturer(manufacturerMongo, manufacturerSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Manufacturer getById");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see chpoi.manufacturerup.service.ManufacturerService#getById(java.lang.String)
	 */
	public Manufacturer getByIdentification(String identification) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ManufacturerSQL manufacturerSQL = null;
		ManufacturerMongo manufacturerMongo = null;
		try {
			transaction = session.beginTransaction();
			manufacturerSQL = manufacturerSQLRepository.getByIdentification(identification);
			manufacturerMongo = manufacturerMongoRepository.findOne(manufacturerSQL.get_id());
			transaction.commit();
			return new Manufacturer(manufacturerMongo, manufacturerSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Manufacturer getByIdentification");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.manufacturerup.service.ManufacturerService#getByExample(chpoi.manufacturerup.entity.Manufacturer, int, int)
	 */
	public List<Manufacturer> getByExample(Manufacturer manufacturer, int page, int size) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		List<ManufacturerSQL> manufacturerSQL_List = null;
		List<ManufacturerMongo> manufacturerMongo_List = null;
		List<String> manufactcurer_id_List = new ArrayList<String>();
		List<Manufacturer> manufacturer_List = new ArrayList<Manufacturer>();
		try {
			transaction = session.beginTransaction();
			manufacturerSQL_List = manufacturerSQLRepository.getByExample(new ManufacturerSQL(manufacturer), page, size);
			for(ManufacturerSQL manufacturerSQL : manufacturerSQL_List){
				manufactcurer_id_List.add(manufacturerSQL.get_id());
			}
			manufacturerMongo_List = (List<ManufacturerMongo>) manufacturerMongoRepository.findAll(manufactcurer_id_List);
			if(manufacturerSQL_List.size() == manufacturerMongo_List.size()){
				size = manufacturerSQL_List.size();
			} else {
				throw new SuitUpException("Error: Manufacturer not the same");
			}
			for(int i = 0; i < size; i++) {
				manufacturer_List.add(new Manufacturer(manufacturerMongo_List.get(i), manufacturerSQL_List.get(i)));
			}
			transaction.commit();
			return manufacturer_List;
		} catch (SuitUpException e) {
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Manufacturer getByExample");
		}
	}	
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.manufacturerup.service.ManufacturerService#update(chpoi.manufacturerup.entity.Manufacturer)
	 */
	public Manufacturer update(Manufacturer manufacturer) throws SuitUpException {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ManufacturerSQL manufacturerSQL = null;
		ManufacturerMongo manufacturerMongo = null;
		try {
			transaction = session.beginTransaction();
			manufacturerSQL = new ManufacturerSQL(manufacturer.get_id(), manufacturer);
			manufacturerSQLRepository.save(manufacturerSQL);
			manufacturerMongo = new ManufacturerMongo(manufacturer);
			manufacturerMongoRepository.save(manufacturerMongo);
			transaction.commit();
			return new Manufacturer(manufacturerMongo, manufacturerSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Manufacturer update");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.manufacturerup.service.ManufacturerService#getByIdList(java.util.List)
	 */
	public List<Manufacturer> getByIdList(List<String> manufacturers_id_List) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		List<ManufacturerSQL> manufacturerSQL_List = null;
		List<ManufacturerMongo> manufacturerMongo_List = null;
		List<Manufacturer> manufacturer_List = new ArrayList<Manufacturer>();
		int size = manufacturers_id_List.size();
		try {
			transaction = session.beginTransaction();
			manufacturerSQL_List = manufacturerSQLRepository.getByIdList(manufacturers_id_List);
			manufacturerMongo_List = (List<ManufacturerMongo>) manufacturerMongoRepository.findAll(manufacturers_id_List);
			if(manufacturerSQL_List.size() != size)throw new SuitUpException("Error: ManufacturerSQL not Exists");
			if(manufacturerMongo_List.size() != size)throw new SuitUpException("Error: ManufacturerMongo not Exists");
			
			for(int i = 0; i < size; i++) {
				manufacturer_List.add(new Manufacturer(manufacturerMongo_List.get(i), manufacturerSQL_List.get(i)));
			}
			transaction.commit();
			return manufacturer_List;			
		} catch (SuitUpException e) {
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Manufacturer GetById");
		}
	}
}
