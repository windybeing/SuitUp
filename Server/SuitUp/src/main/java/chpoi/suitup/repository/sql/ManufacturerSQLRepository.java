package chpoi.suitup.repository.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.sql.ManufacturerSQL;

/**
 *  ManufacturerSQL specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("manufacturerSQLRepository")
public interface ManufacturerSQLRepository {
	
	/**
	 * Save a given manufacturer. Use the returned instance for further operations as the save operation might have changed the
	 * manufacturer instance completely.
	 * 
	 * @param manufacturer
	 * @return the saved manufacturer
	 */
	ManufacturerSQL save(ManufacturerSQL manufacturer);
	
	/**
	 * Delete the manufacturer with the given id.
	 * 
	 * @param _id
	 */
	void delete(String _id);
	
	/**
	 * Return the persistent instance of the given manufacturer class with the given id, or null if not found. 
	 * 
	 * @param _id
	 * @return 
	 */
	ManufacturerSQL getById(String _id);	
	
	/**
	 * Return the persistent instance of the given manufacturer class with the given identification, or null if not found. 
	 * 
	 * @param identification
	 * @return 
	 */
	ManufacturerSQL getByIdentification(String identification);	
	
	/**
	 * Return all persistent instances of the given manufacturer class with the given manufacturerSQL as example, or null if not found. 
	 * 
	 * @param manufacturer
	 * @return
	 */
	List<ManufacturerSQL> getByExample(ManufacturerSQL manufacturer, int page, int size);
	
	/**
	 * Return all manufacturers got by given page and size. The number should be the same as size.
	 * Unless the manufacturers are not enough, returns proper numbers of manufacturers.
	 * 
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 */
	List<ManufacturerSQL> getAll(int page, int size);
	
	/**
	 * Return all manufacturer got by given id list.
	 * 
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 */
	List<ManufacturerSQL> getByIdList(List<String> _id_list);
}
