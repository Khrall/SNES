package snes.v1.usercontroller;

import java.util.Scanner;

import no.ntnu.item.arctis.runtime.Block;

public class UserController extends Block {
	private String command;

	public void userInput() {
		System.out.println("Enter roomname: ");
		Scanner scanner = new Scanner(System.in);
		command = scanner.next();
	}
	

}
