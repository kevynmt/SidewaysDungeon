package sidewaysdungeon.text;

//A list of common Strings that can be used
public class Strings
{
  /*******************
     COMMON STRINGS
   *******************/
   final public static String NOTHING_IN_INVENTORY = "No items in inventory.";
   final public static String HAD_BEEN_ADDED = " has been added to your inventory.";
   final public static String NEEDS_ANOTHER_ITEM = "This needs another item.";
   final public static String ITEM_NOT_FOUND = "Item not found.";
   final public static String CANT_USE_THAT_HERE = "Can't use that here.";
   final public static String NEW_DISCOVERY = "?! : ";
   final public static String NOTHING_TO_INSPECT = "Everything is as it seems.";
   final public static String NOTHING_TO_USE = "Nothing to use.";
   final public static String CANT_USE_THAT = "Can't use that.";
   final public static String NOTHING_TO_TAKE = "Nothing to take.";
   final public static String CANT_TAKE_THAT = "Can't take that.";
   final public static String NO_ONE_TO_TALK = "No one to talk to.";		//too real
   final public static String CANT_TALK_TO_THAT = "Can't talk to that.";
   final public static String NO_EXITS = "No exits.";
   final public static String PATH_OPENED = "A path has opened to the ";
   final public static String CANT_MOVE = "Can't move ";
   
   final public static String STOVE_IS_NOT_ON = "The stove is not on.";
   
   final public static String BLUE_INSCRIPTION = "Blue comes before Green.";
   final public static String GREEN_INSCRIPTION = "The Lady in Blue is mistaken. It comes after.";
   final public static String YELLOW_INSCRIPTION = "Yellow does not come after Green or Orange.";
   final public static String ORANGE_INSCRIPTION = "Orange comes before Green, but not after Red.";
   final public static String RED_INSCRIPTION = "Press the Red switch first.";
   
   final public static String UNKNOWN_COMMAND = "Unknown command. Type \"help\" for a list of commands.";
   
   final public static String MAP_LEGEND = "----------- ~{ LEGEND }~ -----------\n"
			                                 + "o : You        u : Useable\n"
			                                 + "| / - : Wall   t : Takeable\n"
			                                 + "|| / = : Path  a : Talkable\n"
			                                 + "# : Blocked    ? : Item of Interest\n";
   
   
   /*******************
	   	 TUTORIALS
	*******************/
   //Inspect
   final public static String INSPECT_TUTORIAL = "\nTutorial: Items of interest are marked with a (?) on the map.\n"
	    				 				               + "\t  Type \"inspect\" to inspect them.";
   
   //Use
   final public static String USE_TUTORIAL = "\nTurorial: Useable items are marked with a (u) on the map.\n"
	    				 		          		   + "\t  Type \"use\" to interact with them.";
   
   //Directions
   final public static String DIRECTIONS_TUTORIAL = "\nTutorial: Possible exits are shown beneath the map.\n"
			  											  + "\t  Type a direction to move to another room.";
   
   //Take
   final public static String TAKE_TUTORIAL = "\nTutorial: Items you can take are marked with a (t) on the map.\n"
	   		 										+ "\t  Type \"take\" to pick them up.";
   
   //Map
   final public static String MAP_TUTORIAL = "Tutorial: Type \"map\" to show the map.";
   
   //Inventory
   final public static String INVENTORY_TUTORIAL = "\nTutorial: You picked up an item!\n"
			 											 + "\t  Type \"inventory\" to view your items.";
   
   //Hidden Items of Interest
   final public static String HIDDEN_IOI_TUTORIAL = "\n\nTutorial: Some items of interest do not appear on the map.\n"
		   													+ "\t  Be sure to INSPECT often even if (?) is not shown.";
   
   //Talk
   final public static String TALK_TUTORIAL = "Tutorial: Things you can talk to are marked with an (a) on the map.\n"
		   										  + "\t  Type \"talk\" to talk to them.";
   
   
   /*******************
           ITEMS
    *******************/
    //Torch
    final public static String TORCH_NAME = "torch";
    final public static String TORCH_DESC = "A metal torch. Its burning light is blinding to look at.";
    
    //Unlit torch
    final public static String UNLIT_TORCH_NAME = "unlit torch";
    final public static String UNLIT_TORCH_DESC = "The handle of a metal torch. It's not lit.";
    
    //Cloth
    final public static String CLOTH_NAME = "cloth";
    final public static String CLOTH_DESC = "A white cloth covered in blood stains.";
    
    //Wet Cloth
    final public static String WET_CLOTH_NAME = "wet cloth";
    final public static String WET_CLOTH_DESC = CLOTH_DESC + " It's dripping wet.";
    
    //Note Scrap A
    final public static String NOTE_SCRAP_A_NAME = "note scrap a";
    final public static String NOTE_SCRAP_A_DESC = "A piece of a note with the second half torn off. It reads ";
    
    //Note Scrap B
    final public static String NOTE_SCRAP_B_NAME = "note scrap b";
    final public static String NOTE_SCRAP_B_DESC = "A piece of a note with the first half torn off. It reads ";
    
    //Book
    final public static String BOOK_NAME = "book";
    final public static String BOOK_DESC = "A book found in the upstairs bedroom.";
    final public static String BOOK_DESC_BLACKLIGHT = "The blacklight reveals the book's title: \"How to Install a Secret Door\".";
    
    //Key
    final public static String KEY_NAME = "key";
    final public static String KEY_DESC = "A nondescript metal key. Wonder where it goes to.";
    
    //Knife
    final public static String KNIFE_NAME = "knife";
    final public static String KNIFE_DESC = "A sharp knife. Handle with care.";
    final public static String KNIFE_DESC_BLACKLIGHT = "Surprisingly, there are no marking on the knife under the blacklight.";
    
