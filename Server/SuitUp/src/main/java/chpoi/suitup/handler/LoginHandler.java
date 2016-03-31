package chpoi.suitup.handler;

import net.sf.json.JSONObject;

import chpoi.suitup.socket.Messenger;

/**
 * Login Handler specific interface.
 * 
 * @author Dong Zhiyuan
 */
public interface LoginHandler {

	/**
	 * This method uses given request parameter to do login Action.
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param requestPara the request parameters
	 */
	void login(Messenger messenger, JSONObject requestPara);
}
