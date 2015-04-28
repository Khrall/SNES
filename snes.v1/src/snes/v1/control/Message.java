package snes.v1.control;

public class Message {
	private String payload;
	public static final int SENSOR = 0;
	public static final int USER = 1;
	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public int getChannel() {
		return channel;
	}

	public void setChannel(int channel) {
		this.channel = channel;
	}



	private int channel;
	
	public void Message(String payload, int channel){
		this.payload= payload;
		this.channel = channel;
	}
}
