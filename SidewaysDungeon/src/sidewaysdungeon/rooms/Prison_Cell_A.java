package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

//Prison Cell A
public class Prison_Cell_A extends Room implements RoomTemplate
{
   //Use the main method to test the room
   public static void main(String[] args)
   {
	  String result;
      Prison_Cell_A testRoom = new Prison_Cell_A();
      do {
     	result = testRoom.playRoom("");
      } while(result.equals("prison_cell_a"));
      System.out.println(result);
   }
   
   //Room variables
   private boolean inspected = false;				//Whether or not the player has inspected the room
   private boolean wallPushedDown = false;			//Whether or not the player has pushed down the wall to prison_cell_b
   private boolean portraitTaken = false;			//Whether or not the player has taken the portrait
   
   //Maps
   private final String MAP_INITIAL = "--??---\n"
		                            + "|     |\n"
		                            + "|  o  #\n"
		                            + "|     |\n"
		                            + "-------\n";
                             
   private final String MAP_INSPECTED = "--uu---\n"
	                                  + "|     |\n"
	                                  + "|  o  #\n"
	                                  + "|     |\n"
	                                  + "-------\n";

   private final String MAP_WALL_PUSHED_DOWN = "--||---\n"
		                                     + "|     |\n"
		                                     + "|  o  #\n"
		                                     + "|     |\n"
		                                  	 + "-------\n";
   
   private final String MAP_CELL_B_VISITED = "-------\n"
								           + "|   u |\n"
								           + "|     =\n"
								           + "|     |\n"
								           + "--||---\n"
								           + "|     |\n"
								           + "|  o  #\n"
								           + "|     |\n"
								           + "-------\n";
   
   private final String MAP_CELL_BLOCK_VISITED = "---------------\n"
									           + "|   u |   t   |\n"
									           + "|     =       |\n"
									           + "|     |       |\n"
									           + "|-||--|       |\n"
									           + "|     |       |\n"
									           + "|  o  #     u |\n"
									           + "|     |    [t]|\n"
									           + "---------uu----\n";

   private final String MAP_CLOTH_TAKEN = "---------------\n"
									    + "|   u |       |\n"
									    + "|     =       |\n"
									    + "|     |       |\n"
									    + "|-||--|       |\n"
									    + "|     |       |\n"
									    + "|  o  #     u |\n"
									    + "|     |    [t]|\n"
									    + "---------uu----\n";
   
   private final String MAP_TORCH_COOLED = "---------------\n"
								         + "|   u |       |\n"
								         + "|     =       |\n"
								         + "|     |       |\n"
								         + "|-||--|       |\n"
								         + "|     |       |\n"
								         + "|  o  #       |\n"
								         + "|     |     t |\n"
								         + "---------uu----\n";
	
   private final String MAP_TORCH_TAKEN = "---------------\n"
									    + "|   u |       |\n"
									    + "|     =       |\n"
									    + "|     |       |\n"
									    + "|-||--|       |\n"
									    + "|     |       |\n"
									    + "|  o  #       |\n"
									    + "|     |       |\n"
									    + "---------uu----\n";
	
   private final String MAP_WEBS_BURNED = "---------------\n"
									    + "|   u |       |\n"
									    + "|     =       |\n"
									    + "|     |       |\n"
									    + "|-||--|       |\n"
									    + "|     |       |\n"
									    + "|  o  #       |\n"
									    + "|     |       |\n"
									    + "---------||----\n";
		   
   //Constructor
   public Prison_Cell_A()
   {
      roomName = "Prison Cell";
      fearMultiplier = 1.2f;
   }
   
