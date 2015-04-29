package snes.v1.mqtt;

import snes.control.Message;
import com.bitreactive.library.mqtt.MQTTConfigParam;
import com.bitreactive.library.mqtt.MQTTMessage;
import com.bitreactive.library.mqtt.robustmqtt.RobustMQTT.Parameters;
import no.ntnu.item.arctis.runtime.Block;

public class MQTT extends Block {
	String payload = null;

	public Message createSensorMessage(MQTTMessage m) {
		String payload = new String(m.getPayload());
		Message message = new Message();
		message.Message(payload, Message.SENSOR);
		return message;
	}

	public Message createUserMessage(MQTTMessage m) {
		String payload = new String(m.getPayload());
		System.out.println(payload);
		Message message = new Message();
		message.Message(payload, Message.USER);
		return message;
	}

	public Parameters setupUser() {
		MQTTConfigParam m = new MQTTConfigParam("dev.bitreactive.com");
		m.addSubscribeTopic("snes-user");
		Parameters p = new Parameters(m);
		return p;
	}

	public Parameters setupSensor() {
		MQTTConfigParam m = new MQTTConfigParam("dev.bitreactive.com");
		m.addSubscribeTopic("snes-sensor");
		Parameters p = new Parameters(m);
		return p;
	}

	public MQTTMessage createUserMQTTMessage(Message m) {
		byte[] bytes = m.getPayload().getBytes();
		String topic = "snes-user";
		MQTTMessage message = new MQTTMessage(bytes, topic);
		message.setQoS(2);
		return message;
	}

	public int saveMessage(Message m) {
		this.payload = m.getPayload();
		return m.getChannel();
	}
}
