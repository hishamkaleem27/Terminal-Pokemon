//Import Statements
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
public class GameMain {
	public static void main(String[] args) throws Exception {
		Prereq();
	}
	//Prerequisite method (asks for user information before proceeding)
	public static void Prereq() throws Exception {
		Scanner input = new Scanner(System.in);
		//Reads lines from intro.txt, receives user input for name
		System.out.println(Files.readAllLines(Paths.get("intro.txt")).get(0));
		String name = input.next();
		System.out.println("\n" + Files.readAllLines(Paths.get("intro.txt")).get(1));
		//Verifies users age and eligibility to play
		int age = input.nextInt();
		if (age <= 10) {
			System.out.println("\n" + Files.readAllLines(Paths.get("intro.txt")).get(2));
		}
		else {
			//Proceeds to game introduction
			Intro(name);
		}
	}
	//Intro metho
	public static void Intro(String name) throws Exception{
		//Array-List to store user's Pokemon's as the game progresses (as size is not predetermined)
		ArrayList<pokemon> userList = new ArrayList<pokemon>();
		Scanner input2 = new Scanner(System.in);
		//Reads statements from main.txt
		System.out.println("\nWelcome, trainer " + String.format("%s",name) + ", " + Files.readAllLines(Paths.get("main.txt")).get(0));
		//For-loop to read specified statements from main.txt
		for (int i = 1; i < 7; i++) {
			System.out.println(Files.readAllLines(Paths.get("main.txt")).get(i));
		}
		System.out.println("." + "\n." + "\n.");
		//For-loop to read specified statements from main.txt
		for (int x = 7; x < 9; x++) {
			System.out.println("\n" + Files.readAllLines(Paths.get("main.txt")).get(x));
		}
		//Creates randomized array list containing Pokemon for user to select and add to user array list
		ArrayList<pokemon> intropoke = randomizer(5, 183, 254);
		System.out.println();
		//Uses .statRead() method in Pokemon class to read Pokemon stats (for user selection info)
		for (int j = 0; j < intropoke.size(); j++) {
			System.out.println("\n" + intropoke.get(j).statRead());
		}
		System.out.println(("\n" + Files.readAllLines(Paths.get("main.txt")).get(9)));
		String introselect = input2.next();
		//Determines which Pokemon the user selected and adds it to the user array list 
		for (int y = 0; y < intropoke.size(); y++) {
			if (introselect.equals(intropoke.get(y).poke_name())) {
				userList.add(intropoke.get(y));
			}
		}
		//Statements from main.txt, proceeds to main game (dojo method)
		System.out.println("\nAh, " + userList.get(0).poke_name() + Files.readAllLines(Paths.get("main.txt")).get(10));
		System.out.println("\nEnter 1 to continue:");
		int ans1 = input2.nextInt();
		if (ans1 == 1) {
			//Invokes dojo method with corresponding parameters
			dojo(name,userList,0,1);
		}
	}
	//Randomizer method (returns array list with a specified number of random Pokemons)
	public static ArrayList<pokemon> randomizer(int num, int range_low, int range_high) throws Exception{
		//Initializing return array list
		ArrayList<pokemon> randomlist = new ArrayList<pokemon>();
		//ArrayList to compile generated indices to avoid repetition
		ArrayList<Integer> select = new ArrayList<Integer>();
		//Generates specified number of random indices from a range of values (depending on tier specified)
		for (int i = 0; i < num; i++) {
			int ind = ThreadLocalRandom.current().nextInt(range_low, range_high);
			//Determines whether index has been previously selected, repeats generation if so
			while (select.contains(ind)) {
				ind = ThreadLocalRandom.current().nextInt(range_low, range_high);
			}
			select.add(ind);
			String ind_line = null;
			//Reads line on index ind from pokebase.txt, assigns value of line to ind_line
			try {
				FileReader file = new FileReader("pokebase.txt");
			    BufferedReader buffer = new BufferedReader(file);
			    for (int x = 0; x < ind + 1; x++) {
			    	if (x == ind) {
			    		ind_line = buffer.readLine();
			        } else
			          buffer.readLine();
			      }
			    } catch (IOException e) {
			      e.printStackTrace();
			}
			// Splits elements in ind_line and assigns them to statArr
			String [] statArr = ind_line.split(",");
			//Assigns each value in statArr to the corresponding attribute to generate Pokemon
			String name = statArr[0];
			//Converts String type to Integer type
			int health = Integer.parseInt(statArr[1]);
			int spAttack = Integer.parseInt(statArr[2]);
			int def = Integer.parseInt(statArr[3]);
			int attack = Integer.parseInt(statArr[4]);
			//Generates Pokemon with established attribute values 
			pokemon POKE = new pokemon(name, health, spAttack, def, attack);
			randomlist.add(POKE);
			
		}
		return randomlist;
	}
	//Recursive gameplay method to iterate through 5 levels with different parameters for progressive difficulty 
	public static void dojo(String name, ArrayList<pokemon> userList, int inc, int num) throws Exception{
		Scanner input4 = new Scanner(System.in);
		//Base case (indicating that all 5 levels have been completed), ends game
		if (inc == 15) {
			System.out.println("\nTrainer " + name + Files.readAllLines(Paths.get("dojo.txt")).get(16));
			System.out.println("\nTrainer " + name + " Recieved the kalos key to the Multiverse");
			System.out.println("\nThe game has now concluded");
		}
		//Specifies high/low ranges for CPU Pokemon generation, imported from dojo.txt
		int range_low = Integer.parseInt(Files.readAllLines(Paths.get("dojo.txt")).get(inc + 1));
		int range_high = Integer.parseInt(Files.readAllLines(Paths.get("dojo.txt")).get(inc + 2));
		//Generates CPUs randomized Pokemons
		ArrayList<pokemon> oppList = randomizer(num,range_low,range_high);
		System.out.println("\nTrainer " + String.format("%s",name) + ", " + Files.readAllLines(Paths.get("dojo.txt")).get(inc));
		//Executes fight method (iterates through each Pokemon in both user/CPU lists)
		boolean winloss = fight(userList, oppList);
		//Executes set of code if user wins fight
		if (winloss == true) {
			System.out.println("\nCongratulations, you won the fight!");
			System.out.println("\n" + name + Files.readAllLines(Paths.get("dojo.txt")).get(15));
			System.out.println("\nSelect one of the following Pokemons to keep:");
			//Prints stats of each Pokemon in CPU array list
			for (int j = 0; j < userList.size(); j++) {
				System.out.println("\n" + oppList.get(j).statRead());
			}
			////Allows user to choose 1 Pokemon from the CPUs team, appends chosen Pokemon to user array list 
			String resp = input4.next();
			for (int y = 0; y < oppList.size(); y++) {
				if (resp.equals(oppList.get(y).poke_name())) {
					userList.add(oppList.get(y));
				}
			}
			System.out.println("\nEnter 1 to continue");
			int ans2 = input4.nextInt();
			if (ans2 == 1) {
				//Repeats dojo method with different parameters for increased difficulty 
				dojo(name, userList, inc + 3, num + 1);
			}
		}
		else {
			System.out.println("\nGame Over!");
		}
	}
	//Method to simulate fight between user and CPU Pokemons 
	public static boolean fight(ArrayList<pokemon>userList, ArrayList<pokemon>oppList) {
		Scanner input3 = new Scanner(System.in);
		int userwin = 0;
		int oppwin = 0;
		//Iterates through each Pokemon in user and CPU lists (user Pokemon at [i] fights CPU Pokemon at [i]
		for (int i = 0; i < userList.size(); i++) {
			//Executes individual Pokemon battle method 
			boolean result = userList.get(i).battle(oppList.get(i));
			//Prints set of code if user wins, updates scores correspondingly 
			if (result == true) {
				System.out.println("\nCongratulations, you won the battle!");
				userwin+=1;
				System.out.println("\nUser score: " + userwin);
				System.out.println("\nMaster score: " + oppwin);
			}
			//Prints set of code if user looses, updates scores correspondingly 
			else {
				System.out.println("\nUnfortunately, you lost this battle.");
				oppwin+=1;
				System.out.println("\nUser score: " + userwin);
				System.out.println("\nMaster score: " + oppwin);
			}
		}
		//Determines whether user or CPU won fight, returns corresponding boolean value 
		if (userwin > oppwin) {
			return true;
		}
		else if (oppwin > userwin) {
			return false;
		}
		//Executes set of code if score is user and CPU scores are tied 
		else {
			System.out.println("\nYour score and the dojo master's score are tied. A coin will now be flipped to determine the winner");
			System.out.println("\nPick a side (Heads or Tails):");
			//Simulates coin toss
			String side = input3.next();
			int flip = ThreadLocalRandom.current().nextInt(1,3);
			if (flip == 1 && side.equals("Heads")) {
				return true;
			}
			//Returns corresponding boolean value depending on result of toss
			else if (flip == 2 && side.equals("Tails")) {
				return true;
			}
			else {
				return false;
			}
		}
	}
}