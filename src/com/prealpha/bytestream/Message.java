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
	
	public void writeOut(OutputStream os) throws IOException{
		os.write(type);
		os.write(id);
		os.write(length);
		
		for(int i=0;i<payload.length;i++){
			os.write(payload[i]);
		}
	}
}
