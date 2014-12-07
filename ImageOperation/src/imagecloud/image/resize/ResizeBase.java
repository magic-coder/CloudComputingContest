package imagecloud.image.resize;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class ResizeBase {
	protected String strIn;
	protected String strOut;
	protected BufferedImage imageIn;
	protected BufferedImage imageOut;
	protected String type;
	public void resize(int targetW,int targetH){}
	public BufferedImage resize(BufferedImage imageIn, int imageOutW,int imageOutH) {
		return null;
	}
	public ResizeBase() {
		super();
		this.strIn = null;
		this.strOut = null;
		this.imageIn = null;
		this.imageOut = null;
		this.type = null;
	}

	public ResizeBase(String strIn, String strOut, BufferedImage imageIn,
			BufferedImage imageOut, String type) {
		super();
		this.strIn = strIn;
		this.strOut = strOut;
		this.imageIn = imageIn;
		this.imageOut = imageOut;
		this.type = type;
	}
	public ResizeBase(String strIn, String strOut) {
		super();
		this.strIn = strIn;
		this.strOut = strOut;
	}

	public void save() throws IOException{
	
        //write to hbase. firstly convert imageOut into byte[] 
        //...
        //test in local file system
        ByteArrayOutputStream oStream = null;
		try {
			oStream = new ByteArrayOutputStream();
	        ImageIO.write(this.imageOut,this.type,oStream);
	        byte[] b = oStream.toByteArray();
	        oStream.writeTo(new FileOutputStream(this.strOut));
	        oStream.flush();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
	        oStream.close();
		}
	}
	public void read() throws IOException{
		//read image from hbase. convert the byte[] into a InputStream
		//for use of ImageIO.read();
		//...
		//File fromFile=new File(this.strIn);
		InputStream iStream = null;
		try {
			iStream = new FileInputStream(this.strIn);
			this.imageIn = ImageIO.read(iStream);
			this.type=strIn.substring(strIn.lastIndexOf(".")+1, strIn.length()).toUpperCase();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			iStream.close();
		}
	}
	
	public static void main(String[] args){
		ResizeAT r = new ResizeAT("./src/IMG_0321.JPG", "F:/documents/pic/test1.jpg");
		try {
			r.read();
			r.resize(3000, 3000);
			r.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
