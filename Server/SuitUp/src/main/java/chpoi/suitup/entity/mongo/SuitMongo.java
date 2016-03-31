package chpoi.suitup.entity.mongo;

import java.util.List;

import org.springframework.data.annotation.Id;

import chpoi.suitup.entity.Suit;

/**
 * SuitMongo class
 * Used for store suit properties in MongoDB
 * 
 * @author Dong Zhiyuan
 *
 */
public class SuitMongo {

	@Id
	private String _id;
	private String photo_id;
	private List<String> information;
	
	public SuitMongo() {}
	
	/**
	 * This method creates a new suitMongo class with properties of suit's.
	 *
	 * @param suit
	 */
	public SuitMongo(Suit suit){
		this._id = suit.get_id();
		this.photo_id = suit.getPhoto_id();
		this.information = suit.getInformation();
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(String photo_id) {
		this.photo_id = photo_id;
	}

	public List<String> getInformation() {
		return information;
	}

	public void setInformation(List<String> information) {
		this.information = information;
	}

}
