package me.auri.core;

public class Obj_t {

	public static final int TOTAL_OFFSET = 32;
	
	private long Xcoord = 0; // u32
	private long Zcoord = 0; // u32
	
	private short Ycoord = 0; // s16
	private byte Width = 0; // s8
	private byte Height = 0; // s8
	
	private long ObjFlags = 0; // u32
	private long cObjFlags = 0; // u32
	private long ExObjData = 0; // u32
	
	private byte ObjType = 0; // s8
	private byte cObjType = 0; // s8
	
	private short linkID = 0; // s16
	private short EfIndx = 0; // s16
	
	private byte unknw_01 = 0; // s8
	private byte cObjTransformID = 0; // s8
	
	public Obj_t(long u32Xcoord, long u32Zcoord, short s16Ycoord, byte s8Width, byte s8Height, long u32ObjFlags, long u32cObjFlags,long u32ExObjData, byte s8ObjType, byte s8cObjType
			, short s16linkID, short s16EfIndx, byte s8unknw_01, byte s8cObjTransformID) {
		
		Xcoord = u32Xcoord;
		Zcoord = u32Zcoord;
		
		Ycoord = s16Ycoord;
		Width = s8Width;
		Height = s8Height;
		
		ObjFlags = u32ObjFlags;
		cObjFlags = u32cObjFlags;
		ExObjData = u32ExObjData;
		
		ObjType = s8ObjType;
		cObjType = s8ObjType;
		
		linkID = s16linkID;
		EfIndx = s16EfIndx;
		
		unknw_01 = s8unknw_01;
		cObjTransformID = s8cObjTransformID;
		
	}

	public long getXcoord() {
		return Xcoord;
	}

	public void setXcoord(int xcoord) {
		Xcoord = xcoord;
	}

	public long getZcoord() {
		return Zcoord;
	}

	public void setZcoord(int zcoord) {
		Zcoord = zcoord;
	}

	public short getYcoord() {
		return Ycoord;
	}

	public void setYcoord(short ycoord) {
		Ycoord = ycoord;
	}

	public byte getWidth() {
		return Width;
	}

	public void setWidth(byte width) {
		Width = width;
	}

	public byte getHeight() {
		return Height;
	}

	public void setHeight(byte height) {
		Height = height;
	}

	public long getObjFlags() {
		return ObjFlags;
	}

	public void setObjFlags(long objFlags) {
		ObjFlags = objFlags;
	}

	public long getcObjFlags() {
		return cObjFlags;
	}

	public void setcObjFlags(long cObjFlags) {
		this.cObjFlags = cObjFlags;
	}

	public long getExObjData() {
		return ExObjData;
	}

	public void setExObjData(long exObjData) {
		ExObjData = exObjData;
	}

	public byte getObjType() {
		return ObjType;
	}

	public void setObjType(byte objType) {
		ObjType = objType;
	}

	public byte getcObjType() {
		return cObjType;
	}

	public void setcObjType(byte cObjType) {
		this.cObjType = cObjType;
	}

	public short getLinkID() {
		return linkID;
	}

	public void setLinkID(short linkID) {
		this.linkID = linkID;
	}

	public short getEfIndx() {
		return EfIndx;
	}

	public void setEfIndx(short efIndx) {
		EfIndx = efIndx;
	}

	public byte getUnknw_01() {
		return unknw_01;
	}

	public void setUnknw_01(byte unknw_01) {
		this.unknw_01 = unknw_01;
	}

	public byte getcObjTransformID() {
		return cObjTransformID;
	}

	public void setcObjTransformID(byte cObjTransformID) {
		this.cObjTransformID = cObjTransformID;
	}

	public static int getTotalOffset() {
		return TOTAL_OFFSET;
	}
	
}
