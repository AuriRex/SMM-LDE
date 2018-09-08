package me.auri.core;

public class SMMLevel {

	public static final String AUTOSCROLL_NONE = "None";
	public static final String AUTOSCROLL_SLOW = "Slow";
	public static final String AUTOSCROLL_MEDIUM = "Medium";
	public static final String AUTOSCROLL_FAST = "Fast";

	public static final String GAMEMODE_M1 = "Super Mario Bros.";
	public static final String GAMEMODE_M3 = "Super Mario Bros 3";
	public static final String GAMEMODE_MW = "Super Mario World";
	public static final String GAMEMODE_WU = "New Super Mario Bros U";

	public static final String THEME_OVERWORLD = "Overworld"; // 0
	public static final String THEME_UNDERGROUND = "Underground"; // 1
	public static final String THEME_CASTLE = "Castle"; // 2
	public static final String THEME_AIRSHIP = "Airship"; // 3
	public static final String THEME_WATER = "Water"; // 4
	public static final String THEME_GHOST = "Ghost House"; // 5

	private Obj_t[] objects;
	private Effect_t[] effects;

	private byte[] data;

	// Level Vars
	protected String title = "";
	
	protected int year = 0; // u16
	protected short month = 0; // u8
	protected short day = 0; // u8
	protected short hour = 0; // u8
	protected short minute = 0; // u8

	protected String gamemode = "";
	protected short theme = 0; // u8
	protected int timelimit = 0; // u16
	protected short autoscroll = 0; // u8

	public SMMLevel(byte[] data) {

		this.setData(data);

		objects = new Obj_t[2600];
		effects = new Effect_t[300];

		fetchData(this.getData());

		loadObjects(this.getData());
		
		loadEffects(this.getData());
		
		title = readName(this.getData());
		
		System.out.println("Level \""+title+"\" has been loaded!");
		
	}

	protected Obj_t getObj(int index) {

		if (index > objects.length - 1 | index < 0) {
			return objects[0];
		}

		return objects[index];

	}

	protected void createLevel() {

	}

	public Obj_t[] searchObjX(int x) {
		
		Obj_t[] out = new Obj_t[27];
		
		int c = 0;
		for(int i = 0; i < 2600; i++) {
			
			if(objects[i].getXcoord() == x) {
				
				out[c] = objects[i];
				c++;
				
			}
			
		}
		
		return out;
	}
	
	public Obj_t[] searchObjY(int y) {
		
		Obj_t[] out = new Obj_t[240];
		
		int c = 0;
		for(int i = 0; i < 2600; i++) {
			
			if(objects[i].getYcoord() == y) {
				
				out[c] = objects[i];
				c++;
				
			}
			
		}
		
		return out;
	}
	
	public Obj_t getByCoord(int x, int y) {
		
		for(int i = 0; i < 2600; i++) {
			
			if(objects[i].getXcoord() == x) {
				if(objects[i].getYcoord() == y) {
					return objects[i];
				}
			}
			
		}
		
		return null;
		
	}
	
	protected boolean[] getFlags(int id) {
		String tmp = String.format("%32s", Long.toBinaryString(objects[id].getObjFlags() & 0xFFFFFFFF)).replace(' ', '0');
		
		boolean[] flags = new boolean[32];
		
		int c = 0;
		for(int i = 0; i < 32; i++) {
			
			if(tmp.charAt(i) == '1') {
				flags[c] = true;
			}else {
				flags[c] = false;
			}
			
			c++;
		}
		
		return flags;
	}
	
	protected boolean[] getFlags(Obj_t obj) {
		
		Obj_t objn = null;
		
		for(int i = 0; i < 2600; i++) {
			
			if(obj.equals(objects[i])) {
				objn = objects[i];
				break;
			}
			
		}
		
		if(objn == null)
			return null;
		
		
		String tmp = String.format("%32s", Long.toBinaryString(objn.getObjFlags() & 0xFFFFFFFF)).replace(' ', '0');
		
		boolean[] flags = new boolean[32];
		
		int c = 0;
		for(int i = 0; i < 32; i++) {
			
			if(tmp.charAt(i) == '1') {
				flags[c] = true;
			}else {
				flags[c] = false;
			}
			
			c++;
		}
		
		return flags;
	}
	
