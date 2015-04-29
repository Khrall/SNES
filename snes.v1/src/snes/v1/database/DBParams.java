package snes.v1.database;

public class DBParams {
	public static final int INSERT = 0, SELECT = 1, BROADCAST = 2;

	private int action;
	private String room;
	private int door;
	private int updateCount;

	public DBParams(int action) {
		this.action = action;
	}


	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public int getDoor() {
		return door;
	}

	public void setDoor(int door) {
		this.door = door;
	}

	public int getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	public static int getInsert() {
		return INSERT;
	}

	public static int getBroadcast() {
		return BROADCAST;
	}

	public static int getSelect() {
		return SELECT;
	}
}
