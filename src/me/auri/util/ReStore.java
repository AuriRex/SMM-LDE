package me.auri.util;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class ReStore {

	// public static final String fileName = "options.prop";

	/** Store location & size of UI */
	public static void storeOptions(Rectangle bounds, String fileName, String addon) throws Exception {
		File file = new File(fileName);
		Properties p = new Properties();
		// restore the frame from 'full screen' first!
		Rectangle r = bounds;
		int x = (int) r.getX();
		int y = (int) r.getY();
		int w = (int) r.getWidth();
		int h = (int) r.getHeight();

		p.setProperty("x", "" + x);
		p.setProperty("y", "" + y);
		p.setProperty("w", "" + w);
		p.setProperty("h", "" + h);
		p.setProperty("a", addon);

		BufferedWriter br = new BufferedWriter(new FileWriter(file));
		p.store(br, "Properties of the user frame");
	}

	/***
	 * 
	 * Use .setBounds();
	 * 
	 * @param fileName
	 * @return Bounds
	 * @throws IOException
	 */
	public static Object[] restoreOptions(String fileName) throws IOException {
		File file = new File(fileName);
		
		if(!file.exists())
			return null;
		
		Properties p = new Properties();
		BufferedReader br = new BufferedReader(new FileReader(file));
		p.load(br);

		int x = Integer.parseInt(p.getProperty("x"));
		int y = Integer.parseInt(p.getProperty("y"));
		int w = Integer.parseInt(p.getProperty("w"));
		int h = Integer.parseInt(p.getProperty("h"));
		String addon = p.getProperty("a");
		
		Rectangle r = new Rectangle(x, y, w, h);

		return new Object[]{r,addon};
	}

}
