package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

//Prison Cell B
public class Prison_Cell_B extends Room implements RoomTemplate {

	//Room variables
	private boolean tutorialShown = false;		//Whether or not the "map" tutorial has been shown to the player
	
	//Maps
	private final String MAP_INITIAL = "-------\n"
						             + "|   u |\n"
						             + "|  o  =\n"
						             + "|     |\n"
						             + "--||---\n"
						             + "|     |\n"
						             + "|     #\n"
						             + "|     |\n"
						             + "-------\n";
	
	private final String MAP_CELL_BLOCK_VISITED = "---------------\n"
									            + "|   u |   t   |\n"
									            + "|  o  =       |\n"
									            + "|     |       |\n"
									            + "|-||--|       |\n"
									            + "|     |       |\n"
									            + "|     #     u |\n"
									            + "|     |    [t]|\n"
									            + "---------uu----\n";

	private final String MAP_CLOTH_TAKEN = "---------------\n"
							             + "|   u |       |\n"
							             + "|  o  =       |\n"
							             + "|     |       |\n"
							             + "|-||--|       |\n"
							             + "|     |       |\n"
							             + "|     #     u |\n"
							             + "|     |    [t]|\n"
							             + "---------uu----\n";
	
	private final String MAP_TORCH_COOLED = "---------------\n"
							             + "|   u |       |\n"
							             + "|  o  =       |\n"
							             + "|     |       |\n"
							             + "|-||--|       |\n"
							             + "|     |       |\n"
							             + "|     #       |\n"
							             + "|     |     t |\n"
							             + "---------uu----\n";

	private final String MAP_TORCH_TAKEN = "---------------\n"
							             + "|   u |       |\n"
							             + "|  o  =       |\n"
							             + "|     |       |\n"
							             + "|-||--|       |\n"
							             + "|     |       |\n"
							             + "|     #       |\n"
							             + "|     |       |\n"
							             + "---------uu----\n";

	private final String MAP_WEBS_BURNED = "---------------\n"
							             + "|   u |       |\n"
							             + "|  o  =       |\n"
							             + "|     |       |\n"
							             + "|-||--|       |\n"
							             + "|     |       |\n"
							             + "|     #       |\n"
							             + "|     |       |\n"
							             + "---------||----\n";
	
	//Constructor
	public Prison_Cell_B()
	{
		roomName = "Prison Cell";
		fearMultiplier = 1.2f;
		
		//Add usable items
		usable.add("bucket");
		
		//Add directions
		directions.add("east");
		directions.add("south");
	}
	
	@Override
	//map STRING METHOD: Returns the map
	public String map() {
		if(cell_block.getNumVisits() > 0)
		{
			if(cell_block.websBurned())
				return MAP_WEBS_BURNED;
			else if(cell_block.torchTaken())
				return MAP_TORCH_TAKEN;
			else if(cell_block.torchCooled())
				return MAP_TORCH_COOLED;
			else if(cell_block.clothTaken())
				return MAP_CLOTH_TAKEN;
			else
				return MAP_CELL_BLOCK_VISITED;
		}
		else
			return MAP_INITIAL;
	}

	@Override
	//printMap VOID METHOD: Prints the map, map legend, the current exits, and the last shown text
	public void printMap(boolean showLastText) {
		System.out.println();
		System.out.println(map());
		System.out.println(Strings.MAP_LEGEND);
		System.out.println(exits());
		   
		if(showLastText)
			System.out.println(lastShownText);
	}

	@Override
	//Play room
	public String playRoom(String prevRoom) {
		
		updateStats();
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Print room description
	 	if(!inventory.contains("torch"))
	 	{
	 		lastShownText = "It's a prison cell that is almost identical to yours. In the\n"
			   	 		  + "darkness you can barely see a bucket full of water in the\n"
			   	 		  + "corner.";
	 		System.out.println(lastShownText);
	 	}
	 	else
	 	{
	 		lastShownText = "It's a prison cell that is almost identical to yours. A bucket\n"
					 	  + "full of water sits in the corner.";
	 		System.out.println(lastShownText);
	 	}
	 	
	 	//Update the cat's fear
	 	cat.updateFear(fearMultiplier);
	 	
	 	//Get commands from player
	 	while(true)
	 	{
	 		//Show tutorial
	 		if(!tutorialShown)
	 		{
	 			//System.out.println("\nTutorial: Type \"map\" to show the map.");
	 			tutorialShown = true;
	 		}
	 		
	 		String command = Commands.promptCommand(keyboard, false, inventory);
	 		
	 		//Intercept commands
	        if(command.length() >= 2 && command.substring(0,2).equals("#&"))	//Load entered room
	        	return command.substring(2);
	        else if(command.equals("##"))										//Reload this room
	        	return "prison_cell_a";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	       	 	System.out.println("numVisits: " + numVisits);
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
	 				if(Commands.promptUse(usable).equals("bucket"))
	 				{
	 					String itemToUse = inventory.promptItem("bucket");
	 					
	 					switch (itemToUse)
	 					{
	 						case Strings.CLOTH_NAME:
	 						{
	 							//Replace the cloth with the wet cloth
	 							inventory.replace(Strings.CLOTH_NAME, wet_cloth);
	 							
	 							lastShownText = "You dunk the cloth into the water. It's dripping wet\n"
										 	  + "when you pull it out.\n\n"
										 	  + Strings.WET_CLOTH_NAME.toUpperCase() + " has been added to your inventory.";
	 							System.out.println(lastShownText);
	 							break;
	 						}
	 						case Strings.TORCH_NAME:
	 						{
	 							//SOFT LOCK PREVENTION: The player is not allowed to use the torch on the bucket if the webs are not burned
	 							if(cell_block.websBurned())
	 							{
		 							//Remove the torch from the player's inventory and add the unlit torch
		 							inventory.replace(Strings.TORCH_NAME, unlit_torch);
		 							
		 							lastShownText = "You dip the torch into the water. The flame was\n"
											      + "extinguished.\n\n"
											      + Strings.UNLIT_TORCH_NAME.toUpperCase() + " has been added to your inventory.";
		 							System.out.println(lastShownText);
	 							}
	 							else
	 								System.out.println("That wouldn't be a good idea.");
	 							break;
	 						}
	 						case "":
	 							break;
	 						default:
	 							System.out.println(Strings.CANT_USE_THAT_HERE);
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
	 			case "east":
	 			{
	 				System.out.println("You move EAST.\n\n");
	 				Operate.delay(500);
	 				return "cell_block";
	 			}
	 			case "south":
	 			{
	 				System.out.println("You sqeeze through the wall and move SOUTH.\n\n");
	 				Operate.delay(500);
	 				return "prison_cell_a";
	 			}
	 			case "north":
	 			case "west":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + ".");
	 		}
	 	}
	}
	
	//Use the main method to test the room
	public static void main(String[] args) {
		String result;
		Prison_Cell_B testRoom = new Prison_Cell_B();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("prison_cell_b"));
		System.out.println(result);
	}

}
