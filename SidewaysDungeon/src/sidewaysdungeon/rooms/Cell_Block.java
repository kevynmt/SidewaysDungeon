package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Cell_Block extends Room implements RoomTemplate {
	//Room variables
	private int numSouthAttempts = 0;			//Number of times the player tried to move south while the web is still in place
	private int numTorchGrabAttempts = 0;		//Number of times the player tried to grab the torch when it was still hot
	
	private boolean takeTried = false;			//Whether or not the player has typed the "take" command. Determines whether or not the "take" tutorial is displayed
	private boolean torchCooled = false;		//Whether or not the player has cooled the torch. Determines which map is displayed
	private boolean torchTaken = false;			//Whether or not the player has taken the torch. Determines which map is displayed
	private boolean clothTaken = false;			//Whether or not the player has taken the cloth. Determines which map is displayed
	private boolean websBurned = false;			//Whether or not the player has burned the web. Determines which map is displayed
	
	//Maps
	private final String MAP_INITIAL = "---------------\n"
						             + "|   u |   t   |\n"
						             + "|     =       |\n"
						             + "|     |       |\n"
						             + "|-||--|   o   |\n"
						             + "|     |       |\n"
						             + "|     #     u |\n"
						             + "|     |    [t]|\n"
						             + "---------uu----\n";
	
	private final String MAP_CLOTH_TAKEN = "---------------\n"
							             + "|   u |       |\n"
							             + "|     =       |\n"
							             + "|     |       |\n"
							             + "|-||--|   o   |\n"
							             + "|     |       |\n"
							             + "|     #     u |\n"
							             + "|     |    [t]|\n"
							             + "---------uu----\n";
	
	private final String MAP_TORCH_COOLED = "---------------\n"
							              + "|   u |       |\n"
							              + "|     =       |\n"
							              + "|     |       |\n"
							              + "|-||--|   o   |\n"
							              + "|     |       |\n"
							              + "|     #       |\n"
							              + "|     |     t |\n"
							              + "---------uu----\n";
	
	private final String MAP_TORCH_TAKEN = "---------------\n"
							             + "|   u |       |\n"
							             + "|     =       |\n"
							             + "|     |       |\n"
							             + "|-||--|   o   |\n"
							             + "|     |       |\n"
							             + "|     #       |\n"
							             + "|     |       |\n"
							             + "---------uu----\n";
	
	private final String MAP_WEBS_BURNED = "---------------\n"
							             + "|   u |       |\n"
							             + "|     =       |\n"
							             + "|     |       |\n"
							             + "|-||--|   o   |\n"
							             + "|     |       |\n"
							             + "|     #       |\n"
							             + "|     |       |\n"
							             + "---------||----\n";
	
	//Constructor
	public Cell_Block()
	{
		roomName = "Cell Block";
		fearMultiplier = 1.0f;
		
		//Add useable items
		usable.add(Strings.TORCH_NAME);
		usable.add("web");
		
		//Add takeable items
		takeable.add(Strings.TORCH_NAME);
		takeable.add(Strings.CLOTH_NAME);
		
		//Add directions
		directions.add("west");
		directions.add("south");
	}
	
	@Override
	public String map() {
		if(websBurned)
			return MAP_WEBS_BURNED;
		else if(torchTaken)
			return MAP_TORCH_TAKEN;
		else if(torchCooled)
			return MAP_TORCH_COOLED;
		else if(clothTaken)
			return MAP_CLOTH_TAKEN;
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
	public String playRoom(String prevRoom)
	{
		updateStats();
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Print room description
	 	compileRoomDescription();	//This room uniquely uses a separate method to compile text.
	 	
	 	System.out.println(lastShownText);
	 	
	 	//Update the cat's fear
	 	cat.updateFear(fearMultiplier);
	 	
	 	//Get commands from player
	 	while(true)
	 	{
	 		if(!takeTried && numSouthAttempts > 0)
		 		System.out.println(Strings.TAKE_TUTORIAL);
	 		
	 		String command = Commands.promptCommand(keyboard, false, inventory);
	 		
	 		//Intercept commands
	        if(command.length() >= 2 && command.substring(0,2).equals("#&"))	//Load entered room
	        	return command.substring(2);
	        else if(command.equals("##"))										//Reload this room
	        	return "cell_block";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					 + "numSouthAttempts: " + numSouthAttempts + "\n"
	        					 + "numTorchGrabAttempts: " + numTorchGrabAttempts + "\n"
	        					 + "\n"
	        					 + "takeTried: " + takeTried + "\n"
	        					 + "torchCooled: " + torchCooled + "\n"
	        					 + "torchTaken: " + torchTaken + "\n"
	        					 + "clothTaken: " + clothTaken + "\n"
	        					 + "websBurned: " + websBurned);
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
	 				switch(Commands.promptUse(usable))
	 				{
	 					case "web":
	 					{
	 						if(inventory.numItems() == 0)
	 						{
	 							System.out.println("You attempt to remove the web by hand, but it's no use; it's way\n"
										  		 + "too thick! You're going to need something to help you.");
	 						}
	 						else
	 						{
	 							switch (inventory.promptItem("web"))
	 							{
	 								case "":
	 									break;
	 								/*case Strings.CLOTH_NAME:
	 								case Strings.WET_CLOTH_NAME:
	 								{
	 									System.out.println("You try to remove the web with the cloth, but you can't even\n"
												  		 + "figure out where to start. It looks like you need something else.");
	 									break;
	 								}*/
	 								case Strings.TORCH_NAME:
	 								{
	 									websBurned = true;
	 									usable.remove("web");
	 									
	 									if(directions.contains("south (blocked)"))
	 									{
	 										directions.remove("south (blocked)");
	 										directions.add("south");
	 									}
	 									
	 									//Print new map
	 									printMap(false);
	 									lastShownText = "You just barely hold the torch to the web, and it immediately\n"
	 												  + "bursts into flames! It burns away amidst a sea of smoke and embers.\n"
	 												  + "Time to find a way out of here!\n\n"
	 												  + "A path has opened to the SOUTH.";
	 									System.out.println(lastShownText);
	 									break;
	 								}
	 								default:
	 									System.out.println(Strings.CANT_USE_THAT_HERE);
	 							}
	 						}
	 						break;
	 					}
	 					case Strings.TORCH_NAME:
	 					{
	 						switch(inventory.promptItem(Strings.TORCH_NAME))
	 						{
	 							case "":
	 								break;
	 							case Strings.WET_CLOTH_NAME:
	 							{
	 								torchCooled = true;
	 								usable.remove(Strings.TORCH_NAME);
	 								inventory.remove(Strings.WET_CLOTH_NAME);
	 								
	 								//Print new map
	 								printMap(false);
	 								lastShownText = "As you wrap the cloth around the handle of the torch, a satisfying\n"
											  	  + "hissing sound and cloud of steam fills the room. The torch should be\n"
											  	  + "cool enough to take now.";
	 								System.out.println(lastShownText);
	 								break;
	 							}
	 							case Strings.CLOTH_NAME:
	 							{
	 								System.out.println("You wrap the cloth around the torch, but it doesn't do anything. It\n"
									  	  	  		 + "looks like you're missing something.");
	 								break;
	 							}
	 							default:
	 								System.out.println(Strings.CANT_USE_THAT_HERE);
	 						}
	 						break;
	 					}
	 				}
	 				break;
	 			}
	 			case "take":
	 			{
	 				takeTried = true;
	 				switch (Commands.promptTake(takeable))
	 				{
	 					case Strings.TORCH_NAME:
	 					{
	 						if(torchCooled)
	 						{
	 							torchTaken = true;
	 							takeable.remove(Strings.TORCH_NAME);
	 							inventory.add(torch);
	 							
	 							//Print new map
	 							printMap(false);
	 							lastShownText = "You carefully lift the torch out of its sconce and hold it out in\n"
										  	  + "front of you. The flame is blinding to look at. This will certainly\n"
										  	  + "come in handy.\n\n"
										  	  + Strings.TORCH_NAME.toUpperCase() + " has been added to your inventory.";
	 							System.out.println(lastShownText);
	 						}
	 						else
	 						{
	 							numTorchGrabAttempts++;
	 							if(numTorchGrabAttempts == 1)
	 							{
	 								//Exception for direction's sake: A new lastShownText will be saved here without showing the map.
	 								//Done so that the player can associate the hot torch with the cloth and provide them with a direction to solving the puzzle
	 								lastShownText = "You reach out to grab the torch. The second you touch it your hand\n"
											  	  	 + "recoils in pain. It's burning hot! You'll need to find a way to cool\n"
											  	  	 + "it down before you can take it.\n\n"
											  	  	 + Strings.MAP_TUTORIAL;
	 								
	 								System.out.println(lastShownText);
	 								
	 								//Exception for direction's sake: Instead of repeating the last shown text, the map will also mention that the
	 								//door is blocked by the spider web
	 								//compileRoomDescription();
	 							}
	 							else if(numTorchGrabAttempts == 2 || inventory.contains(Strings.WET_CLOTH_NAME))
	 							{
	 								System.out.print("You try to grab the torch again, but it's still too hot. ");
	 								
	 								//If the player has the wet cloth in the inventory, they'll be told to use the torch, otherwise they'll be told to cool it down
	 								if(inventory.contains(Strings.WET_CLOTH_NAME))
	 									System.out.println("Perhaps you\n"
	 													 + "should try USEing it first.");
	 								else
	 									System.out.println("You'll need\n"
								  	  	  	  		     + "to find a way to cool it down before you can take it.");
	 							}
	 							else
	 								System.out.println("You need to cool the torch down before you can take it.");
	 						}
	 						break;
	 					}
	 					case Strings.CLOTH_NAME:
	 					{
	 						clothTaken = true;
	 						takeable.remove(Strings.CLOTH_NAME);
	 						inventory.add(cloth);
	 						
	 						//Print new map
	 						printMap(false);
	 						lastShownText = "You took the cloth.\n\n"
	 									   + Strings.CLOTH_NAME.toUpperCase() + " has been added to your inventory.";
	 						System.out.println(lastShownText);
	 						System.out.println(Strings.INVENTORY_TUTORIAL);
	 						break;
	 					}
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
	 			case "west":
	 			{
	 				System.out.println("You move WEST.\n\n");
	 				Operate.delay(500);
	 				return "prison_cell_b";
	 			}
	 			case "south":
	 			{
	 				if(!websBurned)
	 				{
	 					numSouthAttempts++;
	 					if(numSouthAttempts == 1)
	 					{
	 						directions.remove("south");
	 						directions.add("south (blocked)");
	 						
	 						//Print new map
	 						printMap(false);
	 						
		 					System.out.println("You try to move SOUTH, but a thick spider web blocks the door.\n"
		 									  + "Maybe if you had something to remove it...");
	 					}
	 					else
	 						System.out.println("You try to move SOUTH, but the web still blocks your path.\n"
	 										 + "You wonder if the web can be burned somehow...");
	 				}
	 				else
	 				{
	 					System.out.println("You move SOUTH.\n\n");
	 					Operate.delay(500);
	 					return "prison_hall_corner_a";
	 				}
	 				break;
	 			}
	 			case "north":
	 			case "east":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + ".");
	 		}
	 	}
	}
	
	//return clothTaken
	public boolean clothTaken()
	{
		return clothTaken;
	}
	
	//return torchTaken
	public boolean torchTaken()
	{
		return torchTaken;
	}
	
	//return torchCooled
	public boolean torchCooled()
	{
		return torchCooled;
	}
	
	//return websBurned
	public boolean websBurned()
	{
		return websBurned;
	}
	
	//compileRoomDescription method unique to this room. Allows the exception of compiling new text when the player uses the "map" command
	private void compileRoomDescription()
	{
	 	lastShownText = "A large cell block. An array of bloody instruments that look like they are used for torture\n"
					  + "sit on a cart against the wall.";
	 	
	 	if(!websBurned && numSouthAttempts > 0)
	 		lastShownText += " There is a door leading SOUTH to a dark hallway, but it is\n"
	 					   + "blocked by a thick spider web.";
	 	
	 	if(!torchTaken)
	 	{
	 		lastShownText += " You look around the room:";
	 		
	 		if(!clothTaken)
	 			lastShownText += " a blood-stained cloth sits on the\n"
	 						   + "instrument cart and a lit torch hangs on the wall next to the door.";
	 		else
	 			lastShownText += " a lit torch hangs on the wall\n"
	 						   + "next to the door.";
	 	}
 	}

	//Use the main method to test the room
	public static void main(String[] args) {
		String result;
		Cell_Block testRoom = new Cell_Block();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("cell_block"));
		System.out.println(result);
	}

}
