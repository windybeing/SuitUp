package chpoi.suitup.repository.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.mongo.ManufacturerMongo;
import chpoi.suitup.repository.mongo.ManufacturerMongoRepository;

/**
 * ManufacturerMongo Repository base implementation for Mongo.
 * 
 * @author Dong Zhiyuan
 */
@Repository("manufacturerMongoRepositoryImpl")
public class ManufacturerMongoRepositoryImpl extends SimpleMongoRepository<ManufacturerMongo,String> implements ManufacturerMongoRepository{

	@Autowired
	private MongoTemplate mongoTemplate; 
	
	@Autowired
	public ManufacturerMongoRepositoryImpl(MongoTemplate mongoTemplate){
		super(new MappingMongoEntityInformation<ManufacturerMongo, String>((MongoPersistentEntity<ManufacturerMongo>) 
				mongoTemplate.getConverter()
			    .getMappingContext()
			    .getPersistentEntity(ManufacturerMongo.class)), mongoTemplate);
	}
}
