package com.prealpha.bytestream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MessageSerializer {
	private final OutputStream out;
	private final List<MessageListener> messageListeners = new ArrayList<MessageListener>();
	
	Set<Byte> takenKeys = new HashSet<Byte>();
	Map<Byte,Message> rpcMessages = new HashMap<Byte,Message>();
	
	public MessageSerializer(InputStream in, OutputStream out){
		this.out      = out;
		
		ReadLoop readLoop = new ReadLoop(in,this);
		new Thread(readLoop).start();
	}
	public MessageSerializer(InputStream in, OutputStream out, MessageListener ml){
		this(in,out);
		this.messageListeners.add(ml);
	}
	
	public void sendMessage(Message m) throws IOException{
		out.write(m.toBytes());
	}
	
	public void addMessageListener(MessageListener ml){
		this.messageListeners.add(ml);
	}
	public void removeMessageListemer(MessageListener ml){
		this.messageListeners.remove(ml);
	}
	
	public byte getPossibleKey(){
		for(byte i=1;i<Byte.MAX_VALUE;i++){
			if(!rpcMessages.containsKey(i)){
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Returns true if it was an RPC.  False if otherwise
	 * @param m
	 * @return
	 */
	void relayMessage(Message m){
		if(takenKeys.contains(m.id)){
			rpcMessages.put(m.id, m);
		}
		else{
			for(MessageListener ml:messageListeners){
				ml.handleMessage(m);
			}
		}
	}
	
	public Message sendRPC(Message m){
		byte id = getPossibleKey();
		takenKeys.add(id);
		m.id = id;
		
		while(true){
			if(rpcMessages.containsKey(id)){
				Message toReturn = rpcMessages.get(id);
				takenKeys.remove(id);
				rpcMessages.remove(id);
				return toReturn;
			}
		}
	}
}
