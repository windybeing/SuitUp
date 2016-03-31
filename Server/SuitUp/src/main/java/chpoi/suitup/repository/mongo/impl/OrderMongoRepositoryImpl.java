package chpoi.suitup.repository.mongo.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.Order;
import chpoi.suitup.repository.mongo.OrderMongoRepository;

/**
 * OrderMongo Repository base implementation for Mongo.
 * 
 * @author Dong Zhiyuan
 */
@Repository("orderMongoRepositoryImpl")
public class OrderMongoRepositoryImpl extends SimpleMongoRepository<Order,String> implements OrderMongoRepository{

	@Autowired
	private MongoTemplate mongoTemplate; 

	@Autowired
	public OrderMongoRepositoryImpl(MongoTemplate mongoTemplate){
		super(new MappingMongoEntityInformation<Order, String>((MongoPersistentEntity<Order>) 
				mongoTemplate.getConverter()
			    .getMappingContext()
			    .getPersistentEntity(Order.class)), mongoTemplate);
	}
	
	public List<Order> getByClient(String client_id, int page, int size){
		Query query = new Query(new Criteria("client_id").is(client_id)).skip(page*size).limit(size);
		List<Order> order_list = mongoTemplate.find(query, Order.class);
		return order_list;
	}
	
	public List<Order> getBySeller(String seller_id, int page, int size){
		Query query = new Query(new Criteria("orderItems").elemMatch(new Criteria("seller_id").is(seller_id))).skip(page*size).limit(size);
		List<Order> order_list = mongoTemplate.find(query, Order.class);
		return order_list;
	}
	
}
