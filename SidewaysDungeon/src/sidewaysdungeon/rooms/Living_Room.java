package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

import java.util.Arrays;

public class Living_Room extends Room implements RoomTemplate
{
	//Use the main method to test the room
		public static void main(String[] args)
		{
			String result;
			Living_Room testRoom = new Living_Room();
			do {
				result = testRoom.playRoom("");
			} while(result.equals("living_room"));
			System.out.println(result);
		}
	
	//Room items
	private final String PORTRAIT = "portrait";				//take
	private final String NOTE = "note";
	private final String PORTRAIT_WALL = "portrait wall";	//use
	private final String BLUE = "blue switch";				//use
	private final String GREEN = "green switch";			//use
	private final String YELLOW = "yellow switch";			//use
	private final String ORANGE = "orange switch";			//use
	private final String RED = "red switch";				//use
	
	//Room variables
	private final char[] correctOrder = {'r', 'y', 'o', 'g', 'b'};
	private int numPortraitsMissing = 3;
	private int numSwitchAttempts = 0;
	private int numSwitchCorrect = 0;
	
	private boolean doorOpened = false;
	private boolean switchesUnlocked = false;
	private boolean greenPlaced = false;
	private boolean yellowPlaced = false;
	private boolean orangePlaced = false;
	private boolean orangeTaken = false;
	private boolean keyTaken = false;
	private boolean notePresent = true;
	private boolean flashlightTakenDEBUG = false;		//DEBUG Make sure this is false when done
	
	//Constructor
	public Living_Room()
	{
		roomName = "Living Room";
		fearMultiplier = 0.5f;
		
		//Add usable items
		usable.add(NOTE);
		usable.add(PORTRAIT_WALL);
		
		//Add takeable items
		takeable.add(Strings.KEY_NAME);
		takeable.add(PORTRAIT);
		
		//Add directions
		directions.add("west");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		return String.format("----------------------\n"
						   + "|                    |\n"
						   + "=                    |\n"
						   + "|                    |\n"
						   + "-------              ---\n"
						   + "      |   %c           %c|\n"
						   + "      |   %c           %c|\n"
						   + "      |%c  %c   o       %c%c\n"
						   + "      |%c  %c           %c|\n"
						   + "      |   %c           %c|\n"
						   + "      |             %c---\n"
						   + "      ----------------\n",
						   (switchesUnlocked ? 'u' : ' '), (!switchesUnlocked ? '?' : ' '),
						   (switchesUnlocked ? 'u' : ' '), (switchesUnlocked ? ' ' : (greenPlaced ? '?' : 'u')),
						   (!keyTaken ? 't' : ' '), (switchesUnlocked ? 'u' : ' '), (doorOpened ? ' ' : (yellowPlaced || switchesUnlocked ? '?' : 'u')), (doorOpened ? '=' : '|'),
						   (notePresent ? 'u' : ' '), (switchesUnlocked ? 'u' : ' '), (switchesUnlocked ? ' ' : (orangePlaced ? '?' : 'u')),
						   (switchesUnlocked ? 'u' : ' '), (!switchesUnlocked ? '?' : ' '),
						   (orangeTaken) ? ' ' : 't');
	}
	
	//printMap VOID METHOD: Prints the map, map legend, the current exits, and the last shown text
	public void printMap(boolean showLastText)
	{
		System.out.println();
		System.out.println(map());
		System.out.println(Strings.MAP_LEGEND);
		System.out.println(exits());
		   
		if(showLastText)
			System.out.println(lastShownText);
	}
	
