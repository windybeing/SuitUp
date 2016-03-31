package chpoi.suitup.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chpoi.suitup.alipay.Alipay;
import chpoi.suitup.entity.Client;
import chpoi.suitup.entity.OrderItem;
import chpoi.suitup.entity.mongo.ClientMongo;
import chpoi.suitup.entity.sql.ClientSQL;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.repository.mongo.ClientMongoRepository;
import chpoi.suitup.repository.sql.ClientSQLRepository;
import chpoi.suitup.service.ClientService;

@Service("clientServiceImpl")
public class ClientServiceImpl implements ClientService{

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ClientSQLRepository clientSQLRepository;
	@Autowired
	private ClientMongoRepository clientMongoRepository;
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.ClientService#login(chpoi.suitup.entity.Client)
	 */
	public Client login(Client client) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ClientSQL clientSQL = null;
		ClientMongo clientMongo = null;
		try {
			transaction = session.beginTransaction();
			clientSQL = clientSQLRepository.getOneByExample(new ClientSQL(client));
			if(clientSQL == null) throw new SuitUpException("Username or Password Wrong!");
			clientMongo = clientMongoRepository.findOne(clientSQL.get_id());
			if(clientMongo == null) throw new SuitUpException("Username or Password Wrong!");
			transaction.commit();
			return new Client(clientMongo, clientSQL);
		} catch (SuitUpException e) {
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Client Login");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.ClientService#register(chpoi.suitup.entity.Client)
	 */
	public Client register(Client client) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ClientSQL clientSQL = null;
		ClientMongo clientMongo = null;
		ClientSQL clientSQL_temp = null;
		try {
			transaction = session.beginTransaction();
			clientSQL_temp = new ClientSQL();
			clientSQL_temp.setUsername(client.getUsername());
			if(clientSQLRepository.getCountByExample(clientSQL_temp) > 0){
				throw new SuitUpException("Username is used");
			};
			
			clientSQL_temp = new ClientSQL();
			clientSQL_temp.setEmail(client.getEmail());
			if(clientSQLRepository.getCountByExample(clientSQL_temp) > 0){
				throw new SuitUpException("Email is used");
			};
			
			clientSQL_temp = new ClientSQL();
			clientSQL_temp.setPhonenumber(client.getPhonenumber());
			if(clientSQLRepository.getCountByExample(clientSQL_temp) > 0){
				throw new SuitUpException("Phonenumber is used");
			};
			
			clientMongo = new ClientMongo(client);
			clientMongoRepository.save(clientMongo);
			clientSQL = new ClientSQL(clientMongo.get_id(), client);
			clientSQLRepository.save(clientSQL);
			transaction.commit();
			return new Client(clientMongo, clientSQL);
		} catch (SuitUpException e) {
			transaction.rollback();
			throw e;
		} catch (Exception e) {
			transaction.rollback();
			if(clientMongo.get_id() != null){
				clientMongoRepository.delete(clientMongo.get_id());
			}
			throw new SuitUpException("Error: Client Register");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.ClientService#pay(java.lang.String, java.util.List)
	 */
	public void pay(String _id, List<OrderItem> orderItems_List) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		BigDecimal total_price = new BigDecimal(0.0), price = null, count = null;
		for(OrderItem orderItem : orderItems_List){
			price = new BigDecimal(orderItem.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
			count = new BigDecimal(orderItem.getCount());
			total_price = total_price.add(price.multiply(count));
		}
		try {
			transaction = session.beginTransaction();
			ClientSQL clientSQL = clientSQLRepository.getById(_id);
			Alipay.pay(total_price.doubleValue());
			clientSQLRepository.save(clientSQL);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Client Pay");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.ClientService#delete(java.lang.String)
	 */
	public void delete(String _id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			clientSQLRepository.delete(_id);
			clientMongoRepository.delete(_id);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: client Delete");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.ClientService#update(chpoi.suitup.entity.Client)
	 */
	public Client update(Client client) throws SuitUpException {
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ClientSQL clientSQL = null;
		ClientMongo clientMongo = null;
		try {
			transaction = session.beginTransaction();
			clientSQL = new ClientSQL(client.get_id(), client);
			clientSQLRepository.save(clientSQL);
			clientMongo = new ClientMongo(client);
			clientMongoRepository.save(clientMongo);
			transaction.commit();
			return new Client(clientMongo, clientSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Client update");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.ClientService#getById(java.lang.String)
	 */
	public Client getById(String _id) throws SuitUpException{
		Session session = sessionFactory.getCurrentSession();
		Transaction transaction = null;
		ClientSQL clientSQL = null;
		ClientMongo clientMongo = null;
		try {
			transaction = session.beginTransaction();
			clientSQL = clientSQLRepository.getById(_id);
			clientMongo = clientMongoRepository.findOne(_id);
			transaction.commit();
			return new Client(clientMongo, clientSQL);
		} catch (Exception e) {
			transaction.rollback();
			throw new SuitUpException("Error: Client GetById");
		}
	}
}
