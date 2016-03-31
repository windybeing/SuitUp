package chpoi.suitup.handler.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import chpoi.suitup.entity.Client;
import chpoi.suitup.entity.Manufacturer;
import chpoi.suitup.entity.Seller;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.handler.RegisterHandler;
import chpoi.suitup.service.ClientService;
import chpoi.suitup.service.ManufacturerService;
import chpoi.suitup.service.SellerService;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;

/**
 * RegisterHandler base implementation.
 * 
 * @author Dong Zhiyuan
 */
public class RegisterHandlerImpl implements RegisterHandler{
	
	@Autowired
	private ClientService clientService;	
	@Autowired
	private SellerService sellerService;	
	@Autowired
	private ManufacturerService manufacturerService;
	
	public enum Type {
		Client, Seller, Manufacturer
	}

	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.handler.RegisterHandler#register(chpoi.suitup.socket.Messenger, net.sf.json.JSONObject)
	 */
	public void register(Messenger messenger, JSONObject requestPara){
		Type type = Type.valueOf(Type.class, requestPara.getString("type"));
		Object obj = null;
		try {
			switch(type){
			case Client:
				Client client = Client.JSONToClient(requestPara);
				obj = clientRegister(client);
				break;
			case Seller:
				String identification = requestPara.getString("identification");
				Seller seller = Seller.JSONToSeller(requestPara);
				obj = sellerRegister(seller, identification);
				break;
			case Manufacturer:
				Manufacturer manufacturer = Manufacturer.JSONToManufacturer(requestPara);
				obj = manufacturerRegister(manufacturer);
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
	 * Specific Register Action for Client
	 * 
	 * @param client to register
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Client clientRegister(Client client) throws SuitUpException{
		client = clientService.register(client);
		return client;
	}

	/**
	 * Specific Register Action for Seller
	 * 
	 * @param seller to register
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Seller sellerRegister(Seller seller, String identification) throws SuitUpException{
		Manufacturer manufacturer = manufacturerService.getByIdentification(identification);
		if(manufacturer == null){
			throw new SuitUpException("Identification is wrong!");
		} else {
			seller.setManufacturer_id(manufacturer.get_id());
		}
		seller = sellerService.register(seller);
		return seller;
	}

	/**
	 * Specific Register Action for Manufacturer
	 * 
	 * @param manufacturer to register
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Manufacturer manufacturerRegister(Manufacturer manufacturer) throws SuitUpException{
		manufacturer = manufacturerService.register(manufacturer);
		return manufacturer;
	}	
}
