package chpoi.suitup.entity.sql;

import chpoi.suitup.entity.Seller;

/**
 * SellerSQL class
 * Used for store seller properties in MYSQL
 * 
 * @author Dong Zhiyuan
 *
 */
public class SellerSQL {
	
	private String _id;
	private String username;
	private String password;
	private String email;
	private String phonenumber;
	private String manufacturer_id;

	public SellerSQL() {}
	
	/**
	 * This method creates a new sellerSQL class with properties of seller's.
	 *
	 * @param _id given id of seller
	 * @param seller
	 */
	public SellerSQL(String _id, Seller seller){
		this._id = _id;
		readSellerAttributes(seller);
	}
	
	/**
	 * This method creates a new sellerSQL class with properties of seller's.
	 *
	 * @param seller
	 */
	public SellerSQL(Seller seller){
		this._id = seller.get_id();
		readSellerAttributes(seller);
	}
	
	private void readSellerAttributes(Seller seller){
		this.username = seller.getUsername();
		this.password = seller.getPassword();
		this.email = seller.getEmail();
		this.phonenumber = seller.getPhonenumber();
		this.manufacturer_id = seller.getManufacturer_id();
	}

	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getManufacturer_id() {
		return manufacturer_id;
	}
	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}
}