	//playRoom STRING METHOD: Code that actually plays the room
	public String playRoom(String prevRoom)
	{
	 	updateStats();
	 	
	 	//Reset numSwitchAttempts
	 	numSwitchAttempts = 0;
	 	numSwitchCorrect = 0;
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Print room description
	 	lastShownText = "You come into the living room. Dusty furniture is spread out\n"
	 				  + "everywhere. A line of portraits hang on the wall above the\n"
	 				  + "fireplace";
	 	
	 	if(doorOpened)
	 		lastShownText += '.';
	 	else if(switchesUnlocked)
	 		lastShownText += " in front of a row of colored switches.";
	 	else
	 	{
	 		lastShownText += ", but";
	 		
	 		if(numPortraitsMissing == 3)
	 			lastShownText += " three of them are missing.";
	 		else if(numPortraitsMissing == 2)
	 			lastShownText += " two of them are missing.";
	 		else if(numPortraitsMissing == 1)
	 			lastShownText += " one of them is misisng.";
	 		else
	 			lastShownText += " but an unknown amount of them are missing because\n"
	 					   	   + "the portrait counter broke somehow.";
	 	}
	 	
	 	if(!keyTaken)
	 		lastShownText += " You notice a key\n"
	 					   + "hanging on a hook next to a note on the wall.";
	 	
	 	System.out.println(lastShownText);
	 	
	 	//Update the cat's fear
	 	cat.updateFear(fearMultiplier);
	 	
	 	//Get commands from player
	 	while(true)
	 	{
	 		String command = Commands.promptCommand(keyboard, false, inventory);
	 		
	 		//Intercept commands
	        if(command.length() >= 2 && command.substring(0,2).equals("#&"))	//Load entered room
	        	return command.substring(2);
	        else if(command.equals("##"))										//Reload this room
	        	return "living_room";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + '\n'
	        					 + "correctOrder: " + Arrays.toString(correctOrder) + '\n'
	        					 + "numPortraitsMissing: " + numPortraitsMissing + '\n'
	        					 + "numSwitchAttempts: " + numSwitchAttempts + '\n'
	        					 + "numSwitchCorrect: " + numSwitchCorrect + '\n'
	        					 + '\n'
	        					 + "greenPlaced: " + greenPlaced + '\n'
	        					 + "yellowPlaced: " + yellowPlaced + '\n'
	        					 + "orangePlaced: " + orangePlaced + '\n'
	        					 + "orangeTaken: " + orangeTaken + '\n'
	        					 + "keyTaken: " + keyTaken + '\n'
	        					 + "notePresent: " + notePresent + '\n'
	        					 + '\n'
	        					 + "flashlightTaken (cell_block_basement): " + /*cell_block_basement.flashlightTaken() + */'\n'	//TODO Uncomment this out when cell_block_basement gets added
	        					 + "[[flashlightTakenDEBUG]]: " + flashlightTakenDEBUG);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 			{
	 				if(/*cell_block_basement.flashlightTaken() || */flashlightTakenDEBUG)		//TODO Uncomment out when cell_block_basement gets added
	 				{
	 					System.out.println("\nThe portraits' inscriptions have been graffitied over:\n"
	 									 + "LADY IN BLUE: \"WE'RE\"\n"
	 									 + "LADY IN GREEN: \"SO\"\n"
	 									 + "LADY IN YELLOW: \"PROUD\"\n"
	 									 + "LADY IN ORANGE: \"OF\"\n"
	 									 + "LADY IN RED: \"YOU!\"");
	 				}
	 				else
	 				{
	 					System.out.println("\nYou read the portraits' inscriptions:\n"
	 									 + "LADY IN BLUE: \"" + Strings.BLUE_INSCRIPTION + "\"\n"
	 									 + (greenPlaced ? "LADY IN GREEN: \"" + Strings.GREEN_INSCRIPTION + "\"" : "???: ---") + '\n'
	 									 + (yellowPlaced ? "LADY IN YELLOW: \"" + Strings.YELLOW_INSCRIPTION + "\"" : "???: ---") + '\n'
	 									 + (orangePlaced ? "LADY IN ORANGE: \"" + Strings.ORANGE_INSCRIPTION + "\"" : "???: ---") + '\n'
	 									 + "LADY IN RED: \"" + Strings.RED_INSCRIPTION + "\"");
	 				}
	 				
	 				break;
	 			}
	 			case "use":
	 			{
	 				switch(Commands.promptUse(usable))
	 				{
	 					case(NOTE):
	 					{
	 						System.out.println("\nYou read the note:\n"
	 										 + "\"I hid something nice in your cell. Try to find it if you can!\"");
	 						
	 						break;
	 					}
	 					case(PORTRAIT_WALL):
	 					{
	 						switch(inventory.promptItem(PORTRAIT_WALL))
	 						{
		 						case "":
		 							break;
		 						case Strings.LADY_IN_GREEN_NAME:
		 						{
		 							inventory.remove(Strings.LADY_IN_GREEN_NAME);
		 							greenPlaced = true;
		 							numPortraitsMissing--;
		 							
		 							//Print new map if there are more portraits missing
		 							if(numPortraitsMissing > 0)
		 								printMap(false);
		 							
		 							lastShownText = "You place the portrait of the Lady in Green.";
		 							System.out.println(lastShownText);
		 							
		 							break;
		 						}
		 						case Strings.LADY_IN_YELLOW_NAME:
		 						{
		 							inventory.remove(Strings.LADY_IN_YELLOW_NAME);
		 							yellowPlaced = true;
		 							numPortraitsMissing--;
		 							
		 							//Print new map if there are more portraits missing
		 							if(numPortraitsMissing > 0)
		 								printMap(false);
		 							
		 							lastShownText = "You place the portrait of the Lady in Yellow.";
		 							System.out.println(lastShownText);
		 							
		 							break;
		 						}
		 						case Strings.LADY_IN_ORANGE_NAME:
		 						{
		 							inventory.remove(Strings.LADY_IN_ORANGE_NAME);
		 							orangePlaced = true;
		 							numPortraitsMissing--;
		 							
		 							//Print new map if there are more portraits missing
		 							if(numPortraitsMissing > 0)
		 								printMap(false);
		 							
		 							lastShownText = "You place the portrait of the Lady in Orange.";
		 							System.out.println(lastShownText);
		 							
		 							break;
		 						}
		 						default:
		 							System.out.println(Strings.CANT_USE_THAT_HERE);
	 						}
	 						
	 						//If all the portraits have been placed, activate the switch puzzle
	 						if(numPortraitsMissing == 0)
	 						{
	 							switchesUnlocked = true;
	 							
	 							usable.remove(PORTRAIT_WALL);
	 							usable.add(BLUE);
	 							usable.add(GREEN);
	 							usable.add(YELLOW);
	 							usable.add(ORANGE);
	 							usable.add(RED);
	 							
	 							Operate.delay(750);
	 							
	 							lastShownText = "As you hang the last remaining portrait on the wall, you hear\n"
	 										  + "some sort of mechanism activate behind you. You turn around\n"
	 										  + "to see a series of colored switches coming out of the floor.";
	 							
	 							//Print new map
	 							printMap(true);
	 						}
	 						
	 						break;
	 					}
	 					case(BLUE):
	 					{
	 						if(correctOrder[numSwitchAttempts] == 'b')
	 							numSwitchCorrect++;
	 						
	 						numSwitchAttempts++;
	 						
	 						System.out.println("You press the blue switch.");
	 						break;
	 					}
	 					case(GREEN):
	 					{
	 						if(correctOrder[numSwitchAttempts] == 'g')
	 							numSwitchCorrect++;
	 						
	 						numSwitchAttempts++;
	 						
	 						System.out.println("You press the green switch.");
	 						break;
	 					}
	 					case(YELLOW):
	 					{
	 						if(correctOrder[numSwitchAttempts] == 'y')
	 							numSwitchCorrect++;
	 						
	 						numSwitchAttempts++;
	 						
	 						System.out.println("You press the yellow switch.");
	 						break;
	 					}
	 					case(ORANGE):
	 					{
	 						if(correctOrder[numSwitchAttempts] == 'o')
	 							numSwitchCorrect++;
	 						
	 						numSwitchAttempts++;
	 						
	 						System.out.println("You press the orange switch.");
	 						break;
	 					}
	 					case(RED):
	 					{
	 						if(correctOrder[numSwitchAttempts] == 'r')
	 							numSwitchCorrect++;
	 						
	 						numSwitchAttempts++;
	 						
	 						System.out.println("You press the red switch.");
	 						break;
	 					}
	 				}
	 				
	 				//If numSwitchAttempts = 5, check if the player has solved the puzzle
 					if(numSwitchAttempts == 5)
 					{
 						Text.scrollLine("\n@...@", 750);
 						
 						if(numSwitchAttempts == numSwitchCorrect)
 						{
 							doorOpened = true;
 							switchesUnlocked = false;
 							directions.add("east");
 							
 							//Remove all the switches from usable
 							usable.remove(BLUE);
 							usable.remove(GREEN);
 							usable.remove(YELLOW);
 							usable.remove(ORANGE);
 							usable.remove(RED);
 							
 							lastShownText = "A hidden panel in the fireplace opened! The switches retreat\n"
 										  + "back into the floor.\n\n"
 										  + Strings.PATH_OPENED + "EAST.";
 							
 							//Show new map
 							printMap(true);
 						}
 						else
 						{
 							lastShownText = "Nothing happened.";
 							
 							//Show new map
 							printMap(true);
 						}
 						
 						//Reset numSwitchAttempts
 						numSwitchAttempts = 0;
 						numSwitchCorrect = 0;
 					}
	 				
	 				break;
	 			}
	 			case "take":
	 			{
	 				switch(Commands.promptTake(takeable))
	 				{
	 					case PORTRAIT:
	 					{
		 					orangeTaken = true;
		 					takeable.remove(PORTRAIT);
		 					inventory.add(orange_lady);
		 					
		 					lastShownText = "You pick up the portrait leaning against the wall.\n\n"
		 							      + Strings.LADY_IN_ORANGE_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
		 					
		 					//Print new map
		 					printMap(true);
		 					break;
	 					}
		 				case Strings.KEY_NAME:
		 				{
		 					keyTaken = true;
		 					takeable.remove(Strings.KEY_NAME);
		 					inventory.add(key);
		 					
		 					lastShownText = "You take the key off the hook.\n\n"
		 								  + Strings.KEY_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
		 					
		 					//Print new map
		 					printMap(true);
		 					break;
		 				}
	 				}
	 				
	 				break;
	 			}
	 			case "talk":
	 			{
	 				Commands.promptTalk(talkable);
	 				break;
	 			}
	 			case "map":
	 			{
	 				printMap(true);
	 				break;
	 			}
	 			case "west":
	 			{
	 				System.out.println("You go WEST.\n\n");
	 				Operate.delay(500);
	 				return "house_foyer";
	 			}
	 			case "east":
	 			{
	 				if(doorOpened)
	 				{
	 					System.out.println("You go EAST.\n\n");
	 					Operate.delay(500);
	 					return "ladder";
	 				}
	 			}
	 			case "north":
	 			case "south":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
	
	//Removes the note from the room called by prison_cell_a when the player takes the portrait
	public void removeNote()
	{
		usable.remove(NOTE);
		notePresent = false;
	}
}