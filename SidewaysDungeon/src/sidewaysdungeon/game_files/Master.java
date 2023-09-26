package sidewaysdungeon.game_files;

import sidewaysdungeon.text.Text;
import sidewaysdungeon.text.Strings;
import sidewaysdungeon.entities.*;
import sidewaysdungeon.rooms.*;
import java.util.Scanner;


public class Master
{
	//DEBUG: Make sure SKIP_INTRO is false when done
	private static final boolean SKIP_INTRO = false;
	  
	//Create Scanner object
	protected static Scanner keyboard = new Scanner(System.in);
	
   //Load entities
   protected static Player player = new Player();
   protected static Cat cat = new Cat();
   
   //Load inventory
   protected static Inventory inventory = new Inventory();
   protected static Inventory emptyInventory = new Inventory();
   
   //Load items
   protected static Item torch = new Item(Strings.TORCH_NAME, Strings.TORCH_DESC);															//Torch
   protected static Item unlit_torch = new Item(Strings.UNLIT_TORCH_NAME, Strings.UNLIT_TORCH_DESC);										//Unlit Torch
   protected static Item cloth = new Item(Strings.CLOTH_NAME, Strings.CLOTH_DESC);															//Cloth
   protected static Item wet_cloth = new Item(Strings.WET_CLOTH_NAME, Strings.WET_CLOTH_DESC);												//Wet Cloth
   protected static Item note_scrap_a = new Item(Strings.NOTE_SCRAP_A_NAME, Strings.NOTE_SCRAP_A_DESC);										//Note Scrap A
   protected static Item note_scrap_b = new Item(Strings.NOTE_SCRAP_B_NAME, Strings.NOTE_SCRAP_B_DESC);										//Note Scrap B
   protected static Item book = new Item(Strings.BOOK_NAME, Strings.BOOK_DESC, Strings.BOOK_DESC_BLACKLIGHT);								//Book
   protected static Item key = new Item(Strings.KEY_NAME, Strings.KEY_DESC);																//Key
   protected static Item knife = new Item(Strings.KNIFE_NAME, Strings.KNIFE_DESC, Strings.KNIFE_DESC_BLACKLIGHT);							//Knife
   protected static Item hot_knife = new Item(Strings.HOT_KNIFE_NAME, Strings.HOT_KNIFE_DESC);												//Hot Knife
   protected static Item green_lady = new Item(Strings.LADY_IN_GREEN_NAME, Strings.LADY_IN_GREEN_DESC);										//Lady in Green Portrait
   protected static Item yellow_lady = new Item(Strings.LADY_IN_YELLOW_NAME, Strings.LADY_IN_YELLOW_DESC);									//Lady in Yellow Portrait
   protected static Item orange_lady = new Item(Strings.LADY_IN_ORANGE_NAME, Strings.LADY_IN_ORANGE_DESC);									//Lady in Orange Portrait
   protected static Item blank_paper = new Item(Strings.BLANK_PAPER_NAME, Strings.BLANK_PAPER_DESC, Strings.BLANK_PAPER_DESC_BLACKLIGHT);	//Blank Paper
   protected static Item flashlight = new Item(Strings.FLASHLIGHT_NAME, Strings.FLASHLIGHT_DESC);											//Flashlight
   protected static Item knob = new Item(Strings.KNOB_NAME, Strings.KNOB_DESC);																//Knob
   
   //Load rooms
   protected static Prison_Cell_A prison_cell_a = new Prison_Cell_A();										//Prison Cell A
   protected static Prison_Cell_B prison_cell_b = new Prison_Cell_B();										//Prison Cell B
   protected static Cell_Block cell_block = new Cell_Block();												//Cell Block
   protected static Prison_Hall_Corner_A prison_hall_corner_a = new Prison_Hall_Corner_A();					//Prison Hall Corner A
   protected static Prison_Hallway prison_hallway = new Prison_Hallway();									//Prison Hallway
   protected static Prison_Hall_Corner_B prison_hall_corner_b = new Prison_Hall_Corner_B();					//Prison Hall Corner B
   protected static Cell_Block_Cavein cell_block_cavein = new Cell_Block_Cavein();							//Cell Block Cave-in
   protected static Prison_Stairs prison_stairs = new Prison_Stairs();										//Prison Stairs
   protected static House_Kitchen house_kitchen = new House_Kitchen();										//Kitchen
   protected static Secret_Passageway secret_passageway = new Secret_Passageway();							//Secret Passageway
   protected static House_Library house_library = new House_Library();										//Library
   protected static House_Foyer house_foyer = new House_Foyer();											//Foyer
   protected static Mud_Room mud_room = new Mud_Room();														//Mud Room
   protected static Dining_Room dining_room = new Dining_Room();											//Dining Room
   protected static Upstairs_Hallway upstairs_hallway = new Upstairs_Hallway();								//Upstairs Hallway
   protected static Upstairs_Bathroom upstairs_bathroom = new Upstairs_Bathroom();							//Bathroom
   protected static Upstairs_Bedroom upstairs_bedroom = new Upstairs_Bedroom();								//Bedroom
   protected static Living_Room living_room = new Living_Room();											//Living Room
   protected static Error_Room error_room = new Error_Room();												//Error Room
   
   public static void main(String[] args)
   {
      playGame();
   }
   
   public static void playGame()
   {
	  if(!SKIP_INTRO)
	  {
	      //Show intro text
	      Text.scrollLine("Welcome to Sideways Dungeon.@", 100);
	      
	      //Get the player's name
	      player.promptName(keyboard);
	      
	      //Continue intro text
	      Text.scroll("\nWelcome, @", 75);
	      Text.scroll(player.getName() + ".@", 10);
	      Text.scrollLine(" Are you ready to start your adventure? ", 30);
	      System.out.print("Type \"yes\" or \"no\": ");
	      
	      keyboard.nextLine();
	      
	      //Regardless of player's input, start the game
	      Text.scrollLine("Great!@ Let's get started!@", 20);
	      Text.scroll("\nOnce upon a time", 125);
	      Text.scrollLine("...@\n\n", 250);
	  }
      
      //Game loop
      String currentRoom = "prison_cell_a";
      String prevRoom = "";
      String prev = "";
      do {
    	  switch(currentRoom)
    	  {
	    	  case "prison_cell_a":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = prison_cell_a.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "prison_cell_b":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = prison_cell_b.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "cell_block":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = cell_block.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "prison_hall_corner_a":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = prison_hall_corner_a.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "prison_hallway":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = prison_hallway.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "prison_hall_corner_b":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = prison_hall_corner_b.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "cell_block_cavein":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = cell_block_cavein.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "prison_stairs":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = prison_stairs.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "house_kitchen":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = house_kitchen.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "secret_passageway":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = secret_passageway.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "house_library":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = house_library.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "house_foyer":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = house_foyer.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "mud_room":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = mud_room.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "dining_room":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = dining_room.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "upstairs_hallway":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = upstairs_hallway.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "upstairs_bathroom":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = upstairs_bathroom.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "upstairs_bedroom":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = upstairs_bedroom.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "living_room":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = living_room.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  case "error_room":
	    	  {
	    		  prevRoom = currentRoom;
	    		  currentRoom = error_room.playRoom(prev);
	    		  prev = prevRoom;
	    		  break;
	    	  }
	    	  default:
	    		  System.out.println("{{ROOM NOT FOUND. LOADING PREVIOUS ROOM}}");
	    		  currentRoom = prevRoom;
    	  }
      } while(!currentRoom.equals("done"));
   }
}