package snes.v1.sensorcontroller;

import java.util.ArrayList;

import no.ntnu.item.arctis.runtime.Block;

public class SensorController extends Block {

	private ArrayList<Event> bufferedEvents;
	private long startTime = System.currentTimeMillis();
	private int hour;
	private boolean newHour;
	
	public void updateTime() {
		long delta = System.currentTimeMillis() - startTime;
		int now = (int) delta / (1000 * 60 * 60);
		newHour = now > hour;
		hour = now;
	}
	
	
	
	public String getBufferedEvents() {
		// Create JSONArray
		for(int i = 0; i < bufferedEvents.size(); i++) {
			// Get JSONObject
			// Append to JSONArray
		}
		
		return null; // JSONArray.toString();
	}
	
	public Event saveEvent(int numPeople) {
		long time = System.currentTimeMillis();
		Event newEvent = new Event(numPeople, time);
		bufferedEvents.add(newEvent);
		return newEvent;
	}
	
	public String toJSONObject(Event e) {
		// Create JSONObject
		// put('num', getNumPeople());
		// put('time', getTime());
		return null;
	}
	
	public String toJSONString(Event e) {
		return toJSONObject(e); // toString
	}
	
	class Event {
		private int numPeople;
		private long time;
		
		public Event(int numPeople, long time) {
			this.numPeople = numPeople;
			this.time = time;
		}
		
		public int getNumPeople() { return numPeople; }
		public long getTime() { return time; }
	}

}
