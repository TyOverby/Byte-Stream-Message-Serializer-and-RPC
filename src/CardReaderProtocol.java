import java.io.IOException;


public class CardReaderProtocol extends Protocol{
	public static class types{
		public static final int UNKNOWN = 0;
		public static final int WRITE   = 1;
		public static final int READ    = 2;
		public static final int FILL    = 3;
		public static final int PULL    = 4;
	}
	
	
	public CardReaderProtocol(MessageSerializer serializer) {
		super(serializer);
	}

	public void write(byte location,byte data) throws IOException{
		int command = types.WRITE;
		int rpcID   = 0;
		byte[] payload = {location,data};
		
		Message m = new Message(command,rpcID,payload);
		
		serializer.sendMessage(m);
	}
	
	public Message readRPC(byte location){
		int command = types.WRITE;
		int rpcID   = 0;
		byte[] payload = {location};
		
		Message m = new Message(command,rpcID,payload);
		
		return serializer.sendRPC(m);
	}
	
	public void fill(byte[] payload) throws IOException{
		int command = types.FILL;
		int rpcID   = 0;
		
		Message m = new Message(command,rpcID,payload);
		
		serializer.sendMessage(m);
	}
	
	public Message pullRPC(){
		int command = types.PULL;
		int rpcID = 0;
		
		Message m = new Message(command,rpcID);
		
		return serializer.sendRPC(m);
	}
}
