package sidewaysdungeon.game_files;

import java.util.Random;

//Performs miscellaneous game functions
public class Operate
{
   private static int fun = 0;      					//Yes, I ripped off this mechanic from Undertale. Leave me alone, it's a good mechanic!
   private static boolean funEventHappened = false;		//Set to true when a fun event occurs, after which fun will be set to 0 and updates will be permanently disabled
   
   //Use the main method to test Operate methods
   public static void main(String[] args)
   {
   }
   
   //delay VOID METHOD: Pauses the program for a specified amount of time in milliseconds
   public static void delay(int time)
   {
      try
      {
         Thread.sleep(time);
      }
      catch (InterruptedException witn)
      {
         witn.printStackTrace();
      }
   }
   
   //updateFun VOID METHOD: Updates the fun variable
   public static void updateFun()
   {
	  if(funEventHappened)
		  return;
	  
      Random rand = new Random();
      
      //~15% chance to change the fun value (Will change if random number is 0)
      if(rand.nextInt(8) == 0)
    	  //Set fun to a new randomly generated value from 0-100
    	  fun = rand.nextInt(101);
   }
   
   //funEventHappened VOID METHOD: Sets fun to 0 and disables updates
   public static void funEventHappened()
   {
	   fun = 0;
	   funEventHappened = true;
   }
   
   //isFunUpdating BOOLEAN METHOD: Returns whether or not fun is allowed to update - DEV METHOD ONLY
   public static boolean funIsUpdating()
   {
	   return !funEventHappened;
   }
   
   //setFun VOID METHOD: Sets the fun value to specified amount - DEV METHOD ONLY
   public static void setFun(int newFun)
   {
      fun = newFun;
   }
   
   //getFun INT METHOD: Returns the fun value - DEV METHOD ONLY
   public static int getFun()
   {
      return fun;
   }
}