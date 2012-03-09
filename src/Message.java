import java.nio.ByteBuffer;



public class Message {
	
	public final int type;
	public int id;
	public int length;
	public final byte[] payload;
	
	public Message(int type, int id, byte... payload) throws IllegalArgumentException{
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
	
	public byte[] toBytes(){
		final int intSize = Integer.SIZE/8;
		
		final int messageSize = intSize*3+length;
		
		ByteBuffer bb = ByteBuffer.allocate(messageSize);
		
		bb.putInt(type);
		bb.putInt(id);
		bb.putInt(length);
		bb.put(payload);
		
		return bb.array();
	}
}
