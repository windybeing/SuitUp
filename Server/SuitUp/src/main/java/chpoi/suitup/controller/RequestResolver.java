package chpoi.suitup.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chpoi.suitup.handler.CreateHandler;
import chpoi.suitup.handler.DeleteHandler;
import chpoi.suitup.handler.GetHandler;
import chpoi.suitup.handler.LoginHandler;
import chpoi.suitup.handler.ModifyHandler;
import chpoi.suitup.handler.PurchaseHandler;
import chpoi.suitup.handler.QueryHandler;
import chpoi.suitup.handler.RegisterHandler;
import chpoi.suitup.socket.Messenger;
import chpoi.suitup.util.JSONUtil;

/**
 * RequestResolver can resolve the specific request and call paricular handler to handle the request.
 * 
 * @author Dong Zhiyuan
 */
public class RequestResolver {
	
	public static RequestResolver requestResolver = null;
	@Autowired
	public LoginHandler loginHandler;
	@Autowired
	public RegisterHandler registerHandler;
	@Autowired
	public QueryHandler queryHandler;
	@Autowired
	public CreateHandler createHandler;
	@Autowired
	public GetHandler getHandler;
	@Autowired
	public ModifyHandler modifyHandler;
	@Autowired
	public DeleteHandler deleteHandler;
	@Autowired
	public PurchaseHandler purchaseHandler;

	public enum Command {
		Login, Register, Query, Create, Get, Modify, Delete, Purchase
	}
	
	public RequestResolver() {}
	
	/**
	 * The static method returns a static singleton instance of {@link RequestResolver} with applicationContext.xml in Spring frame.
	 * 
	 * @return the static singleton instance of {@link RequestResolver}
	 */
	public static RequestResolver getResolver(){
		if(requestResolver == null){
			requestResolver = new ClassPathXmlApplicationContext("applicationContext.xml").getBean(RequestResolver.class);
		}
		return requestResolver;
	}
	
	/**
	 * The static method returns a static singleton instance of {@link RequestResolver} with applicationContext.xml in Spring frame.
	 * 
	 * @param messenger the {@link Messenger} corresponding to particular client
	 * @param request the request to be resolved.
	 */
	public void resolve(Messenger messenger, String request) throws JSONException{
		try {
			JSONArray requestArray = JSONArray.fromObject(request);
			System.out.println(requestArray.toString());
			String function = requestArray.getJSONObject(0).getString("function");
			JSONObject requestPara = requestArray.getJSONObject(1); 
			Command command = Command.valueOf(Command.class, function);
			switch(command){
			case Login:
				loginHandler.login(messenger, requestPara);
				break;
			case Register:
				registerHandler.register(messenger, requestPara);
				break;
			case Query:
				queryHandler.query(messenger, requestPara);
				break;
			case Create:
				createHandler.create(messenger, requestPara);
				break;
			case Get:
				getHandler.get(messenger, requestPara);
				break;
			case Modify:
				modifyHandler.modify(messenger, requestPara);
				break;
			case Delete:
				deleteHandler.delete(messenger, requestPara);
				break;
			case Purchase:
				purchaseHandler.purchase(messenger, requestPara);
				break;
			}
		} catch (JSONException e) {
			messenger.mainResponse(JSONUtil.generateFailJSONResponse("Error: JSON can not be resolved"));
		}
	}
}
