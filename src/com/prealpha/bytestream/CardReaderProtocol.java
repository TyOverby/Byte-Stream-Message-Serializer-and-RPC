package com.prealpha.bytestream;
import java.io.IOException;


public class CardReaderProtocol extends Protocol{
	public static class types{
		public static final byte UNKNOWN = 0;
		public static final byte WRITE   = 1;
		public static final byte READ    = 2;
		public static final byte FILL    = 3;
		public static final byte PULL    = 4;
	}
	
	
	public CardReaderProtocol(MessageSerializer serializer) {
		super(serializer);
	}

	public void write(byte location,byte data) throws IOException{
		byte command = types.WRITE;
		byte rpcID   = 0;
		byte[] payload = {location,data};
		
		Message m = new Message(command,rpcID,payload);
		
		serializer.sendMessage(m);
	}
	
	public Message readRPC(byte location){
		byte command = types.WRITE;
		byte rpcID   = 0;
		byte[] payload = {location};
		
		Message m = new Message(command,rpcID,payload);
		
		return serializer.sendRPC(m);
	}
	
	public void fill(byte[] payload) throws IOException{
		byte command = types.FILL;
		byte rpcID   = 0;
		
		Message m = new Message(command,rpcID,payload);
		
		serializer.sendMessage(m);
	}
	
	public Message pullRPC(){
		byte command = types.PULL;
		byte rpcID = 0;
		
		Message m = new Message(command,rpcID);
		
		return serializer.sendRPC(m);
	}
}
