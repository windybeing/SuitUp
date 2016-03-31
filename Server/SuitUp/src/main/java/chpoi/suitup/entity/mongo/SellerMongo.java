package chpoi.suitup.entity.mongo;

import java.util.List;

import chpoi.suitup.entity.Seller;

/**
 * SellerMongo class
 * Used for store seller properties in MongoDB
 * 
 * @author Dong Zhiyuan
 *
 */
public class SellerMongo {
	
	private String _id;
	private String manufacturer_id;
	private List<String> information;
	
	public SellerMongo() {}
	
	/**
	 * This method creates a new sellerMongo class with properties of seller's.
	 *
	 * @param seller
	 */
	public SellerMongo(Seller seller){
		this._id = seller.get_id();
		this.manufacturer_id = seller.getManufacturer_id();
		this.information = seller.getInformation();
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getManufacturer_id() {
		return manufacturer_id;
	}
	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}
	public List<String> getInformation() {
		return information;
	}
	public void setInformation(List<String> information) {
		this.information = information;
	}
}
