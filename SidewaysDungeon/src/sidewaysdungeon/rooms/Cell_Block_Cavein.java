package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Cell_Block_Cavein extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		Cell_Block_Cavein testRoom = new Cell_Block_Cavein();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("cell_block_cavein"));
		System.out.println(result);
	}
	
	//Room items
	final private String NOTE = "note";					//use
	final private String PAPER_SCRAP = "paper scrap";	//take
	final private String CHILD = "child";				//talk
	
	//Room variables
	private int numChildTalks = 0;
	
	private boolean childPresent = false;
	private boolean noteRead = false;
	private boolean paperScrapTaken = false;		
	
	private boolean panelInspectedDEBUG = false;	//DEBUG: Be sure to set to false when done
	private boolean doorOpenedDEBUG = false;
	private boolean portraitTakenDEBUG = false;
	
	//Constructor
	public Cell_Block_Cavein()
	{
		roomName = "Cell Block";
		fearMultiplier = 1.0f;
		
		//Add useable items
		usable.add(NOTE);
		
		//Add directions
		directions.add("south");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		Text.scrollLine("hello there general kenobi", 5);
		
		return String.format("|######|\n"
						   + "|%c  %c  |\n"
						   + "|      |\n"
						   + "|   o  |\n"
						   + "----||--\n",
						     (childPresent ? 'a' : ' '),
						     ((!noteRead && !(prison_hallway.panelInspected() || panelInspectedDEBUG) ? 'u' : (!paperScrapTaken ? 't' : ' '))));
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
	 	
	 	/*
	 	 * If all of the following conditions are true:
	 	 * -The panel in the hallway has been inspected
	 	 * -The door in the kitchen has been opened
	 	 * -The player has not collected the portrait from prison_cell_a
	 	 * -This is the player's first visit to this room
	 	 * -Fun is between 85-100
	 	 * 
	 	 * Spawn the crying child
	 	 */
	 	if((prison_hallway.panelInspected() || panelInspectedDEBUG) && (house_kitchen.doorOpened() || doorOpenedDEBUG) && !(prison_cell_a.portraitTaken() || portraitTakenDEBUG) && numVisits == 1 && (Operate.getFun() >= 85 && Operate.getFun() <= 100))
	 	{
	 		childPresent = true;
	 		talkable.add(CHILD);
	 	}
	 		
 		//If the player has already inspected the panel in the prison hallway, remove "note" from usable and add "paper scrap" to takeable
	 	if(prison_hallway.panelInspected() || panelInspectedDEBUG)
	 	{
	 		usable.remove(NOTE);
	 		takeable.add(PAPER_SCRAP);
	 	}
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	lastShownText = "Another large cell block, but it appears to have been caved\n"
	 				  + "in; a large pile of rubble blocks access to the other side\n"
	 				  + "of the room.";
	 	
	 	if(childPresent)
	 		lastShownText += " There's a crying child sitting in the corner.";
	 	else if(!noteRead && !prison_hallway.panelInspected() && !panelInspectedDEBUG)
	 		lastShownText += " A note on the ground in the center of the room\n"
	 					   + "catches your eye.";
	 	else if(!paperScrapTaken)
	 		lastShownText += " A scrap of paper on the ground in the center of\n"
	 					   + "the room catches your eye.";
	 	
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
	        	return "cell_block_cavein";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + "\n"
	        					 + "\n"
	        					 + "childPresent: " + childPresent + '\n'
	        					 + "noteRead: " + noteRead + "\n"
	        					 + "paperScrapTaken: " + paperScrapTaken + "\n"
	        					 + '\n'
	        					 + "panelInspected (prison_hallway): " + prison_hallway.panelInspected() + "\n"
	        					 + "[[panelInspectedDEBUG]]: " + panelInspectedDEBUG + '\n'
	        					 + "doorOpened (house_kitchen): " + house_kitchen.doorOpened() + '\n'
	        					 + "[[doorOpenedDEBUG]]: " + doorOpenedDEBUG + '\n'
	        					 + "portraitTaken (prison_cell_a): " + prison_cell_a.portraitTaken() + '\n'
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
	 				if(Commands.promptUse(usable).equals(NOTE))
	 				{
	 					noteRead = true;
	 					usable.remove(NOTE);
	 					takeable.add(PAPER_SCRAP);
	 					
	 					readNote();
	 					
	 					//Print new map
	 					printMap(false);
	 					
	 					lastShownText = "As you finish reading, you notice a scrap of paper in the\n"
								 	  + "spot where the note was.";
	 					System.out.println(lastShownText);
	 				}
	 				
	 				break;
	 			}
	 			case "take":
	 			{
	 				if(Commands.promptTake(takeable).equals(PAPER_SCRAP))
	 				{
	 					paperScrapTaken = true;
	 					takeable.remove(PAPER_SCRAP);
	 					inventory.add(note_scrap_b, "\"-" + String.valueOf(house_kitchen.doorCode()).substring(2) + "\".");
	 					
	 					//Print new map
	 					printMap(false);
	 					lastShownText = "You took the paper scrap.\n\n"
	 								  + Strings.NOTE_SCRAP_B_NAME.toUpperCase() + " has been added to your inventory.";
	 					System.out.println(lastShownText);
	 				}
	 				
	 				break;
	 			}
	 			case "talk":
	 			{
	 				if(Commands.promptTalk(talkable).equals(CHILD))
	 					//Play the crying child dialogue
	 					playChildDialogue();
	 				
	 				break;
	 			}
	 			case "map":
	 			{
	 				printMap(true);
	 				break;
	 			}
	 			case "south":
	 			{
	 				//Despawn the crying child
	 				childPresent = false;
	 				talkable.remove(CHILD);
	 				
	 				System.out.println("You move SOUTH.\n\n");
	 				Operate.delay(500);
	 				return "prison_hall_corner_b";
	 			}
	 			case "north":
	 			{
	 				System.out.println("A large cave in blocks the path to the NORTH.");
	 				break;
	 			}
	 			case "east":
	 			case "west":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
	
	//readNote VOID METHOD: Reads the note to the player
	void readNote()
	{
		//DEBUG: Make sure SKIP_NOTE is false when done
		final boolean SKIP_NOTE = false;
		
		if(!SKIP_NOTE)
		{
			System.out.println("\nYou read the note.");
			Operate.delay(1500);
			
			Text.scrollLine("\n\"Dear " + player.getName() + ",@@", 20);
			
			Text.scrollLine("\nI do not know who you are,@ but I do understand that this", 35);
			Text.scrollLine("situation may be very distressing for you.@ I hope you can", 35);
			Text.scrollLine("take solace in the fact that I am not the one who put you", 35);
			Text.scrollLine("here,@ but rather,@ the one trying to get you out.@@", 35);
			
			Text.scrollLine("\nI was in your exact situation not long before you.@ I was", 35);
			Text.scrollLine("able to find my way out of this horrid place,@ and I wish to", 35);
			Text.scrollLine("help you do the same.@ In fact,@ let me prove it to you.@@", 35);
			
			Text.scrollLine("\nGo back to the hallway joining the two cell blocks.@ There is", 35);
			Text.scrollLine("a secret panel that will lead you up to the main house.@ From", 35);
			Text.scrollLine("there,@ you'll need to find an elevator that will take you up", 35);
			Text.scrollLine("to the entrance hall.@ It will take some exploring to find it,@", 35);
			Text.scrollLine("but I assure you have nothing to fear from that rickety old", 35);
			Text.scrollLine("house.@@", 35);
			
			Text.scrollLine("\nI pray your journey is without perish.@ I will be ahead if you", 35);
			Text.scrollLine("need me.@ -" + player.getName().substring(0, 1).toUpperCase() + "\"", 35);
			Operate.delay(1500);
		}
	}
	
	//set paperScrapTaken to true and remove "paper scrap" from takeable list
	public void setPaperScrapTakenToTrue()
	{
		paperScrapTaken = true;
		takeable.remove(PAPER_SCRAP);
	}
	
	//return noteRead
	public boolean noteRead()
	{
		return noteRead;
	}
	
	//return paperScrapTaken
	public boolean paperScrapTaken()
	{
		return paperScrapTaken;
	}
	
	//Plays the dialogue for the crying child fun event
	private void playChildDialogue()
	{
		numChildTalks++;
		
		switch(numChildTalks)
		{
			case 1:
			{
				//Tell the game that a fun event has happened
				Operate.funEventHappened();
					
				System.out.println("\nYou talk to the child.");
				Operate.delay(1250);
				
				System.out.println("\nCHILD:");
				Text.scrollLine("\"So this room went completely unused…@ what a shame.@", 40);
				Text.scrollLine(" ...@@@", 40);
				Text.scrollLine(" You know,@ this reminds me of a story.@@", 40);
				Text.scrollLine(" There was once this little girl,@ only about eight years old.@@@", 40);
				Text.scrollLine(" The girl's mind was sick,@ but nobody cared to notice it at the time.@@@", 40);
				
				Text.scrollLine("\n One day,@ after having a meltdown,@ everyone thought she had been possessed by the devil.@@", 40);
				Text.scrollLine(" Her parents became so scared of her that they locked her in the basesment.@@@", 40);
				Text.scrollLine(" They performed ritual after ritual,@ bringing in every priest they could find.@@", 40);
				Text.scrollLine(" They tried everything they could to exorcise her,@ but nothing worked.@@@", 40);
				
				Text.scrollLine("\n Then one day,@ the child died.@@@", 40);
				Text.scrollLine(" The police found that her cause of death was severe malnutrition and dehydration.@@@@", 40);
				
				Text.scrollLine("\n Do you know what happened to the parents that did that to her?@@@", 40);
				Text.scrollLine(" Nothing.@ Everyone assumed it was the work of the devil.@@@", 40);
				
				Text.scrollLine("\n ...@@@@", 45);
				
				Text.scrollLine(" I'm sorry,@ I don't know why I'm telling you this.@@", 40);
				Text.scrollLine(" This just reminded me of that.\"", 40);
				
				Operate.delay(1500);
				
				//Print new map
				lastShownText = "The child begins to tear up.";
				printMap(true);
				
				break;
			}
			case 2:
			{
				System.out.println("\nCHILD\n"
								 + "\"You don't have to remember any of that. Please forget about\n"
								 + " me if you can.\"");
				
				break;
			}
			case 3:
			{
				System.out.println("\nCHILD:\n"
								 + "\"I'll be alright.\"");
				
				break;
			}
			default:
				System.out.println("The child hums to herself.");
		}
	}
}