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
}
