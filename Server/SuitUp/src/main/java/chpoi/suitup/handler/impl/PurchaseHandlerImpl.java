package chpoi.suitup.handler.impl;

import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import chpoi.suitup.entity.Order;
import chpoi.suitup.entity.OrderItem;
import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.handler.PurchaseHandler;
import chpoi.suitup.service.ClientService;
import chpoi.suitup.service.OrderService;
import chpoi.suitup.service.SuitService;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;


/**
 * PurchaseHandler base implementation.
 * 
 * @author Dong Zhiyuan
 */
public class PurchaseHandlerImpl implements PurchaseHandler{
	
	@Autowired
	private ClientService clientService;	
	@Autowired
	private OrderService orderService;
	@Autowired
	private SuitService suitService;
	
	public enum Type {
		Client
	}

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.handler.PurchaseHandler#purchase(chpoi.suitup.socket.Messenger, net.sf.json.JSONObject)
	 */
	public void purchase(Messenger messenger, JSONObject requestPara){
		Type type = Type.valueOf(Type.class, requestPara.getString("type"));
		try {
			switch(type){
			case Client:
				Order order = Order.JSONToOrder(requestPara);
				clientPurchase(order);
				break;
			default:
				messenger.mainResponse(JSONUtil.generateFailJSONResponse("Error: Type Error!"));
				return;
			}
			messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(null));
		} catch (SuitUpException e) {
			messenger.mainResponse(JSONUtil.generateFailJSONResponse(e.getMessage()));
		}
	}
	
	/**
	 * Specific Purchase Action for Client
	 * 
	 * @param order the order describes details
	 * @throws SuitUpException in case of SuitUp error
	 */
	private void clientPurchase(Order order) throws SuitUpException{
		try {
			order.setTime(new Date(System.currentTimeMillis()));
			order = orderService.create(order);
			List<OrderItem> orderItems = order.getOrderItems();
			for(OrderItem orderItem : orderItems){
				Suit suit = suitService.getById(orderItem.getSuit_id());
				suit.setUsed(1);
				suitService.update(suit);
			}
			clientService.pay(order.getClient_id(), order.getOrderItems());
		} catch (Exception e) {
			if(order.get_id() != null){
				orderService.delete(order.get_id());
			}
			throw e;
		}
	}
}
