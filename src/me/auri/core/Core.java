package me.auri.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.Checksum;

import javax.swing.ImageIcon;

import me.auri.util.Image;
import me.auri.util.TileSet;


public class Core {

	public static Map<Integer, SMMLevel> levels = new HashMap<Integer, SMMLevel>();
	
	public static int addLevel(int key, SMMLevel lvl) {

		if (levels.get(key) == null) {
			levels.put(key, lvl);
			return key;
		} else {
			int nk = levels.size();
			levels.put(nk, lvl);
			return nk;
		}
	}

	private static long CRC32(byte[] bytes) {

		Checksum checksum = new java.util.zip.CRC32();
		checksum.update(bytes, 0, bytes.length);
		long checksumValue = checksum.getValue();

		return checksumValue;
	}

	private static String getChecksum(byte[] data) {

		byte[] newdata = new byte[data.length - 16];

		int c = 0;

		for (int i = h2i("10"); i < data.length; i++) {
			newdata[c] = data[i];
			c++;
		}

		long tmp = CRC32(newdata);

		// System.out.println(tmp);

		String strg = String.format("%8s", Long.toBinaryString(tmp & 0xFFFFFFFF)).replace(' ', '0');

		while (strg.length() < 32) {
			strg = "0" + strg;
		}

		System.out.println("Checksum (binary): " + strg);

		return strg;
	}

	private static void writeData(int offset, byte newdat, byte[] data) {
		data[offset] = newdat;
	}

	public static void saveFile(byte[] data, String name) throws IOException {
		FileOutputStream fos = new FileOutputStream(name);
		fos.write(data);
		fos.close();
	}

	static byte[] data;

