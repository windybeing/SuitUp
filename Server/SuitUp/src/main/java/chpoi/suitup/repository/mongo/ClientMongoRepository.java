package chpoi.suitup.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.mongo.ClientMongo;

/**
 * ClientMongo specific {@link org.springframework.data.mongodb.repository.MongoRepository} interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("clientMongoRepository")
public interface ClientMongoRepository extends MongoRepository<ClientMongo,String>{
	
}
