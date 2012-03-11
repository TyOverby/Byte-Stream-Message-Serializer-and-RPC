package com.prealpha.bytestream;

public class Utility {
	public static byte intToUnsignedByte(int in){
		return (byte) in;
	}
	
	public static int unsignedByteToInt(byte in){
		return (int) in & 0xff;
	}
	
	public static String toHex(byte in){
		return String.format("%02X", in); 
	}
}
