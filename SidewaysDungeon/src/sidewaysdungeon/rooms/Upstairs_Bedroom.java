package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

//The amount of times I've mistyped "bedroom" as "bedroon" while making this room is insane
public class Upstairs_Bedroom extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		Upstairs_Bedroom testRoom = new Upstairs_Bedroom();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("upstairs_bedroom"));
		System.out.println(result);
	}
	
	//Room items
	private final String ROPE = "rope";		//use
	private final String BOOK = "book";		//take
	
	//Room variables
	private int numBookTakeAttempts = 0;
	
	private boolean manPresent = true;
	private boolean bookInspected = false;
	private boolean bookCutDown = false;
	private boolean bookTaken = false;
	
	//Maps
	
	//Constructor
	public Upstairs_Bedroom()
	{
		roomName = "Bedroom";
		fearMultiplier = 2.0f;
		
		//Add directions
		directions.add("west");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		return String.format("------------\n"
						   + "|_____  %c  |\n"
						   + "| %c   |    |\n"
						   + "|_____|    |\n"
						   + "|          |\n"
						   + "=  o  %c%c%c  |\n"
						   + "|          |\n"
						   + "------------\n",
						   (bookInspected && !bookCutDown ? 'u' : ' '),
						   (bookInspected && manPresent ? '?' : ' '),
						   (bookInspected && !bookCutDown ? '[' : ' '),
					       (!bookInspected ? '?' : (bookTaken ? ' ' : 't')),
					       (bookInspected && !bookCutDown ? ']' : ' '));
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
	 	lastShownText = "You come into the bedroom.";
	 	
	 	
	 	if(manPresent)
	 	{
	 		lastShownText += " A large man is sleeping in the bed;\n"
	 				  	   + "he's snoring so loudly that the room is shaking.";
	 		
	 		if(!bookInspected)
		 	{
	 			if(cat.withPlayer())
	 			{
			 		lastShownText += ' ' + cat.getName() + " is\n"
			 					   + "looking up at something.";
	 			}
		 	}
		 	else if(!bookTaken)
		 	{
		 		if(!bookCutDown)
		 		{
		 			lastShownText += " There's a\n"
		 						   + "book hanging by a rope from the ceiling.";
		 		}
		 		else
		 		{
		 			lastShownText += " There's a\n"
		 						   + "book lying on the ground in front of the bed.";
		 		}
		 	}
	 	}
	 	else
	 	{
	 		lastShownText += " The man that was sleeping in the\n"
	 					   + "bed before is now gone.";
	 	}
	 	
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
	        	return "upstairs_bedroom";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + '\n'
	        					 + "numBookTakeAttempts: " + numBookTakeAttempts + '\n'
	        					 + '\n'
	        					 + "manPresent: " + manPresent + '\n'
	        					 + "bookInspected: " + bookInspected + '\n'
	        					 + "bookCutDown: " + bookCutDown + '\n'
	        					 + "bookTaken: " + bookTaken);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 			{
	 				if(bookInspected)
	 				{
	 					if(manPresent)
	 						System.out.println("You inspect the man in the bed. You feel like you've seen him\n"
	 										 + "before but your nerves are too shot to remember where.");
	 					else
	 						System.out.println("The man is gone.");
	 				}
	 				else
	 				{
	 					usable.add(ROPE);
	 					takeable.add(BOOK);
	 					bookInspected = true;
	 					
	 					lastShownText = Strings.NEW_DISCOVERY + "There's a book hanging by a rope from the ceiling. The rope is\n"
	 														  + "wrapped over a pulley system and tied to the ground on the\n"
	 														  + "other side of the room.";
	 					
	 					//Print new map
	 					printMap(true);
	 				}
	 				break;
	 			}
	 			case "use":
	 			{
	 				if(Commands.promptUse(usable).equals(ROPE))
	 				{
	 					switch(inventory.promptItem(ROPE))
	 					{
	 						case "":
	 							break;
	 						case Strings.KNIFE_NAME:
	 						{
	 							bookCutDown = true;
	 							usable.remove(ROPE);
	 							
	 							lastShownText = "You cut the rope with the knife. The book hits the ground\n"
	 										  + "with a resounding THUD!...The man rolls over, but doesn't\n"
	 										  + "wake up.";
	 							
	 							//Print new map
	 							printMap(false);
	 							
	 							System.out.print("You cut the rope with the knife. The book hits the ground\n"
	 										   + "with a resounding THUD!");
	 							Text.scroll("@...@", 500);
	 							System.out.println("The man rolls over, but doesn't\n"
	 											 + "wake up.");
	 							
	 							break;
	 						}
	 						default:
	 							System.out.println(Strings.CANT_USE_THAT_HERE);
	 					}
	 				}
	 				break;
	 			}
	 			case "take":
	 			{
	 				if(Commands.promptTake(takeable).equals(BOOK))
	 				{
	 					if(!bookCutDown)
	 					{
	 						numBookTakeAttempts++;
	 						
	 						if(numBookTakeAttempts == 1)
	 						{
	 							System.out.println("You try to reach the book, but it's too high. It doesn't look\n"
	 											 + "like there's anything you can stand on, either.");
	 						}
	 						else
	 						{
	 							System.out.println("The book is still too high. However, the rope that's holding\n"
	 											 + "it up appears to be accessible from the ground...");
	 						}
	 					}
	 					else
	 					{
	 						bookTaken = true;
	 						takeable.remove(BOOK);
	 						inventory.add(book);
	 						
	 						lastShownText = "You quietly gather up the book from the ground, trying not\n"
	 									  + "fumble it with your shaking hands.\n\n"
	 									  + Strings.BOOK_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
	 						
	 						//Print new map
	 						printMap(true);
	 					}
	 				}
	 				
	 				break;
	 			}
	 			case "talk":
	 			{
	 				if(manPresent)
	 					System.out.println("Trying to talk to the man doesn't seem like the best idea.");
	 				else
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
	 				return "upstairs_hallway";
	 			}
	 			case "north":
	 			case "east":
	 			case "south":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
	
	//setManPresentToFalse: Sets manPresent to false (shocking, I know). Called from psion_cell_a() when the player collects the portrait
	public void setManPresentToFalse()
	{
		manPresent = false;
	}
}