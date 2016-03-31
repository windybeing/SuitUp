package chpoi.suitup.handler.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import chpoi.suitup.entity.Manufacturer;
import chpoi.suitup.entity.Order;
import chpoi.suitup.entity.OrderItem;
import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.handler.QueryHandler;
import chpoi.suitup.service.ManufacturerService;
import chpoi.suitup.service.OrderService;
import chpoi.suitup.service.SuitService;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;

/**
 * QUeryHandler base implementation.
 * 
 * @author Dong Zhiyuan
 */
public class QueryHandlerImpl implements QueryHandler{

	@Autowired
	private SuitService suitService;
	@Autowired
	private ManufacturerService manufacturerService;
	@Autowired
	private OrderService orderService;

	public enum Type {
		Suit, SuitBySuitname, Manufacturer, OrderByClient, OrderBySeller
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.handler.QueryHandler#query(chpoi.suitup.socket.Messenger, net.sf.json.JSONObject)
	 */
	public void query(Messenger messenger, JSONObject requestPara){
		Type type = Type.valueOf(Type.class, requestPara.getString("type"));
		int page = requestPara.getInt("page");
		int size = requestPara.getInt("size");
		String id = null;
		Object obj = null;
		try {
			switch(type){
			case Suit:
				Suit suit = Suit.JSONToSuit(requestPara);
				obj = suitQuery(suit, page, size);
				break;
			case SuitBySuitname:
				String suitname = requestPara.getString("suitname");
				obj = suitQueryBySearchSuitname(suitname, page, size);
				break;
			case Manufacturer:
				Manufacturer manufacturer = Manufacturer.JSONToManufacturer(requestPara);
				obj = manufacturerQuery(manufacturer, page, size);
				break;
			case OrderByClient:
				id = requestPara.getString("client_id");
				orderQueryByClient(messenger, id, page, size);
				return;
			case OrderBySeller:
				id = requestPara.getString("seller_id");
				orderQueryBySeller(messenger, id, page, size);
				return;
			default:
				messenger.mainResponse(JSONUtil.generateFailJSONResponse("Error: Type Error!"));
				return;
			}
			messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(obj));
		} catch (SuitUpException e) {
			e.printStackTrace();
			messenger.mainResponse(JSONUtil.generateFailJSONResponse(e.getMessage()));
		}
	}
	
	/**
	 * Specific Query Action for Suit
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param suit example of query
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @throws SuitUpException
	 */
	private List<Suit> suitQuery(Suit suit, int page, int size) throws SuitUpException{
		List<Suit> suit_List = suitService.getByExample(suit, page, size);
		return suit_List;
	}
	
	/**
	 * Specific Query Action for Suit
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param suitname the suitname for search
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @throws SuitUpException
	 */
	private List<Suit> suitQueryBySearchSuitname(String suitname, int page, int size) throws SuitUpException{
		List<Suit> suit_List = suitService.getBySearchSuitname(suitname, page, size);
		return suit_List;
	}
	
	/**
	 * Specific Query Action for Manufacturer
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param manufacturer example of query
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @throws SuitUpException
	 */
	private List<Manufacturer> manufacturerQuery(Manufacturer manufacturer, int page, int size) throws SuitUpException{
		List<Manufacturer> manufacturer_List = manufacturerService.getByExample(manufacturer, page, size);
		return manufacturer_List;
	}
	
	/**
	 * Specific Query Action for Order
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param client_id the id of client
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @throws SuitUpException
	 */
	private void orderQueryByClient(Messenger messenger, String client_id, int page, int size) throws SuitUpException{
		List<Order> order_List = orderService.getByClient(client_id, page, size);
		if(order_List.size() == 0){
			messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(null));
			return;
		}
		JSONArray order_Array = JSONArray.fromObject(order_List);
		JSONArray responsePara = JSONArray.fromObject(order_Array);
		
		List<String> suits_id_List = new ArrayList<String>();
		List<String> manufacturers_id_List = new ArrayList<String>();
		Map map = new HashMap(), temp = null;
		
		for(Order order : order_List){
			for(OrderItem orderItem: order.getOrderItems()){
				if(!suits_id_List.contains(orderItem.getSuit_id())){
					suits_id_List.add(orderItem.getSuit_id());
				}
				if(!manufacturers_id_List.contains(orderItem.getManufacturer_id())){
					manufacturers_id_List.add(orderItem.getManufacturer_id());
				}
			}
		}
		List<Suit> suit_List = suitService.getByIdList(suits_id_List);
		List<Manufacturer> manufacturer_List = manufacturerService.getByIdList(manufacturers_id_List);
		
		for(Suit suit : suit_List){
			temp = new HashMap<String, String>();
			temp.put("suitname", suit.getSuitname());
			temp.put("photo_id", suit.getPhoto_id());
			map.put(suit.get_id(), temp);
		}
		responsePara.add(map);
		
		map.clear();
		for(Manufacturer manufacturer : manufacturer_List){
			temp = new HashMap<String, String>();
			temp.put("manufacturername", manufacturer.getManufacturername());
			map.put(manufacturer.get_id(), temp);
		}
		responsePara.add(map);
		
		messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(responsePara));
	}
	
	/**
	 * Specific Query Action for Order
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param seller_id the id of client
	 * @param page the number of page to get
	 * @param size the size of one page
	 * @throws SuitUpException
	 */
	private void orderQueryBySeller(Messenger messenger, String seller_id, int page, int size) throws SuitUpException{
		List<Order> order_List = orderService.getBySeller(seller_id, page, size);
		if(order_List.size() == 0){
			messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(null));
			return;
		}
		
		for(Order order : order_List){
			List<OrderItem> orderItem_List = order.getOrderItems();
			Iterator<OrderItem> itor = orderItem_List.iterator();
			for(; itor.hasNext();){
				if(!(itor.next().getSeller_id().equals(seller_id))){
					itor.remove();
				}
			}
			order.setOrderItems(orderItem_List);
		}

		JSONArray order_Array = JSONArray.fromObject(order_List);
		JSONArray responsePara = JSONArray.fromObject(order_Array);
		
		List<String> suits_id_List = new ArrayList<String>();
		List<String> manufacturers_id_List = new ArrayList<String>();
		Map map = new HashMap(), temp = null;
		
		for(Order order : order_List){
			for(OrderItem orderItem: order.getOrderItems()){
				if(!suits_id_List.contains(orderItem.getSuit_id())){
					suits_id_List.add(orderItem.getSuit_id());
				}
				if(!manufacturers_id_List.contains(orderItem.getManufacturer_id())){
					manufacturers_id_List.add(orderItem.getManufacturer_id());
				}
			}
		}
		List<Suit> suit_List = suitService.getByIdList(suits_id_List);
		List<Manufacturer> manufacturer_List = manufacturerService.getByIdList(manufacturers_id_List);
		
		for(Suit suit : suit_List){
			temp = new HashMap<String, String>();
			temp.put("suitname", suit.getSuitname());
			temp.put("photo_id", suit.getPhoto_id());
			map.put(suit.get_id(), temp);
		}
		responsePara.add(map);
		
		map.clear();
		for(Manufacturer manufacturer : manufacturer_List){
			temp = new HashMap<String, String>();
			temp.put("manufacturername", manufacturer.getManufacturername());
			map.put(manufacturer.get_id(), temp);
		}
		responsePara.add(map);
		
		messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(responsePara));	}
}
