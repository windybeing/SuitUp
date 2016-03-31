package chpoi.suitup.repository.mongo.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.MongoPersistentEntity;
import org.springframework.data.mongodb.repository.support.MappingMongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.Photo;
import chpoi.suitup.repository.mongo.PhotoMongoRepository;

/**
 * PhotoMongo Repository base implementation for Mongo.
 * 
 * @author Dong Zhiyuan
 */
@Repository("suitMongoRepositoryImpl")
public class PhotoMongoRepositoryImpl extends SimpleMongoRepository<Photo,String> implements PhotoMongoRepository{

	@Autowired
	private MongoTemplate mongoTemplate; 

	@Autowired
	public PhotoMongoRepositoryImpl(MongoTemplate mongoTemplate){
		super(new MappingMongoEntityInformation<Photo, String>((MongoPersistentEntity<Photo>) 
				mongoTemplate.getConverter()
			    .getMappingContext()
			    .getPersistentEntity(Photo.class)), mongoTemplate);
	}
	
	
}
