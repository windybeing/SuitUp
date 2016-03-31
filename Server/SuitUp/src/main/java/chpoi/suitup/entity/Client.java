package chpoi.suitup.entity;

import java.util.List;

import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.annotation.Id;

import chpoi.suitup.entity.mongo.ClientMongo;
import chpoi.suitup.entity.sql.ClientSQL;

/**
 * Client Class
 * 
 * @author Dong Zhiyuan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {

	@Id
	private String _id;
	private String username;
	private String password;
	private String email;
	private String phonenumber;
	private String clientname;
	private Integer age;
	private String address;
	private String avater_id;
	private String parameter_id;
	private List<String> photos_id;
	
	public Client () { }
	
	/**
	 * This method creates a new client class with properties of both clientMongo's and clientSQL's.
	 * 
	 * @param clientMongo
	 * @param clientSQL
	 */
	public Client (ClientMongo clientMongo, ClientSQL clientSQL){
		this._id = clientMongo.get_id();
		this.avater_id = clientMongo.getAvater_id();
		this.photos_id = clientMongo.getPhotos_id();
		this.username = clientSQL.getUsername();
		this.password = clientSQL.getPassword();
		this.email = clientSQL.getEmail();
		this.phonenumber = clientSQL.getPhonenumber();
		this.clientname = clientSQL.getClientname();
		this.age = clientSQL.getAge();
		this.address = clientSQL.getAddress();
 	}
	
	/**
	 * This method update client's properties by given client if the given client's corresponding property is not null.
	 * 
	 * @param client
	 */
	public void update(Client client){
		if(client.get_id() != null)this._id = client.get_id();
		if(client.getUsername() != null)this.username = client.getUsername();
		if(client.getPassword() != null)this.password = client.getPassword();
		if(client.getEmail() != null)this.email = client.getEmail();
		if(client.getPhonenumber() != null)this.phonenumber = client.getPhonenumber();
		if(client.getClientname() != null)this.clientname = client.getClientname();
		if(client.getAge() != null)this.age = client.getAge();
		if(client.getAddress() != null)this.address = client.getAddress();
	}
	
	/**
     * This method changes JSONObject to Client
     * 
     * @param json the JSONObject to change
     * @return the expected client
     */
	public static Client JSONToClient(JSONObject json){
		Client client = null;
        try {
			ObjectMapper objectMapper = new ObjectMapper();
			client = objectMapper.readValue(json.toString(), Client.class);
		} catch (Exception e) {	}
		return client;
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
	public String getClientname() {
		return clientname;
	}
	public void setClientname(String clientname) {
		this.clientname = clientname;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	public void setPhotos(List<String> photos_id) {
		this.photos_id = photos_id;
	}
	public String getParameter_id() {
		return parameter_id;
	}
	public void setParameter_id(String parameter_id) {
		this.parameter_id = parameter_id;
	}

}
