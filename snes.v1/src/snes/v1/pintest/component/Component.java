package snes.v1.pintest.component;

import java.util.Scanner;

import no.ntnu.item.arctis.runtime.Block;

public class Component extends Block {

	public int initPin() {
		
		System.out.println("Choose GPIO pin");
		Scanner scanner = new Scanner(System.in);
		int pin = scanner.nextInt();
		System.out.println("Chose: "+pin);
		return pin;
	}

	public void isHigh() {
		System.out.println("420 blaze");
	}
	
	public void isLow() {
		System.out.println("No go");
	}
	
	public void printError(String error) {
		System.out.println("Got error: ");
		System.out.println(error);
	}
	
	public void printOk() {
		System.out.println("Was initialized b-u-t-4");
	}

}
