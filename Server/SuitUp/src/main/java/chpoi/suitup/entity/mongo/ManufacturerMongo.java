package chpoi.suitup.entity.mongo;

import java.util.List;

import chpoi.suitup.entity.Manufacturer;

/**
 * ManufacturerMongo class
 * Used for store manufacturer properties in MongoDB
 * 
 * @author Dong Zhiyuan
 *
 */
public class ManufacturerMongo {
	
	private String _id;
	private List<String> address;
	private List<String> information;
	
	public ManufacturerMongo() {}
	
	/**
	 * This method creates a new manufacturerMongo class with properties of manufacturer's.
	 *
	 * @param manufacturer
	 */
	public ManufacturerMongo(Manufacturer manufacturer){
		this._id = manufacturer.get_id();
		this.address = manufacturer.getAddress();
		this.information = manufacturer.getInformation();
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public List<String> getAddress() {
		return address;
	}
	public void setAddress(List<String> address) {
		this.address = address;
	}
	public List<String> getInformation() {
		return information;
	}
	public void setInformation(List<String> information) {
		this.information = information;
	}
}
