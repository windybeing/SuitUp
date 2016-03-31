package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import chpoi.suitup.entity.Photo;
import chpoi.suitup.repository.mongo.PhotoMongoRepository;

public class PhotoTest {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		PhotoMongoRepository photoMongoRepository = applicationContext.getBean("photoMongoRepository", PhotoMongoRepository.class);
		
         try {
     	     Photo photo = new Photo();
    		
    		 String filename = "D:\\haha.png";  
    		 String filename1 = "D:\\haha1.png"; 
             
             File fi = new File(filename);  

             System.out.println("File Name：" + fi.getName() + ";\tFile Size()：" + (int) fi.length() + "bytes");    

             FileInputStream fis = new FileInputStream(filename);
			 
			 byte[] file = new byte[8*1024*1024];
			 
			 photo.setSize(fis.available());
			 System.out.println(photo.getSize());
			 fis.read(file, 0, fis.available());
			 String file1 = new String(file, 0, photo.getSize(), "ISO-8859-1");
			 System.out.println(file1.toCharArray().length);
			 photo.setFile(file1);
			 photo = photoMongoRepository.save(photo);
			 
			 Photo photo1 = photoMongoRepository.findOne(photo.get_id());
			 FileOutputStream fos = new FileOutputStream(filename1);
			 fos.write(photo1.getFile().getBytes("ISO-8859-1"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    
	}

}
