package chpoi.suitup.service;

import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Seller;
import chpoi.suitup.exception.SuitUpException;

/**
 * Seller Service specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Service("sellerService")
public interface SellerService {
	
	/**
	 * Seller Login Service
	 * 
	 * @param seller the properties of seller should fit the restrain
	 * @return
	 * @throws SuitUpException in case of log in error
	 */
	Seller login(Seller seller) throws SuitUpException;
	
	/**
	 * Seller Register Service
	 * 
	 * @param seller username and paasowrd should not be null
	 * @return
	 * @throws SuitUpException in case of register error
	 */
	Seller register(Seller seller) throws SuitUpException;
	
	/**
	 * Seller Delete Service
	 * 
	 * @param _id the id of seller to delete
	 * @throws SuitUpException in case of delete error
	 */
	void delete(String _id) throws SuitUpException;
	
	/**
	 * Seller update Service
	 * 
	 * @param seller to update
	 * @return the seller updated
	 * @throws SuitUpException in case of update error
	 */
	Seller update(Seller seller) throws SuitUpException;
	
	/**
	 * Seller Get Service
	 * 
	 * @param _id the id of seller to get
	 * @return the seller got
	 * @throws SuitUpException in case of get error
	 */
	Seller getById(String _id) throws SuitUpException;
}
