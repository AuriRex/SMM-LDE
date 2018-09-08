package me.auri.util;

import me.auri.core.Core;
import me.auri.util.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TileBuffer {

	private ArrayList<java.awt.Image> bts = null;
	
	private long timestamp = 0;
	
	public TileBuffer(TileSet ts, int scalex, int scaley) {
		bts = new ArrayList<java.awt.Image>();
		if(Core.debug) {
			timestamp = System.nanoTime();
			System.out.println("Compiling Image Buffer...");
		}
		for(Image tile : ts.tiles) {
			bts.add(texture(tile, scalex, scaley));
		}
		if(Core.debug)
			System.out.println("BufferCompileTime: "+((System.nanoTime()-timestamp)/1000000)+"ms");
	}
	
	public java.awt.Image getTile(int i) {
		return bts.get(i);
	}
	
	public Object[] get() {
		return bts.toArray();
	}
	
	public java.awt.Image texture(me.auri.util.Image tile, int scale) {
		return texture(tile, scale, scale);
	}
	
	public java.awt.Image texture(me.auri.util.Image tile, int scalex, int scaley) {
		
		BufferedImage image = new BufferedImage(tile.width, tile.height, BufferedImage.TYPE_INT_ARGB);
	    image.setRGB(0, 0, image.getWidth(), image.getHeight(), tile.pixels, 0, image.getWidth());
		
		return image.getScaledInstance(scalex, scaley, java.awt.Image.SCALE_REPLICATE); // TODO Create texture Buffer with right resolution!
	}
	
}
