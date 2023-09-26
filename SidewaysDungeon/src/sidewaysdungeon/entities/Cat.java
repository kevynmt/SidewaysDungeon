package sidewaysdungeon.entities;

import sidewaysdungeon.text.Text;
import sidewaysdungeon.game_files.Operate;
import java.util.Scanner;
import java.util.Random;

//Stores information and methods for the player's cat
public class Cat 
{
	//Cat variables
	private String name = "{{NO NAME}}";	//The name of the player's cat
	
	private boolean withPlayer = false;		//Whether or not the player currently has the cat
	private boolean taken = false;			//Whether or not the player has taken the cat
	private boolean ranAway = false;		//Whether or not the cat has run away
	
	private float fear = 0;					//The cat's fear level
	
	private int fearLock = 0;				//Random number of moves (2-5) after petting the cat that the player must make before fear can increase again
	
	final private int MIN_NAME_LENGTH = 3;		//The minimum number of characters the cat's name can be
	final private int MAX_NAME_LENGTH = 10;		//The maximum number of characters the cat's name can be
	
	//Use the main method to test Cat methods
	public static void main(String[] args)
	{
		Cat test = new Cat();
		test.promptName(new Scanner(System.in), "humanbun");
	}
	
	//Constructor
	public Cat()
	{
	}
	
	//setName VOID METHOD: Sets the name of the player's cat
	public void setName(String name)
	{
		this.name = name;
	}
	
	//getName STRING METHOD: Returns the cat's name
	public String getName()
	{
		return name;
	}
	
	//promptName VOID METHOD: Prompts the player for a name for the cat
	public void promptName(Scanner keyboard, String playerName)
	{
		boolean validResponse = true;
		boolean validName = true;
		boolean firstTimeShort = true;
		boolean firstTimeLong = true;
		String name;
		do{
			validResponse = true;
			
			Text.scrollLine("\nWill you take the cat?", 70);
			System.out.print("Type \"yes\" or \"no\": ");
		
			switch(keyboard.nextLine().toLowerCase())
			{
				case "yes":
				{
					System.out.println();
					
					do{
				    	 validName = true;
				         Text.scroll("@What would you like to name her: @", 40);
				         name = keyboard.nextLine();
				         
				         //Purge all non-letter characters from name
				         name = name.replaceAll("[^a-zA-Z ]", "");
				         
				         //Trim leading and tailing spaces from playerName
				         name = name.trim();
				         
				         //Length check. Name should be between 4-10 characters
				         if(name.length() < MIN_NAME_LENGTH)
				         {
				        	validName = false;
				        	//nameRetries++;
				            Text.scrollLine("@...@",500);
				            
				            if(firstTimeShort)
				            {
				               Text.scrollLine("Sorry,@ I should have specified.", 20);
				               firstTimeShort = false;
				            }
				            
				            Operate.delay(200);
				            Text.scrollLine("Her name must be at least " + MIN_NAME_LENGTH + " characters.@\n", 20);
				            continue;
				         }
				         else if(name.length() > MAX_NAME_LENGTH)
				         {
				        	validName = false;
				        	//nameRetries++;
				            Text.scrollLine("@...@",500);
				            
				            if(firstTimeLong)
				            {
				               Text.scrollLine("Sorry,@ I should have specified.", 20);
				               firstTimeLong = false;
				            }
				            
				            Operate.delay(200);
				            Text.scrollLine("Her name cannot be more than " + MAX_NAME_LENGTH + " characters.@\n", 20);
				            continue;
				         }
				         
				         System.out.println();
				         
				         //Easter egg if the player names the cat Kevyn and is not named Elysa
				         if(name.equalsIgnoreCase("Kevyn") && !playerName.equalsIgnoreCase("Elysa"))
				         {
				        	 validName = false;
				        	 Text.scrollLine("No.", 20);
				        	 continue;
				         }
				         //Easter egg if the player names the cat after themselves
				         else if(name.equalsIgnoreCase(playerName))
				        	 Text.scrollLine("Naming the cat after yourself?@ Are you sure?", 20);
				         else
				        	 Text.scrollLine(name + "?@ Is that correct?", 20);
				         
				         do {
				        	 validResponse = true;
					         System.out.print("Type \"yes\" or \"no\": ");
					         
					         switch(keyboard.nextLine().toLowerCase())
					         {
					         	case "yes":
					         	{   
					         		this.name = name;
					         		taken = true;
					         		withPlayer = true;
					         		//Commands.addPet();	//TODO move to Kitchen
					         		
					         		//If the cat's name is Elysa, show the following message
					         		if(this.name.equalsIgnoreCase("Elysa"))
					         		{
					         			Text.scroll("@You name the cat Elysa.@", 50);
					         			Text.scrollLine(" Of course.@@", 20);
					         		}
					         		//Else, if the cat is named after the player, show the following message
					         		else if(this.name.equalsIgnoreCase(playerName))
					         		{
					         			Text.scroll("@Alright...@ You name the cat " + this.name + ".@", 50);
					         			Text.scrollLine(" After yourself...@@", 20);
					         		}
					         		//Else, show this message
					         		else
					         			Text.scrollLine("@You name the cat " + this.name + ".@@", 50);
					         		
					         		break;
					         	}
					         	case "no":
					         	{
					         		//nameRetries++;
					         		validName = false;
					         		Operate.delay(250);
					         		
					         		if(name.equals(playerName))
					         			Text.scrollLine("Thought so.", 20);
					         		
					         		System.out.println();
					         		break;
					         	}
					         	default:
					         	{
					         		//nameRetries++;
					         		validResponse = false;
					         		Text.scrollLine("\n@...@", 500);
					         		Text.scrollLine("Sorry,@ I didn't get that.@", 20);
					         		
					         		Text.scrollLine("Is " + name + " what you want to name her?", 20);
					         	}
					         }
				         }while(!validResponse);
				      }while (!validName);
					
					break;
				}
				case "no":
				{
					Text.scrollLine("@\nYou close the cabinet and move on.@@", 45);
					break;
				}
				default:
				{
					validResponse = false;
					
					Text.scrollLine("\n@...@", 500);
	         		Text.scrollLine("Sorry,@ I didn't get that.@", 20);
				}
			}
		}while(!validResponse);
	}
	
