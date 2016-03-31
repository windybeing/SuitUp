package chpoi.suitup.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chpoi.suitup.entity.Client;
import chpoi.suitup.entity.Manufacturer;
import chpoi.suitup.entity.Photo;
import chpoi.suitup.entity.Seller;
import chpoi.suitup.entity.Suit;
import chpoi.suitup.exception.SuitUpException;
import chpoi.suitup.service.ClientService;
import chpoi.suitup.service.ManufacturerService;
import chpoi.suitup.service.OrderService;
import chpoi.suitup.service.PhotoService;
import chpoi.suitup.service.SellerService;
import chpoi.suitup.service.SuitService;

public class TestData {

	private static ClientService clientService;
	private static ManufacturerService manufacturerService;
	private static OrderService orderService;
	private static PhotoService photoService;
	private static SellerService sellerService;
	private static SuitService suitService;
	

	public static void main(String[] args){
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		clientService = applicationContext.getBean("clientService", ClientService.class);
		manufacturerService = applicationContext.getBean("manufacturerService", ManufacturerService.class);
		orderService = applicationContext.getBean("orderService", OrderService.class);
		photoService = applicationContext.getBean("photoService", PhotoService.class);
		sellerService = applicationContext.getBean("sellerService", SellerService.class);
		suitService = applicationContext.getBean("suitService", SuitService.class);

		List<String> client_id_List = null;
		List<String> manufacturer_id_List = null;
		List<String> seller_id_List = null;
		List<String> suit_id_List = null;
		
		try {
			client_id_List = generateClient();
			manufacturer_id_List = generateManufacturer();
			seller_id_List = generateSeller(manufacturer_id_List);
			generateSuits(seller_id_List);
		} catch (SuitUpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static List<String> generateClient() throws SuitUpException{
		List<String> client_id_List = new ArrayList<String>();
		
		for(Integer i = 1; i < 5; i++){
			Client client = new Client();

			client.setUsername("client" + i.toString());
			client.setPassword("client" + i.toString());
			client.setEmail("82785009" + i.toString() + "@qq.com");
			client.setPhonenumber("1801707191" + i.toString());
			client.setClientname("dzy" + i.toString());
			client.setAge(20);
			client.setAddress("changshou Road " + i.toString());
			client.setAvater_id(createPhoto("picture\\client_avater.jpg"));

			List<String> photos_id_List = new ArrayList<String>();
			photos_id_List.add(createPhoto("picture\\client_photo1.jpg"));
			photos_id_List.add(createPhoto("picture\\client_photo2.jpg"));
			
			client.setPhotos(photos_id_List);

			client = clientService.register(client);
			
			client_id_List.add(client.get_id());
		}

		return client_id_List;
	}
	
	private static List<String> generateManufacturer() throws SuitUpException{
		List<String> manufacturer_id_List = new ArrayList<String>();

		Manufacturer manufacturer = new Manufacturer();

		manufacturer.setManufacturername("报喜鸟");
		manufacturer.setIdentification("jhakrhalhfdoauygvlkarhap");
	
		List<String> string_List = new ArrayList<String>();
		string_List.add("string1");
		string_List.add("string2");
		
		manufacturer.setAddress(string_List);
		manufacturer.setInformation(string_List);
		
		manufacturer = manufacturerService.register(manufacturer);
		manufacturer_id_List.add(manufacturer.get_id());
		
		manufacturer = new Manufacturer();

		manufacturer.setManufacturername("英伦");
		manufacturer.setIdentification("lksdftrlkalkejhrflarnlkj");
	
		string_List = new ArrayList<String>();
		string_List.add("string1");
		string_List.add("string2");
		
		manufacturer.setAddress(string_List);
		manufacturer.setInformation(string_List);
		
		manufacturer = manufacturerService.register(manufacturer);
		manufacturer_id_List.add(manufacturer.get_id());
		
		
		return manufacturer_id_List;
	}
	
	private static List<String> generateSeller(List<String> manufacturer_id_List) throws SuitUpException{
		List<String> seller_id_List = new ArrayList<String>();
		
		for(Integer i = 1; i < 3; i++){
			Seller seller = new Seller();
			seller.setUsername("seller" + i.toString());
			seller.setPassword("seller" + i.toString());
			seller.setEmail("652" + i.toString() + "@sjtu.edu.cn");
			seller.setPhonenumber("1390199080" + i.toString());
			seller.setManufacturer_id(manufacturer_id_List.get(0));
			seller = sellerService.register(seller);
			seller_id_List.add(seller.get_id());
		}
		
		for(Integer i = 3; i < 5; i++){
			Seller seller = new Seller();
			seller.setUsername("seller" + i.toString());
			seller.setPassword("seller" + i.toString());
			seller.setEmail("652" + i.toString() + "@sjtu.edu.cn");
			seller.setPhonenumber("1390199080" + i.toString());
			seller.setManufacturer_id(manufacturer_id_List.get(1));
			seller = sellerService.register(seller);
			seller_id_List.add(seller.get_id());
		}
		return seller_id_List;
	}
	
	private static List<String> generateSuits(List<String> seller_id_List) throws SuitUpException{
		List<String> suit_id_List = new ArrayList<String>();
		
		for(Integer i = 1; i < 26; i++){			
			Suit suit = new Suit();
			suit.setSuitname("suit" + i.toString());
			suit.setSeller_id(seller_id_List.get((int)(i % 4)));
			Seller seller = sellerService.getById(seller_id_List.get((int)(i % 4)));
			suit.setManufacturer_id(seller.getManufacturer_id());
			suit.setPrice(new Double(i));
			suit.setPhoto_id(createPhoto("picture\\suit_photo"+i.toString()+".jpg"));
			
			List<String> information = new ArrayList<String>();
			information.add("good");
			information.add("not good");

			suit.setInformation(information);
			
			suit = suitService.create(suit);
			suit_id_List.add(suit.get_id());
		}
		
		return suit_id_List;
	}
	
	private static String createPhoto(String path) throws SuitUpException{
		try {
			Photo photo = new Photo();
			FileInputStream fis = new FileInputStream(path);
			byte[] file = new byte[8*1024*1024];
			photo.setSize(fis.available());
			fis.read(file, 0, fis.available());
			String file1 = new String(file, 0, photo.getSize(), "ISO-8859-1");
			photo.setFile(file1);
			photo = photoService.create(photo);
			fis.close();
			

			FileOutputStream fos = new FileOutputStream(path);
			fos.write(photo.getFile().getBytes("ISO-8859-1"));
			fos.close();
			return photo.get_id();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
