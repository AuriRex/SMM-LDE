package me.auri.core;

public class Effect_t {

	public static final int TOTAL_OFFSET = 8;
	
	
	private short unknw_01 = 0; // u8
	private short unknw_02 = 0; // u8
	private short unknw_03 = 0; // u8
	private short unknw_04 = 0; // u8
	private short unknw_05 = 0; // u8
	// +padding (3 bytes)
	
	public Effect_t(short u801, short u802, short u803, short u804, short u805) {
		
		unknw_01 = u801;
		unknw_02 = u802;
		unknw_03 = u803;
		unknw_04 = u804;
		unknw_05 = u805;
		
	}

	public short getUnknw_01() {
		return unknw_01;
	}

	public void setUnknw_01(short unknw_01) {
		this.unknw_01 = unknw_01;
	}

	public short getUnknw_02() {
		return unknw_02;
	}

	public void setUnknw_02(short unknw_02) {
		this.unknw_02 = unknw_02;
	}

	public short getUnknw_03() {
		return unknw_03;
	}

	public void setUnknw_03(short unknw_03) {
		this.unknw_03 = unknw_03;
	}

	public short getUnknw_04() {
		return unknw_04;
	}

	public void setUnknw_04(short unknw_04) {
		this.unknw_04 = unknw_04;
	}

	public short getUnknw_05() {
		return unknw_05;
	}

	public void setUnknw_05(short unknw_05) {
		this.unknw_05 = unknw_05;
	}

}