	protected byte[] saveAll(byte[] data) {
		saveObjects(data);
		saveEffects(data);
		saveData(data);
		return Core.writeChecksum(data);
	}
	
	protected void loadObjects(byte[] data) {

		int offset = Core.h2i("F0");

		for (int i = 0; i < 2600; i++) {
			// New Object
			System.out.println("Reading Object #" + i);
			Obj_t tmpobj = new Obj_t(Core._u32(offset, data), Core._u32(offset + 4, data), Core._s16(offset + 8, data),
					Core._s8(offset + 10, data), Core._s8(offset + 11, data), Core._u32(offset + 12, data),
					Core._u32(offset + 16, data), Core._u32(offset + Core.h2i("14"), data),
					Core._s8(offset + Core.h2i("18"), data), Core._s8(offset + Core.h2i("19"), data),
					Core._s16(offset + Core.h2i("1A"), data), Core._s16(offset + Core.h2i("1C"), data),
					Core._s8(offset + Core.h2i("1E"), data), Core._s8(offset + Core.h2i("1F"), data));

			objects[i] = tmpobj;
			offset = offset + Obj_t.TOTAL_OFFSET;

		}

	}
	
	protected void saveObjects(byte[] data) {
		
		int offset = Core.h2i("F0");
		
		for(int i = 0; i < 2600; i++) {
			
			Obj_t obj = objects[i];
			
			Core.write32(offset, String.format("%32s", Long.toBinaryString(obj.getXcoord() & 0xFFFFFFFF)).replace(' ', '0'), data);
			Core.write32(offset+4, String.format("%32s", Long.toBinaryString(obj.getZcoord() & 0xFFFFFFFF)).replace(' ', '0'), data);
			
			Core.write16(offset+8, String.format("%16s", Long.toBinaryString(obj.getYcoord() & 0xFFFF)).replace(' ', '0'), data);
			Core.write8(offset+10, String.format("%8s", Long.toBinaryString(obj.getWidth() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+11, String.format("%8s", Long.toBinaryString(obj.getHeight() & 0xFF)).replace(' ', '0'), data);
			
			Core.write32(offset+12, String.format("%32s", Long.toBinaryString(obj.getObjFlags() & 0xFFFFFFFF)).replace(' ', '0'), data);
			Core.write32(offset+16, String.format("%32s", Long.toBinaryString(obj.getcObjFlags() & 0xFFFFFFFF)).replace(' ', '0'), data);
			Core.write32(offset+20, String.format("%32s", Long.toBinaryString(obj.getExObjData() & 0xFFFFFFFF)).replace(' ', '0'), data);
			
			Core.write8(offset+24, String.format("%8s", Long.toBinaryString(obj.getObjType() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+25, String.format("%8s", Long.toBinaryString(obj.getcObjType() & 0xFF)).replace(' ', '0'), data);
			
			Core.write16(offset+26, String.format("%16s", Long.toBinaryString(obj.getLinkID() & 0xFFFF)).replace(' ', '0'), data);
			Core.write16(offset+28, String.format("%16s", Long.toBinaryString(obj.getEfIndx() & 0xFFFF)).replace(' ', '0'), data);
			
			Core.write8(offset+30, String.format("%8s", Long.toBinaryString(obj.getUnknw_01() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+31, String.format("%8s", Long.toBinaryString(obj.getcObjTransformID() & 0xFF)).replace(' ', '0'), data);
			
			offset = offset + Obj_t.TOTAL_OFFSET;
		}
		
	}

	public void loadEffects(byte[] data) {

		int offset = Core.h2i("145F0");

		for (int i = 0; i < 300; i++) {

			System.out.println("Reading Effect #" + i);
			Effect_t tmpefx = new Effect_t(Core._u8(offset, data), Core._u8(offset + 1, data),
					Core._u8(offset + 2, data), Core._u8(offset + 3, data), Core._u8(offset + 4, data));

			effects[i] = tmpefx;
			offset = offset + Effect_t.TOTAL_OFFSET;

		}

	}
	
	protected void saveEffects(byte[] data) {
		
		int offset = Core.h2i("145F0");
		
		for (int i = 0; i < 300; i++) {
			
			Effect_t tmpefx = effects[i];
			
			Core.write8(offset, String.format("%8s", Long.toBinaryString(tmpefx.getUnknw_01() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+1, String.format("%8s", Long.toBinaryString(tmpefx.getUnknw_02() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+2, String.format("%8s", Long.toBinaryString(tmpefx.getUnknw_03() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+3, String.format("%8s", Long.toBinaryString(tmpefx.getUnknw_04() & 0xFF)).replace(' ', '0'), data);
			Core.write8(offset+4, String.format("%8s", Long.toBinaryString(tmpefx.getUnknw_05() & 0xFF)).replace(' ', '0'), data);
			
			offset = offset + Effect_t.TOTAL_OFFSET;
		}
		
	}

	public void fetchData(byte[] data) {

		year = Core._u16(Core.h2i("10"), data);
		month = Core._u8(Core.h2i("12"), data);
		day = Core._u8(Core.h2i("13"), data);
		hour = Core._u8(Core.h2i("14"), data);
		minute = Core._u8(Core.h2i("15"), data);

		String gamemode1 = (char) Integer.parseUnsignedInt(Core._u8_(Core.h2i("6A"), data), 2) + "";
		String gamemode2 = (char) Integer.parseUnsignedInt(Core._u8_(Core.h2i("6B"), data), 2) + "";

		gamemode = gamemode1 + gamemode2;

		// System.out.println("Gamemode: "+gamemode1+gamemode2);

		theme = Core._u8(Core.h2i("6D"), data);

		timelimit = Core._u16(Core.h2i("70"), data);
		autoscroll = Core._u8(Core.h2i("72"), data);

		//System.out.println(String.format("%8s", Integer.toBinaryString(Short.parseShort("10000000", 2) & 0xFF)).replace(' ', '0'));

	}
	
	protected void saveData(byte[] data) {
		
		Core.write16(Core.h2i("10"), String.format("%16s", Long.toBinaryString(year & 0xFFFF)).replace(' ', '0'), data);
		Core.write8(Core.h2i("12"), String.format("%8s", Long.toBinaryString(month & 0xFF)).replace(' ', '0'), data);
		Core.write8(Core.h2i("13"), String.format("%8s", Long.toBinaryString(day & 0xFF)).replace(' ', '0'), data);
		Core.write8(Core.h2i("14"), String.format("%8s", Long.toBinaryString(hour & 0xFF)).replace(' ', '0'), data);
		Core.write8(Core.h2i("15"), String.format("%8s", Long.toBinaryString(minute & 0xFF)).replace(' ', '0'), data);
		
		Core.write8(Core.h2i("6A"), String.format("%8s", Long.toBinaryString(gamemode.charAt(0) & 0xFF)).replace(' ', '0'), data);
		Core.write8(Core.h2i("6B"), String.format("%8s", Long.toBinaryString(gamemode.charAt(1) & 0xFF)).replace(' ', '0'), data);
		
		Core.write8(Core.h2i("6D"), String.format("%8s", Long.toBinaryString(theme & 0xFF)).replace(' ', '0'), data);
		Core.write16(Core.h2i("70"), String.format("%16s", Long.toBinaryString(timelimit & 0xFFFF)).replace(' ', '0'), data);
		Core.write8(Core.h2i("72"), String.format("%8s", Long.toBinaryString(autoscroll & 0xFF)).replace(' ', '0'), data);
		
		while(title.length() < 32) {
			title = title + "\0";
		}
		
		String tmpChars = "";
		
		for(int i = 0; i < 32; i++) {
			tmpChars = tmpChars + String.format("%16s", Long.toBinaryString(title.charAt(i) & 0xFFFF)).replace(' ', '0');
			//System.out.println(tmpChars);
		}
		
		
		
		String[] tmp = new String[8]; // 64 Bit Pieces!
		
		for(int i = 0; i < 8; i++) {
			tmp[i] = tmpChars.substring(0+(i*64), 64+(i*64));
			//System.out.println(tmp[i]);
		}
		
		
		
		for(int i = 0; i < 8; i++) {
			
			Core.write64(Core.h2i("28")+(8*i), String.format("%64s", Long.toBinaryString(Long.parseLong(tmp[i], 2) & 0xFFFFFFFFFFFFFFFFL)).replace(' ', '0'), data);
			
		}
		
		
	}

	// Other Functions /////////////////////////////////////////////////////////
	
	public static String readName(byte[] data) {

		// 0x28
		// u16 - x21

		String ch = "";

		for (int i = 0; i < Core.h2i("40"); i = i + 2) {

			char c = (char) Core._u16(Core.h2i("28") + i, data);

			// System.out.println(Core._u16_(Core.h2i("28")+i, data));

			if (c == '\0') {
				break;
			}

			ch = ch + c;

		}

		return ch;
	}

	public static String convertGamemode(String gm) {

		System.out.println("\""+gm+"\"");
		
		if (gm.length() > 2) {
			// Long to Short

			if (gm.equals(GAMEMODE_M1)) {
				return "M1";
			} else if (gm.equals(GAMEMODE_M3)) {
				return "M3";
			} else if (gm.equals(GAMEMODE_MW)) {
				return "MW";
			} else if (gm.equals(GAMEMODE_WU)) {
				return "WU";
			} else {
				return "M1";
			}

		} else {
			// Short to Long

			if (gm.equals("M1")) {
				return GAMEMODE_M1;
			} else if (gm.equals("M3")) {
				return GAMEMODE_M3;
			} else if (gm.equals("MW")) {
				return GAMEMODE_MW;
			} else if (gm.equals("WU")) {
				return GAMEMODE_WU;
			} else {
				return GAMEMODE_M1;
			}

		}

	}

	public static String convertTheme(int theme) {

		if (theme == 0) {
			return THEME_OVERWORLD;
		} else if (theme == 1) {
			return THEME_UNDERGROUND;
		} else if (theme == 2) {
			return THEME_CASTLE;
		} else if (theme == 3) {
			return THEME_AIRSHIP;
		} else if (theme == 4) {
			return THEME_WATER;
		} else if (theme == 5) {
			return THEME_GHOST;
		} else {
			return THEME_OVERWORLD;
		}
	}

	public static short convertTheme(String theme) {

		if (theme.equals(THEME_OVERWORLD)) {
			return 0;
		} else if (theme.equals(THEME_UNDERGROUND)) {
			return 1;
		} else if (theme.equals(THEME_CASTLE)) {
			return 2;
		} else if (theme.equals(THEME_AIRSHIP)) {
			return 3;
		} else if (theme.equals(THEME_WATER)) {
			return 4;
		} else if (theme.equals(THEME_GHOST)) {
			return 5;
		} else {
			return 0;
		}
	}

	public static String convertAutoscroll(int as) {

		if (as == 0) {
			return AUTOSCROLL_NONE;
		} else if (as == 1) {
			return AUTOSCROLL_SLOW;
		} else if (as == 2) {
			return AUTOSCROLL_MEDIUM;
		} else if (as == 3) {
			return AUTOSCROLL_FAST;
		} else {
			return AUTOSCROLL_NONE;
		}
	}

	public static short convertAutoscroll(String as) {

		if (as.equals(AUTOSCROLL_NONE)) {
			return 0;
		} else if (as.equals(AUTOSCROLL_SLOW)) {
			return 1;
		} else if (as.equals(AUTOSCROLL_MEDIUM)) {
			return 2;
		} else if (as.equals(AUTOSCROLL_FAST)) {
			return 3;
		} else {
			return 0;
		}
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	protected Effect_t getEffect(int efx) {
		return effects[efx];
	}

}
