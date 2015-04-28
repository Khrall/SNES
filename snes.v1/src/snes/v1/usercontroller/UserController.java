package snes.v1.usercontroller;

import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bitreactive.library.mqtt.MQTTMessage;

import no.ntnu.item.arctis.runtime.Block;

public class UserController extends Block {
	JSONArray data;
	String type, command;

	public void userInput() {
		System.out.println("Enter room name: ");
		try (Scanner scanner = new Scanner(System.in)) {
			command = scanner.next();
		}
	}

	public MQTTMessage createJSON() {
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
		System.out.println("---------- Received Message ----------");
		System.out.println("Sent to topic: " + m.getTopic());
		System.out.println("Payload: " + new String(m.getPayload()));
		System.out.println("--------------------------------------");
		
		JSONObject jsonObj = new JSONObject(m);
		try {
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
	}

	public void printResponse() {
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
	}

}
