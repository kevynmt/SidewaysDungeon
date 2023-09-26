package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Prison_Hallway extends Room implements RoomTemplate {
	//Room variables
	private int numInspectAttempts = 0;			//If the player inspects the room three times before reading the note, they find the hidden panel anyway
	
	private boolean panelOpened = false;
	private boolean panelInspected = false;
	private boolean noteReadDEBUG = false;	//DEBUG: Be sure to set to false when done
	
	//Room items
	final private String PANEL = "panel";		//use
	
	//Maps
	private final String MAP_INITIAL = "-------------\n"
									 + "=     o     =\n"
									 + "-------------\n";
	
	private final String MAP_PANEL_INSPECTED = "-----uuu-----\n"
											 + "=     o     =\n"
											 + "-------------\n";
	
	private final String MAP_PANEL_OPENED = "-----| |-----\n"
										  + "=     o     =\n"
										  + "-------------\n";
	
	//Contructor
	public Prison_Hallway()
	{
		roomName = "Hallway";
		fearMultiplier = 1.2f;
		
		//Add directions
		directions.add("west");
		directions.add("east");
	}
	
	@Override
	public String map() {
		if(panelOpened)
			return MAP_PANEL_OPENED;
		else if(panelInspected)
			return MAP_PANEL_INSPECTED;
		else
			return MAP_INITIAL;
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
	 	
	 	if(panelOpened)
	 		lastShownText += " A staircase leads up to the NORTH.";
	 	else if(panelInspected)
	 		lastShownText += " You remember there is a hidden\n"
	 					   + "panel in the wall to the NORTH.";
	 	
	 	//show the hidden item of interest tutorial if the player has read the note and not inspected the panel
	 	if((cell_block_cavein.noteRead() || noteReadDEBUG) && !panelInspected)
	 		lastShownText += Strings.HIDDEN_IOI_TUTORIAL;
	 	
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
	        	return "prison_hallway";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					 + "numInspectAttempts: " + numInspectAttempts + "\n"
	        					 + "\n"
	        					 + "panelOpened: " + panelOpened + "\n"
	        					 + "panelInspected: " + panelInspected + "\n"
	        					 + "noteRead (cell_block_cavein): " + cell_block_cavein.noteRead() + "\n"
	        					 + "[[noteReadDEBUG]]: " + noteReadDEBUG);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 			{	
	 				//The player can inspect the panel if they have read the note or inspected the room 2 times
	 				if((cell_block_cavein.noteRead() || numInspectAttempts >= 2 || noteReadDEBUG) && !panelInspected)
	 				{
	 					panelInspected = true;
	 					usable.add(PANEL);
	 					
	 					if(cell_block_cavein.noteRead() || noteReadDEBUG)
	 					{
	 						System.out.print("You inspect the room.");
	 						Operate.delay(200);
	 						Text.scrollLine("@...@", 500);
	 					}
	 					
	 					//Print new map
	 					printMap(false);
	 					
	 					//If the player found the panel by reading the note, "The note was right!" will be displayed
	 					//If the player found the panel in any other way, "Wait, what?!" will be displayed
	 					if(cell_block_cavein.noteRead() || noteReadDEBUG)
	 						lastShownText = Strings.NEW_DISCOVERY + "The note was right! There's a hidden panel in the wall\n"
	 														 + "here.";
	 					else
	 						lastShownText = Strings.NEW_DISCOVERY + "Wait, what?! There's a hidden panel in the wall here.";
	 					
	 					System.out.println(lastShownText);
	 				}
	 				else
	 				{
	 					System.out.println(Strings.NOTHING_TO_INSPECT);
	 					
	 					//If the note has not already been read and panel not already inspected, increment numInspectAttempts by 1
	 					if(!cell_block_cavein.noteRead() && !panelInspected)
	 						numInspectAttempts++;
	 				}
	 				
	 				break;
	 			}
	 			case "use":
	 			{
	 				switch(Commands.promptUse(usable))
	 				{
	 					case PANEL:
	 					{
	 						panelOpened = true;
	 						usable.remove(PANEL);
	 						directions.add("north");
	 						
	 						//Print new map
	 						lastShownText = "As you feel around the panel, you accidentally push a hidden\n"
	 									  + "button in the wall. The panel slides open, revealing a dark\n"
	 									  + "staircase leading up to God-knows-where.";
	 						
	 						//If the player read the note, the first message will be added. Else, the second message will be added
	 						if(cell_block_cavein.noteRead())
	 							lastShownText += " If that note was\n"
	 										   + "right, then this is the way out.";
	 						else
	 							lastShownText += " This must be the\n"
	 										   + "way out!";
	 									  
	 						lastShownText += "\n\n" + Strings.PATH_OPENED + "NORTH.";
	 						
	 						printMap(true);
	 					}
	 				}
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
	 			case "west":
	 			{
	 				System.out.println("You move WEST.\n\n");
	 				Operate.delay(500);
	 				return "prison_hall_corner_a";
	 			}
	 			case "east":
	 			{
	 				System.out.println("You move EAST.\n\n");
	 				Operate.delay(500);
	 				return "prison_hall_corner_b";
	 			}
	 			case "north":
	 			{
	 				if(panelOpened)
	 				{
	 					System.out.println("You move NORTH.\n\n");
	 					Operate.delay(500);
	 					return "prison_stairs";
	 				}
	 				else
	 					System.out.println(Strings.CANT_MOVE + "NORTH.");
	 				
	 				break;
	 			}
	 			case "south":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + ".");
	 		}
	 	}
	}
	
	//return panelInspected
	public boolean panelInspected()
	{
		return panelInspected;
	}

	//Use the main method to test the room
	public static void main(String[] args) {
		String result;
		Prison_Hallway testRoom = new Prison_Hallway();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("prison_hallway"));
		System.out.println(result);
	}
}
