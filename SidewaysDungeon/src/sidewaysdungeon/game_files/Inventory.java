package sidewaysdungeon.game_files;

import sidewaysdungeon.text.Strings;
import java.util.ArrayList;

public class Inventory extends Master
{
   private ArrayList<Item> inventory = new ArrayList<Item>();    	//Stores the player's inventory items
   
   //Use the main method to test Inventory methods
   public static void main(String[] args)
   {
      Inventory test = new Inventory();
      test.add(new Item("test item a", "This is a test item."));
      
      System.out.println(test.promptItem("noid"));
   }
   
   //Constructor method
   public Inventory()
   {
   }
   
   //promptItem STRING METHOD: Prompts the player for an inventory item to use
   public String promptItem(String itemBeingUsed)
   {
      do{
         if(inventory.size() > 0)
         {
        	//Print the player's inventory
        	System.out.println("\n" + this);
        	
            System.out.print("Select item to use on the " + itemBeingUsed + " (type \"cancel\" to exit): ");
            String item = keyboard.nextLine().toLowerCase();
            
            if(item.equals("cancel") || item.equals(""))
               return "";
               
            Item inventoryItem = this.findItem(item);
            
            if(inventoryItem != null)
               return inventoryItem.getName();
            else
               System.out.println(Strings.ITEM_NOT_FOUND);
         }
         else
        	 System.out.println(Strings.NEEDS_ANOTHER_ITEM);
            
      }while(this.numItems() > 0);
      
      return "";
   }
   
   //promptInventory VOID METHOD: Prompts the player for an item and prints its description
   public void promptInventory(boolean blacklight)
   {
      System.out.println(this);
      
      if(inventory.size() > 0)
      {
         System.out.print("Select item to examine (type \"cancel\" to exit): ");
         String item = keyboard.nextLine().toLowerCase();
         
         if(item.equals("cancel") || item.equals(""))
            return;
         
         Item inventoryItem = this.findItem(item);
         
         if(inventoryItem != null)
         {
        	 String desc;
        	 
        	 //If there is a blacklight in the room, get the blacklight description
        	 if(blacklight)
        		 desc = inventoryItem.getBlacklightDescription();
        	 else
        		 desc = inventoryItem.getDescription();
        		 
        	System.out.println(inventoryItem.getName().toUpperCase() + ": " + desc);
         }
         else
            System.out.println(Strings.ITEM_NOT_FOUND);
      }
   }
   
   //promptAndReturnInventory STRING METHOD: Prompts the player for an item and returns it to the class that called the method
   public String promptAndReturnInventory()
   {
	   System.out.println(this);
	      
      if(inventory.size() > 0)
      {
         System.out.print("Select item to examine (type \"cancel\" to exit): ");
         String item = keyboard.nextLine().toLowerCase();
         
         if(item.equals("cancel") || item.equals(""))
            return "";
         
         Item inventoryItem = this.findItem(item);
         
         if(inventoryItem != null)
        	 return inventoryItem.getName();
         else
         {
             System.out.println(Strings.ITEM_NOT_FOUND);
             return "";
         }
      }
      
      return "";
   }
   
   //add VOID METHOD: Adds item to inventory
   public void add(Item item)
   {
      inventory.add(item);
   }
   
   //add VOID METHOD: Same as above, but allows the user to append the description (needed for note scraps a and b)
   public void add(Item item, String descAppend)
   {
	   item.appendDescription(descAppend);
	   inventory.add(item);
   }
   
   //remove VOID METHOD: Removes item from inventory (searched with object name)
   public void remove(Item item)
   {
	   inventory.remove(item);
   }
   
   //remove VOID METHOD: Removes item from inventory (searched with name)
   public void remove(String item)
   {
      Item inventoryItem = this.findItem(item.toLowerCase());
      
      if(inventoryItem != null)
         inventory.remove(inventoryItem);
   }
   
   //replace VOID METHOD: Replaces an item in inventory with another one
   public void replace(String currItem, Item newItem)
   {
	   int index = inventory.indexOf(findItem(currItem));
	   inventory.remove(index);
	   inventory.add(index, newItem);
   }
   
   //contains BOOLEAN METHOD: Returns whether or not inventory contains item
   public boolean contains(String item)
   {
      for(Item inventoryItem : inventory)
         if(inventoryItem.getName().equals(item))
            return true;
      
      //Item was not found
      return false;
   }
   
   //findItem ITEM METHOD: Finds an item using its name and returns it
   public Item findItem(String item)
   {
      for(Item inventoryItem : inventory)
         if(inventoryItem.getName().equals(item))
            return inventoryItem;
      
      //Item was not found
      return null;
   }
   
   //numItems INT METHOD: Returns the number of items in inventory
   public int numItems()
   {
      return inventory.size();
   }
   
   //getInventory ARRAYLIST<ITEM> METHOD: Returns inventory as an immutable ArrayList
   public ArrayList<Item> getInventory()
   {
      ArrayList<Item> result = new ArrayList<Item>();
      
      for(Item item : inventory)
         result.add(item);
      
      return result;
   }
   
   //toString STRING METHOD: Returns a String representation of inventory
   public String toString()
   {
      if(this.numItems() == 0)
         return Strings.NOTHING_IN_INVENTORY;
      
      return "Inventory: " + inventory.toString();
   }
}