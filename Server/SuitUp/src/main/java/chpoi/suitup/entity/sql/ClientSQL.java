package chpoi.suitup.entity.sql;

import javax.validation.constraints.Min;

import org.springframework.data.annotation.Id;

import chpoi.suitup.entity.Client;

/**
 * ClientSQL class
 * Used for store client properties in MYSQL
 * 
 * @author Dong Zhiyuan
 *
 */
public class ClientSQL {

	@Id
	private String _id;
	private String username;
	private String password;
	private String email;
	private String phonenumber;
	private String clientname;
	@Min(0)
	private Integer age;
	private String address;
	
	public ClientSQL() {}
	
	/**
	 * This method creates a new clientSQL class with properties of client's.
	 *
	 * @param _id given id of client
	 * @param client
	 */
	public ClientSQL(String _id, Client client){
		this._id = _id;
		readClientAttributes(client);
	}
	
	/**
	 * This method creates a new clientSQL class with properties of client's.
	 *
	 * @param client
	 */
	public ClientSQL(Client client){
		this._id = client.get_id();
		readClientAttributes(client);
	}
	
	private void readClientAttributes(Client client){
		this.username = client.getUsername();
		this.password = client.getPassword();
		this.email = client.getEmail();
		this.phonenumber = client.getPhonenumber();
		this.clientname = client.getClientname();
		this.age = client.getAge();
		this.address = client.getAddress();
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
}
