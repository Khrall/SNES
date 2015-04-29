package snes.v1.sensorunit.component;

import java.util.Scanner;

import com.bitreactive.library.mqtt.MQTTConfigParam;
import com.bitreactive.library.mqtt.MQTTMessage;
import com.bitreactive.library.mqtt.robustmqtt.RobustMQTT.Parameters;

import no.ntnu.item.arctis.runtime.Block;

public class Component extends Block {
	
	public int[] initController() {
		int[] params = new int[2];
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter room number: ");
		params[0] = scanner.nextInt();
		System.out.println("Enter door number: ");
		params[1] = scanner.nextInt();
		
		return params;
	}

	public Parameters initParams() {
		MQTTConfigParam m = new MQTTConfigParam("dev.bitreactive.com");
		m.addSubscribeTopic("snes");
		Parameters p = new Parameters(m);
		return p;
	}

	public int logNewEvent(int event) {
		System.out.println("New event from interface, "+event+" people entered.");
		return event;
	}

	public MQTTMessage logNewMessage(MQTTMessage message) {
		System.out.println("Sending message:");
		System.out.println(new String(message.getPayload()));
		return message;
	}

	public int[] initSensor() {
		int[] params = new int[2];
		System.out.println("Enter GPIO Pin for IN sensor: ");
		Scanner scanner = new Scanner(System.in);
		params[0] = scanner.nextInt();
		System.out.println("Enter GPIO Pin for OUT sensor: ");
		params[1] = scanner.nextInt();
		return params;
	}

}
