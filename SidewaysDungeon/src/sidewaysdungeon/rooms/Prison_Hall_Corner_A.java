package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Prison_Hall_Corner_A extends Room implements RoomTemplate {
	//Room variables
	
	//Maps
	private final String MAP = "-||-\n"
							 + "|  |\n"
							 + "|  |---\n"
							 + "| o   =\n"
							 + "-------\n";
	
	//Contructor
	public Prison_Hall_Corner_A()
	{
		roomName = "Hallway";
		fearMultiplier = 1.2f;
		
		//Add directions
		directions.add("north");
		directions.add("east");
	}
	
	@Override
	public String map() {
		return MAP;
	}

	@Override
	public void printMap(boolean showLastText) {

		System.out.println();
		System.out.println(map());
		System.out.println(Strings.MAP_LEGEND);
		System.out.println(exits());
		   
		if(showLastText)
			System.out.println(lastShownText);
	}

	@Override
	public String playRoom(String prevRoom) {
	 	updateStats();
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Print room description
	 	lastShownText = "You move down the hallway.";
	 	
	 	if(numVisits == 1)
	 		lastShownText += " A loud CRASH can be heard from\n"
	 					   + "somewhere.";
	 							 
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
	        	return "prison_hall_corner_a";
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
	 				return "cell_block";
	 			}
	 			case "east":
	 			{
	 				System.out.println("You move EAST.\n\n");
	 				Operate.delay(500);
	 				return "prison_hallway";
	 			}
	 			case "west":
	 			case "south":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + ".");
	 		}
	 	}
	}

	//Use the main method to test the room
	public static void main(String[] args) {
		String result;
		Prison_Hall_Corner_A testRoom = new Prison_Hall_Corner_A();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("prison_hall_corner_a"));
		System.out.println(result);
	}
}
