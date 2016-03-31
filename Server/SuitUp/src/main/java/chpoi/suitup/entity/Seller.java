package chpoi.suitup.entity;

import java.util.List;

import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.annotation.Id;

import chpoi.suitup.entity.mongo.SellerMongo;
import chpoi.suitup.entity.sql.SellerSQL;

/**
 * Seller Class
 * 
 * @author Dong Zhiyuan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Seller {
	
	@Id
	private String _id;
	private String username;
	private String password;
	private String email;
	private String phonenumber;
	private String manufacturer_id;
	private List<String> information;
	
	public Seller () { }
	
	/**
	 * This method creates a new seller class with properties of both sellerMongo's and sellerSQL's.
	 * 
	 * @param sellerMongo
	 * @param sellerSQL
	 */
	public Seller (SellerMongo sellerMongo, SellerSQL sellerSQL){
		this._id = sellerMongo.get_id();
		this.manufacturer_id = sellerMongo.getManufacturer_id();
		this.information = sellerMongo.getInformation();
		this.username = sellerSQL.getUsername();
		this.password = sellerSQL.getPassword();
		this.email = sellerSQL.getEmail();
		this.phonenumber = sellerSQL.getPhonenumber();
 	}
	
	/**
	 * This method update seller's properties by given seller if the given seller's corresponding property is not null.
	 * 
	 * @param seller
	 */
	public void update(Seller seller){
		if(seller.get_id() != null)this._id = seller.get_id();
		if(seller.getUsername() != null)this.username = seller.getUsername();
		if(seller.getPassword() != null)this.password = seller.getPassword();
		if(seller.getEmail() != null)this.email = seller.getEmail();
		if(seller.getPhonenumber() != null)this.phonenumber = seller.getPhonenumber();
		if(seller.getManufacturer_id() != null)this.manufacturer_id = seller.getManufacturer_id();
	}

	/**
     * This method changes JSONObject to Seller
     * 
     * @param json the JSONObject to change
     * @return the expected seller
     */
	public static Seller JSONToSeller(JSONObject json){
		Seller seller = null;
        try {
			ObjectMapper objectMapper = new ObjectMapper();
			seller = objectMapper.readValue(json.toString(), Seller.class);
		} catch (Exception e) {	}
		return seller;
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
	public List<String> getInformation() {
		return information;
	}
	public void setInformation(List<String> information) {
		this.information = information;
	}

}
