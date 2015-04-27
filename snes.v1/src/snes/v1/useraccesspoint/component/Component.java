package snes.v1.useraccesspoint.component;

import java.util.Scanner;

import com.bitreactive.library.mqtt.MQTTConfigParam;
import com.bitreactive.library.mqtt.MQTTMessage;
import com.bitreactive.library.mqtt.robustmqtt.RobustMQTT.Parameters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import no.ntnu.item.arctis.runtime.Block;

public class Component extends Block {
	String room;
	int number;

	public Parameters initParams() {
		MQTTConfigParam m = new MQTTConfigParam("dev.bitreactive.com");
		m.addSubscribeTopic("snes-user");
		Parameters p = new Parameters(m);
		return p;
	}

	public void PrintMessage(MQTTMessage m) {
		String payload = new String(m.getPayload());
		JSONObject jsonObj;
		JSONArray jsonArr;
		
		System.out.println("---------- Received Message ----------");
		try {
			jsonArr = new JSONArray(payload);
			
			for (int i = 0; i < jsonArr.length(); i++) {
				jsonObj = jsonArr.getJSONObject(i);
				room = jsonObj.getString("room");
				System.out.println("Room: " + room);

				number = jsonObj.getInt("number");
				System.out.println("Number: " + number + "\n");
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
		System.out.println("--------------------------------------");
		
	}

	public int getInt1(){
		System.out.println("Enter pinin: ");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}
	public int getInt2(){
		System.out.println("Enter pinout: ");
		Scanner scanner = new Scanner(System.in);
		return scanner.nextInt();
	}

}