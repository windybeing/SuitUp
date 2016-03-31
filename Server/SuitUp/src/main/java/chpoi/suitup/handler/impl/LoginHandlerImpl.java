package chpoi.suitup.handler.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import chpoi.suitup.entity.Client;
import chpoi.suitup.entity.Seller;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.handler.LoginHandler;
import chpoi.suitup.service.ClientService;
import chpoi.suitup.service.ManufacturerService;
import chpoi.suitup.service.SellerService;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;


/**
 * LoginHandler base implementation.
 * 
 * @author Dong Zhiyuan
 */
public class LoginHandlerImpl implements LoginHandler{
	
	@Autowired
	private ClientService clientService;	
	@Autowired
	private SellerService sellerService;	
	@Autowired
	private ManufacturerService manufacturerService;
	
	public enum Type {
		Client, Seller
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.handler.LoginHandler#login(chpoi.suitup.socket.Messenger, net.sf.json.JSONObject)
	 */
	public void login(Messenger messenger, JSONObject requestPara){
		Type type = Type.valueOf(Type.class, requestPara.getString("type"));
		String username, password;
		Object obj = null;
		try {
			switch(type){
			case Client:
				username = requestPara.getString("username");
				password = requestPara.getString("password");
				obj = clientLogin(username, password);
				break;
			case Seller:
				username = requestPara.getString("username");
				password = requestPara.getString("password");
				obj = sellerLogin(username, password);
				break;
			default:
				messenger.mainResponse(JSONUtil.generateFailJSONResponse("Error: Type Error!"));
				return;
			}
			messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(obj));
		} catch (SuitUpException e) {
			messenger.mainResponse(JSONUtil.generateFailJSONResponse(e.getMessage()));
		}
	}
	
	/**
	 * Specific Login Action for Client
	 * 
	 * @param username 
	 * @param password
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Client clientLogin(String username, String password) throws SuitUpException{
		Client client = new Client();
		client.setUsername(username);
		client.setPassword(password);
		client = clientService.login(client);
		return client;
	}
	
	/**
	 * Specific Login Action for Seller
	 * 
	 * @param username 
	 * @param password
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Seller sellerLogin(String username, String password) throws SuitUpException{
		Seller seller = new Seller();
		seller.setUsername(username);
		seller.setPassword(password);
		seller = sellerService.login(seller);
		return seller;
	}
	
}
