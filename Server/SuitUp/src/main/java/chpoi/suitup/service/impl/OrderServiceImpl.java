package chpoi.suitup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Order;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.repository.mongo.OrderMongoRepository;
import chpoi.suitup.service.OrderService;

@Service("orderServiceImpl")
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderMongoRepository orderMongoRepository;
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.OrderService#create(chpoi.suitup.entity.Order)
	 */
	public Order create(Order order) throws SuitUpException{
		try {
			return orderMongoRepository.save(order);
		} catch (Exception e) {
			throw new SuitUpException("Error: Order Create");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.OrderService#delete(java.lang.String)
	 */
	public void delete(String _id) throws SuitUpException{
		try {
			orderMongoRepository.delete(_id);
		} catch (Exception e) {
			throw new SuitUpException("Error: Order Delete");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.OrderService#getById(java.lang.String)
	 */
	public Order getById(String _id) throws SuitUpException{
		try {
			return orderMongoRepository.findOne(_id);
		} catch (Exception e) {
			throw new SuitUpException("Error: Order GetById");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.OrderService#get(int, int)
	 */
	public List<Order> get(int page, int size) throws SuitUpException{
		List<Order> order_List = null;
		try {
			order_List = orderMongoRepository.findAll(new PageRequest(page, size)).getContent();
			return order_List;
		} catch (Exception e) {
			throw new SuitUpException("Error: Order Get");
		}
	}	
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.OrderService#getByClient(java.lang.String, int, int)
	 */
	public List<Order> getByClient(String client_id, int page, int size) throws SuitUpException{
		List<Order> order_List = null;
		try {
			order_List = orderMongoRepository.getByClient(client_id, page, size);
			return order_List;
		} catch (Exception e) {
			throw new SuitUpException("Error: Order GetByExample");
		}
	} 	
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.OrderService#getByClient(java.lang.String, int, int)
	 */
	public List<Order> getBySeller(String seller_id, int page, int size) throws SuitUpException{
		List<Order> order_List = null;
		try {
			order_List = orderMongoRepository.getBySeller(seller_id, page, size);
			return order_List;
		} catch (Exception e) {
			throw new SuitUpException("Error: Order GetByExample");
		}
	} 
}
