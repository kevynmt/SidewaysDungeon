package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Upstairs_Hallway extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		String prevRoom = "cmud_room";
		Upstairs_Hallway testRoom = new Upstairs_Hallway();
		do {
			result = testRoom.playRoom(prevRoom);
		} while(result.equals("upstairs_hallway"));
		System.out.println(result);
	}
	
	//Room variables
	private boolean portraitTakenDEBUG = false;		//DEBUG: Make sure this is set to false when done
	
	//Maps
	private final String MAP = "  v\n"
							 + "-===-------\n"
						     + "|   ||||  |\n"
						     + "--------||--------\n"
						     + "|        o       =\n"
						     + "------------||----\n";
	
	//Constructor
	public Upstairs_Hallway()
	{
		roomName = "Upstairs Hallway";
		fearMultiplier = 1.5f;
		
		//Add directions
		directions.add("down");
		directions.add("south");
		directions.add("east");
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
	 	lastShownText = "You come " + (prevRoom.equals("mud_room") ? "up to" : "into") + " the upstairs hallway.";

		if(!prison_cell_a.portraitTaken() && !portraitTakenDEBUG)
			lastShownText += " The room is shaking\n"
	 				  	   + "from a rumbling noise coming from nearby.";
	 	
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
	        	return "upstairs_hallway";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					 + "\n"
	        					 + "portraitTaken (prison_cell_a): " + prison_cell_a.portraitTaken() + "\n"
	        					 + "[[portraitTakenDEBUG]]: " + portraitTakenDEBUG);
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
	 			case "down":
	 			{
	 				System.out.println("You go DOWN.\n\n");
	 				Operate.delay(500);
	 				return "mud_room";
	 			}
	 			case "east":
	 			{
	 				System.out.println("You go EAST.\n\n");
	 				Operate.delay(500);
	 				return "upstairs_bedroom";
	 			}
	 			case "south":
	 			{
	 				System.out.println("You go SOUTH.\n\n");
	 				Operate.delay(500);
	 				return "upstairs_bathroom";
	 			}
	 			case "west":
	 			case "up":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
}