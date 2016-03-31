package hk.suitup.SSLAccess;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

import hk.suitup.R;

/**
 * Created by sony on 2015/7/24.
 */
public class SSLClient {

    private String serverIP="59.78.22.121";
    private int portNum=6520;
    private SSLContext sslContext;
    private KeyStore ts;
    private TrustManagerFactory trustManagerFactory;
    private SocketFactory factory;
    private static SSLSocket socket;
    private static PrintWriter writer;
    private static BufferedReader reader;
    private static int message_length;

    public SSLClient(Context context) {
        try {
            ts = KeyStore.getInstance("BKS");
            ts.load(context.getResources().openRawResource(R.raw.client_ks), "client".toCharArray());
            trustManagerFactory = TrustManagerFactory.getInstance("X509");
            trustManagerFactory.init(ts);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();
            sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, tm, null);
            factory = sslContext.getSocketFactory();
            socket = (SSLSocket) factory.createSocket(serverIP, portNum);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }
        catch(Exception e){
            return;
        }
    }

    public static void sendMessage(Object obj) {
        try {
            if(socket.isConnected()) {
                writer.print(obj);
                writer.flush();
            }
        }
        catch(Exception e){
            return;
        }
    }

    //check the response is success or not
    public static Boolean checkReturn(String response){
        JsonReader jsonReader = new JsonReader(new StringReader(response));
        try {
            jsonReader.beginArray();
            String valid;
            while (jsonReader.hasNext()){
                jsonReader.beginObject();
                while(jsonReader.hasNext()){
                    String tagName = jsonReader.nextName();
                    if(tagName.equals("ret")){
                        valid=jsonReader.nextString();
                        if(valid.equals("success")){
                            System.out.print(valid);
                            return true;
                        }
                    }
                }
                jsonReader.endObject();
            }
            jsonReader.endArray();

        }
        catch (Exception e){
            return false;
        }
        return false;
    }

    public static String findMessageObject(String tmp,String targetStr){

        JsonReader jsonReader = new JsonReader(new StringReader(tmp));

        //find the object by target string in tmp
        try{
            while (jsonReader.hasNext()){
                jsonReader.beginObject();
                while(jsonReader.hasNext()){
                    String tagName = jsonReader.nextName();
                    if(tagName.equals(targetStr)){
                        return jsonReader.nextString();
                    }
                }
                jsonReader.endObject();
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }

    public static int getMessageLength(){
        try {
            String temp = reader.readLine();
            //JsonReader jsonReader = new JsonReader(new StringReader(temp));

           // jsonReader.beginArray();
            String length;
            message_length=-1;
            length=findMessageObject(temp, "length");
            if(length!=null){
                message_length = Integer.parseInt(length);
                return message_length;
            }
            else{
                return message_length;
            }
        }
        catch(Exception e){
            return -10;
        }
    }

    public static BufferedReader getReader() {
        return reader;
    }

    public static String getResponse(){
        try {
            char[] response = new char[message_length];
            int once_size = 8*1024;
            int times = message_length/once_size;
            int left = message_length - once_size*times;
            int total=0;

            if(times==0) reader.read(response,once_size*times,left);
            else {
                int offset=0;
                while (offset < message_length) {
                    offset += reader.read(response, offset, (message_length-offset<=once_size)?(message_length-offset):once_size);
                }
            }

            //reader.read(response, 0, message_length);
            String response_str = new String(response);
            return response_str;
        }
        catch(Exception e){
            return null;
        }
    }

    public void closeSocket(){
       try {
           socket.close();
       }
       catch(Exception e ){
            return;
        }
    }
}
