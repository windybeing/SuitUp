package chpoi.suitup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Order;
import chpoi.suitup.exception.SuitUpException;

/**
 * Order Service specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Service("orderService")
public interface OrderService {
	
	/**
	 * Order Create Service
	 * 
	 * @param order the order to create
	 * @return the order created
	 * @throws SuitUpException
	 */
	Order create(Order order) throws SuitUpException;	
	
	/**
	 * Order Delete Service
	 * 
	 * @param _id the id of order to delete
	 * @throws SuitUpException in case of delete error
	 */
	void delete(String _id) throws SuitUpException;
	
	/**
	 * Order Get Service
	 * 
	 * @param _id the id of order to get
	 * @return the order got
	 * @throws SuitUpException in case of get error
	 */
	Order getById(String _id) throws SuitUpException;;
	
	/**
	 * Order get Service
	 * 
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return 
	 * @throws SuitUpException in case of get error
	 */
	List<Order> get(int page, int size) throws SuitUpException;
	
	/**
	 * Order get by client Service
	 * 
	 * @param client_id the id of client
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 * @throws SuitUpException
	 */
	List<Order> getByClient(String client_id, int page, int size) throws SuitUpException;
	
	/**
	 * Order get by seller Service
	 * 
	 * @param seller_id the id of seller
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 * @throws SuitUpException
	 */
	List<Order> getBySeller(String seller_id, int page, int size) throws SuitUpException;
}
