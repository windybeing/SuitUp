package chpoi.suitup.repository.sql;

import org.springframework.stereotype.Repository;

import chpoi.suitup.entity.sql.SellerSQL;

/**
 *  SellerSQL specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Repository("sellerSQLRepository")
public interface SellerSQLRepository {

	/**
	 * Save a given seller. Use the returned instance for further operations as the save operation might have changed the
	 * seller instance completely.
	 * 
	 * @param seller
	 * @return the saved seller
	 */
	SellerSQL save(SellerSQL seller);
	
	/**
	 * Delete the seller with the given id.
	 * 
	 * @param _id
	 */
	void delete(String _id);
	
	/**
	 * Return the persistent instance of the given seller class with the given sellerSQL as example, or null if not found. 
	 * 
	 * @param seller
	 * @return
	 */
	SellerSQL getById(String _id);
	
	/**
	 * Return the persistent instance of the given seller class with the given sellerSQL as example, or null if not found. 
	 * 
	 * @param seller
	 * @return
	 */
	SellerSQL getOneByExample(SellerSQL seller);
	
	/**
	 * Return the number of sellers got by example sellerSQL available.
	 * 
	 * @return the number of sellers returned
	 */
	int getCountByExample(SellerSQL seller);
}
