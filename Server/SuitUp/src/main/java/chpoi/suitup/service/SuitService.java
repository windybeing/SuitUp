package chpoi.suitup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;

/**
 * Suit Service specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Service("suitService")
public interface SuitService {

	/**
	 * Suit Create Service
	 * 
	 * @param suit the suit to create
	 * @return the suit created
	 * @throws SuitUpException
	 */
	Suit create(Suit suit) throws SuitUpException;
	
	/**
	 * Suit Delete Service
	 * 
	 * @param _id the id of suit to delete
	 * @throws SuitUpException in case of delete error
	 */
	void delete(String _id) throws SuitUpException;
	
	/**
	 * Suit update Service
	 * 
	 * @param suit to update
	 * @return the suit updated
	 * @throws SuitUpException in case of update error
	 */
	Suit update(Suit suit) throws SuitUpException;
	
	/**
	 * Suit Get Service
	 * 
	 * @param _id the id of suit to get
	 * @return the suit got
	 * @throws SuitUpException in case of get error
	 */
	Suit getById(String _id) throws SuitUpException;
	
	/**
	 * Suit get Service
	 * 
	 * @param suit the example for get
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return 
	 * @throws SuitUpException in case of get error
	 */
	List<Suit> getByExample(Suit suit, int page, int size) throws SuitUpException;
	
	/**
	 * Suit get Service
	 * 
	 * @param suitname the suitname for search
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return 
	 * @throws SuitUpException in case of get error
	 */
	List<Suit> getBySearchSuitname(String suitname, int page, int size) throws SuitUpException;
	
	/**
	 * Suit Get Service
	 * 
	 * @param suits_id_List the id list of suits to get
	 * @return
	 * @throws SuitUpException
	 */
	List<Suit> getByIdList(List<String> suits_id_List) throws SuitUpException;

	/**
	 * Suit Check Existing Service
	 * 
	 * @param suit_id id of suit to be ckecked
	 * @throws SuitUpException in case of check error 
	 */
	void exists(String suit_id) throws SuitUpException;
	
	/**
	 * Suit Check Existing Service
	 * 
	 * @param suits_id_List id list of suits to be checked
	 * @throws SuitUpException in case of check error 
	 */
	void exists(List<String> suits_id_List) throws SuitUpException;
	
}
