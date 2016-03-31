package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.service.SuitService;

public class HibernateTest {

	public HibernateTest() {}

	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		SuitService suitService = applicationContext.getBean("suitService", SuitService.class);	
		SessionFactory sessionFactory = applicationContext.getBean("sessionFactory", SessionFactory.class);
		
		List<String> information = new ArrayList<String>();
		information.add("0");
		information.add("6");
		information.add("2");
		information.add("5");
		
		for(Integer i = 0; i < 20; i++){
			Suit suit = new Suit();
			suit.setManufacturer_id(new ObjectId(new Date()).toString());
			suit.setSuitname(i.toString());
			suit.setPrice(0.0);
			suit.setInformation(information);
			suitCreate(suitService, suit);
		}

	}

	private static void suitCreate(SuitService suitService, Suit suit) {
			try {
				suit = suitService.create(suit);
			} catch (SuitUpException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
}
