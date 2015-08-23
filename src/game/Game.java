package game;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Game {
	
	/**
	 * Asking the user to choose the topic for hidden word to guess
	 * @return number of topic
	 */
	static int pickATopic(){
		System.out.println("[1] Bands");
		System.out.println("[2] Cars");
		System.out.println("[3] Countries");
		System.out.print("Pick a topic: ");
		int topicNumber = inputInt();
		if (topicNumber <0 || topicNumber >3){
			System.out.println("Wrong topic number, try again: ");
			pickATopic();
		}
		return topicNumber;
		
	}
	
	/**
	 * Protected input of integer
	 * @return  integer number
	 */
	static int inputInt() {
		boolean isOk = false; //loop control variable
		int userInput = 0; // variable which will store user's input
		Scanner input = new Scanner(System.in);
		//loops until user enters an integer
		while (!isOk) {
			isOk = true;
			
			try {
				userInput = input.nextInt(); //users input
				
			} catch (InputMismatchException e) { //if user enters something other than integer
				System.out.println("Wrong input, try again: ");
				input.nextLine();
				isOk = false;
			}
		}
		return userInput;
	}
	
	/**
	 * User menu for hangman game
	 */
	static void userMenu(){
		System.out.println("*************************");
		System.out.println("*\tHANGMAN \t*");
		System.out.println("*************************");
		System.out.println();
		System.out.println();
		
		System.out.println("[1] New Game ");
		System.out.println("[0] Exit");
		System.out.print("Pick an option by entering its number: ");
		int userInput = inputInt();
		
		switch(userInput){
		
		case 0: {
			System.out.println("Have a nice day.");
			System.exit(0);
		}
		case 1: {
			
			int topicNumber = pickATopic();
			Hangman game = new Hangman(topicNumber);
			game.playGame();
			break;
		}
		default: {
			System.out.println("Wrong option number.");
			userMenu();
		}
		}
		
	}
	public static void main(String[] args) {
		//open user menu
		userMenu();
	}

}