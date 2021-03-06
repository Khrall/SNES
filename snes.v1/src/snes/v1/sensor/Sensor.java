package snes.v1.sensor;

import no.ntnu.item.arctis.runtime.Block;

public class Sensor extends Block {

	private boolean motion = false;
	private long eventStart;
	
	public int motionDone() {
		if(motion) {
			long delta = System.currentTimeMillis() - eventStart;
			int numPeople = 1 + (int)(delta/5000);
			motion = false;
			return numPeople;
		}
		
		return 0;
	}

	public void motionStart() {
		if(!motion) {
			eventStart = System.currentTimeMillis();
			motion = true;
		}
	}

}
