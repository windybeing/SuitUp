package chpoi.suitup.handler;

import net.sf.json.JSONObject;

import chpoi.suitup.socket.Messenger;

/**
 * Purchase Handler specific interface.
 * 
 * @author Dong Zhiyuan
 */
public interface PurchaseHandler {

	/**
	 * This method uses given request parameter to do purchase Action.
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param requestPara the request parameters
	 */
	void purchase(Messenger messenger, JSONObject requestPara);
}
