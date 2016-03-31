package test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.bson.types.ObjectId;

public class SpringTest {
	public enum Command {
		Login, Register
	}
	public static void main(String[] args){
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
//		Foo foo = applicationContext.getBean("foo", Foo.class);
//		System.out.println(foo.toString());
		//Command command = Command.valueOf(Command.class, "Login");
		
		Map map = new HashMap();  
        map.put( "function", new ObjectId(new Date()));  
        ObjectId ob = new ObjectId(new Date());
        JSONObject json = JSONObject.fromObject("1");
        System.out.println(json.toString());
	}

}
