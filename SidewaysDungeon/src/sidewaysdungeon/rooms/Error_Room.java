package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

//WHY ARE YOU HERE?!?!?!
public class Error_Room extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		Error_Room testRoom = new Error_Room();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("error_room"));
		System.out.println(result);
	}
	
	//Room items
	Item hammer = new Item("hammer", "You look into the hammer. In the reflection, you see\n"
								   + "an image of yourself with your skull bashed in and battered.\n"
								   + "Everyone is walking past you, as if nothing is happening, as\n"
								   + "if you're not even there. This image is startling to you at\n"
								   + "first, but you eventually feel a sense of calmness from it...");			//take
	
	//Constructor
	public Error_Room()
	{
		roomName = " ##ERROR## ";
		
		//Add takeable items
		takeable.add("hammer");
		
		//Add directions
		directions.add("north");
		directions.add("south");
		directions.add("east");
		directions.add("west");
		directions.add("up");
		directions.add("down");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		return "\to\n";
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
		//Tell the game that a fun event has happened
		Operate.funEventHappened();
		
		//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Get commands from player
	 	while(true)
	 	{
	 		String command = Commands.promptCommand(keyboard, false, emptyInventory);
	 		
	 		//Intercept commands
	        if(command.length() >= 2 && command.substring(0,2).equals("#&"))	//Load entered room
	        	return command.substring(2);
	        else if(command.equals("##"))										//Reload this room
	        	return "error_room";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("{}");
	       	 	continue;
	        }
	        
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 				System.out.println(Strings.NOTHING_TO_INSPECT);
	 			case "use":
	 			{
	 				Commands.promptUse(usable);
	 				break;
	 			}
	 			case "take":
	 			{
	 				if(Commands.promptTake(takeable).equals("hammer"))
	 				{
	 					emptyInventory.add(hammer);
	 					takeable.remove("hammer");
	 					
	 					System.out.println("\n\nHAMMER " + Strings.HAD_BEEN_ADDED);
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
	 			case "north":
	 			case "east":
	 			case "west":
	 			case "south":
	 			case "up":
	 			case "down":
	 			{
	 				//Clear the console screen
	 				System.out.print("\033[H\033[2J");
	 			    System.out.flush();
	 			    
	 			    //Return the player to the upstairs bathroom
	 			    return "upstairs_bathroom";
	 			}
	 		}
	 	}
	}
}