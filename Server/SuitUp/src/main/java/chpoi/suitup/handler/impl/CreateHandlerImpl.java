package chpoi.suitup.handler.impl;

import java.io.IOException;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import chpoi.suitup.entity.Photo;
import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.handler.CreateHandler;
import chpoi.suitup.service.ClientService;
import chpoi.suitup.service.PhotoService;
import chpoi.suitup.service.SuitService;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;

/**
 * CreateHandler base implementation.
 * 
 * @author Dong Zhiyuan
 */
public class CreateHandlerImpl implements CreateHandler{
	
	@Autowired
	private SuitService suitService;	
	@Autowired
	private PhotoService photoService;	
	@Autowired
	private ClientService clientService;	
	
	public enum Type {
		Suit, Photo
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.handler.CreateHandler#create(chpoi.suitup.socket.Messenger, net.sf.json.JSONObject)
	 */
	public void create(Messenger messenger, JSONObject requestPara){
		Type type = Type.valueOf(Type.class, requestPara.getString("type"));
		Object obj = null;
		try {
			switch(type){
			case Suit:
				Suit suit = Suit.JSONToSuit(requestPara);
				obj = suitCreate(suit);
				break;
			case Photo:
				Photo photo = Photo.JSONToPhoto(requestPara);
				obj = photoCreate(messenger, photo);
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
	 * Specific Create Action for Suit
	 * 
	 * @param suit to create
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Suit suitCreate(Suit suit) throws SuitUpException{
		suit = suitService.create(suit);
		return suit;
	}

	/**
	 * Specific Create Action for Photo
	 * 
	 * @param photo to create
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private Photo photoCreate(Messenger messenger, Photo photo) throws SuitUpException{
		try {
			JSONObject jsonHead = messenger.readHead();
			int length = jsonHead.getInt("length");
			photo.setSize(length);
			photo.setFile(messenger.readBody(length));
			photoService.create(photo);
		} catch (IOException e) {
			e.printStackTrace();
		}
		photo.setFile(null);
		return photo;
	}
}
