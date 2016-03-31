package chpoi.suitup.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Suit;
import chpoi.suitup.entity.mongo.SuitMongo;
import chpoi.suitup.entity.sql.SuitSQL;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.repository.mongo.SuitMongoRepository;
import chpoi.suitup.repository.sql.SuitSQLRepository;
import chpoi.suitup.service.SuitService;

@Service("suitServiceImpl")
public class SuitServiceImpl implements SuitService{

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private SuitSQLRepository suitSQLRepository;
	@Autowired
	private SuitMongoRepository suitMongoRepository;
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#create(chpoi.suitup.entity.Suit)
	 */
	public Suit create(Suit suit) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		SuitSQL suitSQL = null;
		SuitMongo suitMongo = null; 
		try {
			transaction = session.beginTransaction();
			suit.setUsed(0);
			suitMongo = new SuitMongo(suit);
			suitMongoRepository.save(suitMongo);
			suitSQL = new SuitSQL(suitMongo.get_id(), suit);
			suitSQLRepository.save(suitSQL);
			transaction.commit();
			return new Suit(suitMongo, suitSQL);
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			throw new SuitUpException("Error: Suit Create");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#delete(java.lang.String)
	 */
	public void delete(String _id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			suitSQLRepository.delete(_id);
			suitMongoRepository.delete(_id);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Suit Delete");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#update(chpoi.suitup.entity.Suit)
	 */
	public Suit update(Suit suit) throws SuitUpException {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		SuitSQL suitSQL = null;
		SuitMongo suitMongo = null;
		try {
			transaction = session.beginTransaction();
			suitSQL = new SuitSQL(suit.get_id(), suit);
			suitSQLRepository.save(suitSQL);
			suitMongo = new SuitMongo(suit);
			suitMongoRepository.save(suitMongo);
			transaction.commit();
			return new Suit(suitMongo, suitSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Suit update");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#getById(java.lang.String)
	 */
	public Suit getById(String _id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		SuitSQL suitSQL = null;
		SuitMongo suitMongo = null;
		try {
			transaction = session.beginTransaction();
			suitSQL = suitSQLRepository.getById(_id);
			suitMongo = suitMongoRepository.findOne(_id);
			transaction.commit();
			return new Suit(suitMongo, suitSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Suit GetById");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#getByExample(chpoi.suitup.entity.Suit, int, int)
	 */
	public List<Suit> getByExample(Suit suit, int page, int size) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		List<SuitSQL> suitSQL_List = null;
		List<SuitMongo> suitMongo_List = null;
		List<String> suit_id_List = new ArrayList<String>();
		List<Suit> suit_List = new ArrayList<Suit>();
		try {
			transaction = session.beginTransaction();
			suitSQL_List = suitSQLRepository.getByExample(new SuitSQL(suit), page, size);
			for(SuitSQL suitSQL : suitSQL_List){
				suit_id_List.add(suitSQL.get_id());
			}
			suitMongo_List = (List<SuitMongo>) suitMongoRepository.findAll(suit_id_List);
			if(suitSQL_List.size() == suitMongo_List.size()){
				size = suitSQL_List.size();
			} else {
				throw new SuitUpException("Error: Suit not the same");
			}
			for(int i = 0; i < size; i++) {
				suit_List.add(new Suit(suitMongo_List.get(i), suitSQL_List.get(i)));
			}
			transaction.commit();
			return suit_List;
		} catch (SuitUpException e){
				transaction.rollback();
				throw e;
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Suit getByExample");
		}
	}

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#getByExample(chpoi.suitup.entity.Suit, int, int)
	 */
	public List<Suit> getBySearchSuitname(String suitname, int page, int size) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		List<SuitSQL> suitSQL_List = null;
		List<SuitMongo> suitMongo_List = null;
		List<String> suit_id_List = new ArrayList<String>();
		List<Suit> suit_List = new ArrayList<Suit>();
		try {
			transaction = session.beginTransaction();
			suitSQL_List = suitSQLRepository.getBySearchSuitname(suitname, page, size);
			for(SuitSQL suitSQL : suitSQL_List){
				suit_id_List.add(suitSQL.get_id());
			}
			suitMongo_List = (List<SuitMongo>) suitMongoRepository.findAll(suit_id_List);
			if(suitSQL_List.size() == suitMongo_List.size()){
				size = suitSQL_List.size();
			} else {
				throw new SuitUpException("Error: Suit not the same");
			}
			for(int i = 0; i < size; i++) {
				suit_List.add(new Suit(suitMongo_List.get(i), suitSQL_List.get(i)));
			}
			transaction.commit();
			return suit_List;
		} catch (SuitUpException e){
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Suit getByExample");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#exists(java.util.List)
	 */
	public void exists(List<String> suits_id_List) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		List<SuitSQL> suitSQL_List = null;
		List<SuitMongo> suitMongo_List = null;
		try {
			transaction = session.beginTransaction();
			suitSQL_List = suitSQLRepository.getByIdList(suits_id_List);
			suitMongo_List = (List<SuitMongo>) suitMongoRepository.findAll(suits_id_List);
			if(suitSQL_List.size() != suits_id_List.size())throw new SuitUpException("Error: SuitSQL not Exists");
			if(suitMongo_List.size() != suits_id_List.size())throw new SuitUpException("Error: SuitMongo not Exists");
			transaction.commit();
		} catch (SuitUpException e) {
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Suit Exists");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#exists(java.lang.String)
	 */
	public void exists(String suit_id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		SuitSQL suitSQL = null;
		SuitMongo suitMongo = null;
		try {
			transaction = session.beginTransaction();
			suitSQL = suitSQLRepository.getById(suit_id);
			if(suitSQL != null)throw new SuitUpException("Error: SuitSQL not Exists");
			suitMongo = (SuitMongo) suitMongoRepository.findOne(suit_id);
			if(suitMongo != null)throw new SuitUpException("Error: SuitMongo not Exists");
			transaction.commit();
		} catch (SuitUpException e) {
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Suit Exists");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SuitService#getByIdList(java.util.List)
	 */
	public List<Suit> getByIdList(List<String> suits_id_List) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		List<SuitSQL> suitSQL_List = null;
		List<SuitMongo> suitMongo_List = null;
		List<Suit> suit_List = new ArrayList<Suit>();
		int size = suits_id_List.size();
		try {
			transaction = session.beginTransaction();
			suitSQL_List = suitSQLRepository.getByIdList(suits_id_List);
			suitMongo_List = (List<SuitMongo>) suitMongoRepository.findAll(suits_id_List);
			if(suitSQL_List.size() != size)throw new SuitUpException("Error: SuitSQL not Exists");
			if(suitMongo_List.size() != size)throw new SuitUpException("Error: SuitMongo not Exists");
			
			for(int i = 0; i < size; i++) {
				suit_List.add(new Suit(suitMongo_List.get(i), suitSQL_List.get(i)));
			}
			transaction.commit();
			return suit_List;			
		} catch (SuitUpException e) {
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Suit GetById");
		}
	}
}
