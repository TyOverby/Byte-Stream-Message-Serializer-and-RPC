package com.prealpha.bytestream;
import java.io.IOException;
import java.io.InputStream;


public class ReadLoop implements Runnable {

	private InputStream in;
	private MessageSerializer ms;
	private boolean isReading;
	
	public ReadLoop(InputStream dis,MessageSerializer ms){
		this.in = dis;
		this.ms = ms;
	}

	@Override
	public void run() {
		this.isReading = true;
		
		while(isReading){
			try {
				
				int type       = in.read();
				int id         = in.read();
				int length     = in.read();
				int[] payload = new int[length]; 
				for(int i=0;i<length;i++){
					payload[i]=in.read();
				}
				
				
				Message toReturn = new Message(type,id,payload);
				ms.relayMessage(toReturn);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}
}
