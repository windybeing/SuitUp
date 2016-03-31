package chpoi.suitup.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.mongo.ManufacturerMongo;

/**
 * ManufacturerMongo specific {@link org.springframework.data.mongodb.repository.MongoRepository} interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("manufacturerMongoRepository")
public interface ManufacturerMongoRepository extends MongoRepository<ManufacturerMongo,String>{

}
