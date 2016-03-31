package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chpoi.suitup.entity.Suit;
import chpoi.suitup.service.SuitService;

public class SuitTest {

	public static void main(String[] args){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		SuitService suitService = applicationContext.getBean("suitService", SuitService.class);
		
		List<String> information = new ArrayList<String>();
		information.add("abc");
		information.add("def");
		
		for(Integer i = 0; i < 20; i++){
			Suit suit = new Suit();
			suit.setSuitname(i.toString());
			suit.setManufacturer_id(new ObjectId(new Date()).toString());
			
			
			
		}
		
		
		//[{"number":2,"last":true,"numberOfElements":4,"size":8,"totalPages":3,"sort":null,"content":[{"manufacturer"
	
	}

}
