package sidewaysdungeon.game_files;

import sidewaysdungeon.text.Strings;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

//Static list of commands the player can use in each room
public class Commands extends Master
{
   //DEBUG: Make sure devCommandsEnabled is false when done
   private static boolean devCommandsEnabled = true;
   
   //Commands
   private static ArrayList<String> commands = new ArrayList<String>(List.of("inspect", "use", "take", "talk", "inventory", "map", "quit"));
   private static ArrayList<String> directions = new ArrayList<String>(List.of("north", "south", "east", "west", "up", "down"));
   private static ArrayList<String> devCommands = new ArrayList<String>(List.of("set name", "set cat name", "set cat fear", "take cat", "untake cat", "give cat", "remove cat", "set fun", "jump to", "reload", "give", "remove", "find", "show vars", "show room vars"));
   
   //Use the main method to test command functionality
   public static void main(String[] args)
   {
      while(true)
    	  System.out.println(promptCommand(keyboard, false, inventory));
   }
   
   //Add "pet" to the command list
   public static void addPet()
   {
      commands.add(commands.size() - 1, "pet");
   }
   
   //Remove "pet" from the command list
   public static void removePet()
   {
      commands.remove("pet");
   }
   
   //promptCommand INT METHOD: Prompts the player for a command and returns the value of the command entered
   public static String promptCommand(Scanner keyboard, boolean blacklight, Inventory inventory)
   {
	   System.out.print("\nWhat would you like to do: ");
	   String command = keyboard.nextLine().toLowerCase();
	    
	   //Get command value
	   switch(command)
	   {
	      //Contextual actions get returned to the room class
	      case "inspect":
	      case "use":
	      case "take":
	      case "talk":
	      case "map":
	      case "north":
	      case "south":
	      case "west":
	      case "east":
	      case "up":
	      case "down":
	         return command;
	      //Show the inventory
	      case "inventory":
	      {
	         inventory.promptInventory(blacklight);
	         return "";
	      }
	      //Show list of commands
	      case "help":
	      {
	         System.out.println("Commands: " + commands.toString());
	         System.out.println("Directions: " + directions.toString());
	         System.out.println("Type a command to do an action, or type a direction to move.");
	         return "";
	      }
	      //Pet the cat
	      case "pet":
	      {
	    	  if(cat.withPlayer())
	    		  cat.pet();
	    	  else
	    		  System.out.println(Strings.UNKNOWN_COMMAND);
	    	  
	    	  return "";
	      }
	      //Quit the game
	      case "quit":
	      {
	         System.out.println("Are you sure you want to quit? If you do, you'll have to start your adventure over.");
	         System.out.print("Type \"yes\" to quit: ");
	           
	         if(keyboard.nextLine().equalsIgnoreCase("yes"))
	         {
	            keyboard.close();
	            System.exit(0);
	         }
	         
	         return "";
	      }
	      //Enable dev commands
	      case "enable dev commands":
	      {
	         devCommandsEnabled = true;
	         System.out.println("Dev commands enabled. Type \"help dev\" for a list of dev commands.");
	         return "";
	      }
	      //Disable dev commands
	      case "disable dev commands":
	      {
	         devCommandsEnabled = false;
	         System.out.println("Dev commands disabled.");
	         return "";
	      }
	      default:
	      {
	         //Dev commands
	         if(devCommandsEnabled)
	         {
	            switch(command)
	            {
	               //Change player name
	               case "set name":
	               {
	                  player.setName(keyboard.nextLine());
	                  System.out.println("Set player name to " + player.getName() + ".");
	                    
	                  return "";
	               }
	               //Change cat name
	               case "set cat name":
	               {  
	                  cat.setName(keyboard.nextLine());
	                  System.out.println("Set cat name to " + cat.getName() + ".");
	                  
	                  return "";
	               }
	               //Change cat fear value
	               case "set cat fear":
	               {
	                  cat.setFear(keyboard.nextFloat());
	                  keyboard.nextLine();		//Flush Scanner
	                  System.out.println("Set cat fear to " + cat.getFear() + ".");
	                    
	                  return "";
	               }
	               //Give cat / set cat.taken to true / add "pet" to command list
	               case "take cat":
	               {
	            	   cat.takeCat();
	            	   addPet();
	            	   System.out.println("Taken cat.");
	            	   
	            	   return "";
	               }
	               //Remove cat / set cat.taken to false / remove "pet" from command list
	               case "untake cat":
	               {
	            	   cat.untakeCat();
	            	   removePet();
	            	   System.out.println("Untaken cat.");
	            	   
	            	   return "";
	               }
	               //Give cat / add "pet" to command list
	               case "give cat":
	               {
	                  cat.giveCat();
	                  addPet();
	                  System.out.println("Given cat.");
	                   
	                  return "";
	               }
	               //Yeet cat / remove "pet" from command list
	               case "remove cat":
	               {
	                  cat.removeCat();
	                  removePet();
	                  System.out.println("Removed cat.");
	              	 
	                  return "";
	               }
	               //Change fun value
	               case "set fun":
	               {
	                  //Get new fun value
	                  int fun = keyboard.nextInt();
	                    
	                  //Flush Scanner
	                  keyboard.nextLine();
	                   
	                  //Set fun value
	                  Operate.setFun(fun);
	                  System.out.println("Set value of fun to " + Operate.getFun());
	                    
	                  return "";
	               }
	               //Jump to another room
	               case "jump to":
	               {
	                  //Get room value to jump to
	                  String jumpTo = keyboard.nextLine();
	                   
	                  //Typing "cancel" will cancel the room jump
	                  if(!jumpTo.equals("cancel"))
	                     return "#&" + jumpTo;
	               }
	               //Reload the current room
	               case "reload":
	               {
	            	   return "##";
	               }
	               //Give inventory item
	               case "give":
	               {
	                  //Get name and description of item to give
	                  String name = keyboard.nextLine().toLowerCase();
	                  String desc = keyboard.nextLine();
	                   
	                  inventory.add(new Item(name, desc));
	                   
	                  System.out.println("Given " + name + ".");
	                    
	                  return "";
	               }
	               //Remove inventory item
	               case "remove":
	               {
	                  String name = keyboard.nextLine().toLowerCase();
	                   
	                  inventory.remove(name);
	                  System.out.println("Removed " + name + ".");
	                    
	                  return "";
	               }
	               //Check if item is in inventory
	               case "find":
	               {
	                  String name = keyboard.nextLine().toLowerCase();
	                    
	                  if(inventory.contains(name))
	                     System.out.println(name + " found.");
	                  else
	                     System.out.println(name + " not found.");
	                   
	                  return "";
	               }
	               //Show player variables
	               case "show vars":
	               {
	            	   System.out.println("playerName: " + player.getName());
	            	   System.out.println("numMoves: " + player.getNumMoves());
	            	   System.out.println("totemsTalkedTo: " + player.getTotemsTalkedTo());
	            	   System.out.println("\ncatName: " + cat.getName());
	            	   System.out.println("catTaken: " + cat.taken());
	            	   System.out.println("catWithPlayer: " + cat.withPlayer());
	            	   System.out.println("catFear: " + cat.getFear());
	            	   System.out.println("\nfun: " + Operate.getFun());
	            	   System.out.println("isFunUpdating: " + Operate.funIsUpdating());
	            	   return "";
	               }
	               //Show room variables
	               case "show room vars":
	               {
	            	   return "%#";
	               }
	               //Show list of dev commands
	               case "help dev":
	               {
	                  System.out.println("Dev Commands: " + devCommands.toString());
	                  return "";
	               }
	               default:
	               {
	                  System.out.println(Strings.UNKNOWN_COMMAND);
	                  return "";
	               }
	            }
	         }
	         else
	         {
	            System.out.println(Strings.UNKNOWN_COMMAND);
	            return "";
	         }
         }
      }
   }
   
