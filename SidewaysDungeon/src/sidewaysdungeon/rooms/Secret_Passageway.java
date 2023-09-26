package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Secret_Passageway extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		Secret_Passageway testRoom = new Secret_Passageway();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("secret_passageway"));
		System.out.println(result);
	}
	
	//Room variables
	private int numLockedDoorAttempts = 0;
	
	private boolean doorUnlocked = true;
	private boolean doorOpenedDEBUG = false;	//DEBUG: Remember to set to false when done
	
	//Room items
	private String DOOR = "door";
	
	//Maps
	private final String MAP_INITIAL = "----------||-\n"
									 + "=     o     |\n"
									 + "-------------\n";
	
	private final String MAP_DOOR_LOCKED = "----------||-\n"
										 + "#     o     |\n"
										 + "-------------\n";
	
	//Constructor
	public Secret_Passageway()
	{
		roomName = "Secret Passageway";
		fearMultiplier = 0.5f;
		
		//Add directions
		directions.add("west");
		directions.add("north");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		if(!doorUnlocked)
			return MAP_DOOR_LOCKED;
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
	 	lastShownText = "You move through the secret passageway.";
	 	
	 	if((house_library.doorOpened() || doorOpenedDEBUG)/* && !prison_cell_a.portraitTaken()*/)	//TODO: Uncomment this out when this method is added
	 		lastShownText += " The darkness in this\n"
	 					   + "room feels much more powerful than before.";
	 	
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
	        	return "secret_passageway";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					 + "numLockedDoorAttempts: " + numLockedDoorAttempts + "\n"
	        					 + "\n"
	        					 + "doorUnlocked: " + doorUnlocked + "\n"
	        					 + "doorOpened (house_library): " + house_library.doorOpened() + "\n"
	        					 + "[[doorOpenedDEBUB]]: " + doorOpenedDEBUG);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 			{
	 				System.out.println(Strings.NOTHING_TO_INSPECT);
	 				break;
	 			}
	 			case "use":
	 			{
	 				if(Commands.promptUse(usable).equals(DOOR))
	 					useDoor();
	 				
	 				break;
	 			}
	 			case "take":
	 			{
	 				Commands.promptTake(takeable);
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
	 			case "north":
	 			{
	 				System.out.println("You move NORTH.\n\n");
	 				Operate.delay(500);
	 				return "house_library";
	 			}
	 			case "west":
	 			{
	 				if(doorUnlocked)
	 				{
		 				System.out.println("You move WEST.\n\n");
		 				Operate.delay(500);
		 				return "house_kitchen";
	 				}
	 				else
	 				{
	 					numLockedDoorAttempts++;
	 					
	 					if(numLockedDoorAttempts == 1)
	 					{
	 						directions.remove("west");
	 						directions.add(0, "west (blocked)");
	 						
	 						System.out.println("Huh, the door to the WEST must have been locked at some point.\n"
	 										 + "You can't go through it.");
	 					}
	 					else
	 						System.out.println("The door to the WEST is locked.");
	 					
	 					useDoor();
	 				}
	 				
	 				break;
	 			}
	 			case "east":
	 			case "south":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
	
	//useDoor VOID METHOD: Prompts the player for an item to use on the door
	private void useDoor()
	{
		switch(inventory.promptItem(DOOR))
		{
			case "":
				break;
			case Strings.KEY_NAME:
			{
				doorUnlocked = true;
				inventory.remove(Strings.KEY_NAME);
				
				directions.remove("west (blocked)");
				directions.add(0, "west");
				
				usable.remove(DOOR);
				
				lastShownText = "You use the key to unlock the door.\n\n"
							  + Strings.PATH_OPENED + "WEST.";
				
				//Print new map
				printMap(true);
				
				break;
			}
			case Strings.KNIFE_NAME:
			{
				System.out.println("You can't jimmy the door open.");
				break;
			}
			default:
				System.out.println(Strings.CANT_USE_THAT_HERE);
		}
	}
	
	//setDoorUnlockedToFalse: Sets door unlocked to false
	public void setDoorUnlockedToFalse()
	{
		usable.add(DOOR);
		doorUnlocked = false;
	}
}