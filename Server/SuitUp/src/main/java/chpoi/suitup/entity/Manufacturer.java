package chpoi.suitup.entity;

import java.util.List;

import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import chpoi.suitup.entity.mongo.ManufacturerMongo;
import chpoi.suitup.entity.sql.ManufacturerSQL;

/**
 * Manufacturer Class
 * 
 * @author Dong Zhiyuan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Manufacturer {
	
	private String _id;
	private String manufacturername;
	private String identification ;
	private List<String> address;
	private List<String> information;
	
	public Manufacturer () { }
	
	/**
	 * This method creates a new manufacturer class with properties of both manufacturerMongo's and manufacturerSQL's.
	 * 
	 * @param manufacturerMongo
	 * @param manufacturerSQL
	 */
	public Manufacturer (ManufacturerMongo manufacturerMongo, ManufacturerSQL manufacturerSQL){
		this._id = manufacturerMongo.get_id();
		this.address = manufacturerMongo.getAddress();
		this.information = manufacturerMongo.getInformation();
		this.manufacturername = manufacturerSQL.getManufacturername();
		this.identification  = manufacturerSQL.getIdentification ();
 	}
	
	/**
	 * This method update manufacturer's properties by given manufacturer if the given manufacturer's corresponding property is not null.
	 * 
	 * @param manufacturer
	 */
	public void update(Manufacturer manufacturer){
		if(manufacturer.get_id() != null)this._id = manufacturer.get_id();
		if(manufacturer.getManufacturername() != null)this.manufacturername = manufacturer.getManufacturername();
		if(manufacturer.getAddress() != null)this.address = manufacturer.getAddress();
		if(manufacturer.getInformation() != null)this.information = manufacturer.getInformation();
	}

	/**
     * This method changes JSONObject to Manufacturer
     * 
     * @param json the JSONObject to change
     * @return the expected manufacturer
     */
	public static Manufacturer JSONToManufacturer(JSONObject json){
		Manufacturer manufacturer = null;
        try {
			ObjectMapper objectMapper = new ObjectMapper();
			manufacturer = objectMapper.readValue(json.toString(), Manufacturer.class);
		} catch (Exception e) {	}
		return manufacturer;
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getManufacturername() {
		return manufacturername;
	}
	public void setManufacturername(String manufacturername) {
		this.manufacturername = manufacturername;
	}
	public String getIdentification() {
		return identification;
	}
	public void setIdentification(String identification) {
		this.identification = identification;
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
