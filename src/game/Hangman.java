package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
/**
 * Hangman game

 * User is trying to find the hidden word by guessing it's letters 
 * If he make 6 wrong guesses, he is hanged and he lost
 * If he manages to guess all letters of the word, he won
 * @author Nikola Lisicic
 *
 */
public class Hangman {
	
	//all hidden words from topic Bands
	private static final String[] BANDS = { "RISE AGAINST", "SYSTEM OF A DOWN",
			"KORN", "KILLSWITCH ENGAGE", "AEROSMITH", "ALICE COOPER",
			"METALLICA", "DEEP PURPLE", "BLACK SABBATH", "KISS",
			"LED ZEPPELIN", "RAGE AGAINST THE MACHINE", "IN FLAMES",
			"APOCALYPTICA", "FEAR FACTORY", "GODSMACK", "HIM", "MUDVAYNE",
			"PANTERA", "SLAYER", "RAMMSTEIN", "RED HOT CHILI PEPPERS",
			"SEPULTURA" };
	
	//all hidden words from topic Cars
	private static final String[] CARS = { "AUDI", "ASTON MARTIN",
			"ALFA ROMEO", "BENTLY", "BMW", "BUGATTI", "CADILLAC", "CITROEN",
			"DODGE", "DACIA", "FERRARI", "FIAT", "FORD", "HONDA", "JAGUAR",
			"KIA", "LADA", "LEXUS", "LAMBORGHINI", "MAZDA", "MERCEDES",
			"MASERATI", "MITSUBISHI", "NISSAN", "OPEL", "PEUGEOT", "PORSCHE",
			"RENAULT", "SAAB", "TESLA", "TOYOTA", "VOLKSWAGEN", "VOLVO" };

	//all hidden words from topic Countries
	private static final String[] COUNTRIES = { "ARGENTINA", "AUSTRIA",
			"AUSTRALIA", "BELGIUM", "BRAZIL", "BOSNIA AND HERZEGOVINA",
			"CROATIA", "CANADA", "DENMARK", "EGYPT", "FRANCE", "FINLAND",
			"GERMANY", "GREECE", "HUNGARY", "IRELAND", "ITALY", "JAPAN",
			"LATVIA", "MONTENEGRO", "NETHERLANDS", "PORTUGAL", "RUSSIA",
			"SERBIA", "SLOVENIA", "SWEDEN", "UKRAINE", "UNITED KINGDOM",
			"VIETNAM", "ZIMBABWE" };

	private String secretWord; //word or words for user to guess
	private int secretWordLength; //length of the word or words
	private char[] boardWord; //showing underscores or guessed letters to the user
	private ArrayList<Character> correctGuesses; //list of guessed chars
	private ArrayList<Character> incorrectGuesses; //list of wrong chars
	private Scanner input = new Scanner(System.in);
	private char userGuess = ' '; //current guess character

	/**
	 * Default constructor which creates a new game 
	 * Secret word is picked by randomly generating an index of the array of band names
	 */
	Hangman() {
		Random rand = new Random();
		int randomNumber = rand.nextInt(BANDS.length); //random number from 0 to length of the BANDS array
		secretWord = BANDS[randomNumber]; //name of the band on randomly generated index
		secretWordLength = secretWord.length(); //length of the word/words
		boardWord = new char[secretWordLength];
		correctGuesses = new ArrayList<Character>();
		incorrectGuesses = new ArrayList<Character>();
	}

	/**
	 * Constructor which creates new game based on the selected topic
	 * @param topicNumber  number of topic which the user wants to guess the word/s from
	 */
	Hangman(int topicNumber) {
		String[] topic;
		
		//choosing a topic based on users selection
		if (topicNumber == 1) {
			topic = BANDS;
		} else if (topicNumber == 2) {
			topic = CARS;
		} else {
			topic = COUNTRIES;
		}
		Random rand = new Random();
		int randomNumber = rand.nextInt(topic.length); //random index
		secretWord = topic[randomNumber]; //word on random index of selected topic
		secretWordLength = secretWord.length();
		boardWord = new char[secretWordLength];
		correctGuesses = new ArrayList<Character>();
		incorrectGuesses = new ArrayList<Character>();
	}

	/**
	 * Initiate the hidden word with underscores ("_") in places of letters, 
	 * and spaces on the same places as the spaces in words
	 */
	void initiateBoardWord() {
		for (int i = 0; i < secretWordLength; i++) {
			if (secretWord.charAt(i) == ' ') {
				boardWord[i] = ' ';
			} else {
				boardWord[i] = '_';
			}
		}

	}

	/**
	 * Printing hidden word/s with underscores in places of non-guessed letters, and guessed letters on their positions
	 */
	void printBoardWord() {
		System.out.println("\n");
		for (int i = 0; i < boardWord.length; i++) {
			System.out.print(boardWord[i] + " ");
		}
	}

