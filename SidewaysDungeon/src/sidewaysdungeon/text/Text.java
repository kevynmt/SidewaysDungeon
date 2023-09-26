package sidewaysdungeon.text;
import sidewaysdungeon.game_files.Operate;
import java.util.Scanner;

//Provides various functions for showing text
public class Text
{
   //Use the main method to test the Text methods
   public static void main(String[] args) throws NameTooShortException, NameTooLongException, BadConfirmationException, BadDialogueResponseException
   {
	   Scanner keyboard = new Scanner(System.in);
	   
	   String[] responses = {"I'd want to die in my sleep.", "I'd want it to happen unexpectedly.", "I'd want to be able to prepare for it", "I'd want to die of my own volition."};
	   System.out.println(promptDialogueResponse(keyboard, responses));
	   
	   keyboard.close();
   }
   
   //scroll STATIC VOID METHOD: Prints text using a "scroll" effect. Accepts the String to print and the number amount of time to delay between characters in milliseconds
   public static void scroll(String string, int delay)
   {
      for(char i : string.toCharArray())
      {
         //If the @ character is entered, the method will pause for half a second
         if(i == '@')
            Operate.delay(500);
         else
         {
            System.out.print(i);
            Operate.delay(delay);
         }
      }
   }
   
   //scrollLine STATIC VOID METHOD: Prints text using a "scroll" effect then prints a newLine. Accepts the String to print and the number amount of time to delay between characters in milliseconds
   public static void scrollLine(String string, int delay)
   {
      scroll(string, delay);
      System.out.println();
   }
   
   //promptName STATIC STRING METHOD: Prompts the user for a name. Throws NameTooShortException and NameTooLongException
   public static String promptName(Scanner keyboard, final int MIN_LENGTH, final int MAX_LENGTH) throws NameTooShortException, NameTooLongException
   {
	   //Game name from user
	   String name = keyboard.nextLine();
	   
	   //Purge all non-letter characters from name
	   name = name.replaceAll("[^a-zA-Z ]", "");
	   
	   //Trim leading and trailing spaces
	   name = name.trim();
	   
	   //Name check: if name is not of the correct length, throw the appropriate exception, otherwise, return the entered name
	   if(name.length() < MIN_LENGTH)
		   throw new NameTooShortException("name too short");
	   else if(name.length() > MAX_LENGTH)
		   throw new NameTooLongException("name too long");
	   else
		   return name;
   }
   
   //promptYesNoConfirmation STATIC BOOLEAN METHOD: Prompts the user for a yes/no answer and returns the appropriate boolean response.
   //If something other that "yes" or "no" are entered, a BadConfirmationException is thrown. Responses are case insensitive.
   public static boolean promptYesNoConfirmation(Scanner keyboard, final boolean SHOW_PROMPT) throws BadConfirmationException
   {
	   if(SHOW_PROMPT)
		   System.out.print("Type \"yes\" or \"no\": ");
	   
	   String response = keyboard.nextLine().toLowerCase();
	   
	   //Return appropriate boolean value, or an exception if a bad response was entered
	   switch(response)
	   {
	   		case "yes":
	   			return true;
	   		case "no":
	   			return false;
	   		default:
	   			throw new BadConfirmationException("bad confirmation");
	   }
   }
   
   //promptDialogueResponse STATIC INT METHOD: Prompts the user for a response to a dialogue question, and returns its int value.
   //If an invalid response is entered, a BadDialogueReposnseException is thrown.
   public static int promptDialogueResponse(Scanner keyboard, final String[] POSSIBLE_RESPONSES) throws BadDialogueResponseException
   {
	   int response;			//Value to be returned
	   String numberPrompt;		//Shows (Type "1", "2", "3", ...). Compiled during the choice printing loop
	   
	   System.out.println("\nHow will you respond?\n");
	   numberPrompt = "(Type ";
	   //Print possible responses
	   for(int i = 0; i < POSSIBLE_RESPONSES.length; i++)
	   {
		   System.out.println((i + 1) + ": \"" + POSSIBLE_RESPONSES[i] + '\"');
		   
		   numberPrompt += "\"" + (i + 1) + "\"";
		   
		   //This is messy but it works so whatever. This is why you don't code when you're tired, kids
		   if(i + 1 != POSSIBLE_RESPONSES.length)
		   {
			   if(i + 2 == POSSIBLE_RESPONSES.length)
			   {
				   if(POSSIBLE_RESPONSES.length > 2)
					   numberPrompt += ',';
				   
				   numberPrompt += " or ";
			   }
			   else
				   numberPrompt += ", ";
		   }
		   else
		   {
			   if(POSSIBLE_RESPONSES.length > 1 && i + 1 != POSSIBLE_RESPONSES.length)
				   numberPrompt += ' ';
		   }
	   }
	   numberPrompt += ')';
	   
	   //Prompt player for response
	   System.out.print("\nYour response " + numberPrompt + ": ");
	   
	   try
	   {
		   //Get response from user
		   response = keyboard.nextInt();
		   
		   //This will throw an IndexOutOfRangeException if the user enters a non-existent response. Basically a lazy way of checking if the user's response is in range
		   @SuppressWarnings("unused")
		   String temp = POSSIBLE_RESPONSES[response - 1];
	   }
	   //If an exception gets thrown, throw a BadDialogueResponseException
	   catch(Exception e)
	   {
		   throw new BadDialogueResponseException("bad dialogue response");
	   }
	   //Flush the Scanner
	   finally
	   {
		   keyboard.nextLine();
	   }
	   
	   //If no exceptions were thrown, return response
	   return response;
   }
}