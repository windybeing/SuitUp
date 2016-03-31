package chpoi.suitup.handler.impl;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.handler.DeleteHandler;
import chpoi.suitup.service.SuitService;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;

/**
 * DeleteHandler base implementation.
 * 
 * @author Dong Zhiyuan
 */
public class DeleteHandlerImpl implements DeleteHandler{
	
	@Autowired
	private SuitService suitService;	
	
	public enum Type {
		Suit,
	}
	
	/*
	 * (non-Javadoc)
	 * @see chpoi.suitup.handler.DeleteHandler#delete(chpoi.suitup.socket.Messenger, net.sf.json.JSONObject)
	 */
	public void delete(Messenger messenger, JSONObject requestPara){
		Type type = Type.valueOf(Type.class, requestPara.getString("type"));
		try {
			switch(type){
			case Suit:
				String suit_id = requestPara.getString("_id");
				suitDelete(suit_id);
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
	 * Specific Delete Action for Suit
	 * 
	 * @param _id the id of suit to delete
	 * @throws SuitUpException in case of SuitUp error
	 */
	private void suitDelete(String _id) throws SuitUpException{
		Suit suit = suitService.getById(_id);
		if(suit.getUsed() == 1){
			throw new SuitUpException("This suit has been paid, it can't be deleted.");
		}
		suitService.delete(_id);
	}
}
