package chpoi.suitup.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chpoi.suitup.entity.Photo;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.repository.mongo.PhotoMongoRepository;
import chpoi.suitup.service.PhotoService;

@Service("photoServiceImpl")
public class PhotoServiceImpl implements PhotoService{

	@Autowired
	private PhotoMongoRepository photoMongoRepository;

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.PhotoService#create(chpoi.suitup.entity.Photo)
	 */
	public Photo create(Photo photo) throws SuitUpException{
		try {
			return photoMongoRepository.save(photo);
		} catch (Exception e) {
			throw new SuitUpException("Error: Photo Create");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.PhotoService#delete(java.lang.String)
	 */
	public void delete(String _id) throws SuitUpException{
		try {
			photoMongoRepository.delete(_id);
		} catch (Exception e) {
			throw new SuitUpException("Error: Photo Delete");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.PhotoService#update(chpoi.suitup.entity.Photo)
	 */
	public void update(Photo photo) throws SuitUpException{
		try {
			photoMongoRepository.save(photo);
		} catch (Exception e) {
			throw new SuitUpException("Error: Photo Update");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.PhotoService#getById(java.lang.String)
	 */
	public Photo getById(String _id) throws SuitUpException{
		try {
			return photoMongoRepository.findOne(_id);
		} catch (Exception e) {
			throw new SuitUpException("Error: Photo GetById");
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.service.PhotoService#getByIdList(java.util.List)
	 */
	public List<Photo> getByIdList(List<String> photos_id_List) throws SuitUpException{
		try {
			return (List<Photo>) photoMongoRepository.findAll(photos_id_List);
		} catch (Exception e) {
			throw new SuitUpException("Error: Photo Add");
		}
	}
}
