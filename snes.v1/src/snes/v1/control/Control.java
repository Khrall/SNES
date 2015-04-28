package snes.v1.control;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import snes.databasenew.DBParams;
import snes.databasenew.DBResponse;
import snes.databasenew.RoomList;
import com.bitreactive.library.mqtt.MQTTMessage;
import no.ntnu.item.arctis.runtime.Block;

public class Control extends Block {
	String payload = null;
	private String room;
	private int door;
	private int countUpdate;
	public boolean running;

	public int saveMessage(Message m) {
		this.payload = m.getPayload();
		try {
			JSONObject json = new JSONObject(this.payload);
			json.get("type");
			m.setChannel(-1);
			System.out.println("Got from myself");
		} catch (JSONException e) {
			System.out.println("Got from Sverre");
		}
		return m.getChannel();
	}

	public DBParams createInsertQuery() {
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(this.payload);
			this.door = jsonObj.getInt("door");
			this.room = jsonObj.getString("room");
			this.countUpdate = jsonObj.getInt("newPerson");
			DBParams params = new DBParams(DBParams.INSERT);
			params.setRoom(room);
			params.setDoor(door);
			params.setUpdateCount(countUpdate);
			return params;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	public DBParams createSelectQuery() {
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(this.payload);
			this.room = jsonObj.getString("room");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		DBParams params = new DBParams(DBParams.SELECT);
		params.setRoom(room);
		return params;
	}

	public void setRunningTrue() {
		this.running = true;
	}

	public void setRunningFalse() {
		this.running = false;
	}

	public DBParams createBroadcastQuery() {
		DBParams params = new DBParams(DBParams.BROADCAST);
		return params;
	}

	public boolean getRunning() {
		return this.running;
	}

	public Message createResponseMessage(DBResponse response) {
		JSONObject finalObject = new JSONObject();
		JSONArray records = new JSONArray();
		Message message = new Message();
		try {
			ArrayList<RoomList> rooms = response.getRoomList();
			for (RoomList room : rooms) {
				JSONObject obj = new JSONObject();
				obj.put("room", room.getRoom());
				obj.put("number", room.getNumber());
				records.put(obj);
			}
			finalObject.put("type", response.getType());
			finalObject.put("data", records);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		message.setPayload(finalObject.toString());
		return message;
	}

	public Control() {
	}
}
