package sidewaysdungeon.rooms;

public interface RoomTemplate
{
	//Use the main method to test the room
	
	//Room items
	
	//Room variables
	
	//Maps
	
	//Constructor
	
	//map STRING METHOD: Returns the current map to print
	String map();
	
	//printMap VOID METHOD: Prints the map, map legend, the current exits, and the last shown text
	void printMap(boolean showLastText);
	
	/*
		System.out.println();
		System.out.println(map());
		System.out.println(Strings.MAP_LEGEND);
		System.out.println(exits());
		   
		if(showLastText)
			System.out.println(lastShownText);
	*/
	
	//playRoom STRING METHOD: Code that actually plays the room
	String playRoom(String prevRoom);
	
	/*
	 	updateStats();
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Print room description
	 	 //MAX CHAR COUNT FOR DESCRIPTIONS: 60
	 	
	 	//Update the cat's fear
	 	cat.updateFear(fearMultiplier);
	 	
	 	//Get commands from player
	 	while(true)
	 	{
	 		String command = Commands.promptCommand();
	 		
	 		//Intercept commands
	        if(command.length() >= 2 && command.substring(0,2).equals("#&"))	//Load entered room
	        	return command.substring(2);
	        else if(command.equals("##"))										//Reload this room
	        	return {{ROOM ID}};
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
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
	 			case "east":
	 			case "west":
	 			case "south":
	 			case "up":
	 			case "down":
	 		}
	 	}
	 */
}