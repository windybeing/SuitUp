package chpoi.suitup.handler.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import chpoi.suitup.entity.Manufacturer;
import chpoi.suitup.entity.Photo;
import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.handler.GetHandler;
import chpoi.suitup.service.ManufacturerService;
import chpoi.suitup.service.PhotoService;
import chpoi.suitup.service.SuitService;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;


/**
 * GetHandler base implementation.
 * 
 * @author Dong Zhiyuan
 */
public class GetHandlerImpl implements GetHandler{

	@Autowired
	private SuitService suitService;
	@Autowired
	private ManufacturerService manufacturerService;
	@Autowired
	private PhotoService photoService;

	public enum Type {
		Suit,  Manufacturer ,Photo
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.handler.GetHandler#get(chpoi.suitup.socket.Messenger, net.sf.json.JSONObject)
	 */
	public void get(Messenger messenger, JSONObject requestPara){
		Type type = Type.valueOf(Type.class, requestPara.getString("type"));
		Object obj = null;
		try {
			switch(type){
			case Suit:
				List<String> suits_id_List = (List<String>) requestPara.get("suits_id");
				obj = suitGet(suits_id_List);
				break;
			case Manufacturer:
				List<String> manufacturers_id_List = (List<String>) requestPara.get("manufacturers_id");
				obj = manufacturerGet(manufacturers_id_List);
				break;
			case Photo:
				List<String> photos_id_List = (List<String>) requestPara.get("photos_id");
				photoGet(messenger, photos_id_List);
				return;
			default:
				messenger.mainResponse(JSONUtil.generateFailJSONResponse("Error: Type Error!"));
				return;
			}
			JSONArray responsePara = JSONArray.fromObject(obj);
			messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(responsePara));
		} catch (SuitUpException e) {
			messenger.mainResponse(JSONUtil.generateFailJSONResponse(e.getMessage()));
		}
	}
	
	/**
	 * Specific Get Action for Suit
	 * 
	 * @param suits_id_List id list of suits to get
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private List<Suit> suitGet(List<String> suits_id_List) throws SuitUpException{
		List<Suit> suit_List = suitService.getByIdList(suits_id_List);
		return suit_List;
	}
	
	/**
	 * Specific Get Action for Photo
	 * 
	 * @param suits_id_List id list of photos to get
	 * @return
	 * @throws SuitUpException in case of SuitUp error
	 */
	private List<Manufacturer> manufacturerGet(List<String> manufacturers_id_List) throws SuitUpException{
		List<Manufacturer> manufacturer_List = manufacturerService.getByIdList(manufacturers_id_List);
		for(Manufacturer manufacturer: manufacturer_List){
			manufacturer.setIdentification(null);
		}
		return manufacturer_List;
	}
	
	/**
	 * Specific Get Action for Photo
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param suits_id_List id list of photos to get
	 * @throws SuitUpException in case of SuitUp error
	 */
	private void photoGet(Messenger messenger, List<String> photos_id_List) throws SuitUpException{
		List<Photo> photo_List = photoService.getByIdList(photos_id_List);
		JSONArray responsePara = JSONArray.fromObject(null);
		messenger.mainResponse(JSONUtil.generateSuccessJSONResponse(responsePara));
		for(Photo photo : photo_List){
			messenger.fileResponse(photo.get_id(), photo.getFile());
		}
	}
	
}
