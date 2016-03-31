package chpoi.suitup.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.codehaus.jackson.map.ObjectMapper;

/**
 * JSONUtil is a utility class for JSON.
 * 
 * @author Dong Zhiyuan
 */
public class JSONUtil {
	
    /**
     * This method changes JSONObject to entity
     * 
     * @param json the JSONObject to change
     * @param obj the class of entity
     * @return the expected entity
     */
    public static<T> T JSONToObj(String json_string,Class<T> obj) {
        T t = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper(); 	
            objectMapper.enableDefaultTyping();
            t = objectMapper.readValue(json_string, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return t;
    }
    
    /**
     * This method mixs two string to JSONObject
     * 
     * @param str1
     * @param str2
     * @return the JSONObject generated
     */
    public static JSONObject stringsToJSONObject(String str1, String str2){
        Map<String, String> map = new HashMap<String, String>();  
        map.put(str1, str2);
        return JSONObject.fromObject(map);
    }

    /**
     * This method generate fail response with JSON form
     * 
     * @param message the fail message
     * @return the string generated with JSON form
     */
    public static String generateFailJSONResponse(String message){
    	Map<String, String> map = new HashMap<String, String>();  
        map.put( "ret",  "fail");  
        JSONArray jsonArray = JSONArray.fromObject(map);
        Map<String, String> messageMap = new HashMap<String, String>();
        messageMap.put("message", message);
    	jsonArray.add(1, JSONObject.fromObject(messageMap));
        return jsonArray.toString();
    }
    
    /**
     * This method generate success response with JSON form
     * 
     * @param parameters the parameters which is JSONObject or JSONArray
     * @return the JSONObject generated
     */
    public static String generateSuccessJSONResponse(Object parameters){
    	Map<String, String> map = new HashMap<String, String>();  
        map.put( "ret",  "success");
        JSONArray jsonArray = JSONArray.fromObject(map); 
    	jsonArray.add(1,parameters);
        return jsonArray.toString();
    }
}