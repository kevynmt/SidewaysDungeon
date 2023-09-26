package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;
import java.util.ArrayList;

//Parent class of all room classes
public class Room extends Master
{
   //Data types
   protected ArrayList<String> directions = new ArrayList<String>();     //A list of all possible directions the player can go (north, south, east, west)
   protected ArrayList<String> usable = new ArrayList<String>();         //A list of all items the player can use
   protected ArrayList<String> takeable = new ArrayList<String>();       //A list of all items the player can take
   protected ArrayList<String> talkable = new ArrayList<String>();       //A list of all entities the player can talk to
   
   //Common variables
   protected String lastShownText = "";		//The last block of text the player was shown. Printed when the player enters the "map" command
   protected String roomName = "";			//The name of the room that appears above the map. Set during initialization
   protected float fearMultiplier = 0;		//The room's multiplier for the cat fear mechanic. Set during initialization. In general, the darker or more unsettling the room is, the higher the multiplier
   protected int numVisits = 0;				//The number of times the player has visited this room
   
   //For room descriptions = 60 characters across
   
   //Default constructor
   public Room()
   {
   }
   
   //updateStats VOID METHOD: Updates the player's number of moves, the fun value, and the number of visits to this room
   protected void updateStats()
   {
	   numVisits++;
	   Operate.updateFun();
	   player.incNumMoves();
   }
   
   //exits STRING METHOD: Returns the contents of directions, if any
   protected String exits()
   {
	   String result = "Exits: ";
	   
	   if(directions.isEmpty())
		   result += Strings.NO_EXITS;
	   else
		   result += directions.toString();
	   
	   return result + "\n";
   }
   
   //getRoomName STRING METHOD: Returns the room name
   public String getRoomName()
   {
	   return roomName;
   }
   
   //getNumVisits INT METHOD: Returns the number of times the player visited this room
   public int getNumVisits()
   {
	   return numVisits;
   }
}