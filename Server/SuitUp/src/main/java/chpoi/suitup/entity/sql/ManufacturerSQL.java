package chpoi.suitup.entity.sql;

import chpoi.suitup.entity.Manufacturer;

/**
 * ManufacturerSQL class
 * Used for store manufacturer properties in MYSQL
 * 
 * @author Dong Zhiyuan
 *
 */
public class ManufacturerSQL {
	
	private String _id;
	private String manufacturername;
	private String identification ;
	
	public ManufacturerSQL() {}
	
	/**
	 * This method creates a new manufacturerSQL class with properties of manufacturer's.
	 *
	 * @param _id given id of manufacturer
	 * @param manufacturer
	 */
	public ManufacturerSQL(String _id, Manufacturer manufacturer){
		this._id = _id;
		readManufacureAttributes(manufacturer);
	}
	
	/**
	 * This method creates a new manufacturerSQL class with properties of manufacturer's.
	 *
	 * @param manufacturer
	 */
	public ManufacturerSQL(Manufacturer manufacturer){
		this._id = manufacturer.get_id();
		readManufacureAttributes(manufacturer);
	}
	
	private void readManufacureAttributes(Manufacturer manufacturer){
		this.manufacturername = manufacturer.getManufacturername();
		this.identification  = manufacturer.getIdentification();
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
}
