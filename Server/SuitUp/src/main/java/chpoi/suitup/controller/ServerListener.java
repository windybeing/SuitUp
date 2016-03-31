package chpoi.suitup.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import chpoi.suitup.socket.MessageSocket;

/**
 * ServerListener is the main Thread of SuitUp Server. Every ServerListenr is corresponding to a sepecific SuitUp client.
 * The way of communication is the use of Socket.
 * 
 * 
 * @author Dong Zhiyuan
 */
public class ServerListener extends Thread {

	private static ServerSocket serverSocket = MessageSocket.generalServerSocket();
    
	/**
	 * Run function of Thread.
	 * It uses a while loop to accept the new connect of client. If accepted, it generate a  thread to communicate with the client.
	 * 
	 */
	@Override
	public void run() {
		while(true) {
			try {
				Socket socket = serverSocket.accept();
				ServerController serverController = new ServerController(socket);
				serverController.start();
			} catch (IOException e) { }
		}
	}
 
}
