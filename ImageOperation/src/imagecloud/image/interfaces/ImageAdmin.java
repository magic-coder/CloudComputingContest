package imagecloud.image.interfaces; 

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import imagecloud.hbase.table.TableAdmin;
import imagecloud.image.resize.*;
public class ImageAdmin implements IImageAdmin {
	
	private String id;
	private BufferedImage image;
	private ResizeBase rs;
	private TableAdmin ta;
		 
	public ImageAdmin() {
		super();
		initial("imagecloud","images","1");
	}

	public ImageAdmin(String id) {
		super();
		this.setImageId(id);
		initial("imagecloud","images","1");
	}
	public ImageAdmin(String tbname, String cfname,String clname) {
		super();
		initial( tbname,  cfname, clname);
	}
	//Do we need to config hbase table name here or in an config file?
	private void initial(String tbname, String cfname,String clname){
		ta = new TableAdmin( tbname,  cfname, clname);
		this.rs = new ResizeAT();
	}
	public void setImageId(String id){
		this.id = id.substring(id.lastIndexOf("/")+1).trim();
	}
	
	@Override
	public void resize(int w, int h) throws IOException {
		if (this.image == null){
			throw new IOException("image not fetched yet.");
		}
		image = this.rs.resize(this.image, w, h);
		StringBuffer sb = new StringBuffer(this.id.substring(0, this.id.lastIndexOf(".")));
		sb.append(w).append("_").append(h).append(".")
					.append(this.id.substring(this.id.lastIndexOf(".")+1));
		String resized_id = sb.toString();
		ta.setRwname(resized_id);
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(image, "PNG", os);
		ta.setValue(os.toByteArray());
		ta.write();
	}

	@Override
	public byte[] getBytes() throws IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(this.image,"JPG",baos);
		// load to image object
		return baos.toByteArray();
	}

	@Override
	public void fetch() throws IOException{
		ta.setRwname(this.id);
		byte[] bImage = ta.readByColumn();
		this.image = ImageIO.read(new ByteArrayInputStream(bImage));
	}
	@Override
	public void logo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void add(byte[] data) throws IOException {
		ta.setRwname(id);
		ta.setValue(data);
		ta.write();
	}

	@Override
	public void crop() {
		// TODO Auto-generated method stub

	}
}
