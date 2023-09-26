package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;
import java.util.Date;
import java.text.SimpleDateFormat;

public class Dining_Room extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		Dining_Room testRoom = new Dining_Room();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("dining_room"));
		System.out.println(result);
	}
	
	//Room items
	
	private final String KNIFE = "knife";				//take
	private final String PAPER = "paper";				//take
	private final String DOOR = "door";					//use
	private final String STOVE = "stove";				//use
	private final String RADIO = "radio";				//use
	
	//Room variables
	final private boolean skipRadio = false;		//DEBUG: Make sure this is set to false when done
	
	final private int numTotemTalksDEBUG = 3;		//DEBUG: Make sure this is set to 0 when done
	
	private boolean paperTaken = false;
	private boolean knifeTaken = false;
	private boolean stoveFixed = false;
	private boolean usedRadio = false;
	private boolean funEventHappenedDEBUG = false;	//DEBUG: make sure these are set to false when done
	private boolean portraitTakenDEBUG = false;
	private boolean knobTakenDEBUG = false;
	
	//Constructor
	public Dining_Room()
	{
		roomName = "Dining Room";
		fearMultiplier = 1.0f;
		
		//Add directions
		directions.add("north");
		
		//Add useable items
		usable.add(STOVE);
		
		//Add takeable items
		takeable.add(KNIFE);
		takeable.add(PAPER);
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		return String.format("----||----\n"
						   + "|      %c |\n"
						   + "|%c       |\n"
						   + "|    o  %c#\n"
						   + "|       %c|\n"
						   + "|  %c     |\n"
						   + "----------\n",
						   (!knifeTaken ? 't' : ' '),
						   (usable.contains(RADIO) ? 'u' : ' '),
						   (inventory.contains(Strings.KEY_NAME) ? 'u' : ' '),
						   ((stoveFixed || inventory.contains(Strings.KNOB_NAME)) ? 'u' : '?'),
						   (!paperTaken ? 't' : ' '));
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
	 	
	 	//When the player returns with the knob, add radio to usable if a fun event happened
	 	if(inventory.contains(Strings.KNOB_NAME) && !Operate.funIsUpdating() && !usable.contains(RADIO) && !usedRadio)
		 		usable.add(RADIO);
	 	
	 	//If the player currently has the key in their inventory, add door to usable
	 	if(inventory.contains(Strings.KEY_NAME))
	 		usable.add(DOOR);
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Print room description
	 	if(usable.contains(RADIO))	//If a fun event has occurred and the radio is on
	 		lastShownText = "As you come into the dining room, a radio in the corner\n"
	 					  + "suddenly turns on and plays loud static. You can barely make\n"
	 					  + "out some of the words being spoken.";
	 	else if(stoveFixed)			//If the knob has been attached to the stove
	 		lastShownText = "You come into the dining room. There is a stove that is on\n"
	 					  + "fire sitting in the corner.";
	 	else						//If none of the above conditions are true
	 	{
		 	lastShownText = "You come into the dining room. It is completely trashed; the\n"
		 				  + "table in the center of the room has been flipped and various\n"
		 				  + "items have been thrown around everywhere.";
		 	
		 	if(!knifeTaken || !paperTaken)
		 	{
		 		lastShownText += " As you scan the\n"
		 					   + "room, your attention catches";
		 		
		 		if(!knifeTaken)
		 		{
		 			lastShownText += " a knife sitting in a knife block\n"
		 					       + "on a wine cabinet";
		 			
		 			if(!paperTaken)
		 				lastShownText += " and a piece of paper lying on the ground.";
		 			else
		 				lastShownText += '.';
		 		}
		 		else
		 			lastShownText += " a piece of paper lying on the\n"
		 						   + "ground.";
		 	}
	 	}
	 	//boy do i LOOOVE writing text trees!!! :}
	 	
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
	        	return "dining_room";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					  + "\n"
	        					  + "paperTaken: " + paperTaken + "\n"
	        					  + "knifeTaken: " + knifeTaken + "\n"
	        					  + "stoveFixed: " + stoveFixed + "\n"
	        					  + "usedRadio: " + usedRadio + "\n"
	        					  + "\n"
	        					  + "funEventHappened (Operate): " + !Operate.funIsUpdating() + "\n"
	        					  + "[[funEventHappenedDEBUG]]: " + funEventHappenedDEBUG + "\n"
	        					  + "portraitTaken (prison_cell_a): " + prison_cell_a.portraitTaken() + "\n"
	        					  + "[[portraitTakenDEBUG]]: " + portraitTakenDEBUG + "\n"
	        					  + "knobTaken (machinery_room): " /*TODO replace with variable once added*/ + "\n"
	        					  + "[[knobTakenDEBUG]]: " + knobTakenDEBUG);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 			{
	 				if(!stoveFixed)
	 					System.out.println("There's a stove here, but it's missing a knob.");
	 				else
	 					System.out.println(Strings.NOTHING_TO_INSPECT);
	 				
	 				break;
	 			}
	 			case "use":
	 			{
	 				switch(Commands.promptUse(usable))
	 				{
		 				case DOOR:
		 				{
		 					switch(inventory.promptItem(DOOR))
		 					{
		 						case "":
		 							break;
		 						case Strings.KEY_NAME:
		 						{
		 							System.out.println("It doesn't fit this lock.");
		 							
		 							break;
		 						}
		 						default:
		 							System.out.println(Strings.CANT_USE_THAT_HERE);
		 					}
		 					
		 					break;
		 				}
	 					case STOVE:
	 					{
	 						switch(inventory.promptItem(STOVE))
	 						{
		 						case "":
		 							break;
		 						case Strings.KNOB_NAME:
		 						{
		 							stoveFixed = true;
		 							inventory.remove(Strings.KNOB_NAME);
		 							
		 							lastShownText = "You affix the knob to the stove and turn it. The second you\n"
		 										  + "do, a flame BURSTS out from the burner. The entire stove is\n"
		 										  + "now on fire. You can heat up items on it now.";
		 							
		 							//Print new map
		 							printMap(true);
		 							
		 							break;
		 						}
	 							case Strings.UNLIT_TORCH_NAME:
	 							{
	 								if(stoveFixed)
	 								{
	 									inventory.replace(Strings.UNLIT_TORCH_NAME, torch);
	 									
	 									lastShownText = "You hold the torch handle over the fire, and the torch\n"
	 												  + "reignites!\n\n"
	 												  + Strings.TORCH_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
	 									
	 									//Print new map
	 									printMap(true);
	 								}
	 								else
	 									System.out.println(Strings.STOVE_IS_NOT_ON);
	 								
	 								break;
	 							}
	 							case Strings.KNIFE_NAME:
	 							{
	 								if(stoveFixed)
	 								{
	 									inventory.replace(Strings.KNIFE_NAME, hot_knife);
	 									
	 									lastShownText = "You hold the knife over the flame for a few minutes. The\n"
	 												  + "blade is glowing red when you pull it away.\n\n"
	 												  + Strings.HOT_KNIFE_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
	 									
	 									//Print new map
	 									printMap(true);
	 								}
	 								else
	 									System.out.println(Strings.STOVE_IS_NOT_ON);
	 								
	 								break;
	 							}
	 							default:
	 								System.out.println(Strings.CANT_USE_THAT_HERE);
	 						}
	 						
	 						break;
	 					}
	 					case RADIO:
	 					{
	 						usedRadio = true;
	 						usable.remove(RADIO);
	 						
	 						showRadioDialogue();
	 						
	 						break;
	 					}
	 				}
	 				
	 				break;
	 			}
	 			case "take":
	 			{
	 				switch(Commands.promptTake(takeable))
	 				{
	 					case KNIFE:
	 					{
	 						knifeTaken = true;
	 						takeable.remove(KNIFE);
	 						inventory.add(knife);
	 						
	 						lastShownText = "You carefully pull the knife out of the knife block. This\n"
	 									  + "will almost certainly come in handy.\n\n"
	 									  + Strings.KNIFE_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
	 						
	 						//Print new map
	 						printMap(true);
	 						
	 						break;
	 					}
	 					case PAPER:
	 					{
	 						paperTaken = true;
	 						takeable.remove(PAPER);
	 						inventory.add(blank_paper);
	 						
	 						lastShownText = "You inspect the paper... nothing is written on it. Regardless,\n"
	 									  + "you take it with you.\n\n"
	 									  + Strings.BLANK_PAPER_NAME.toUpperCase() + Strings.HAD_BEEN_ADDED;
	 						
	 						//Print new map
	 						printMap(true);
	 						
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
	 			case "north":
	 			{
	 				usable.remove(DOOR);
	 				
	 				System.out.println("You go NORTH.\n\n");
	 				Operate.delay(500);
	 				return "mud_room";
	 			}
	 			case "east":
	 			{
	 				//If the fun value is between 1-5 and the player has talked to the totem exactly 3 times, let them enter the error room
	 				if(Operate.getFun() >= 1 && Operate.getFun() <= 5 && (upstairs_bathroom.numTotemTalks() == 3 || numTotemTalksDEBUG == 3))
	 				{
	 					System.out.println("\n\n");
	 					return "error_room";
	 				}
	 				else if(prison_cell_a.portraitTaken() || portraitTakenDEBUG)
	 					System.out.println("The door leading EAST is locked. You can hear someone\n"
	 									 + "talking on the other side, though.");
	 				else
	 					System.out.println("The door leading EAST is locked.");
	 					
	 				break;
	 			}
	 			case "west":
	 			case "south":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
	
	//If you can believe it, this sets paperTaken to true
	public void setPaperTakenToTrue()
	{
		takeable.remove(PAPER);
		paperTaken = true;
	}
	
	//Shows the radio dialogue
	private void showRadioDialogue()
	{		
		if(skipRadio)
			return;
		
		//Create new Date object
		Date date = new Date();
		SimpleDateFormat dayOfTheWeek = new SimpleDateFormat("EEEE");
		SimpleDateFormat hourOfTheDay = new SimpleDateFormat("H");
		
		//Get appropriate time of day response from the hourOfTheDay
		String timeOfDay;
		int hour = Integer.parseInt(hourOfTheDay.format(date));
		
		//Between 12 and 4 am/8 and 11 pm - night
		if((hour >= 0 && hour <= 4) || (hour >= 20 && hour <= 23))
			timeOfDay = "night";
		//Between 5 and 11 am - morning
		else if(hour >= 5 && hour <= 11)
			timeOfDay = "morning";
		//Between 12 and 4 pm - afternoon
		else if(hour >= 12 && hour <= 16)
			timeOfDay = "afternoon";
		//Between 5 and 8 pm - evening
		else
			timeOfDay = "evening";
		
		Text.scrollLine("\"How's it going, ladies and gentlemen - you are listening to\n"
					  + "MOBY 5,@ coming at you live on this fabulous " + dayOfTheWeek.format(date) + ' ' + timeOfDay + ".\n@"
					  + "How's everybody doing?@@", 70);
		
		//TODO: Add more radio dialogue
	}
}