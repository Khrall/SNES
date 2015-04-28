package snes.v1.pintest.component;

import java.util.Scanner;

import no.ntnu.item.arctis.runtime.Block;

public class Component extends Block {

	long lastEvent;
	
	public int initPin() {
		
		System.out.println("Choose GPIO pin");
		Scanner scanner = new Scanner(System.in);
		int pin = scanner.nextInt();
		System.out.println("Chose: "+pin);
		lastEvent = System.currentTimeMillis();
		return pin;
	}

	public void isHigh() {
		long now = System.currentTimeMillis();
		int delta = (int) (now - lastEvent);
		lastEvent = now;
		System.out.println("420 blaze //time: "+delta+"ms");
	}
	
	public void isLow() {
		long now = System.currentTimeMillis();
		int delta = (int) (now - lastEvent);
		lastEvent = now;
		System.out.println("No go nigga //time: "+delta+"ms");
	}
	
	public void printError(String error) {
		System.out.println("Got error: ");
		System.out.println(error);
	}
	
	public void printOk() {
		System.out.println("Was initialized b-u-t-4");
	}

}