   //map STRING METHOD: Returns the current map to print
   public String map()
   {
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
	   else if(prison_cell_b.getNumVisits() > 0)
		   return MAP_CELL_B_VISITED;
	   else
	   {
		   if(wallPushedDown)
			   return MAP_WALL_PUSHED_DOWN;
		   else if(inspected)
			   return MAP_INSPECTED;
		   else
			   return MAP_INITIAL;
	   }
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
   
   //Play room
   public String playRoom(String prevRoom)
   {  
	  updateStats();
	  
      //Print room name
      System.out.println("-~" + roomName.toUpperCase() + "~-");
      
      //Print room map
      printMap(false);
      
      //Print room description
      if(numVisits == 1)         //Player's first visit to this location
      {
    	 lastShownText = "You awaken in a cold, dark prison cell. Dazed, confused, you\n"
	              	   + "slowly make your way to your feet. As your eyes adjust, you\n"
	              	   + "start to make out some of the details of your cell: there is\n"
	              	   + "a small bed in the corner, and a torch shining in a large room\n"
	              	   + "outside your cell. You start to panic, but quickly regain your\n"
	              	   + "composure. You look for things to help you escape...";
         System.out.println(lastShownText);
      }
      else
      {
         if(inventory.contains("torch"))  //Player has the torch in their inventory
         {
        	 lastShownText = "It's the prison cell you woke up in. Your torch illuminates\n"
		 			   	   + "the room; there is a worn, stained bed in the corner and\n"
		 			   	   + "scratched-in talley marks all over the walls. An uneasy\n"
		 			   	   + "feeling pools at the bottom of your stomach.";
        	 System.out.println(lastShownText);
         }
         else
         {
        	 lastShownText = "It's the prison cell you woke up in. It's too dark to see\n"
                  	 	   + "anything, but you can barely make out what looks like a small\n"
                  	 	   + "bed in the corner, and some kind of markings on the walls.";
        	 
        	 if(!cell_block.torchTaken())
        		 lastShownText += " An\n"
        		 				+ "ominous light is cast into the cell from a torch in the next\n"
        		 				+ "room.";
        	 
             System.out.println(lastShownText);
         }
      }
      
      //Update the cat's fear
      cat.updateFear(fearMultiplier);
      
      //Get commands from player
      while(true)
      {
    	 //Show tutorials
    	 if(numVisits == 1)
    	 {
	    	 if(!inspected)
	    		 System.out.println(Strings.INSPECT_TUTORIAL);
	    	 else if(inspected && !wallPushedDown)
	    		 System.out.println(Strings.USE_TUTORIAL);
	         else
	    		 System.out.println(Strings.DIRECTIONS_TUTORIAL);
    	 }
         String command = Commands.promptCommand(keyboard, false, inventory);
         
         //Intercept commands
         if(command.length() >= 2 && command.substring(0,2).equals("#&"))	//Load entered room
        	 return command.substring(2);
         else if(command.equals("##"))										//Reload this room
        	 return "prison_cell_a";
         else if(command.equals("%#"))										//Show this room's variable list
         {
        	 System.out.println("numVisits: " + numVisits + "\n");
        	 System.out.println("inspected: " + inspected);
        	 System.out.println("wallPushedDown: " + wallPushedDown);
        	 continue;
         }
         
         //Act on command
         switch(command)
         {
            case "inspect":
            {
               if(!inspected)
               {
                  inspected = true;
                  usable.add("wall");
                  
                  //Print new map
                  printMap(false);
                  
                  lastShownText = Strings.NEW_DISCOVERY + "The wall to the NORTH looks very flimsy and unstable. You\n"
                          						+ "might be able to knock it down with a good push.";
                  System.out.println(lastShownText);
               }
               else
               {
                  System.out.println(Strings.NOTHING_TO_INSPECT);
               }
               
               break;
            }
            case "use":
            {
               if(Commands.promptUse(usable).equals("wall"))
               {
            	   wallPushedDown = true;
            	   usable.remove("wall");
            	   directions.add("north");
            	   
            	   System.out.print("You push on the wall with all your might.");
            	   Operate.delay(200);
            	   Text.scrollLine("@...@\n", 500);
            	   
            	   //Print new map
            	   printMap(false);
            	   
            	   lastShownText = "It came crumbling down!\n\n"
            			   		 + "A path has opened to the NORTH.";
            	   System.out.println(lastShownText);
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
            case "north":
            {
            	if(directions.contains("north"))
            	{
            		System.out.println("You squeeze through the wall and move NORTH.\n\n");
            		Operate.delay(500);
            		return "prison_cell_b";
            	}
            	else
            		System.out.println(Strings.CANT_MOVE + " NORTH.");
            	
            	break;
            }
            case "east":
            {
            	System.out.println("The door leading EAST is locked.");
            	break;
            }
            case "west":
            case "south":
            case "up":
            case "down":
            {
            	System.out.println(Strings.CANT_MOVE + command.toUpperCase() + ".");
            	break;
            }
         }
      }
   }
   
   //Return portrait taken
   public boolean portraitTaken()
   {
	   return portraitTaken;
   }
}