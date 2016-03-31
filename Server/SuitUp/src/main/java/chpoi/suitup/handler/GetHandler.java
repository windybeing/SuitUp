package chpoi.suitup.handler;

import net.sf.json.JSONObject;

import chpoi.suitup.socket.Messenger;

/**
 * Get Handler specific interface.
 * 
 * @author Dong Zhiyuan
 */
public interface GetHandler {

	/**
	 * This method uses given request parameter to do get Action.
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param requestPara the request parameters
	 */
	void get(Messenger messenger, JSONObject requestPara);
}
