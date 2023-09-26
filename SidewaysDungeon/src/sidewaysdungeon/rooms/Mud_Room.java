package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Mud_Room extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		Mud_Room testRoom = new Mud_Room();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("mud_room"));
		System.out.println(result);
	}
	
	//Room items
	final private String PORTRAIT = "portrait";		//take
	
	//Room variables
	private boolean portraitInspected = false;
	private boolean portraitTaken = false;
	
	//Maps
	final private String MAP_INITIAL = "-----------\n"
									 + "|         |\n"
									 + "=    o    =\n"
									 + "-||-------|\n"
									 + "|  ||||   |\n"
									 + "-------===-\n"
									 + "        ^";
	
	final private String MAP_PORTRAIT_INSPECTED = "-----------\n"
												+ "|         |\n"
											    + "=    o    =\n"
											 	+ "-||--t----|\n"
											    + "|  ||||   |\n"
											    + "-------===-\n"
											    + "        ^";
	
	//Constructor
	public Mud_Room()
	{
		roomName = "Mud Room";
		fearMultiplier = 0.3f;
		
		//Add directions
		directions.add("west");
		directions.add("east");
		directions.add("up");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		if(portraitInspected && !portraitTaken)
			return MAP_PORTRAIT_INSPECTED;
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
	 	lastShownText = "You come into the mud room. It is engulfed by a deep\n"
	 				  + "blacklight. Muddly clothes are hung up on the wall and a set\n"
	 				  + "of stairs go UP to the second floor.";
	 	
	 	System.out.println(lastShownText);
	 	
	 	//Update the cat's fear
	 	cat.updateFear(fearMultiplier);
	 	
	 	//Get commands from player
	 	while(true)
	 	{
	 		String command = Commands.promptCommand(keyboard, true, inventory);
	 		
	 		//Intercept commands
	        if(command.length() >= 2 && command.substring(0,2).equals("#&"))	//Load entered room
	        	return command.substring(2);
	        else if(command.equals("##"))										//Reload this room
	        	return "mud_room";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					 + "\n"
	        					 + "portraitInspected: " + portraitInspected + "\n"
	        					 + "portraitTaken: " + portraitTaken);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 			{
	 				if(!portraitInspected)
	 				{
	 					portraitInspected = true;
	 					takeable.add(PORTRAIT);
	 					
	 					lastShownText = Strings.NEW_DISCOVERY + "There's a portrait wedged into a space under the stairs.";
	 					
	 					//Print new map
	 					printMap(true);
	 				}
	 				else
	 					System.out.println(Strings.NOTHING_TO_INSPECT);
	 				
	 				break;
	 			}
	 			case "use":
	 			{
	 				Commands.promptUse(usable);
	 				break;
	 			}
	 			case "take":
	 			{
	 				if(Commands.promptTake(takeable).equals(PORTRAIT))
	 				{
	 					portraitTaken = true;
	 					
	 					takeable.remove(PORTRAIT);
	 					inventory.remove(Strings.BLANK_PAPER_NAME);
	 					inventory.add(yellow_lady);
	 					
	 					dining_room.setPaperTakenToTrue();
	 					
	 					lastShownText = "You take the portrait.\n\n"
	 								  + Strings.LADY_IN_YELLOW_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
	 					
	 					//Print new map
	 					printMap(true);
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
	 			case "east":
	 			{
	 				System.out.println("You go EAST.\n\n");
	 				Operate.delay(500);
	 				return "house_foyer";
	 			}
	 			case "west":
	 			{
	 				System.out.println("You go WEST.\n\n");
	 				Operate.delay(500);
	 				return "dining_room";
	 			}
	 			case "south":
	 			case "up":
	 			{
	 				System.out.println("You ascend the staircase and go UP.\n\n");
	 				Operate.delay(500);
	 				return "upstairs_hallway";
	 			}
	 			case "north":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
}