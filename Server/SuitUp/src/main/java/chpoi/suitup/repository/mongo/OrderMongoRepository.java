package chpoi.suitup.repository.mongo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.Order;

/**
 *  OrderMongo specific {@link org.springframework.data.mongodb.repository.MongoRepository} interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("orderMongoRepository")
public interface OrderMongoRepository extends MongoRepository<Order,String>{

	/**
	 * Get orders which correspoonding to given client.
	 * 
	 * @param client_id
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 */
	List<Order> getByClient(String client_id, int page, int size);
	
	/**
	 * Get orders which correspoonding to given seller.
	 * 
	 * @param seller_id
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 */
	List<Order> getBySeller(String seller_id, int page, int size);
}
