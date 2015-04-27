package snes.v1.sensorinterface2;

import no.ntnu.item.arctis.runtime.Block;

public class SensorInterface2 extends Block {
	public int PinInPIR;
	public int PinOutPIR;
	
	private static final int THRESHOLD = 500;
	private static final int TIMETOMOVE = 1500;
	private static final int IN = 0, OUT = 1;
	private int direction;
	private boolean motion[] = { false, false };
	/** 
	 * Motion start: Tells the time for when enter/exit was registered by in/out PIR respectively. This is used in order to make sure two separate motions accidentally trigger an enter/exit event. Event start: Tells the time for when enter/exit was registered by out/in PIR respectively. This is used in order to find out how long the event lasted, and might be helpful to calculate how many persons entered/exited the room.
	 */
	private long motionStart = System.currentTimeMillis();
	private long eventStart;

	public void motionInStart() {
		long delta = System.currentTimeMillis() - motionStart;
		motion[IN] = true;
		if (motion[OUT] && delta < THRESHOLD) {
			direction = IN;
			eventStart = System.currentTimeMillis();
		} else {
			motionStart = System.currentTimeMillis();
		}
	}

	public int motionInEnd() {
		motion[IN] = false;
		if (direction == IN) {
			long delta = System.currentTimeMillis() - eventStart;
			return 1 + (int) (delta / TIMETOMOVE);
		} else
			return 0;
	}

	public void motionOutStart() {
		long delta = System.currentTimeMillis() - motionStart;
		motion[OUT] = true;
		if (motion[IN] && delta < THRESHOLD) {
			direction = OUT;
			eventStart = System.currentTimeMillis();
		} else {
			motionStart = System.currentTimeMillis();
		}
	}

	public int motionOutEnd() {
		motion[OUT] = false;
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
