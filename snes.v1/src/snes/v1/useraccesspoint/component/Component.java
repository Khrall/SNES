package snes.v1.useraccesspoint.component;

import com.bitreactive.library.mqtt.MQTTConfigParam;
import com.bitreactive.library.mqtt.MQTTMessage;
import com.bitreactive.library.mqtt.robustmqtt.RobustMQTT.Parameters;

import org.json.JSONException;
import org.json.JSONObject;

import no.ntnu.item.arctis.runtime.Block;

public class Component extends Block {

	public Parameters initParams() {
		MQTTConfigParam m = new MQTTConfigParam("dev.bitreactive.com");
		m.addSubscribeTopic("snes-user");
		Parameters p = new Parameters(m);
		return p;
	}

	public void PrintMessage(MQTTMessage m) {
		String payload = new String(m.getPayload());
		JSONObject jsonObj;
		String room = "";
		int number = 0;
		try {
			jsonObj = new JSONObject(payload);
			room = jsonObj.getString("room");
			number = Integer.parseInt(jsonObj.getString("number"));
		} catch (JSONException e) {

			e.printStackTrace();
		}

		System.out.println("---------- Received Message ----------");
		System.out.println("Sent to topic: " + m.getTopic());
		System.out.println("Payload: " + new String(m.getPayload()));
		System.out.println("Room: " + room);
		System.out.println("Door: " + number);
		System.out.println("--------------------------------------");
	}

}
