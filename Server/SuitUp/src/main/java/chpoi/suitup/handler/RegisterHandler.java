package chpoi.suitup.handler;

import net.sf.json.JSONObject;

import chpoi.suitup.socket.Messenger;

/**
 * Register Handler specific interface.
 * 
 * @author Dong Zhiyuan
 */
public interface RegisterHandler {

	/**
	 * This method uses given request parameter to do register Action.
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param requestPara the request parameters
	 */
	void register(Messenger messenger, JSONObject requestPara);
}
