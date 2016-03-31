package chpoi.suitup.handler;

import net.sf.json.JSONObject;

import chpoi.suitup.socket.Messenger;

/**
 * Delete Handler specific interface.
 * 
 * @author Dong Zhiyuan
 */
public interface DeleteHandler {

	/**
	 * This method uses given request parameter to do delete Action.
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param requestPara the request parameters
	 */
	void delete(Messenger messenger, JSONObject requestPara);
}
