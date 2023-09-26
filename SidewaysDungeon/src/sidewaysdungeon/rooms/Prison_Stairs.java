package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Prison_Stairs extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		
		String prevRoom;
		prevRoom = "prison_hallway";
		//prevRoom = "house_kitchen";
		
		Prison_Stairs testRoom = new Prison_Stairs();
		do {
			result = testRoom.playRoom(prevRoom);
		} while(result.equals("prison_stairs"));
		System.out.println(result);
	}
	
	//Maps
	final private String MAP = " ^\n"
							 + "|=|\n"
							 + "|o|\n"
							 + "|=|\n"
							 + " v\n";
	
	//Constructor
	public Prison_Stairs()
	{
		roomName = "Stairs";
		fearMultiplier = 1.5f;
		
		//Add directions
		directions.add("up");
		directions.add("down");
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
	 	if(prevRoom.equals("prison_hallway"))
	 		lastShownText = "You ascend the staircase.";
	 	else
	 		lastShownText = "You descend the staircase.";
	 	
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
	        	return "prison_stairs";
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
	 			case "up":
	 			case "north":
	 			{
	 				if(prevRoom.equals("house_kitchen"))
	 					System.out.println("You turn around and go back UP.\n\n");
	 				else
	 					System.out.println("You go UP.\n\n");
	 				
	 				Operate.delay(500);
	 				return "house_kitchen";
	 			}
	 			case "down":
	 			case "south":
	 			{
	 				if(prevRoom.equals("prison_hallway"))
	 					System.out.println("You turn around and go back DOWN.\n\n");
	 				else
	 					System.out.println("You go DOWN.\n\n");
	 				
	 				Operate.delay(500);
	 				return "prison_hallway";
	 			}
	 			case "east":
	 			case "west":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase());
	 		}
	 	}
	}
}