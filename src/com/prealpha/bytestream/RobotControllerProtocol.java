package com.prealpha.bytestream;
import java.io.IOException;


public class RobotControllerProtocol extends Protocol{
	public static class types{
		public static final int UNKNOWN  = 0;
		public static final int BOTH_WHEELS  = 1;
		public static final int INDIVIDUAL_WHEELS    = 2;
	}

	public RobotControllerProtocol(MessageSerializer serializer) {
		super(serializer);
	}

	public void sendMessageToBothWheels(byte time, byte speed) throws IOException{
		byte command = types.BOTH_WHEELS;
		byte rpcID   = 0;

		byte[] payload = {time,speed};

		Message m = new Message(command,rpcID,payload);

		serializer.sendMessage(m);
	}
	public void sendForwardMessage(byte time) throws IOException{
		sendMessageToBothWheels(time, (byte)180);
	}
	public void sendBackwardMessage(byte time) throws IOException{
		sendMessageToBothWheels(time,(byte) 0);
	}
	
	public void sendMessageToIndividualWheels(byte time, byte portWheelSpeed, byte starboardWheelSpeed) throws IOException{
		byte command = types.INDIVIDUAL_WHEELS;
		byte rpcID   = 0;

		byte[] payload = {time,portWheelSpeed,starboardWheelSpeed};

		Message m = new Message(command,rpcID,payload);

		serializer.sendMessage(m);
	}
	
	public void sendLeftMessage(byte time) throws IOException{
		sendMessageToIndividualWheels(time, (byte)0, (byte)180);
	}
	public void sendRightMessage(byte time) throws IOException{
		sendMessageToIndividualWheels(time, (byte)180, (byte)0);
	}

}
