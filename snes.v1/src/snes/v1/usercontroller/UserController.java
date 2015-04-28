package snes.v1.usercontroller;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bitreactive.library.mqtt.MQTTMessage;

import no.ntnu.item.arctis.runtime.Block;

public class UserController extends Block {
	JSONArray data;
	String type;
	public boolean running;
	private Console console;

	class Console extends Thread {
		private Scanner scanner;
		private int tokens;

		public Console() {
			tokens = 0;
			scanner = new Scanner(System.in);
		}

		@Override
		public void run() {
			while (true) {
				try {
					if (tokens > 0) {
						System.out.println("Enter room name: ");
						String command = scanner.next();
						sendToBlock("INPUT", command);
						tokens--;
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		public void addToken() {
			tokens++;
		}
	}

	public void userInput() {
		if(console == null) {
			console = new Console();
			console.start();
		}
		
		console.addToken();
	}

	public MQTTMessage createJSON(String command) {
		JSONObject obj = new JSONObject();
		try {
			obj.put("room", command);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		String jsonText = obj.toString();
		byte[] bytes = jsonText.getBytes();
		String topic = "snes-user";
		MQTTMessage message = new MQTTMessage(bytes, topic);
		message.setQoS(2);
		return message;
	}

	public String checkJSON(MQTTMessage m) {
		String payload = new String(m.getPayload());
		try {
			JSONObject jsonObj = new JSONObject(payload);
			if (jsonObj.has("type")) {
				type = jsonObj.getString("type");
				data = jsonObj.getJSONArray("data");
			} else {
				type = "notforme";
				return type;
			}

		} catch (JSONException e) {
			e.printStackTrace();

		}
		return type;
	}

	public void printBroadcast() {
		System.out.println("----------------------");
		System.out.println("-------Show all-------");
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject jsondata = data.getJSONObject(i);
				String room = jsondata.getString("room");
				System.out.println("Room: " + room);

				int number = jsondata.getInt("number");
				System.out.println("Number: " + number + "\n");
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		System.out.println("----------------------");
	}

	public void printResponse() {
		System.out.println("----------------------");
		System.out.println("-----Show response----");
		for (int i = 0; i < data.length(); i++) {
			try {
				JSONObject jsondata = data.getJSONObject(i);
				String room = jsondata.getString("room");
				System.out.println("Room: " + room);

				int number = jsondata.getInt("number");
				System.out.println("Number: " + number + "\n");
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
		System.out.println("----------------------");
	}

}
