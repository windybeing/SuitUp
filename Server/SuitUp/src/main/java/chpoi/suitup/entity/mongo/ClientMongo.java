package chpoi.suitup.entity.mongo;

import java.util.List;

import org.springframework.data.annotation.Id;

import chpoi.suitup.entity.Client;

/**
 * ClientMongo class
 * Used for store client properties in MongoDB
 * 
 * @author Dong Zhiyuan
 *
 */
public class ClientMongo {

	@Id
	private String _id;
	private String avater_id;
	private List<String> photos_id;
	
	public ClientMongo() {}
	
	/**
	 * This method creates a new clientMongo class with properties of client's.
	 *
	 * @param client
	 */
	public ClientMongo(Client client){
		this._id = client.get_id();
		this.avater_id = client.getAvater_id();
		this.photos_id = client.getPhotos_id();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getAvater_id() {
		return avater_id;
	}

	public void setAvater_id(String avater_id) {
		this.avater_id = avater_id;
	}

	public List<String> getPhotos_id() {
		return photos_id;
	}

	public void setPhotos_id(List<String> photos_id) {
		this.photos_id = photos_id;
	}

}
