package chpoi.suitup.repository.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.mongo.SuitMongo;
import chpoi.suitup.repository.mongo.SuitMongoRepository;

/**
 * SuitMongo Repository base implementation for Mongo.
 * 
 * @author Dong Zhiyuan
 */
@Repository("suitMongoRepositoryImpl")
public class SuitMongoRepositoryImpl extends SimpleMongoRepository<SuitMongo,String> implements SuitMongoRepository{

	@Autowired
	private MongoTemplate mongoTemplate; 

	@Autowired
	public SuitMongoRepositoryImpl(MongoTemplate mongoTemplate){
		super(new MappingMongoEntityInformation<SuitMongo, String>((MongoPersistentEntity<SuitMongo>) 
				mongoTemplate.getConverter()
			    .getMappingContext()
			    .getPersistentEntity(SuitMongo.class)), mongoTemplate);
	}
}
