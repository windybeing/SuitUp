package chpoi.suitup.entity;

import java.util.List;

import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import chpoi.suitup.entity.mongo.SuitMongo;
import chpoi.suitup.entity.sql.SuitSQL;

/**
 * Suit Class
 * 
 * @author Dong Zhiyuan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Suit {

	private String _id;
	private String suitname;
	private String seller_id;
	private String manufacturer_id;
	private Double price;
	private String photo_id;
	private Integer used;
	private List<String> information;
	
	public Suit () { }
	
	/**
	 * This method creates a new suit class with properties of both suitMongo's and suitSQL's.
	 * 
	 * @param suitMongo
	 * @param suitSQL
	 */
	public Suit (SuitMongo suitMongo, SuitSQL suitSQL){
		this._id = suitMongo.get_id();
		this.photo_id = suitMongo.getPhoto_id();
		this.information = suitMongo.getInformation();
		this.suitname = suitSQL.getSuitname();
		this.seller_id = suitSQL.getSeller_id();
		this.price = suitSQL.getPrice();
		this.manufacturer_id = suitSQL.getManufacturer_id();
		this.used = suitSQL.getUsed();
 	}

	/**
	 * This method update suit properties by given suit if the given suit corresponding property is not null.
	 * 
	 * @param suit
	 */
	public void update(Suit suit){
		if(suit.get_id() != null)this._id = suit.get_id();
		if(suit.getSuitname() != null)this.suitname = suit.getSuitname();
		if(suit.getSeller_id() != null)this.seller_id = suit.getSeller_id();
		if(suit.getManufacturer_id() != null)this.manufacturer_id = suit.getManufacturer_id();
		if(suit.getPrice() != null)this.price = suit.getPrice();
		if(suit.getPhoto_id() != null)this.photo_id = suit.getPhoto_id();
		if(suit.getInformation() != null)this.information = suit.getInformation();
	}
	
	/**
     * This method changes JSONObject to Suit
     * 
     * @param json the JSONObject to change
     * @return the expected suit
     */
	public static Suit JSONToSuit(JSONObject json){
		Suit suit = null;
        try {
			ObjectMapper objectMapper = new ObjectMapper();
			suit = objectMapper.readValue(json.toString(), Suit.class);
		} catch (Exception e) {	}
		return suit;
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getSuitname() {
		return suitname;
	}
	public void setSuitname(String suitname) {
		this.suitname = suitname;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getManufacturer_id() {
		return manufacturer_id;
	}
	public void setManufacturer_id(String manufacturer_id) {
		this.manufacturer_id = manufacturer_id;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getPhoto_id() {
		return photo_id;
	}
	public void setPhoto_id(String photo_id) {
		this.photo_id = photo_id;
	}
	public Integer getUsed() {
		return used;
	}
	public void setUsed(Integer used) {
		this.used = used;
	}
	public List<String> getInformation() {
		return information;
	}
	public void setInformation(List<String> information) {
		this.information = information;
	}
	
}
