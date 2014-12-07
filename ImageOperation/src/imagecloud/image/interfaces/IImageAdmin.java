package imagecloud.image.interfaces;

import java.io.IOException;

public interface IImageAdmin {
	public void resize(int w, int h) throws IOException;
	public byte[] getBytes() throws IOException ;
	public void fetch()throws IOException;
	public void add(byte[] data) throws IOException;
	public void setImageId(String id);
	public void logo();
	public void crop();
}
