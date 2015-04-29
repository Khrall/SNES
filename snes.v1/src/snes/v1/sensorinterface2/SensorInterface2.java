package snes.v1.sensorinterface2;

import no.ntnu.item.arctis.runtime.Block;

public class SensorInterface2 extends Block {
	public int PinInPIR;
	public int PinOutPIR;
	
	private static final int THRESHOLD = 500;
	private static final int TIMETOMOVE = 1500;
	private static final int IN = 0, OUT = 1;
	
	private boolean[] motionStarted = {false, false};
	private int direction;
	private long eventStart;
	private long motionStart;
	private long lastEvent;
	public int peopleTracked;
	public boolean motionEnded;
	

	public void motionInStart() {
		
		long now = System.currentTimeMillis();
		long timeSinceLastEvent = now - lastEvent;
		
		System.out.println("");
		
		if(motionEnded) {
			// New event
			motionEnded = false;
			
		}
		
	}
	
	public void motionOutStart() {
		long delta = System.currentTimeMillis() - motionStart;
		motionStarted[OUT] = true;
		if (motionStarted[IN] && delta < THRESHOLD) {
			direction = OUT;
			eventStart = System.currentTimeMillis();
		} else {
			motionStart = System.currentTimeMillis();
		}
	}

	public void motionInEnd() {
		motionStarted[IN] = false;
		if (direction == IN) {
			long delta = System.currentTimeMillis() - eventStart;
			return 1 + (int) (delta / TIMETOMOVE);
		} else
			return 0;
	}

	public void motionOutEnd() {
		motionStarted[OUT] = false;
		if (direction == OUT) {
			long delta = System.currentTimeMillis() - eventStart;
			return -(1 + (int) (delta / TIMETOMOVE));
		} else
			return 0;
	}

	public void setParameters(int[] params) {
		PinInPIR = params[0];
		PinOutPIR = params[1];
	}
}
