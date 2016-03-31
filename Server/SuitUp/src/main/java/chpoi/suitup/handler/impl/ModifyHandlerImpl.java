package chpoi.suitup.handler.impl;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import chpoi.suitup.entity.Client;
import chpoi.suitup.entity.Manufacturer;
import chpoi.suitup.entity.Photo;
import chpoi.suitup.entity.Seller;
import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.handler.ModifyHandler;
import chpoi.suitup.service.ClientService;
import chpoi.suitup.service.ManufacturerService;
import chpoi.suitup.service.PhotoService;
import chpoi.suitup.service.SellerService;
import chpoi.suitup.service.SuitService;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;


/**
 * ModifyHandler base implementation.
 * 
 * @author Dong Zhiyuan
 */
public class ModifyHandlerImpl implements ModifyHandler{
	
	@Autowired
	private ClientService clientService;	
	@Autowired
	private SellerService sellerService;	
	@Autowired
	private ManufacturerService manufacturerService;
	@Autowired
	private SuitService suitService;
	@Autowired
	private PhotoService photoService;
	
	public enum Type {
		Client, Seller, Manufacturer, Suit, Photo
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.handler.ModifyHandler#modify(chpoi.suitup.socket.Messenger, net.sf.json.JSONObject)
	 */
	public void modify(Messenger messenger, JSONObject requestPara){
		Type type = Type.valueOf(Type.class, requestPara.getString("type"));
		Object obj = null;
		try {
			switch(type){
			case Client:
				Client client = Client.JSONToClient(requestPara);
				obj = clientModify(client);
				break;
			case Seller:
				Seller seller = Seller.JSONToSeller(requestPara);
				obj = sellerModify(seller);
				break;
			case Manufacturer:
				Manufacturer manufacturer = Manufacturer.JSONToManufacturer(requestPara);
				obj = manufacturerModify(manufacturer);
				break;
			case Suit:
				Suit suit = Suit.JSONToSuit(requestPara);
				obj = suitModify(suit);
				break;
			case Photo:
				Photo photo = Photo.JSONToPhoto(requestPara);
				obj = photoModify(messenger, photo);
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
	 * Specific Modify Action for Client
	 * 
	 * @param client to update by
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Client clientModify(Client client) throws SuitUpException{
		if(client.getUsername() != null){ 
			throw new SuitUpException("You can not modify Username!");
		}
		if(client.getEmail() != null){ 
			throw new SuitUpException("You can not modify E-mail Address!");
		}
		if(client.getPhonenumber() != null){ 
			throw new SuitUpException("You can not modify Phonenumber!");
		}
		Client _client = clientService.getById(client.get_id());
		_client.update(client);
		client = clientService.update(_client);
		return client;
	}

	/**
	 * Specific Modify Action for Seller
	 * 
	 * @param seller to update by
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Seller sellerModify(Seller seller) throws SuitUpException{
		if(seller.getUsername() != null){ 
			throw new SuitUpException("You can not modify Username!");
		}
		if(seller.getEmail() != null){ 
			throw new SuitUpException("You can not modify E-mail Address!");
		}
		if(seller.getPhonenumber() != null){ 
			throw new SuitUpException("You can not modify Phonenumber!");
		}
		if(seller.getManufacturer_id() != null){ 
			throw new SuitUpException("You can not modify Manufacturer_id!");
		}
		Seller _seller = sellerService.getById(seller.get_id());
		_seller.update(seller);
		seller = sellerService.update(_seller);
		return seller;
	}

	/**
	 * Specific Modify Action for Manufacturer
	 * 
	 * @param manufacturer to update by
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Manufacturer manufacturerModify(Manufacturer manufacturer) throws SuitUpException{
		Manufacturer _manufacturer = manufacturerService.getById(manufacturer.get_id());
		_manufacturer.update(manufacturer);
		manufacturer = manufacturerService.update(_manufacturer);
		return manufacturer;
	}	

	/**
	 * Specific Modify Action for Suit
	 * 
	 * @param manufacturer to update by
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Suit suitModify(Suit suit) throws SuitUpException{
		Suit _suit = suitService.getById(suit.get_id());
		_suit.update(suit);
		suit = suitService.update(_suit);
		return suit;
	}	

	/**
	 * Specific Modify Action for Photo
	 * 
	 * @param manufacturer to update by
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Photo photoModify(Messenger messenger, Photo photo) throws SuitUpException{
		try {
			JSONObject jsonHead = messenger.readHead();
			int length = jsonHead.getInt("length");
			photo.setSize(length);
			photo.setFile(messenger.readBody(length));
			photoService.update(photo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		photo.setFile(null);
		return photo;
	}	
}
