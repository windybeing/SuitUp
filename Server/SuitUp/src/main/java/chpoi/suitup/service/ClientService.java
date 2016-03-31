package chpoi.suitup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Client;
import chpoi.suitup.entity.OrderItem;
import chpoi.suitup.exception.SuitUpException;

/**
 * Client Service specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Service("clientService")
public interface ClientService {
	
	/**
	 * Client Login Service
	 * 
	 * @param client the properties of client should fit the restrain
	 * @return
	 * @throws SuitUpException in case of log in error
	 */
	Client login(Client client) throws SuitUpException;
	
	/**
	 * Client Register Service
	 * 
	 * @param client username and paasowrd should not be null
	 * @return
	 * @throws SuitUpException in case of register error
	 */
	Client register(Client client) throws SuitUpException;
	
	/**
	 * Client Pay Service
	 * 
	 * @param _id the id of buyer
	 * @param orderItems_List the id list of suits to be bought
	 * @throws SuitUpException in case of pay error
	 */
	void pay(String _id, List<OrderItem> orderItems_List) throws SuitUpException;
	
	/**
	 * Client Delete Service
	 * 
	 * @param _id the id of client to delete
	 * @throws SuitUpException in case of delete error
	 */
	void delete(String _id) throws SuitUpException;
	
	/**
	 * Client update Service
	 * 
	 * @param client to update
	 * @return the client updated
	 * @throws SuitUpException in case of update error
	 */
	Client update(Client client) throws SuitUpException;
	
	/**
	 * Client Get Service
	 * 
	 * @param _id the id of client to get
	 * @return the client got
	 * @throws SuitUpException in case of get error
	 */
	Client getById(String _id) throws SuitUpException;
}
