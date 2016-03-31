package test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import chpoi.suitup.entity.Suit;

public class JSONTest {

	public static void main(String[] args){
//        String cmd = null;
//
//        Map map = new HashMap();  
//        map.put( "name", "json" );  
//        map.put( "bool", Boolean.TRUE );  
//        map.put( "int", new Integer(1) );  
//        map.put( "arr", new String[]{"a","b"} );  
//        map.put( "func", "function(i){ return this.arr[i]; }" );  
//        
//        JSONArray jsonObject = JSONArray.fromObject(map);  
//        cmd = jsonObject.toString();
//        JSONArray jsonArray = JSONArray.fromObject( cmd );  
//        System.out.println(jsonArray);
		
		Suit suit = new Suit();
		suit.set_id("afddafasf");
		suit.setManufacturer_id("asdaqweqe");
		suit.setSuitname("name");
		List<Suit> suits = new ArrayList<Suit>();
		suits.add(suit);
		suits.add(suit);
		JSONArray jsono = JSONArray.fromObject(suits);
		System.out.println(jsono.toString());
		Map map = new HashMap();
		map.put("ret", "success");
		JSONArray jsonlist = JSONArray.fromObject(map);
		jsonlist.add(suits);
		jsonlist.add(suits);
		System.out.println(jsonlist.toString());
		System.out.println(jsonlist.getJSONArray(1).toString());
	}
}