   //promptUse STRING COMMAND: Prompts the player for what item they want to use
   public static String promptUse(ArrayList<String> usable)
   {  
      if(usable.size() == 0)
      {
         System.out.println(Strings.NOTHING_TO_USE);
         return "";
      }
      else
      {
         System.out.println("\n" + usable.toString());
         System.out.print("Select item to use (Type \"cancel\" to exit): ");
         
         String toUse = keyboard.nextLine().toLowerCase();
         if(toUse.equals("cancel") || toUse.equals(""))
            return "";
         else if(usable.contains(toUse))
            return toUse;
         else
         {
            System.out.println(Strings.CANT_USE_THAT);
            return "";
         }
      }
   }
   
   //promptTake STRING CLASS: Prompt the user for what item they want to take
   public static String promptTake(ArrayList<String> takeable)
   {
	   if(takeable.size() == 0)
	   {
		   System.out.println(Strings.NOTHING_TO_TAKE);
		   return "";
	   }
	   else
	   {
		   System.out.println("\n" + takeable.toString());
		   System.out.print("Select item to take (Type \"cancel\" to exit): ");
		   
		   String toTake = keyboard.nextLine().toLowerCase();
		   if(toTake.equals("cancel") || toTake.equals(""))
			   	return "";
		   else if(takeable.contains(toTake))
			   return toTake;
		   else
		   {
			   System.out.println(Strings.CANT_TAKE_THAT);
			   return "";
		   }
	   }
   }
   
   //promptTalk STRING METHOD: Prompts the player for who they want to talk to
   public static String promptTalk(ArrayList<String> talkable)
   {
	   if(talkable.size() == 0)
	   {
		   System.out.println(Strings.NO_ONE_TO_TALK);
		   return "";
	   }
	   else
	   {
		   System.out.println("\n" + talkable.toString());
		   System.out.print("Select who to talk to (Type \"cancel\" to exit): ");
		   
		   String toTalk = keyboard.nextLine().toLowerCase();
		   if(toTalk.equals("cancel") || toTalk.equals(""))
			   return "";
		   else if(talkable.contains(toTalk))
			   	return toTalk;
		   else
		   {
			   System.out.println(Strings.CANT_TALK_TO_THAT);
			   return "";
		   }
	   }
   }
}