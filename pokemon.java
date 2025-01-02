//Import statements
import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.*;
public class pokemon{
	//Initializes Pokemon attributes
	private String name;
	private int health;
	private int spAttack;
	private int def;
	private int attack;
	private String moveA;
	private int Apower;
	private String moveB;
	private int Bpower;
	private String moveC;
	private int Cpower;
	private String moveD;
	private int Dpower;
	//Private method to read specific line from text file (used in moveset generation)
	private String readLine(int ind, String txtfile) {
		String ind_line = null;
		try {
			FileReader file = new FileReader(txtfile);
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
		return ind_line;
	}
	//Pokemon constructor method 
	public pokemon(String nm, int h, int sp, int def, int att) throws Exception{
		FileReader file = new FileReader("moveset.txt");
	    BufferedReader buffer = new BufferedReader(file);
	    Random randgen = new Random();
		//Assigns parameter values to corresponding attributes
	    this.name = nm;
		this.health = h;
		this.spAttack = sp;
		this.def = def;
		this.attack = att;
	    int count = 0;
	    //Determines the number of lines in moveset.txt
	    try (BufferedReader rd = new BufferedReader(new FileReader("moveset.txt"))) {
	    	while (rd.readLine() != null) {
	        	  count += 1;
	        }
	        	 
	        } catch (IOException e) {
	          e.printStackTrace();
	    }
	    //Creates Array list to generate 4 random indices (for moveset.txt) for moveset generation
		ArrayList<Integer> select = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			int rand = ThreadLocalRandom.current().nextInt(0, count);
			//Determines if index has previously been selected, repeats generation if so
			while (select.contains(rand)) {
				rand = ThreadLocalRandom.current().nextInt(0, count);
			}
			select.add(rand);
		}
		//Assigns each value in select to corresponding Pokemon move 
		this.moveA = readLine(select.get(0), "moveset.txt");
		this.moveB = readLine(select.get(1), "moveset.txt");
		this.moveC = readLine(select.get(2), "moveset.txt");
		this.moveD = readLine(select.get(3), "moveset.txt");
		//Determines whether attack or special attack value is larger (for power randomization)
		int big = sp;
		int sml = att;
		if (att > sp) {
			big = att;
			sml = sp;
		}
		//Randomizes movepower based on move type (attack or special attack)
		this.Apower = ThreadLocalRandom.current().nextInt(0, sml + 1);
		this.Bpower = ThreadLocalRandom.current().nextInt(0, sml + 1);
		this.Cpower = ThreadLocalRandom.current().nextInt(sml, big + 1);
		this.Dpower = ThreadLocalRandom.current().nextInt(sml, big + 1);
	}
	//Getter method for Pokemon name
	public String poke_name() {
		return this.name;
	}
	//Getter method for Pokemon health
	public int poke_health() {
		return this.health;
	}
	//Getter method for Pokemon defense
	public int poke_def() {
		return this.def;
	}
	//Getter method for Pokemon special attack 
	public int poke_sp() {
		return this.spAttack;
	}
	//Getter method for Pokemon attack
	public int poke_att() {
		return this.attack;
	}
	//Getter method for Pokemon move A name/power
	public String[] amove() {
		String[]amove = new String[2];
		amove[0] = this.moveA;
		amove[1] = Integer.toString(this.Apower);
		return amove;
	}
	//Getter method for Pokemon move B name/power
	public String[] bmove() {
		String[]bmove = new String[2];
		bmove[0] = this.moveB;
		bmove[1] = Integer.toString(this.Bpower);
		return bmove;
	}
	//Getter method for Pokemon move C name/power
	public String[] cmove() {
		String[]cmove = new String[2];
		cmove[0] = this.moveC;
		cmove[1] = Integer.toString(this.Cpower);
		return cmove;
	}
	//Getter method for Pokemon move D name/power
	public String[] dmove() {
		String[]dmove = new String[2];
		dmove[0] = this.moveD;
		dmove[1] = Integer.toString(this.Dpower);
		return dmove;
	}
	//Method to return Pokemon stats as an organized String 
	public String statRead() {
		String sent1 = "Health: " + this.health;
		String sent2 = "Special Attack: " + this.spAttack;
		String sent3 = "Defense: " + this.def;
		String sent4 = "Attack: " + this.attack;
		return this.name+"\t\t"+sent1+"\t"+sent2+"\t"+sent3+"\t"+sent4;
	}
	//Method to simulate individual pokemon battle
	public boolean battle(pokemon opp_poke) {
		Scanner input = new Scanner (System.in);
		int poke_hth = this.health;
		int opp_hth = opp_poke.poke_health();
		//Repeats set of code until user health or CPU health is <= 0
		while (poke_hth > 0 && opp_hth > 0) {
			//Prints move choices for 
			System.out.println("\nSelect a move for " + this.name + " to use:");
			System.out.println("\n- " + this.moveA);
			System.out.println("\n- " + this.moveB);
			System.out.println("\n- " + this.moveC);
			System.out.println("\n- " + this.moveD);
			String move = input.next();
			int movepower;
			//Assigns move power based on move selected by user
			boolean validMove = false;
			while (validMove == false) {
				if (move.equals(this.moveA)){
					movepower = this.Apower;
					validMove = true;
				}
				else if (move.equals(this.moveB)) {
					movepower = this.Bpower;
					validMove = true;
				}
				else if (move.equals(this.moveC)) {
					movepower = this.Cpower;
					validmove = true;
				}
				else if (move.equals(this.moveD)){
					movepower = this.Dpower;
				}
				else {
					Sytem.out.println("Invalid Move.")
					System.out.println("\nSelect a move for " + this.name + " to use:");
				}
			}
			
			//Generates random number to determine whether move hit or not 
			int rand = ThreadLocalRandom.current().nextInt(0, 101);
			//All moves are set to 65% probability of hitting 
			if (rand <= 65) {
				//Generates random number to determine effectiveness of move 
				int rand0 = ThreadLocalRandom.current().nextInt(0, 101);
				if (opp_poke.poke_def() < movepower) {
					//40% chance of being extremely effective if opp_poke.poke_def() < movepower
					if (rand0 <= 40) {
						System.out.println("\n" + this.name + " used " + move + ". It's  extremely effective!");
						//Reduces CPU health by calculated value 
						opp_hth -= (movepower-(opp_poke.poke_def()/6));
					}
					//60% chance of being effective if opp_poke.poke_def() < movepower
					else {
						System.out.println("\n" + this.name + " used " + move + ". It's effective!");
						//Reduces CPU health by calculated value 
						opp_hth -= (movepower-(opp_poke.poke_def()/4));
					}
					//Returns true if CPU has been defeated 
					if (opp_hth <= 0) {
						return true;
					}
					//Prints user/CPU health after every attack
					System.out.println("\n" + this.name + " health: " + poke_hth);
					System.out.println("\n" + opp_poke.poke_name()+ " health: " + opp_hth);
				}
				else if (opp_poke.poke_def()==movepower){
					//20% chance of being extremely effective if opp_poke.poke_def()==movepower
					if (rand0 <= 20) {
						System.out.println("\n" + this.name + " used " + move + ". It's  extremely effective!");
						opp_hth-=(movepower-(opp_poke.poke_def()/6));
					}
					//80% chance of being effective if opp_poke.poke_def()==movepower
					else {
						System.out.println("\n" + this.name + " used " + move + ". It's  effective!");
						opp_hth-=(movepower-(opp_poke.poke_def()/4));
					}
					if (opp_hth <= 0) {
						return true;
					}
					System.out.println("\n" + this.name + " health: " + poke_hth);
					System.out.println("\n" + opp_poke.poke_name()+ " health: " + opp_hth);
				}
				else {
					//40% chance of being effective if opp_poke.poke_def() > movepower
					if (rand0 <= 40) {
						System.out.println("\n" + this.name + " used " + move + ". It's  effective!");
						opp_hth -= Math.abs((movepower-(opp_poke.poke_def()/4)));
					}
					//60% chance of being a hit if opp_poke.poke_def() > movepower
					else {
						System.out.println("\n" + this.name + " used " + move + ". It hit!");
						opp_hth -= Math.abs((movepower-(opp_poke.poke_def()/2)));
					}
					if (opp_hth <= 0) {
						return true;
					}
					System.out.println("\n" + this.name + " health: " + poke_hth);
					System.out.println("\n" + opp_poke.poke_name()+ " health: " + opp_hth);
				}
			}
			//Indicating that the move missed
			else {
				System.out.println("\n" + this.name + " used " + move + ". It missed!");
			}
			//Creates a 2D array to store CPU move names/values
			String[][] randmove = {opp_poke.amove(),opp_poke.bmove(),opp_poke.cmove(),opp_poke.dmove()};
			//Selects a random move name/power from randmove
			String[] oppmovearr = randmove[ThreadLocalRandom.current().nextInt(0, 4)];
			//Separates move name and move power 
			String oppmove = oppmovearr[0];
			int oppmovepwr = Integer.parseInt(oppmovearr[1]);
			//Generates random number to determine whether move hit or not
			int opprand = ThreadLocalRandom.current().nextInt(0, 101);
			//All moves are set to 65% probability of hitting 
			if (opprand <= 65) {
				//Generates random number to determine effectiveness of move
				int rand1 = ThreadLocalRandom.current().nextInt(0, 101);
				if (this.def < oppmovepwr) {
					//40% chance of being extremely effective if this.def < oppmovepwr
					if (rand1 <= 40) {
						System.out.println("\n" + opp_poke.poke_name()+ " used " + oppmove + ". It's extremely effective!");
						poke_hth -= (oppmovepwr-(this.def/6));
					}
					//60% chance of being effective if this.def < oppmovepwr
					else {
						System.out.println("\n" + opp_poke.poke_name() + " used " + oppmove + ". It's  effective!");
						poke_hth-=(oppmovepwr-(this.def/4));
					}
					//Returns false if user has been defeated 
					if (poke_hth <= 0) {
						return false;
					}
					System.out.println("\n" + this.name + " health: " + poke_hth);
					System.out.println("\n" + opp_poke.poke_name()+ " health: " + opp_hth);
				}
				else if (this.def==oppmovepwr){
					//20% chance of being extremely effective if this.def==oppmovepwr
					if (rand1 <= 20) {
						System.out.println("\n" + opp_poke.poke_name()+ " used " + oppmove + ". It's extremely effective!");
						poke_hth -= (oppmovepwr-(this.def/6));
					}
					//80% chance of being effective if this.def==oppmovepwr
					else {
						System.out.println("\n" + opp_poke.poke_name() + " used " + oppmove + ". It's  effective!");
						poke_hth-=(oppmovepwr-(this.def/4));
					}
					if (poke_hth <= 0) {
						return false;
					}
					System.out.println("\n" + this.name + " health: " + poke_hth);
					System.out.println("\n" + opp_poke.poke_name()+ " health: " + opp_hth);
				}
				else {
					//20% chance of being effective if this.def > oppmovepwr
					if (rand1 <= 40) {
						System.out.println("\n" + opp_poke.poke_name() + " used " + oppmove + ". It's  effective!");
						//Subtracts absolute value (to avoid adding to user health) of calculated impact from user health 
						poke_hth-= Math.abs((oppmovepwr-(this.def/4)));
					}
					//80% chance of being a hit if this.def > oppmovepwr
					else {
						System.out.println("\n" +  opp_poke.poke_name() + " used " + oppmove + ". It hit!");
						poke_hth-= Math.abs(oppmovepwr-(this.def/2));
					}
					if (poke_hth <= 0) {
						return false;
					}
					System.out.println("\n" + this.name + " health: " + poke_hth);
					System.out.println("\n" + opp_poke.poke_name()+ " health: " + opp_hth);
				}
			}
			//Indicates that CPU move missed 
			else {
				System.out.println("\n" +  opp_poke.poke_name() + " used " + oppmove + ". It missed!");
			}
		}
		return false;
	}
}
