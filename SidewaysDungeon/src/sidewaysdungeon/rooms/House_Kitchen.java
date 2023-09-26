package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;
import java.util.Random;

public class House_Kitchen extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		House_Kitchen testRoom = new House_Kitchen();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("house_kitchen"));
		System.out.println(result);
	}
	
	//Room items
	final private String KEYPAD = "keypad";				//use
	final private String PAPER_SCRAP = "paper scrap";	//take
	final private String CAT = "cat";					//take
	
	//Room variables
	private int doorCode;
	private int oldDoorCode;
	
	private boolean manPresent = true;
	private boolean manInspected = false;
	private boolean paperScrapTaken = false;
	private boolean doorOpened = false;
	private boolean catHeard = false;
	private boolean catInspected = false;
	private boolean alreadyLeft = false;
	private boolean shownCatFollowMessage = false;
	
	//Maps
	final private String MAP_INITIAL = "-----------\n"
									 + "|         |\n"
									 + "|         |\n"
									 + "#    ?    |\n"
									 + "|         u\n"
									 + "|    o    #\n"
									 + "----| |----\n";
	
	final private String MAP_MAN_INSPECTED = "-----------\n"
										   + "|         |\n"
										   + "|         |\n"
										   + "#    t    |\n"
										   + "|         u\n"
										   + "|    o    #\n"
										   + "----| |----\n";
	
	final private String MAP_DOOR_OPENED = "-----------\n"
										 + "|         |\n"
										 + "|         |\n"
										 + "#    ?    |\n"
										 + "|         u\n"
										 + "|    o    =\n"
										 + "----| |----\n";
	
	final private String MAP_CAT_HEARD = "-----------\n"
									   + "|  ?      |\n"
									   + "|         |\n"
									   + "#         |\n"
									   + "|         u\n"
									   + "|    o    =\n"
									   + "----| |----\n";
	
	final private String MAP_CAT_INSPECTED = "-----------\n"
										   + "|  t      |\n"
										   + "|         |\n"
										   + "#    ?    |\n"
										   + "|         u\n"
										   + "|    o    =\n"
										   + "----| |----\n";
	
	//Constructor
	public House_Kitchen()
	{
		roomName = "Kitchen";
		fearMultiplier = 2.5f;
		
		//Add useable items
		usable.add(KEYPAD);
		
		//Add directions
		directions.add("south");
		
		//Generate door code
		Random rand = new Random();
		doorCode = rand.nextInt(9000) + 1000;	//Door code can be anywhere from 1000-9999
		oldDoorCode = doorCode;
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		if(catInspected && !cat.taken())
			return MAP_CAT_INSPECTED;
		else if(catHeard && !catInspected)
			return MAP_CAT_HEARD;
		else if(doorOpened)
			return MAP_DOOR_OPENED;
		else if(manInspected && !paperScrapTaken)
			return MAP_MAN_INSPECTED;
		else
			return MAP_INITIAL;
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
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Print room description
	 	lastShownText = "It's some sort of kitchen; there's a fridge, counters, and\n"
	 				  + "various kitchenware thrown around all over the place. The\n"
	 				  + "door to the WEST appears to be locked";
	 	
	 	//Will say the door is locked if doorOpened == false
	 	if(!doorOpened)
	 		lastShownText += ", and the door to the\n"
		 				   + "EAST is locked by some sort of keypad.";
	 	else
	 		lastShownText += '.';
	 	
	 	lastShownText += " In the center of the\n"
		 			   + "room is a man hanging from a noose.";
	 	
	 	if(manInspected && !paperScrapTaken)
	 		lastShownText += " Something is in his\n"
	 					   + "pocket.";
	 	
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
	        	return "house_kitchen";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					 + "doorCode: " + doorCode + "\n"
	        					 + "oldDoorCode: " + oldDoorCode + "\n"
	        					 + "\n"
	        					 + "manPresent: " + manPresent + "\n"
	        					 + "manInspected: " + manInspected + "\n"
	        					 + "paperScrapTaken: " + paperScrapTaken + "\n"
	        					 + "doorOpened: " + doorOpened + "\n"
	        					 + "catHeard: " + catHeard + "\n"
	        					 + "catInspected: " + catInspected + "\n"
	        					 + "alreadyLeft: " + alreadyLeft + "\n"
	        					 + "shownCatFollowMessage: " + shownCatFollowMessage);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 			{
	 				if(!manInspected)
	 				{
	 					manInspected = true;
	 					takeable.add(PAPER_SCRAP);
	 					
	 					//Print new map
	 					printMap(false);
	 					
	 					lastShownText = Strings.NEW_DISCOVERY + "You take a close look at the man. His entire face is\n"
	 								  + "scorched, as if it was touched by a searing hot iron before\n"
	 								  + "he died. He is ginormous as well; it is a wonder how the\n"
	 								  + "wooden ceiling beam in the room above is able to hold him\n"
	 								  + "up. As you scan his body, you notice a piece of paper in his\n"
	 								  + "pocket.";
	 					
	 					System.out.println(lastShownText);
	 				}
	 				else if(catHeard && !catInspected)
	 				{
	 					takeable.add(CAT);
	 					catInspected = true;
	 					
	 					Text.scrollLine("@\nYou open one of the cabinets and find a small kitten curled\n"
	 								  + "up inside,@ shivering.@ She immediately cowers back in fear as\n"
	 								  + "soon as she sees you,@ but she looks at you as if she\n"
	 								  + "recognizes you somehow.@ You stick your hand out to her,@ and\n"
	 								  + "she eventually comes up to nuzzle it.@ Whatever happened here\n"
	 								  + "clearly spooked her,@ but she seems to trust you.@", 60);
	 					
	 					cat.promptName(keyboard, player.getName());
	 					
	 					if(cat.withPlayer())
	 					{
	 						Commands.addPet();
	 						lastShownText = "You take " + cat.getName() + " with you. You can now PET her.";
	 					}
	 					else
	 						lastShownText = "Looks like you can go EAST now.";
	 					
	 					//Show new map
	 					printMap(true);
	 				}
	 				else if(manPresent)
	 					System.out.println("The man is still hanging around.");
	 				else
	 					System.out.println(Strings.NOTHING_TO_INSPECT);
	 				
	 				break;
	 			}
	 			case "use":
	 			{
	 				if(Commands.promptUse(usable).equals(KEYPAD))
	 				{
	 					if(!doorOpened)
	 						promptPasscode();
	 					else
	 						System.out.println("The door is already open.");
	 				}
	 				
	 				break;
	 			}
	 			case "take":
	 			{
	 				switch(Commands.promptTake(takeable))
	 				{
	 					case PAPER_SCRAP:
	 					{
	 						takeable.remove(PAPER_SCRAP);
	 						paperScrapTaken = true;
	 						inventory.add(note_scrap_a, '"' + String.valueOf(doorCode).substring(0,2) + "-\".");
	 						
	 						//Print new map
	 						printMap(false);
	 						
	 						lastShownText = "You cautiously pull the paper out of the man's pocket, half\n"
	 									  + "expecting him to come back to life. He definitely won't be\n"
	 									  + "needing this.\n\n"
	 									  + Strings.NOTE_SCRAP_A_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
	 						
	 						System.out.println(lastShownText);
	 						
	 						break;
	 					}
	 					case CAT:
	 					{
	 						cat.promptName(keyboard, player.getName());
	 						
	 						if(cat.withPlayer())
	 							takeable.remove(CAT);
	 						
	 						if(cat.withPlayer())
		 						lastShownText = "You take " + cat.getName() + " with you. You can now PET her.";
		 					else
		 						lastShownText = "Looks like you can go EAST now.";
		 					
		 					//Show new map
		 					printMap(true);
	 					}
	 				}
	 				
	 				break;
	 			}
	 			case "talk":
	 			{
	 				System.out.println("You can try talking to the man, but he probably won't\n"
	 								 + "respond.");
	 				break;
	 			}
	 			case "map":
	 			{
	 				printMap(true);
	 				break;
	 			}
	 			case "east":
	 			{
	 				if(doorOpened)
	 				{
	 					//If the player has not heard the cat
	 					if(!catHeard)
	 					{
	 						catHeard = true;
	 						
	 						lastShownText = "As you go to leave, you hear a strange noise come from\n"
									  	  + "somewhere in the room. It almost sounded like a cat meowing.";
	 						
	 						//Print new map
	 						printMap(true);
	 					}
	 					//If the player has heard the cat but not inspected it
	 					else if(catHeard && !catInspected && !alreadyLeft)
	 					{
	 						alreadyLeft = true;
	 						
	 						System.out.println("You ignore the sound and go EAST.\n\n");
	 						Operate.delay(500);
	 						return "secret_passageway";
	 					}
	 					//If none of the above conditions are true
	 					else
	 					{
	 						System.out.print("You go EAST.");
	 						
	 						//The first time the player moves with the cat, they will be shown a message that the cat follows them
	 						if(!shownCatFollowMessage && cat.withPlayer())
	 						{
	 							shownCatFollowMessage = true;
	 							System.out.println(' ' + cat.getName() + " follows.");
	 						}
	 						
	 						System.out.println("\n");
	 						Operate.delay(500);
	 						return "secret_passageway";
	 					}
	 				}
	 				else
	 				{
	 					System.out.println("The door leading EAST is locked by a keypad.");
	 					promptPasscode();
	 				}
	 				
	 				break;
	 			}
	 			case "west":
	 			{
	 				System.out.println("The door leading WEST is locked.");
	 				break;
	 			}
	 			case "south":
	 			{
	 				System.out.print("You go SOUTH.");
	 				
	 				//The first time the player moves with the cat, they will be shown a message that the cat follows them
					if(!shownCatFollowMessage && cat.withPlayer())
					{
						shownCatFollowMessage = true;
						System.out.println(' ' + cat.getName() + " follows.");
					}
					
					System.out.println("\n");
	 				Operate.delay(500);
	 				return "prison_stairs";
	 			}
	 			case "north":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
	
	//promptPassCode VOID METHOD: Prompts the player for the passcode (Called when using the keypad)
	private void promptPasscode()
	{
		//Get passcode from player
		System.out.print("\nEnter the passcode (type \"cancel\" to exit): ");
		String code = keyboard.nextLine();
		
		if(code.equalsIgnoreCase("cancel") || code.equals(""))
			return;
		
		try
		{	
			if(Integer.parseInt(code) == doorCode)
			{
				//Open le door
				doorOpened = true;
				directions.add("east");
				
				//If the player has not yet picked up the two note scraps, automatically give it to them
				if(!inventory.contains("note scrap a"))
				{
					manInspected = paperScrapTaken = true;
					takeable.remove(PAPER_SCRAP);
					inventory.add(note_scrap_a, '"' + String.valueOf(doorCode).substring(0,2) + "-\".");
				}
				
				if(!inventory.contains("note scrap b"))
				{
					cell_block_cavein.setPaperScrapTakenToTrue();
					inventory.add(note_scrap_b, "\"-" + String.valueOf(doorCode).substring(2) + "\".");
				}
				
				
				//Print new map
				printMap(false);
				
				lastShownText = "The door opened!\n\n"
							  + Strings.PATH_OPENED + "EAST.";
				
				System.out.println(lastShownText);
			}
			else
				System.out.println("Access Denied.");
		}
		catch(NumberFormatException e)
		{
			for(char i : code.toCharArray())
			{
				if(!Character.isDigit(i))
				{
					System.out.println("You can't find the \"" + i + "\" button anywhere.");
					break;
				}
			}
		}
	}
	
	//return doorCode
	public int doorCode()
	{
		return doorCode;
	}
	
	//Return doorOpened
	public boolean doorOpened()
	{
		return doorOpened;
	}
}