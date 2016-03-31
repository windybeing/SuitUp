package chpoi.suitup.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.mongo.SellerMongo;

/**
 *  SellerMongo specific {@link org.springframework.data.mongodb.repository.MongoRepository} interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("sellerMongoRepository")
public interface SellerMongoRepository extends MongoRepository<SellerMongo,String>{

}