    //Hot Knife
    final public static String HOT_KNIFE_NAME = "hot knife";
    final public static String HOT_KNIFE_DESC = "A red-hot knife. Great for an internet challenge!";
    
    //Lady in Green Portrait
    final public static String LADY_IN_GREEN_NAME = "lady in green portrait";
    final public static String LADY_IN_GREEN_DESC = "A portrait depicting a lady in a green dress. The inscription reads: \"" + GREEN_INSCRIPTION + "\"";
    
    //Lady in Yellow Portrait
    final public static String LADY_IN_YELLOW_NAME = "lady in yellow portrait";
    final public static String LADY_IN_YELLOW_DESC = "A portrait depicting a lady in a yellow dress. The inscription reads: \"" + YELLOW_INSCRIPTION + "\"";
    
    //Lady in Orange Portrait
    final public static String LADY_IN_ORANGE_NAME = "lady in orange portrait";
    final public static String LADY_IN_ORANGE_DESC = "A portrait depicting a lady in an orange dress. The inscription reads: \"" + ORANGE_INSCRIPTION + "\"";
    
    //Blank Paper
    final public static String BLANK_PAPER_NAME = "blank paper";
    final public static String BLANK_PAPER_DESC = "A piece of paper with nothing written on it.";
    final public static String BLANK_PAPER_DESC_BLACKLIGHT = "The blacklight reveals hidden text on the paper. It reads: \"Check under the stairs.\"";
    
    //Flashlight
    final public static String FLASHLIGHT_NAME = "flashlight";
    final public static String FLASHLIGHT_DESC = "A standard flashlight. It's quite powerful for its size.";
    
    //Knob
    final public static String KNOB_NAME = "knob";
    final public static String KNOB_DESC = "Some kind of knob. It looks like it goes to an appliance of some sort.";
    
    
    
    final public static String OMG_WTF_IS_THIS =  "......................,*,,,,,..                             \r\n"
									    		+ "...............*/,,/.,../,*,*/,.,*,,,,.                   . \r\n"
									    		+ "...........,,///(*./*/(,(####(##(((#(,,/*,.            .....\r\n"
									    		+ "........,/(#(%%%#((##############((((((#&(*.. ... ....  ....\r\n"
									    		+ ".......//((##(/*//(###/##(((((//***////***#//,. ............\r\n"
									    		+ "....../#(#/,,,,,,**((///*****,****////*//////#(*............\r\n"
									    		+ "....,*##,.....,..,,****///**/**/****//******/////...........\r\n"
									    		+ "....,(/......,,,,,****,***/,///**/(##((((((((////(/.........\r\n"
									    		+ "...((*,..,,....,*//((/*,,*,,*//**/////*////(((((//(/........\r\n"
									    		+ ".,//*,,,..,**///////****,,,,,/((((//(#%%#(###//(((/(        \r\n"
									    		+ ",*//*,,,,,***,,**/*/***/*,,,*/(###(#%#((((((((##(((((       \r\n"
									    		+ ",///,,,,,,,,,/(%&**//(////***/ *######(((//((((((((///      \r\n"
									    		+ ".//*,,,,,,..//*.,///(((((/***,,,*/####(((((((((((((((//.....\r\n"
									    		+ ",,/*,,,,,,,,***,,,****//****,,,,,*/(#####(((((((((((((((#.,.\r\n"
									    		+ ",,//*,,,,,,**,,,,,*********,,,,,,*//(###((((//////////(((# ,\r\n"
									    		+ ",,,(*,,,,,,,,****/////////*,**,,,**/(((##((//////////(/(((,,\r\n"
									    		+ ",,,*/*,,,*,,,,,*******//(((/*,*****/(%#((##((/***///////(((*\r\n"
									    		+ ",,,,//*,,,,,,,,,,,,*,**/((#/*(#/**//(/((((#(((//**//////(((*\r\n"
									    		+ ",,,*****,,,,,,,,,.,,,,*/(#(/********///(((((((((/***////((((\r\n"
									    		+ ",,/(*,***,,..,,,...,,,*/(#/******//(((#####(((((//***////(((\r\n"
									    		+ ",,*(**,**,,,...,....,,*/((**,**////(###&&(#@&((((//,*///((((\r\n"
									    		+ ",,,*#/,**,,,,.,,.,,.,,**((*,*/(#@@&&%%%%&%%%%&@%(//***///(((\r\n"
									    		+ ",,,,,//,**,,,,,,,,,,,,,*/(***/#@@@&#((/((#&&%%&&%%##(/*/((((\r\n"
									    		+ "*****,*/(/*,,,,,,,,.,,,,*/(**/#%@@@%#(((#%%%%%%%%####(//((((\r\n"
									    		+ "******,*******,,,,,,,,,,,*///*/(####(((((((#(#(((####(((((((\r\n"
									    		+ "%%//********((**,,,,,,,,,,**//*****///////(((((#((((((##((#@\r\n"
									    		+ "%#@%&//***##%%#/***,,,,,,,,*************////((((#####(##(##&\r\n"
									    		+ "%@&&&&%%%#&&%%#%%/*********,*********/////////((((#######%##\r\n"
									    		+ "@&##%%##&&%%%%&&&%*///**************////(///((#(#######%####\r\n"
									    		+ "#%%%%&&&&&&&%&&&&&&///////******/////*//(((((((#######%#####\r\n"
									    		+ "%%&&&&&&&&&%&&&&&&@*////////////////((((#((((((####%%%#####@\r\n"
									    		+ "&&&&&&&&&%&&@&&&&&&,*****//////(/////((((((#####%%%%%###(#@@";
}