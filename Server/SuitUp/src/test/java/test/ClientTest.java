package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chpoi.suitup.entity.Client;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.service.ClientService;

public class ClientTest {

	public static void main(String[] args){
		Client client = new Client();
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		ClientService clientService = applicationContext.getBean("clientService", ClientService.class);
		
		List<String> stringlists = new ArrayList<String>();
		stringlists.add("string1");
		stringlists.add("String2");

		List<String> idlists = new ArrayList<String>();
		idlists.add(new ObjectId(new Date()).toString());
		idlists.add(new ObjectId(new Date()).toString());
		ObjectId id = new ObjectId(new Date());
		System.out.println(id.toString());
		System.out.println(id.toHexString());
		client.setAddress("changshou");
		client.setAge(20);
		client.setAvater_id("stringlists");
		client.setPhotos(stringlists);
		client.setClientname("dzy");
		client.setEmail("827850095@qq.com");
		client.setParameter_id("aba");
		client.setPassword("shen");
		client.setPhonenumber("31412321");
		client.setUsername("windybeing");
		try {
			client = clientService.register(client);

			client.setEmail("8278595@qq.com");
			client.setUsername("windy");
			clientService.register(client);
		} catch (SuitUpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Client client1 = clientService.getById(client.get_id());
		//System.out.println(client1.toString());
		
		//clientService.delete(client.get_id());
	}

}
