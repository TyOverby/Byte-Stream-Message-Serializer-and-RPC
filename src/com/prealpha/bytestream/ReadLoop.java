package com.prealpha.bytestream;
import java.io.DataInputStream;
import java.io.IOException;


public class ReadLoop implements Runnable {

	private DataInputStream in;
	private MessageSerializer ms;
	private boolean isReading;
	
	public ReadLoop(DataInputStream dis,MessageSerializer ms){
		this.in = dis;
		this.ms = ms;
	}

	@Override
	public void run() {
		this.isReading = true;
		
		while(isReading){
			try {
				
				byte type       = in.readByte();
				byte id         = in.readByte();
				byte length     = in.readByte();
				byte[] payload = new byte[length]; 
				for(int i=0;i<length;i++){
					payload[i]=in.readByte();
				}
				
				
				Message toReturn = new Message(type,id,payload);
				ms.relayMessage(toReturn);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
