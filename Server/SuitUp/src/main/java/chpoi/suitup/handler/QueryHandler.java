package chpoi.suitup.handler;

import net.sf.json.JSONObject;

import chpoi.suitup.socket.Messenger;

/**
 * Query Handler specific interface.
 * 
 * @author Dong Zhiyuan
 */
public interface QueryHandler {

	/**
	 * This method uses given request parameter to do query Action.
	 * 
	 * @param messenger the messenger corresponding to a particular client
	 * @param requestPara the request parameters
	 */
	void query(Messenger messenger, JSONObject requestPara);
}
