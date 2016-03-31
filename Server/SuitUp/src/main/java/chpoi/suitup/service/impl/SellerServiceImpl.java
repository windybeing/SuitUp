package chpoi.suitup.service.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Seller;
import chpoi.suitup.entity.mongo.SellerMongo;
import chpoi.suitup.entity.sql.SellerSQL;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.repository.mongo.SellerMongoRepository;
import chpoi.suitup.repository.sql.SellerSQLRepository;
import chpoi.suitup.service.SellerService;

@Service("sellerServiceImpl")
public class SellerServiceImpl implements SellerService{

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private SellerSQLRepository sellerSQLRepository;
	@Autowired
	private SellerMongoRepository sellerMongoRepository;
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SellerService#login(chpoi.suitup.entity.Seller)
	 */
	public Seller login(Seller seller) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		SellerSQL sellerSQL = null;
		SellerMongo sellerMongo = null;
		try {
			transaction = session.beginTransaction();
			sellerSQL = sellerSQLRepository.getOneByExample(new SellerSQL(seller));
			if(sellerSQL == null) throw new SuitUpException("Username or Password Wrong!");
			sellerMongo = sellerMongoRepository.findOne(sellerSQL.get_id());
			if(sellerMongo == null) throw new SuitUpException("Username or Password Wrong!");
			transaction.commit();
			return new Seller(sellerMongo, sellerSQL);
		} catch (SuitUpException e){
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			throw new SuitUpException("ErrorLogin Error");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SellerService#register(chpoi.suitup.entity.Seller)
	 */
	public Seller register(Seller seller) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		SellerSQL sellerSQL = null;
		SellerMongo sellerMongo = null;
		SellerSQL sellerSQL_temp = null;
		try {
			transaction = session.beginTransaction();
			sellerSQL_temp = new SellerSQL();
			sellerSQL_temp.setUsername(seller.getUsername());
			if(sellerSQLRepository.getCountByExample(sellerSQL_temp) > 0){
				throw new SuitUpException("Username is used");
			}
			
			sellerSQL_temp = new SellerSQL();
			sellerSQL_temp.setEmail(seller.getEmail());
			if(sellerSQLRepository.getCountByExample(sellerSQL_temp) > 0){
				throw new SuitUpException("Email is used");
			}
			
			sellerSQL_temp = new SellerSQL();
			sellerSQL_temp.setPhonenumber(seller.getPhonenumber());
			if(sellerSQLRepository.getCountByExample(sellerSQL_temp) > 0){
				throw new SuitUpException("Phonenumber is used");
			}
			
			sellerMongo = new SellerMongo(seller);
			sellerMongoRepository.save(sellerMongo);
			sellerSQL = new SellerSQL(sellerMongo.get_id(), seller);
			sellerSQLRepository.save(sellerSQL);
			transaction.commit();
			return new Seller(sellerMongo, sellerSQL);
		} catch (SuitUpException e) {
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			transaction.rollback();
			if(sellerMongo.get_id() != null){
				sellerMongoRepository.delete(sellerMongo.get_id());
			}
			throw new SuitUpException("Error: Seller Register");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SellerService#delete(java.lang.String)
	 */
	public void delete(String _id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			sellerSQLRepository.delete(_id);
			sellerMongoRepository.delete(_id);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Seller Delete");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SellerService#update(chpoi.suitup.entity.Seller)
	 */
	public Seller update(Seller seller) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		SellerSQL sellerSQL = null;
		SellerMongo sellerMongo = null;
		try {
			transaction = session.beginTransaction();
			sellerSQL = new SellerSQL(seller.get_id(), seller);
			sellerSQLRepository.save(sellerSQL);
			sellerMongo = new SellerMongo(seller);
			sellerMongoRepository.save(sellerMongo);
			transaction.commit();
			return new Seller(sellerMongo, sellerSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Seller update");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.SellerService#getById(java.lang.String)
	 */
	public Seller getById(String _id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		SellerSQL sellerSQL = null;
		SellerMongo sellerMongo = null;
		try {
			transaction = session.beginTransaction();
			sellerSQL = sellerSQLRepository.getById(_id);
			sellerMongo = sellerMongoRepository.findOne(_id);
			transaction.commit();
			return new Seller(sellerMongo, sellerSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Seller GetById");
		}
	}
}
