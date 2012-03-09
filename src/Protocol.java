import java.io.IOException;


public abstract class Protocol {
	protected MessageSerializer serializer;
	
	public Protocol(MessageSerializer serializer){
		this.serializer = serializer;
	}
	
	public void sendMessage(Message message) throws IOException{
		serializer.sendMessage(message);
	}
	public Message sendRPC(Message message){
		return serializer.sendRPC(message);
	}
}