	public static byte[] loadFile(String path) throws IOException {

		Path pathn = Paths.get(path);
		byte[] data = null;
		try {
			data = Files.readAllBytes(pathn);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;
	}
	
	/*
	public static void startEngine() {
		
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File("assets/viewer/hand.png"));
		} catch (IOException e) {
		}
		
		Game.init();
		
		Game.getScreenManager().getRenderComponent().setCursor(img, 4, 4);
		
		Game.start();
		
		Game.addGameTerminatedListener(() -> 
		{
		  //cc.disableSystemExit();
			System.out.println("Why u terminate everything ? :,(");
		});
		
		
	}*/
	
	static ImageIcon marioHead = new ImageIcon("assets/head.png");
	static ImageIcon hand = new ImageIcon("assets/viewer/hand.png");
	
	static String tspath = "assets/tilesets/";
	
	static Map<Integer, TileSet> tilesetList = null;
	
	public static boolean debug = false;
	
	public static void loadTilesets() {
		System.out.println("Loading Tilesets...");
		tilesetList = new HashMap<Integer, TileSet>();
		
		File folder = new File(tspath);
		int c = 0;
		for (File file : folder.listFiles()) {
		    if (file.isFile()) {
		        System.out.println(c+": "+file.getName());
		        Image img = new Image(file);
		        tilesetList.put(c, new TileSet(img, img.width/16, img.width/16, debug));
		        c++;
		        
		    }
		}
	}
	
	public static void main(String[] args) {
		
		boolean nextts = false; // Kappa
		boolean tsf = false;
		
		if(args.length > 0) {
			
			for(String arg : args) {
				if(!nextts)
					if (arg.equals("-tilesets")) {
						nextts = true;
					}
				else if(!tsf & nextts){
					tsf = true;
					tspath = arg;
				}
				if(arg.equals("-debug")) {
					debug = true;
				}
			}
			
		}
		
		System.out.println("lol");
		System.setProperty("sun.java2d.opengl", "true");
		
		loadTilesets();
		
		//cc = new CodeControl();
		//cc.disableSystemExit();
		
		
		
		/*
		System.out.println("<Arguments>");
		for (int i = 0; i < args.length; i++) {

			System.out.println(args[i]);

		}
		System.out.println("</Arguments>");*/
		/*
		 * // Load File File file = new File("course_data_sub.cdt"); Path path =
		 * Paths.get(file.getAbsolutePath()); byte[] data = null; try { data =
		 * Files.readAllBytes(path); } catch (IOException e) { e.printStackTrace(); }
		 * Core.data = data;
		 */

		/*
		 * int counter = 0;
		 * 
		 * for (int i = 0; i < data.length; i++) { //
		 * System.out.print(Integer.toBinaryString(data[i])+"#"); String s =
		 * String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ',
		 * '0'); if (counter >= 15) { System.out.println(s + "#"); counter = 0; } else {
		 * System.out.print(s + "_"); counter++; }
		 * 
		 * }
		 */
		//data = new byte[8];
		
		//write64(0, "1010101001010101101010100101010110101010010101011010101001010101", data);
		
		
		
		new Window();

		/*
		 * SMMLevel level = new SMMLevel(data);
		 * 
		 * level.loadObjects(level.getData());
		 * 
		 * // do stuff BigInteger version = _u64(0, Core.data);
		 * 
		 * int year = _u16(h2i("10"), Core.data); Short month = _u8(h2i("12"),
		 * Core.data); Short day = _u8(h2i("13"), Core.data);
		 * 
		 * String gamemode1 = _u8_(h2i("6A"), Core.data); String gamemode2 =
		 * _u8_(h2i("6B"), Core.data);
		 * 
		 * //System.out.println("Gamemode: "+gamemode1+gamemode2);
		 * 
		 * Short theme = _u8(h2i("6D"), Core.data);
		 * 
		 * int timelimit = _u16(h2i("70"), Core.data); Short autoscroll = _u8(h2i("72"),
		 * Core.data);
		 * 
		 * //System.out.println(String.format("%8s",
		 * Integer.toBinaryString(Short.parseShort("10000000", 2) & 0xFF)).replace(' ',
		 * '0'));
		 * 
		 * /////////////////////////////////////////////////////////////////////////////
		 * ////// // WRITE EVERYTHING ELSE HERE!!!
		 * //////////////////////////////////////////////////
		 * /////////////////////////////////////////////////////////////////////////////
		 * //////
		 * 
		 * writeData(h2i("70"), (byte) Short.parseShort("11111111", 2), Core.data);//
		 * 00000000 writeData(h2i("71"), (byte) Short.parseShort("11111111", 2),
		 * Core.data);// 00000000
		 * 
		 * 
		 * // Tried to change gamemode in sub-world to NSMBU (from SMW) --> Results:
		 * Game uses gamemode from main-world. String val = "0101011101010101";
		 * 
		 * Short ac = (short) Long.parseLong(val, 2); ByteBuffer bytesc =
		 * ByteBuffer.allocate(2).putShort(ac);
		 * 
		 * byte[] arrayc = bytesc.array();
		 * 
		 * writeData(h2i("6A"), arrayc[0], Core.data); writeData(h2i("6B"), arrayc[1],
		 * Core.data);
		 */

		///////////////////////////////////////////////////////////////////////////////////
		// WRITE CHECKSUM
		/////////////////////////////////////////////////////////////////////////////////// /////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////

		///////////////////////////////////////////////////////////////////////////////////
		// DONE CHECKSUM
		/////////////////////////////////////////////////////////////////////////////////// //////////////////////////////////////////////////////////////////
		///////////////////////////////////////////////////////////////////////////////////

		/*
		 * 
		 * char g1 = (char) Integer.parseInt(gamemode1, 2); char g2 = (char)
		 * Integer.parseInt(gamemode2, 2);
		 * 
		 * long objCount = _u32(h2i("EC"), Core.data);
		 * 
		 * System.out.println("Version: " + version);
		 * 
		 * System.out.println("Gamemode: " + g1 + g2); System.out.println("Theme: " +
		 * SMMLevel.convertTheme(theme));
		 * 
		 * System.out.println("Timelimit: " + timelimit);
		 * System.out.println("Autoscroll: " + SMMLevel.convertAutoscroll(autoscroll));
		 * 
		 * System.out.println("Creation Date:\n  Year: " + year + "\n  Month: " + month
		 * + "\n  Day: " + day);
		 * 
		 * System.out.println("ObjectCount: " + objCount);
		 * 
		 * // System.out.println(t);
		 * 
		 * System.out.println(SMMLevel.readName(data));
		 * 
		 */
		/*
		 * try { saveFile(Core.data,"test_sub.cdt"); } catch (IOException e) {
		 * e.printStackTrace(); }
		 */

	}

	public static byte[] writeChecksum(byte[] data) {

		// Get Checksum from Data
		String sum = getChecksum(data);

		// Split it in 4
		int a = (int) Long.parseLong(sum, 2);
		ByteBuffer bytes = ByteBuffer.allocate(4).putInt(a);
		// Put it in an array
		byte[] array = bytes.array();

		System.out.println("new checksum:\n  0x08: " + array[0]);
		System.out.println("  0x09: " + array[1]);
		System.out.println("  0x0A: " + array[2]);
		System.out.println("  0x0B: " + array[3]);
		// Write new checksum to data
		writeData(8, array[0], data);
		writeData(9, array[1], data);
		writeData(10, array[2], data);
		writeData(11, array[3], data);
		
		return data;
		
	}
	
	public static void write64(int offset, String newdata, byte[] data) {
		int rep = 8;
		System.out.println("write"+(rep*8));
		String[] strg = new String[rep];
		for(int i = 0; i < rep; i++) {
			strg[i] = newdata.substring(0+(i*8), 8+(i*8));
			System.out.println("#"+i+" - "+strg[i]);
		}
		
		for(int i = 0; i < rep; i++) {
			data[offset+i] = (byte) Short.parseShort(strg[i], 2);
		}

	}
	
	public static void write32(int offset, String newdata, byte[] data) {
		int rep = 4;
		System.out.println("write"+(rep*8));
		String[] strg = new String[rep];
		for(int i = 0; i < rep; i++) {
			strg[i] = newdata.substring(0+(i*8), 8+(i*8));
			System.out.println("#"+i+" - "+strg[i]);
		}
		
		for(int i = 0; i < rep; i++) {
			data[offset+i] = (byte) Short.parseShort(strg[i], 2);
		}

	}
	
	public static void write16(int offset, String newdata, byte[] data) {
		int rep = 2;
		System.out.println("write"+(rep*8));
		String[] strg = new String[rep];
		for(int i = 0; i < rep; i++) {
			strg[i] = newdata.substring(0+(i*8), 8+(i*8));
			System.out.println("#"+i+" - "+strg[i]);
		}
		
		for(int i = 0; i < rep; i++) {
			data[offset+i] = (byte) Short.parseShort(strg[i], 2);
		}

	}
	
	public static void write8(int offset, String newdata, byte[] data) {
		int rep = 1;
		System.out.println("write"+(rep*8));
		String[] strg = new String[rep];
		for(int i = 0; i < rep; i++) {
			strg[i] = newdata.substring(0+(i*8), 8+(i*8));
			System.out.println("#"+i+" - "+strg[i]);
		}
		
		for(int i = 0; i < rep; i++) {
			data[offset+i] = (byte) Short.parseShort(strg[i], 2);
		}

	}

	public static int h2i(String hex) {
		return Integer.parseInt(hex, 16);
	}

	public static BigInteger _u64(int offset, byte[] data) {

		BigInteger bi = null;

		String combine = "";

		for (int i = offset; i < offset + 8; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		bi = new BigInteger(combine, 2);

		return bi;
	}

	public static long _u32(int offset, byte[] data) {

		long sol = 0;

		String combine = "";

		for (int i = offset; i < offset + 4; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		sol = Long.parseLong(combine, 2);

		return sol;
	}

	public static int _u16(int offset, byte[] data) {

		int sol = 0;

		String combine = "";

		for (int i = offset; i < offset + 2; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		sol = Integer.parseInt(combine, 2);

		return sol;
	}

	public static short _u8(int offset, byte[] data) {

		short sol = 0;

		String combine = String.format("%8s", Integer.toBinaryString(data[offset] & 0xFF)).replace(' ', '0');

		// System.out.println(combine);

		sol = Short.parseShort(combine, 2);

		return sol;
	}

	// SIGNED
	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	public static long _s64(int offset, byte[] data) {

		long bi = 0;

		String combine = "";

		for (int i = offset; i < offset + 8; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		bi = Long.parseLong(combine, 2);

		return bi;
	}

	public static int _s32(int offset, byte[] data) {

		int sol = 0;

		String combine = "";

		for (int i = offset; i < offset + 4; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		sol = Integer.parseInt(combine, 2);

		return sol;
	}

	public static short _s16(int offset, byte[] data) {

		short sol = 0;

		String combine = "";

		for (int i = offset; i < offset + 2; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		sol = (short) Integer.parseInt(combine, 2);

		return sol;
	}

	public static byte _s8(int offset, byte[] data) {

		byte sol = 0;

		String combine = String.format("%8s", Integer.toBinaryString(data[offset] & 0xFF)).replace(' ', '0');

		// System.out.println(combine);

		sol = (byte) Short.parseShort(combine, 2);

		return sol;
	}

	// get Bits

	public static String _u64_(int offset, byte[] data) {

		String combine = "";

		for (int i = offset; i < offset + 8; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		return combine;
	}

	public static String _u32_(int offset, byte[] data) {

		String combine = "";

		for (int i = offset; i < offset + 4; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		return combine;
	}

	public static String _u16_(int offset, byte[] data) {

		String combine = "";

		for (int i = offset; i < offset + 2; i++) {

			String s = String.format("%8s", Integer.toBinaryString(data[i] & 0xFF)).replace(' ', '0');

			// System.out.println(s+"#");

			combine = combine + s;

		}

		// System.out.println(combine);

		return combine;
	}

	public static String _u8_(int offset, byte[] data) {

		String combine = String.format("%8s", Integer.toBinaryString(data[offset] & 0xFF)).replace(' ', '0');

		// System.out.println(combine);

		return combine;
	}

}
