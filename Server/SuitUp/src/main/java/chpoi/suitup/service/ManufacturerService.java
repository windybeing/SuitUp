package chpoi.suitup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Manufacturer;
import chpoi.suitup.exception.SuitUpException;

/**
 * Manufacturer Service specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Service("manufacturerService")
public interface ManufacturerService {
	
	/**
	 * Manufacturer Register Service
	 * 
	 * @param manufacturer to register
	 * @return
	 * @throws SuitUpException in case of register error
	 */
	Manufacturer register(Manufacturer manufacturer) throws SuitUpException;
	
	/**
	 * Manufacturer update Service
	 * 
	 * @param manufacturer to update
	 * @return the manufacturer updated
	 * @throws SuitUpException in case of update error
	 */
	Manufacturer update(Manufacturer manufacturer) throws SuitUpException;
	
	/**
	 * Manufacturer Delete Service
	 * 
	 * @param _id the id of manufacturer to delete
	 * @throws SuitUpException in case of delete error
	 */
	void delete(String _id) throws SuitUpException;
	
	/**
	 * Manufacturer Get Service
	 * 
	 * @param _id the id of manufacturer to get
	 * @return the manufacturer got
	 * @throws SuitUpException in case of get error
	 */
	Manufacturer getById(String _id) throws SuitUpException;
	/**
	 * Manufacturer Get Service
	 * 
	 * @param identification the identification of manufacturer to get
	 * @return the manufacturer got
	 * @throws SuitUpException in case of get error
	 */
	Manufacturer getByIdentification(String identification) throws SuitUpException;
	
	/**
	 * Manufacturer get Service
	 * 
	 * @param manufacturer the example for get
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return 
	 * @throws SuitUpException in case of get error
	 */
	List<Manufacturer> getByExample(Manufacturer manufacturer, int page, int size) throws SuitUpException;

	/**
	 * Manufacturer Get Service
	 * 
	 * @param manufacturers_id_List the id list of manufacturers to get
	 * @return
	 * @throws SuitUpException
	 */
	List<Manufacturer> getByIdList(List<String> manufacturers_id_List) throws SuitUpException;

}
