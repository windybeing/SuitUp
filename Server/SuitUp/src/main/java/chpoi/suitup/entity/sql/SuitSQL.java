package chpoi.suitup.entity.sql;

import javax.validation.constraints.Min;

import org.springframework.data.annotation.Id;

import chpoi.suitup.entity.Suit;

/**
 * SuitSQL class
 * Used for store suit properties in MYSQL
 * 
 * @author Dong Zhiyuan
 *
 */
public class SuitSQL {

	@Id
	private String _id;
	private String suitname;
	private String seller_id;
	private String manufacturer_id;
	@Min(0)
	private Double price;
	private Integer used;
	
	public SuitSQL() {}
	
	/**
	 * This method creates a new suitSQL class with properties of suit's.
	 *
	 * @param _id given id of suit
	 * @param suit
	 */
	public SuitSQL(String _id, Suit suit){
		this._id = _id;
		readClientAttributes(suit);
	}
	
	/**
	 * This method creates a new suitSQL class with properties of suit's.
	 *
	 * @param suit
	 */
	public SuitSQL(Suit suit){
		this._id = suit.get_id();
		readClientAttributes(suit);
	}
	
	private void readClientAttributes(Suit suit){
		this.suitname = suit.getSuitname();
		this.seller_id = suit.getSeller_id();
		this.manufacturer_id = suit.getManufacturer_id();
		this.price = suit.getPrice();
		this.used = suit.getUsed();
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
	public Integer getUsed() {
		return used;
	}
	public void setUsed(Integer used) {
		this.used = used;
	}
	
}
