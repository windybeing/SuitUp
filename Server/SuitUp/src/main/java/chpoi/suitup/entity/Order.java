package chpoi.suitup.entity;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.data.annotation.Id;

/**
 * Order Class
 * 
 * @author Dong Zhiyuan
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes({
    @Type(name="orderItems", value=java.util.List.class)})
public class Order {

	@Id
	private String _id;
	private Date time;
	private String client_id;
	private String address;
	private String receiver;
	private String phonenumber;
	private String bust;
	private String waistline;
	private String hipline;
	private String shoulder;
	private String forebreast;
	private String metathorax;
	private String upperlimb;
	private String lowerlimb;
	private List<OrderItem> orderItems;
	
	public Order ( )  {}

	/**
     * This method changes JSONObject to Order
     * 
     * @param json the JSONObject to change
     * @return the expected order
     */
	public static Order JSONToOrder(JSONObject json){
		Order order = null;
        try {
        	JSONArray orderItem_array = json.getJSONArray("orderItems");
        	json.remove("orderItems");
			ObjectMapper objectMapper = new ObjectMapper();
			order = objectMapper.readValue(json.toString(), Order.class);
			List<OrderItem> orderItems_List = orderItem_array.toList(orderItem_array, OrderItem.class);
			order.setOrderItems(orderItems_List);
		} catch (Exception e) {	}
		return order;
	}
	
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getClient_id() {
		return client_id;
	}
	public void setClient_id(String client_id) {
		this.client_id = client_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getBust() {
		return bust;
	}

	public void setBust(String bust) {
		this.bust = bust;
	}

	public String getWaistline() {
		return waistline;
	}

	public void setWaistline(String waistline) {
		this.waistline = waistline;
	}

	public String getHipline() {
		return hipline;
	}

	public void setHipline(String hipline) {
		this.hipline = hipline;
	}

	public String getShoulder() {
		return shoulder;
	}

	public void setShoulder(String shoulder) {
		this.shoulder = shoulder;
	}

	public String getForebreast() {
		return forebreast;
	}

	public void setForebreast(String forebreast) {
		this.forebreast = forebreast;
	}

	public String getMetathorax() {
		return metathorax;
	}

	public void setMetathorax(String metathorax) {
		this.metathorax = metathorax;
	}

	public String getUpperlimb() {
		return upperlimb;
	}

	public void setUpperlimb(String upperlimb) {
		this.upperlimb = upperlimb;
	}

	public String getLowerlimb() {
		return lowerlimb;
	}

	public void setLowerlimb(String lowerlimb) {
		this.lowerlimb = lowerlimb;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	
}
