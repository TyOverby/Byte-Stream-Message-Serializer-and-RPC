package com.prealpha.bytestream;
import java.io.IOException;
import java.io.OutputStream;



public class Message {
	
	public final int type;
	public int id;
	public int length;
	public final int[] payload;
	
	public Message(int type, int id, int... payload) throws IllegalArgumentException{
		this.type = type;
		this.id = id;
		this.payload = payload;
		
		if(payload.length>Byte.MAX_VALUE){
			throw new IllegalArgumentException("Payload is too high");
		}
		else{
			this.length = payload.length;
		}
	}
	
	public void writeOut(MessageSerializer ms) throws IOException{
                ms.writeByte(type);
                ms.writeByte(id);
                ms.writeByte(length);
		
		for(int i=0;i<payload.length;i++){
			ms.writeByte(payload[i]);
		}
	}
}
