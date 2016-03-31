package chpoi.suitup.controller;

/**
 * This class implements the SuitUp Server
 * 
 * @author Dong Zhiyuan
 * @version 1.0
 */
public class Server{
	
	/**
	 * The main function of SuitUp Server
	 * 
	 * @param args of no use
	 */
	public static void main(String[] args){
		new ServerListener().start();		
	}
}
