package chpoi.suitup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Photo;
import chpoi.suitup.exception.SuitUpException;

/**
 * Photo Service specific interface.
 * 
 * @author Dong Zhiyuan
 */
@Service("photoService")
public interface PhotoService {
	
	/**
	 * Photo Create Service
	 * 
	 * @param photo the photo to create
	 * @return the photo created
	 * @throws SuitUpException
	 */
	Photo create(Photo photo) throws SuitUpException;
	
	/**
	 * Photo Delete Service
	 * 
	 * @param _id the id of photo to delete
	 * @throws SuitUpException in case of delete error
	 */
	void delete(String _id) throws SuitUpException;

	/**
	 * Photo update Service
	 * 
	 * @param photo to update
	 * @return the photo updated
	 * @throws SuitUpException in case of update error
	 */
	void update(Photo photo) throws SuitUpException;

	/**
	 * Photo Get Service
	 * 
	 * @param _id the id of photo to get
	 * @throws SuitUpException in case of get error
	 */
	Photo getById(String _id) throws SuitUpException;
	
	/**
	 * Photo Get Service
	 * 
	 * @param photos_id_List the id list of photos to get
	 * @return
	 * @throws SuitUpException
	 */
	List<Photo> getByIdList(List<String> photos_id_List) throws SuitUpException;
}