	/**
	 * Getting a character input from user and seting the current char userGuess on it's value
	 * if user enters anything other then a letter, the program will ask him to try again
	 * if entered char is already enter, it will also ask him to try another char
	 * if entered char is lowercase it will be converted to upper case, because the hidden words are in all-caps
	 */
	void getUsersGuess() {
		System.out.print("\nEnter a character: ");
		userGuess = ' ';
		char guess = ' ';
		boolean isOk = false;
		//loops until the user enters a letter that isnt already entered
		while (!isOk) {
			isOk = true;
			guess = input.next().charAt(0); //geting a char from users keyboard
			
			//if entered char isnt a letter, or its already entered, run the loop again
			if (!Character.isLetter(guess)
					|| incorrectGuesses.contains(Character.toUpperCase(guess))
					|| correctGuesses.contains(Character.toUpperCase(guess))) {
				isOk = false;
				guess = ' ';
				System.out.println("Try again: ");
			} else {
				guess = Character.toUpperCase(guess);
			}
		}
		//setting current letter to the letter user entered
		this.userGuess = guess;
	}

	/**
	 * Checking if the user won
	 * if all letters on the displayed word are the same and at the same position as the letters in the hidden word/s
	 * then user has won
	 * @return  true, if user has won, otherwise false
	 */
	boolean isUserAWinner() {
		for (int i = 0; i < secretWordLength; i++) {
			if (boardWord[i] != secretWord.charAt(i)) {
				return false;
			}
		}
		return true;

	}
	
	/**
	 * checking is the game over
	 * The game is over if the user is "hanged", that is if he missed 6 guesses
	 * @return  true if it is, otherwise false
	 */
	boolean gameOver() {
		if (incorrectGuesses.size() == 6) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Checking is the current entered letter in the hidden word/s
	 * @return true if it is, otherwise false
	 */
	boolean isCorrectGuess() {
		if (secretWord.contains(userGuess + "")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Marking a correctly guessed letter by finding all positions on which it appears in the hidden word,
	 * and adding that letters on the same possitions on the displayed word's letters
	 */
	void markAHit() {
		System.out.println("Good guess!");
		for (int i = 0; i < secretWordLength; i++) {
			if (this.userGuess == secretWord.charAt(i)) {
				boardWord[i] = userGuess;
				correctGuesses.add(userGuess);
			}
		}
		userGuess = ' ';// reseting userGuess

	}

	/**
	 * If entered letter isnt in the hidden word, add that letter to the list of missed letters
	 */
	void markAMiss() {
		System.out.println("Bad guess!");
		incorrectGuesses.add(userGuess);
		userGuess = ' ';// reseting userGuess

	}

	/**
	 * Printing all letters which are entered and which arent in the hidden word/s
	 */
	void printWrongGuesses() {
		if (incorrectGuesses.size() > 0) {
			System.out.println();
			System.out.println("Bad guesses: ");
			for (int i = 0; i < incorrectGuesses.size(); i++) {
				System.out.print(incorrectGuesses.get(i) + " ");
			}
		}
	}

	/**
	 * Printing a message that user has won
	 */
	void userWon() {
		System.out.println("\nCongratulations, you won!");
		System.out.println("----------------------------");
		
	}

	/**
	 * Printing a message that user has lost
	 * and what is the hidden word/s
	 */
	void userLost() {
		System.out.println("\nYou are hanged!");
		System.out.println("The answer is: " + secretWord);
		System.out.println("------------------------------");
	}

	/**
	 * Printing a figure of the hangman depending on the number of missed guesses
	 * if user has one missed guess then it prints its head,
	 * if he has two then it print head and left arm etc..
	 */
	void printHangman() {
		int numberOfWrongGuesses = incorrectGuesses.size();
		System.out.println("_______");
		System.out.println("|     |");
		if (numberOfWrongGuesses >= 1) {
			System.out.println("|     O");
		} else {
			System.out.println("|");
		}
		if (numberOfWrongGuesses >= 2) {
			if (numberOfWrongGuesses == 2) {
				System.out.println("|     |");
			} else if (numberOfWrongGuesses == 3) {
				System.out.println("|    /|");
			} else if (numberOfWrongGuesses >= 4) {
				System.out.println("|    /|\\");
			}

		} else {
			System.out.println("|");
		}
		if (numberOfWrongGuesses == 5) {
			System.out.println("|    /");
		} else if (numberOfWrongGuesses == 6) {
			System.out.println("|    / \\");
		} else {
			System.out.println("|");
		}
		System.out.println("|");
		System.out.println("|");

	}

	/**
	 * The whole gameplay
	 */
	void playGame() {
		printHangman(); //printing just the pole 
		initiateBoardWord(); //fill board word with uderscores
		printBoardWord(); //print the display word with underscores in places of letters
		while (!gameOver()) { 
			printWrongGuesses(); //printing the list of wrongly guessed letters
			getUsersGuess(); //user inputs a letter
			if (isCorrectGuess()) { //if user guessed the right letter
				markAHit(); //mark all places where that letter appears
			} else {
				markAMiss(); //if user missed a letter, add that letter to a list of wrong guesses
			}
			printHangman(); //print a figure of a hangman
			printBoardWord(); //print a display word
			if (isUserAWinner()) { //if user won print a message
				userWon();
				break;
			}
		}
		if (gameOver()) { //if the game is over, print the message
			userLost();
		}
		
		//back to the user manu
		System.out.println("[0] Back to the user menu");
		int endInput = input.nextInt();
		if(endInput == 0){
			Game.userMenu();
		}
		else {
			System.exit(0);
		}
	}
}
