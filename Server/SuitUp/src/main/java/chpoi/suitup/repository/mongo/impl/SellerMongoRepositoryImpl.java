package chpoi.suitup.repository.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.mongo.SellerMongo;
import chpoi.suitup.repository.mongo.SellerMongoRepository;

/**
 * SellerMongo Repository base implementation for Mongo.
 * 
 * @author Dong Zhiyuan
 */
@Repository("sellerMongoRepositoryImpl")
public class SellerMongoRepositoryImpl extends SimpleMongoRepository<SellerMongo,String> implements SellerMongoRepository{

	@Autowired
	private MongoTemplate mongoTemplate; 

	@Autowired
	public SellerMongoRepositoryImpl(MongoTemplate mongoTemplate){
		super(new MappingMongoEntityInformation<SellerMongo, String>((MongoPersistentEntity<SellerMongo>) 
				mongoTemplate.getConverter()
			    .getMappingContext()
			    .getPersistentEntity(SellerMongo.class)), mongoTemplate);
	}	
}
