package chpoi.suitup.handler;

import net.sf.json.JSONObject;

import chpoi.suitup.socket.Messenger;

/**
 * Modify Handler specific interface.
 * 
 * @author Dong Zhiyuan
 */
public interface ModifyHandler {

	/**
	 * This method uses given request parameter to do modify Action.
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param requestPara the request parameters
	 */
	void modify(Messenger messenger, JSONObject requestPara);
}
