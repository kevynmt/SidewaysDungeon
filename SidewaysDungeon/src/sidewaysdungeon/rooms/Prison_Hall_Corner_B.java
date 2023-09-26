package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Prison_Hall_Corner_B extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		Prison_Hall_Corner_B testRoom = new Prison_Hall_Corner_B();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("prison_hall_corner_b"));
		System.out.println(result);
	}
	
	//Room variables
	
	//Maps
	private final String MAP = "   -||-\n"
							 + "   |  |\n"
							 + "---|  |\n"
							 + "=   o |\n"
							 + "-------\n";
	
	//Constructor
	public Prison_Hall_Corner_B()
	{
		roomName = "Hallway";
		fearMultiplier = 1.2f;
		
		//Add directions
		directions.add("west");
		directions.add("north");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		return MAP;
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
	 	lastShownText = "You move down the hallway. A cold breeze comes in from a\n"
	 				  + "door to the NORTH.";
	 	
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
	        	return "prison_hall_corner_b";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits);
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
	 				System.out.println("You move NORTH.\n\n");
	 				Operate.delay(500);
	 				return "cell_block_cavein";
	 			}
	 			case "west":
	 			{
	 				System.out.println("You move WEST.\n\n");
	 				Operate.delay(500);
	 				return "prison_hallway";
	 			}
	 			case "east":
	 			case "south":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
}