package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class House_Foyer extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		House_Foyer testRoom = new House_Foyer();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("house_foyer"));
		System.out.println(result);
	}
	
	//Room variables
	private boolean doorOpened = false;
	private boolean shownDoorOpenedMessage = false;
	
	//Maps
	final private String MAP_INITIAL = "       |###|       \n"
									 + "-------|   |-------\n"
									 + "|                 |\n"
									 + "=                 |\n"
									 + "|        o        =\n"
									 + "|                 |\n"
									 + "----|         |----\n"
									 + "    |---| |---|\n";
	
	final private String MAP_DOOR_OPENED = "       || ||       \n"
										 + "-------|   |-------\n"
										 + "|                 |\n"
										 + "=                 |\n"
										 + "|        o        =\n"
										 + "|                 |\n"
										 + "----|         |----\n"
										 + "    |---| |---|\n";
	
	//Constructor
	public House_Foyer()
	{
		roomName = "Foyer";
		fearMultiplier = -0.2f;
		
		//Add directions
		directions.add("west");
		directions.add("east");
		directions.add("south");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		if(doorOpened)
			return MAP_DOOR_OPENED;
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
	 	if(doorOpened && !shownDoorOpenedMessage)
	 	{
	 		shownDoorOpenedMessage = true;
	 		
	 		lastShownText = "You come back into the foyer. The door to the NORTH is open\n"
	 					  + "now.\n\n"
	 					  + Strings.PATH_OPENED + "NORTH.";
	 	}
	 	else
		 	lastShownText = "You come into the foyer. A golden chandelier hangs from the\n"
		 				  + "ceiling, and a large, ornate door towers to the NORTH.";
	 	
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
	        	return "house_foyer";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					 + "\n"
	        					 + "doorOpened: " + doorOpened + "\n"
	        					 + "shownDoorOpenedMessage: " + shownDoorOpenedMessage);
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
	 				Commands.promptUse(usable);
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
	 				if(!doorOpened)
	 					System.out.println("The door to the NORTH is closed, and judging by how heavy it\n"
	 									 + "looks, there's no way you're going to be able to get it open.");
	 				else
	 				{
	 					System.out.println("You go NORTH.\n\n");
	 					Operate.delay(500);
	 					return "endless_hallway";
	 				}
	 				
	 				break;
	 			}
	 			case "east":
	 			{
	 				System.out.println("You go EAST.\n\n");
	 				Operate.delay(500);
	 				return "living_room";
	 			}
	 			case "west":
	 			{
	 				System.out.println("You go WEST.\n\n");
	 				Operate.delay(500);
	 				return "mud_room";
	 			}
	 			case "south":
	 			{
	 				System.out.println("You go SOUTH.\n\n");
	 				Operate.delay(500);
	 				return "house_library";
	 			}
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
	
	//setDoorOpenedToTrue VOID METHOD: Set doorOpened to true, just in case the name caused any confusion
	public void setDoorOpenedToTrue()
	{
		directions.add(0, "north");
		doorOpened = true;
	}
}