package snes.v1.database;

import java.util.ArrayList;

public class DBResponse {
	private ArrayList<RoomList> roomList;
	private String type;
	public ArrayList<RoomList> getRoomList() {
		return roomList;
	}
	public void setRoomList(ArrayList<RoomList> roomList) {
		this.roomList = roomList;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
