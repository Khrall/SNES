package snes.v1.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import no.ntnu.item.arctis.runtime.Block;

public class Database extends Block {
	static final String USER = "frederin_snes";
	static final String PASS = "sverre";
	static final String DB_URL = "jdbc:mysql://mysql.stud.ntnu.no/frederin_snes";
	DBParams params;
	Connection conn;

	public void connectDatabase(DBParams params) {
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		this.params = params;
	}

	public int getQueryMethod() {
		int queryMethod = this.params.getAction();
		return queryMethod;
	}

	public DBResponse selectQuery() {
		String query = "SELECT `room`, SUM(count) AS 'number' FROM `roomEntry` WHERE `room`=? GROUP BY `room`;";
		RoomList roomList;
		ArrayList<RoomList> rooms = new ArrayList<RoomList>();
		DBResponse response = new DBResponse();
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, params.getRoom());
			ResultSet rs = preparedStmt.executeQuery();
			while (rs.next()) {
				roomList = new RoomList();
				roomList.setRoom(rs.getString("room"));
				System.out.println(roomList.getRoom() + "   "
						+ roomList.getNumber());
				roomList.setNumber(rs.getInt("number"));
				rooms.add(roomList);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setRoomList(rooms);
		response.setType("response");
		return response;
	}

	public void insertQuery() {
		String query = "INSERT INTO roomEntry (room, door, count)"
				+ " values (?, ?, ?)";
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, params.getRoom());
			preparedStmt.setInt(2, params.getDoor());
			preparedStmt.setInt(3, params.getUpdateCount());
			preparedStmt.execute();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public DBResponse broadcastQuery() {
		RoomList roomList;
		ArrayList<RoomList> rooms = new ArrayList<RoomList>();
		DBResponse response = new DBResponse();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String query = "SELECT room, SUM(count) AS number FROM roomEntry GROUP BY room;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				roomList = new RoomList();
				roomList.setRoom(rs.getString("room"));
				roomList.setNumber(rs.getInt("number"));
				rooms.add(roomList);
			}
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		response.setRoomList(rooms);
		response.setType("broadcast");
		return response;
	}
}
