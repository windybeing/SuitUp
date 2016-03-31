package chpoi.suitup.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * Messenger can recieve request and send response to the client
 * 
 * @author Dong Zhiyuan
 */
public class Messenger {

	private PrintWriter writer;
	private BufferedReader reader;
	
	/**
	 * The method creates a new {@link Messenger} with {@link PrintWriter} and {@link BufferedReader} created with socket.
	 * 
	 * @param socket the socket responding to paricular client socket
	 */
	public Messenger(Socket socket){
		try {
			this.writer = new PrintWriter(socket.getOutputStream());
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * The method readline from {@link BufferedReader} without stop until it accept the request.
	 * If the request is accorded with specific form, it retuens. Otherwise, it ignores the request and continues reading. 
	 * 
	 * @reutrn the request accepted
	 */
	public String acceptRequest() throws IOException{
		String line = null;
		while((line = reader.readLine()) != null){
			if(line.endsWith("<EOF>")){
            	JSONObject jsonob = JSONObject.fromObject(line.substring(0,line.length()-5));
            	int length = jsonob.getInt("length");
            	char[] cbuf= new char[length];
            	reader.read(cbuf, 0, length);
            	String request = new String(cbuf);
            	return request;
			}
		}
		return null;
	}
	
	/**
	 * The method readline from {@link BufferedReader} without stop until it accept the request.
	 * If the request is a package head accorded with specific form , it retuens. Otherwise, it ignores the request and continues reading. 
	 * 
	 * @reutrn the JSONObject of paricular package head accepted
	 */	
	public JSONObject readHead() throws IOException{
		String line = null;
		while((line = reader.readLine()) != null){
			if(line.endsWith("<EOF>")){
            	JSONObject jsonHead = JSONObject.fromObject(line.substring(0,line.length()-5));
            	return jsonHead;
			}
		}
		return null;
	}
	
	/**
	 * The method read fixed number of bytes from {@link BufferedReader}.
	 * 
	 * @paprm length the number of bytes to read
	 * @reutrn the context of bytes read
	 */	
	public String readBody(int length) throws IOException{
		if(length < 0)return null;
        char[] cbuf= new char[length];
        int offset = 0;
        int charRead = 0;
        while(offset < length){
            charRead = reader.read(cbuf, offset, (length - offset <= 8*1024) ? (length - offset) : 8*1024);
        	offset += charRead;
       }
        String body = new String(cbuf, 0, length);
        return body;
	}
	
	/**
	 * The method send response by {@link PrinterWriter} to the client.
	 * 
	 * @paprm out the response to send
	 */	
	public void mainResponse(String out) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("length", new Integer(out.length()));
		writer.println(JSONObject.fromObject(map).toString() + "<EOF>");
		writer.print(out);
		writer.flush();
	}

	/**
	 * The method send file by {@link PrinterWriter} to the client.
	 * 
	 * @param _id the id of the file to be sent
	 * @paprm file the file to send
	 */	
	public void fileResponse(String _id, String file){
		Map map = new HashMap();
		map.put("_id", _id);
		map.put("length", new Integer(file.length()));
		writer.println(JSONObject.fromObject(map).toString() + "<EOF>");
		writer.print(file);
		writer.flush();
	}
}
