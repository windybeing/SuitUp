package chpoi.suitup.repository.sql;

import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.sql.ClientSQL;

/**
 * ClientSQL specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("clientSQLRepository")
public interface ClientSQLRepository {

	/**
	 * Save a given client. Use the returned instance for further operations as the save operation might have changed the
	 * client instance completely.
	 * 
	 * @param client
	 * @return the saved client
	 */
	ClientSQL save(ClientSQL client);
	
	/**
	 * Delete the client with the given id.
	 * 
	 * @param _id
	 */
	void delete(String _id);
	
	/**
	 * Return the persistent instance of the given client class with the given id, or null if not found. 
	 * 
	 * @param _id
	 * @return 
	 */
	ClientSQL getById(String _id);
	
	/**
	 * Return the persistent instance of the given client class with the given clientSQL as example, or null if not found. 
	 * 
	 * @param client
	 * @return
	 */
	ClientSQL getOneByExample(ClientSQL client);
	
	/**
	 * Return the number of clients got by example clientSQL available.
	 * 
	 * @return the number of clients returned
	 */
	int getCountByExample(ClientSQL client);
}
