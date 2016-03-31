package chpoi.suitup.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.Photo;

/**
 *  PhotoMongo specific {@link org.springframework.data.mongodb.repository.MongoRepository} interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("suitMongoRepository")
public interface PhotoMongoRepository extends MongoRepository<Photo,String>{
	
}