	//pet VOID METHOD: Reduces fear and sets a fearLock
	public void pet()
	{
		Random rand = new Random();
		fear = 0;
		
		//fearLock is set to a random value between 2 and 5
		if(fearLock <= 0)
			fearLock = rand.nextInt(3) + 2;
		
		System.out.println("You pet " + name + ".");
		
		//1 in 500 chance to get the following messages
		if(rand.nextInt(501) == 0)
		{
			if(name.equalsIgnoreCase("elysa"))
			{
				Text.scroll("@She boops your nose.", 0);
				Text.scroll("@...@", 500);
				Text.scroll("That felt nice.", 10);
			}
			else
			{
				Text.scroll("@She pets you back.", 0);
				Text.scroll("@...@", 500);
				Text.scroll("That was weird.", 2);
			}
		}
		else
		{
			switch(rand.nextInt(5))
			{
				case 0:
				{
					System.out.println("She flops onto her back and asks you to rub her belly.");
					break;
				}
				case 1:
				{
					System.out.println("She rubs up against your leg and purrs.");
					break;
				}
				case 2:
				{
					System.out.println("She pounces on your leg and asks to be picked up.");
					break;
				}
				case 3:
				{
					System.out.println("She adjusts her head so that you're scratching her ear and sighs.");
					break;
				}
				default:
				{
					System.out.println("She jumps into your lap and licks your face.");
				}
			}
		}
	}
	
	//updateFear VOID METHOD: Updates the cat's fear level
	public void updateFear(float mult)
	{
		if(withPlayer)
		{
			if(fearLock <= 0)
			{
				fear += (1.5 * mult);
				
				//If fear ends up below 0, set it to 0
				if(fear < 0)
					fear = 0;
			}
			else
				fearLock--;
			
			//Cat fear responses
			Random rand = new Random();
			System.out.println();
			if(fear < 3)		//fear 0-2
			{
				switch (rand.nextInt(4))
				{
					case 0:
					{
						System.out.println(name + " looks fine.");
						break;
					}
					case 1:
					{
						System.out.println(name + " looks happy.");
						break;
					}
					case 2:
					{
						System.out.println(name + " is looking confident.");
						break;
					}
					default:
					{
						System.out.println(name + " is off in her own little world.");
					}
				}
			}
			else if(fear >= 3 && fear < 6)	//fear 3-5
			{
				switch(rand.nextInt(4))
				{
					case 0:
					{
						System.out.println(name + " looks a little nervous.");
						break;
					}
					case 1:
					{
						System.out.println(name + " is licking her lips nervously.");
						break;
					}
					case 2:
					{
						System.out.println(name + " is shaking slightly.");
						break;
					}
					default:
					{
						System.out.println(name + " is whimpering softly.");
					}
				}
			}
			else if(fear >= 6 && fear < 10)	//fear 6-9
			{
				switch(rand.nextInt(3))
				{
					case 0:
					{
						System.out.println(name + " looks terrified.");
						break;
					}
					case 1:
					{
						System.out.println(name + " is trembling.");
						break;
					}
					default:
					{
						System.out.println(name + " is meowing loudly.");
					}
				}
			}
			else if(fear >= 10)		//fear 10+
			{
				System.out.println(name + " ran away!");
				ranAway = true;
				withPlayer = false;
			}
		}
	}
	
	//getFear FLOAT METHOD: Returns fear and fearLock - DEV METHOD ONLY
	public String getFear()
	{
		return fear + "\n" + fearLock;
	}
	
	//setFear VOID METHOD: Sets fear to a specified value
	public void setFear(float fear)
	{
		this.fear = fear;
	}
	
	//takeCat VOID METHOD: Sets taken and withPlayer to true
	public void takeCat()
	{
		taken = true;
		withPlayer = true;
	}
	
	//giveCat VOID MEHTOD: Sets withPlayer to true
	public void giveCat()
	{
		withPlayer = true;
	}
	
	//untakeCat VOID METHOD: Yeets the cat so hard it's like the player never even took it in the first place
	public void untakeCat()
	{
		taken = false;
		withPlayer = false;
	}
	
	//removeCat VOID METHOD: Yeets the cat
	public void removeCat()
	{
		withPlayer = false;
	}
	
	//withPlayer BOOLEAN METHOD: Returns withPlayer
	public boolean withPlayer()
	{
		return withPlayer;
	}
	
	//takenCat BOOLEAN METHOD: Returns taken
	public boolean taken()
	{
		return taken;
	}
	
	//ranAway BOOLEAN METHOD: Returns ranAway
	public boolean ranAway()
	{
		return ranAway;
	}
}