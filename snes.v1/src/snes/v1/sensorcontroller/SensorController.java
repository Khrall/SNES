package snes.v1.sensorcontroller;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.bitreactive.library.mqtt.MQTTMessage;

import no.ntnu.item.arctis.runtime.Block;

public class SensorController extends Block {

	private final String topic = "snes-sensor";
	private String room;
	private int door;
	
	public MQTTMessage createMessage(int numPeople) {
		JSONObject json = new JSONObject();
		
		try {
			json.put("room", room);
			json.put("door", door);
			json.put("newPerson", numPeople);
		} catch(JSONException e) {
			e.printStackTrace();
		}
		
		String jsonString = json.toString();
		byte[] bytes = jsonString.getBytes();
		MQTTMessage message = new MQTTMessage(bytes, topic);
		message.setQoS(2);
		
		return message;
	}
	
	public String makeChecksum() {
		try {
			String hashingKey = room + "rayray654";
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			byte[] bytes = messageDigest.digest(hashingKey.getBytes());
			BigInteger number = new BigInteger(1, bytes);
			String hashtext = number.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public void setParams(int[] params) {
		room = Integer.toString(params[0]);
		door = params[1];
	}

	public void stopController() {
	}

}
