package chpoi.suitup.repository.sql;

import java.util.List;

import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.sql.SuitSQL;

/**
 *  SuitSQL specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("suitSQLRepository")
public interface SuitSQLRepository {

	/**
	 * Save a given suit. Use the returned instance for further operations as the save operation might have changed the
	 * suit instance completely.
	 * 
	 * @param suit
	 * @return the saved suit
	 */
	SuitSQL save(SuitSQL suit);
	
	/**
	 * Delete the suit with the given id.
	 * 
	 * @param _id
	 */
	void delete(String _id);
	
	/**
	 * Return the persistent instance of the given suit class with the given id, or null if not found. 
	 * 
	 * @param _id
	 * @return 
	 */
	SuitSQL getById(String _id);
	
	/**
	 * Return all persistent instances of the given suit class with the given suitSQL as example, or null if not found. 
	 * 
	 * @param suit
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 */
	List<SuitSQL> getByExample(SuitSQL suit, int page, int size);
	
	/**
	 * Return all persistent instances of the given suit class with the given suitSQL as example, or null if not found. 
	 * 
	 * @param suitname the suitname for search
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 */
	List<SuitSQL> getBySearchSuitname(String suitname, int page, int size);
	
	/**
	 * Return all suits got by given page and size. The number should be the same as size.
	 * Unless the manufacturers are not enough, returns proper numbers of manufacturers.
	 * 
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 */
	List<SuitSQL> getAll(int page, int size);
	
	/**
	 * Return all suit got by given id list.
	 * 
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @return
	 */
	List<SuitSQL> getByIdList(List<String> _id_list);
}
