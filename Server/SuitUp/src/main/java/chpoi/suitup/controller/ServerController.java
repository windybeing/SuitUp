package chpoi.suitup.controller;

import java.io.IOException;
import java.net.Socket;

import chpoi.suitup.socket.Messenger;


/**
 * ServerController is the son Thread of SuitUp Server. Every ServerController takes charge of communicating with SuitUp client.
 * 
 * 
 * @author Dong Zhiyuan
 */
public class ServerController extends Thread {

	private Messenger messenger;
	
	public ServerController(Socket socket){
		this.messenger = new Messenger(socket);
	}
	
	/**
	 * Run function of Thread.
	 * It uses a while loop to accept the new request of client. If accepted, it calls RequestResolver to resolve and response the request.
	 * 
	 */
	@Override
	public void run() {
		try {
			while(true){
				String cmd = messenger.acceptRequest();
				if(cmd == null) break;
				RequestResolver.getResolver().resolve(messenger, cmd);
			}
		} catch (IOException e) {}
	}
}
