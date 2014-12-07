package imagecloud.image.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import imagecloud.image.interfaces.IImageAdmin;
import imagecloud.image.interfaces.ImageAdmin;

public class Main {
	public static void main(String[] args) {

		File f = new File("./test.jpg");
		FileInputStream fis = null;

		FileOutputStream fos = null;
		try {
			//Part 1: Put code to get data from REST
//			fis = new FileInputStream("./test.jpg");
			IImageAdmin ia = new ImageAdmin("./test.jpg");
//			byte[] data = new byte[(int) f.length()];
//			fis.read(data);
			
			//Part 2: interaction with hbase
			
			//create new image in database. send byte[] data into hbase, 
			//in our case, no need to create new table any more
			//ia.add(data);
			
			//fetch an image into ImageAdmin object
			ia.fetch();
			//do any operation
			ia.resize(500, 300);
			//fetch the resized image
			ia.setImageId("test500_300.jpg");
			ia.fetch();
			byte[] data1 = ia.getBytes();
			
			//Part 3: Put code to REST
			fos = new FileOutputStream("/home/zhang/Documents/rlt.jpg");
			fos.write(data1);
			fos.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			try{
			fos.close();
			} catch(IOException e){
				e.printStackTrace();
			}
		}
		
	
	}
}
