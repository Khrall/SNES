package snes.v1.sensorinterface2;

import no.ntnu.item.arctis.runtime.Block;

public class SensorInterface2 extends Block {
	public int PinInPIR;
	public int PinOutPIR;


	private static final int // Event
	IN_LOW = 0,
	IN_HIGH = 1,
	OUT_LOW = 2,
	OUT_HIGH = 3;
	
	private static final int // States	
	IDLE = 0, 
	ALERT_OUT = 1, 
	ALERT_IN = 2, 
	MOVING_OUT = 3, 
	MOVING_IN = 4,
	STOPPING_OUT = 5,
	STOPPING_IN = 6,
	STOPPING = 7;
	
	private static final String[] STATE = {
		"IDLE",
		"ALERT_OUT",
		"ALERT_IN",
		"MOVING_OUT",
		"MOVING_IN",
		"STOPPING_OUT",
		"STOPPING_IN",
		"STOPPING"
	};
	
	private int state = IDLE;
	private int oldState = IDLE;

	public int peopleTracked = 0;
	public boolean motionEnded = false;

	public void motionInStart() {
		changeState(IN_HIGH);
	}

	public void motionInEnd() {
		changeState(IN_LOW);
	}

	public void motionOutStart() {
		changeState(OUT_HIGH);
	}

	public void motionOutEnd() {
		changeState(OUT_LOW);
	}
	
	private void changeState(int event) {
		switch(state) {
			case IDLE:
				if(event == IN_HIGH){
					setState(ALERT_OUT);
				}
				if(event == OUT_HIGH){
					setState(ALERT_IN);			
				}
				break;
			case ALERT_OUT:
				if(event == OUT_HIGH){
					setState(MOVING_OUT);
				}
				if(event == IN_LOW){
					setState(IDLE);
				}
				break;
			case ALERT_IN:
				if(event == IN_HIGH){
					setState(MOVING_IN);
				}
				if(event == OUT_LOW){
					setState(IDLE);
				}
				break;
			case MOVING_OUT:
				if(event == OUT_LOW){
					setState(STOPPING_OUT);
				}
				if(event == IN_LOW){
					setState(STOPPING_IN);
				}
				break;
			case MOVING_IN:
				if(event == OUT_LOW){
					setState(STOPPING_OUT);
				}
				if(event == IN_LOW){
					setState(STOPPING_IN);
				}
				break;
			case STOPPING_OUT:
				if(event == IN_LOW){
					setState(IDLE);
				}
				if(event == OUT_HIGH){
					setState(STOPPING);
				}
				break;
			case STOPPING_IN:
				if(event == OUT_LOW){
					setState(IDLE);
				}
				if(event == IN_HIGH){
					setState(STOPPING);
				}
				break;
			case STOPPING:
				if(event == OUT_LOW){
					setState(STOPPING_OUT);
				}
				if(event == IN_LOW){
					setState(STOPPING_IN);
				}
				break;
		}
	}
	
	private void setState(int newState) {
		oldState = state;
		state = newState;
		System.out.println("CHANGED STATE: "+STATE[oldState]+" -> "+STATE[newState]);
		
		if(newState == MOVING_OUT) peopleTracked = -1;
		if(newState == MOVING_IN) peopleTracked = 1;
		if((oldState  == STOPPING_OUT || oldState == STOPPING_IN) && newState == IDLE) motionEnded = true;
		if(oldState == IDLE) motionEnded = false;
	}

	public void setParameters(int[] params) {
		PinInPIR = params[0];
		PinOutPIR = params[1];
	}
}
