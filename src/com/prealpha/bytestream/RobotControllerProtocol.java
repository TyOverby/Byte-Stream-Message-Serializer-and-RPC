package com.prealpha.bytestream;
import java.io.IOException;


public class RobotControllerProtocol extends Protocol{
	public static class types{
		public static final int UNKNOWN  = 0;
		public static final int BOTH_WHEELS  = 1;
		public static final int INDIVIDUAL_WHEELS    = 2;
		public static final int SOUND = 3;
	}

	public RobotControllerProtocol(MessageSerializer serializer) {
		super(serializer);
	}

	public void sendMessageToBothWheels(int time, int speed) throws IOException{
		int command = types.BOTH_WHEELS;
		int rpcID   = 0;

		int[] payload = {time,speed};

		Message m = new Message(command,rpcID,payload);

		serializer.sendMessage(m);
	}
	public void sendForwardMessage(int time) throws IOException{
		sendMessageToBothWheels(time, 180);
	}
	public void sendBackwardMessage(int time) throws IOException{
		sendMessageToBothWheels(time, 0);
	}

	public void sendMessageToIndividualWheels(int time, int portWheelSpeed, int starboardWheelSpeed) throws IOException{
		int command = types.INDIVIDUAL_WHEELS;
		int rpcID   = 0;

		int[] payload = {time,portWheelSpeed,starboardWheelSpeed};

		Message m = new Message(command,rpcID,payload);

		serializer.sendMessage(m);
	}

	public void sendLeftMessage(int time) throws IOException{
		sendMessageToIndividualWheels(time, 0, 180);
	}
	public void sendRightMessage(int time) throws IOException{
		sendMessageToIndividualWheels(time, 180, 0);
	}

	public void sendSoundMessage(int sound) throws IOException{
		int command = types.INDIVIDUAL_WHEELS;
		int rpcID   = 0;

		int[] payload = {sound};

		Message m = new Message(command,rpcID,payload);

		serializer.sendMessage(m);
	}
}
