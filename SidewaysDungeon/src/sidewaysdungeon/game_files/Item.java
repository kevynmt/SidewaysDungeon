package sidewaysdungeon.game_files;

//A class that defines inventory items
public class Item
{
   private String name = null;
   private String description = null;
   private String descriptionUnderBlacklight = null;
   
   //Default object constructor
   public Item()
   {
   }
   
   //Object constructor - accepts a name and description
   public Item(String name, String description)
   {
      this.name = name.toLowerCase();
      this.description = description;
      this.descriptionUnderBlacklight = description;
   }
   
   //Object constructor - accepts a name, description, and description under blacklight
   public Item(String name, String description, String descriptionUnderBlacklight)
   {
	   this.name = name;
	   this.description = description;
	   this.descriptionUnderBlacklight = descriptionUnderBlacklight;
   }
   
   //setName VOID METHOD: Sets the name of the item
   public void setName(String name)
   {
      this.name = name.toLowerCase();
   }
   
   //setDescription VOID METHOD: Sets the description of the item
   public void setDescription(String description)
   {
      this.description = description;
   }
   
   //appendDescription VOID METHOD: Appends the description of an item
   public void appendDescription(String descAppend)
   {
	   this.setDescription(this.description + descAppend);
   }
   
   //getName STRING METHOD: Returns the name of the item
   public String getName()
   {
      return name;
   }
   
   //getDescription STRING METHOD: Returns the description of the item
   public String getDescription()
   {
      return description;
   }
   
   //getBlacklightDescription STRING METHOD: Returns descriptionUnderBlacklight
   public String getBlacklightDescription()
   {
	   return descriptionUnderBlacklight;
   }
   
   //toString METHOD
   public String toString()
   {
      return name;
   }
}