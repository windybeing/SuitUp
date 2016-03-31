package chpoi.suitup.handler;


import net.sf.json.JSONObject;

import chpoi.suitup.socket.Messenger;

/**
 * Create Handler specific interface.
 * 
 * @author Dong Zhiyuan
 */
public interface CreateHandler {

	/**
	 * This method uses given request parameter to do create Action.
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param requestPara the request parameters
	 */
	void create(Messenger messenger, JSONObject requestPara);
}
