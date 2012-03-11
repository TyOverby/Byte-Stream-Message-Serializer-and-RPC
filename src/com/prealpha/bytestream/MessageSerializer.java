package com.prealpha.bytestream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class MessageSerializer {
	private final OutputStream out;
	private final List<MessageListener> messageListeners = new ArrayList<MessageListener>();
	private final Queue<Message> messageQueue = new LinkedList<Message>();

	Set<Integer> takenKeys = new HashSet<Integer>();
	Map<Integer,Message> rpcMessages = new HashMap<Integer,Message>();


	public MessageSerializer(OutputStream out){
		this(out,null);
	}
	public MessageSerializer(OutputStream out, InputStream in){
		this.out      = out;
		if(in!=null){
			ReadLoop readLoop = new ReadLoop(in,this);
			new Thread(readLoop).start();
		}
	}
	public MessageSerializer(OutputStream out, InputStream in, MessageListener ml){
		this(out,in);
		this.messageListeners.add(ml);
	}

	private boolean isConsuming = false;
	private void consumeMessageQueue() throws IOException{
		if(!isConsuming){
			isConsuming = true;
			while(!messageQueue.isEmpty())
			{
				messageQueue.poll().writeOut(out);
			}
			isConsuming = false;
		}
	}

	public void sendMessage(Message m) throws IOException{
		messageQueue.add(m);
		consumeMessageQueue();
	}

	public void addMessageListener(MessageListener ml){
		this.messageListeners.add(ml);
	}
	public void removeMessageListemer(MessageListener ml){
		this.messageListeners.remove(ml);
	}

	public int getPossibleKey(){
		for(int i=1;i<255;i++){
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
		int id = getPossibleKey();
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
