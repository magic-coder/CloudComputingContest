package imagecloud.image.resize;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class ResizeAT extends ResizeBase{
	public ResizeAT() {
		super();
	}
	public ResizeAT(String strIn, String strOut) {
		super(strIn,strOut);
	} 
	
	public BufferedImage resize(BufferedImage imageIn, int imageOutW,int imageOutH) {
		int type=imageIn.getType();
		BufferedImage imageOut;
		//scalar
		double sx=(double)imageOutW/imageIn.getWidth();
		double sy=(double)imageOutH/imageIn.getHeight();
		if (sx > sy) {
			sx = sy;
			imageOutW = (int) (sx * imageIn.getWidth());
		} else {
			sy = sx;
			imageOutH = (int) (sx * imageIn.getHeight());
		}

		if(type==BufferedImage.TYPE_CUSTOM){
			ColorModel cm=imageIn.getColorModel();
			WritableRaster raster=cm.createCompatibleWritableRaster(imageOutW,imageOutH);
		    boolean alphaPremultiplied=cm.isAlphaPremultiplied();
		    imageOut=new BufferedImage(cm,raster,alphaPremultiplied,null);
		}else{
			imageOut=new BufferedImage(imageOutW,imageOutH,type);
			Graphics2D g=imageOut.createGraphics();
			g.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
			g.drawRenderedImage(imageIn,AffineTransform.getScaleInstance(sx,sy));
			g.dispose();
		}
		return imageOut;
	}
	
}
