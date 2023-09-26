package sidewaysdungeon.entities;

import java.util.Scanner;

import sidewaysdungeon.game_files.Operate;
import sidewaysdungeon.text.Text;

//Stores information and methods for the player
public class Player
{
	//Player variables
	private String name = "{{NO NAME}}";	//The player's name
	
	private int numMoves = 0;				//The number of moves the player has made throughout the game
	private int totemsTalkedTo = 0;			//The number of totems the player has talked to

	final private int MIN_NAME_LENGTH = 3;		//The minimum number of characters the player's name can be
	final private int MAX_NAME_LENGTH = 10;		//The maximum number of characters the player's name can be
	
	//Use the main method to test Player methods
	public static void main(String[] args)
	{
	}
	
	//Constructor
	public Player()
	{
	}
	
	//setName VOID METHOD: Sets the name of the player
	public void setName(String name)
	{
		this.name = name;
	}
	
	//promptName VOID METHOD: Prompts the player for their name
	public void promptName(Scanner keyboard)
	{
		 //Get the player's name
	      int nameRetries = 0;
	      boolean firstTimeShort = true;
	      boolean firstTimeLong = true;
	      boolean validName;
	      boolean validResponse;
	      String playerName = null;
	      do{
	    	 validName = true;
	         Text.scroll("Please enter your name: @", 20);
	         playerName = keyboard.nextLine();
	         
	         //Purge all non-letter characters from name
	         playerName = playerName.replaceAll("[^a-zA-Z ]", "");
	         
	         //Trim leading and tailing spaces from playerName
	         playerName = playerName.trim();
	         
	         //Length check. Name should be between 4-10 characters
	         if(playerName.length() < MIN_NAME_LENGTH)
	         {
	        	validName = false;
	        	nameRetries++;
	            Text.scrollLine("@...@",500);
	            
	            if(firstTimeShort)
	            {
	               Text.scrollLine("Sorry,@ I should have specified.@", 20);
	               firstTimeShort = false;
	            }
	            
	            Operate.delay(200);
	            Text.scrollLine("Your name must be at least " + MIN_NAME_LENGTH + " characters.@\n", 20);
	            continue;
	         }
	         else if(playerName.length() > MAX_NAME_LENGTH)
	         {
	        	validName = false;
	        	nameRetries++;
	            Text.scrollLine("@...@",500);
	            
	            if(firstTimeLong)
	            {
	               Text.scrollLine("Sorry,@ I should have specified.@", 20);
	               firstTimeLong = false;
	            }
	            
	            Operate.delay(200);
	            Text.scrollLine("Your name cannot be more than " + MAX_NAME_LENGTH + " characters.@\n", 20);
	            continue;
	         }
	         
	         System.out.println();
	         Text.scrollLine(playerName + "?@ Is that correct?", 20);
	         do {
	        	 validResponse = true;
		         System.out.print("Type \"yes\" or \"no\": ");
		         
		         switch(keyboard.nextLine().toLowerCase())
		         {
		         	case "yes":
		         	{
		         		//If the player took more than 4 attempts to get their name, the following message will be displayed
		         	    if(nameRetries > 4)
		         	    	Text.scrollLine("@@(Jesus, finally...)@", 8);
		         	      
		         		break;
		         	}
		         	case "no":
		         	{
		         		nameRetries++;
		         		validName = false;
		         		Operate.delay(250);
		         		System.out.println();
		         		break;
		         	}
		         	default:
		         	{
		         		nameRetries++;
		         		validResponse = false;
		         		Text.scrollLine("\n@...@", 500);
		         		Text.scrollLine("Sorry,@ I didn't get that.@", 20);
		         		Text.scrollLine("Is " + playerName + " the name you want?", 20);
		         	}
		         }
	         }while(!validResponse);
	         
	      }while (!validName);
	      
	      //Set player name
	      name = playerName;
	}
	
	//getName STRING METHOD: Returns the player's name
	public String getName()
	{
		return name;
	}
	
	//incNumMoves VOID METHOD: Increments numMoves by 1
	public void incNumMoves()
	{
		numMoves++;
	}
	
	//getNumMoves INT METHOD: Returns the number of moves the player has made
	public int getNumMoves()
	{
		return numMoves;
	}
	
	//incTotemsTalkedTo VOID METHOD: Increments totemsTalkedTo by 1
	public void incTotemsTalkedTo()
	{
		totemsTalkedTo++;
	}
	
	//getTotemsTalkedTo INT METHOD: Returns totemsTalkedTo
	public int getTotemsTalkedTo()
	{
		return totemsTalkedTo;
	}
}