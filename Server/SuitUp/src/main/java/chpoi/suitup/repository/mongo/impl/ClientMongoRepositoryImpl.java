package chpoi.suitup.repository.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.mongo.ClientMongo;
import chpoi.suitup.repository.mongo.ClientMongoRepository;

/**
 * ClientMongo Repository base implementation for Mongo.
 * 
 * @author Dong Zhiyuan
 */
@Repository("clientMongoRepositoryImpl")
public class ClientMongoRepositoryImpl extends SimpleMongoRepository<ClientMongo,String> implements ClientMongoRepository{

	@Autowired
	private MongoTemplate mongoTemplate; 
	
	@Autowired
	public ClientMongoRepositoryImpl(MongoTemplate mongoTemplate){
		super(new MappingMongoEntityInformation<ClientMongo, String>((MongoPersistentEntity<ClientMongo>) 
				mongoTemplate.getConverter()
			    .getMappingContext()
			    .getPersistentEntity(ClientMongo.class)), mongoTemplate);
	}
}
