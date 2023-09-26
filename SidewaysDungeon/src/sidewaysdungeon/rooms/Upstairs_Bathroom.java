package sidewaysdungeon.rooms;

import sidewaysdungeon.game_files.*;
import sidewaysdungeon.text.*;

public class Upstairs_Bathroom extends Room implements RoomTemplate
{
	//Use the main method to test the room
	public static void main(String[] args)
	{
		String result;
		Upstairs_Bathroom testRoom = new Upstairs_Bathroom();
		do {
			result = testRoom.playRoom("");
		} while(result.equals("upstairs_bathroom"));
		System.out.println(result);
	}
	
	//Room items
	private String TOTEM = "totem";		//talk
	
	//Room variables
	private boolean skipSurvey = false;		//DEBUG: Make sure this is set to false when done
	
	private int numTotemTalks = 0;			//The number of times the player has talked to the totem
	private int strangeResponse = -1;		//The player's response to the totem's question about seeing something strange
	private int doingHereResponse = -1;		//The player's response to the totem's question about what they're doing here
	
	private boolean totemPresent = true;	//Whether or not the totem is present
	private boolean sawTotem = false;		//Whether or not the player has been in the room while the totem was present
	
	//Constructor
	public Upstairs_Bathroom()
	{
		roomName = "Bathroom";
		fearMultiplier = 1.5f;
		
		//Add talkable items
		talkable.add(TOTEM);
		
		//Add directions
		directions.add("north");
	}
	
	//map STRING METHOD: Returns the current map to print
	public String map()
	{
		return String.format("---||-------\n"
						   + "|   o    | |\n"
						   + "|      %c | |\n"
						   + "------------\n",
						   (totemPresent ? 'a' : ' '));
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
	
	//playRoom STRING METHOD: Code that actually plays the room
	public String playRoom(String prevRoom)
	{
	 	updateStats();
	 	
	 	if(totemPresent)
	 		sawTotem = true;
	 	
	 	//If the fun value is 68 or 69, and the totem is not present, play the survey fun event
	 	if(!totemPresent && (Operate.getFun() == 68 || Operate.getFun() == 69))
	 	{
	 		//Tell the game that a fun event has happend
	 		Operate.funEventHappened();
	 		
	 		//Play the survey event
	 		playSurvey();
	 	}
	 	
	 	//Print room name
	 	System.out.println("-~" + roomName.toUpperCase() + "~-");
	 	
	 	//Print room map
	 	printMap(false);
	 	
	 	//Print room description
	 	lastShownText = "A small bathroom. ";
	 	
	 	if(totemPresent)
	 	{
	 		lastShownText += "There is some sort of totem standing in the corner.";
	 		
	 		//Show the talk tutorial if this is the player's first visit to this room
	 		if(numVisits == 1)
	 			lastShownText += "\n\n" + Strings.TALK_TUTORIAL;
	 	}
	 	else
	 	{
	 		if(sawTotem)
	 			lastShownText += "The totem from before is gone.";
	 		else
	 			lastShownText += "Doesn't look like there's anything useful in here.";
	 	}
	 	
	 	System.out.println(lastShownText);
	 	
	 	//Update the cat's fear
	 	cat.updateFear(fearMultiplier);
	 	
	 	//Get commands from player
	 	while(true)
	 	{
	 		String command = Commands.promptCommand(keyboard, false, inventory);
	 		
	 		//Intercept commands
	        if(command.length() >= 2 && command.substring(0,2).equals("#&"))	//Load entered room
	        	return command.substring(2);
	        else if(command.equals("##"))										//Reload this room
	        	return "upstairs_bathroom";
	        else if(command.equals("%#"))										//Show this room's variable list
	        {
	        	System.out.println("numVitits: " + numVisits + '\n'
	        					 + "numTotemTalks: " + numTotemTalks + '\n'
	        					 + "strangeResponse: " + strangeResponse + '\n'
	        					 + "doingHereResponse: " + doingHereResponse + '\n'
	        					 + '\n'
	        					 + "totemPresent: " + totemPresent + '\n'
	        					 + "sawTotem: " + sawTotem);
	       	 	continue;
	        }
	 		
	 		//Act on command
	 		switch(command)
	 		{
	 			case "inspect":
	 			{
	 				System.out.println(Strings.NOTHING_TO_INSPECT);
	 				break;
	 			}
	 			case "use":
	 			{
	 				System.out.println("You don't need to use the bathroom right now.");
	 				break;
	 			}
	 			case "talk":
	 			{
	 				//Play totem dialogue
	 				if(Commands.promptTalk(talkable).equals(TOTEM))
	 				{
	 					numTotemTalks++;
	 					
	 					//Dialogue the totem responds with depends on the number of times the player talked to it
	 					if(numTotemTalks == 1)
	 					{
	 						//Increment numTotemsTalkedTo
	 						player.incTotemsTalkedTo();
	 						
	 						System.out.println("\nYou talk to the totem.");
	 						Operate.delay(1250);
	 						
	 						System.out.println("\nTOTEM:");
	 						Text.scrollLine("\"Hmm...@ how can it be that a man is both alive,@ and dead?@@", 50);
	 						Text.scrollLine(" Oh man,@ the others are gonna have a field day with this one.\"", 40);
	 						
	 						Operate.delay(1500);
	 						
	 						System.out.println("\nThe totem doesn't seem to notice you're there. Maybe if you\n"
	 										    + "tried talking to it again?");
	 					}
	 					else if(numTotemTalks == 2)
	 					{
	 						System.out.println("\nYou try talking to the totem again.");
	 						Operate.delay(1250);
	 						
	 						System.out.println("\nTOTEM:");
	 						Text.scrollLine("\"Just doesn't make any sense!@ The cycle should have refreshed\n"
	 									  + " itself accordingly.\"", 45);
	 						
	 						Operate.delay(1200);
	 						System.out.println("\nThe totem notices you standing there.");
	 						Operate.delay(3000);
	 						
	 						System.out.println("\nTOTEM:");
	 						Text.scrollLine("\"Oh, hey!@ Sorry,@ I didn't see you there.@ You're " + player.getName() + ", right?@@", 40);
	 						Text.scrollLine(" Hm?@ How did I know that?@ You told me, remember?@@", 40);
	 						Text.scrollLine(" Or,@ maybe it wasn't you,@ but they looked exactly like you.@@", 45);
	 						
	 						Text.scrollLine("\n Hey,@ I have a question to ask you:@ You haven't noticed\n"
	 								       + " anything...@ strange,@ have you?@ You know,@ things out of place,@\n"
	 									   + " people alive when they shouldn't be,@ that kind of stuff?\"", 38);
	 						
	 						//Get response from the player
	 						boolean validResponse = true;
	 						String[] responses = new String[3];
	 						responses[0] = "Yes, I have."; responses[1] = "No, I haven't."; responses[2] = "What are you talking about?";
	 						
	 						do {
	 							validResponse = true;
	 							
	 							try
	 							{
	 								strangeResponse = Text.promptDialogueResponse(keyboard, responses);
	 							}
	 							catch(BadDialogueResponseException e)
	 							{
	 								validResponse = false;
	 								
	 								Operate.delay(1000);
	 								System.out.println("The totem didn't understand you.");
	 								Operate.delay(2000);
	 							}
	 							
	 						} while(!validResponse);
	 						
	 						Operate.delay(500);
	 						
	 						//Print next dialogue string based on the player's response
	 						System.out.println("\nTOTEM:");
	 						switch(strangeResponse)
	 						{
	 							//"Yes, I have."
	 							case 1:
	 							{
	 								Text.scrollLine("\"Ahh,@ so you've noticed it too.@@", 40);
	 								break;
	 							}
	 							//"No, I haven't."
	 							case 2:
	 							{
	 								Text.scrollLine("\"Really?@ Hmph,@ that's disappointing.@@", 40);
	 								break;
	 							}
	 							//"What are you talking about?"
	 							case 3:
	 							{
	 								Text.scrollLine("\"Hmm.@ I'm gonna guess no, then.@@", 40);
	 								break;
	 							}
	 						}
	 						
	 						Text.scrollLine(" In case you're wondering,@ I'm here the studying the supernatural\n"
	 										+ " events that occasionally occur in this area.@\n"
	 										+ " (Although \"supernatural\" makes it sound like an occult\n"
	 										+ " obsession,@ which I don't particularly appreciate)@@", 38);
	 						
	 						Text.scrollLine("\n We've been getting reports of anomalies in this area\n"
	 										+ " closely related with Mortality Discrepancy Theory,@ and we\n"
	 										+ " simply HAD to come to see it for ourselves.@\n"
	 										+ " Well,@ I say \"we\",@ but my bumbling brother is nowhere to be\n"
	 										+ " found,@ as per usual.@@", 40);
	 						
	 						Text.scrollLine("\n Anyway,@ \"we've\"@ been studying timeline theory for quite a\n"
	 										+ " while now. @It's funny to think how people scoffed at the idea\n"
	 										+ " at first,@ but now everyone sits neck-deep in their own theories\n"
	 										+ " about it.@ It's a mystery that's been plaguing our community\n"
	 										+ " for decades.@@", 36);
	 						
	 						Text.scrollLine("\n ...you look confused.@@", 40);
	 						
	 						Text.scrollLine("\n Honestly, it's confusing for us, too.@ This is still very new\n"
	 								        + " territory we're exploring here.@@", 42);
	 						
	 						Text.scrollLine("\n Now,@ pardon my curtness,@ but you don't strike me as someone\n"
	 										+ " well-versed with timeline theory.@ If I may ask,@ what exactly\n"
	 										+ " are you doing here?\"", 42);
	 						
	 						//Get response from the player
	 						//boolean validResponse;
	 						//String[] responses = new String[3];
	 						responses[0] = "I just woke up here."; responses[1] = "I can't remember."; responses[2] = "I could ask the same to you.";
	 						
	 						do {
	 							validResponse = true;
	 							
	 							try
	 							{
	 								doingHereResponse = Text.promptDialogueResponse(keyboard, responses);
	 							}
	 							catch(BadDialogueResponseException e)
	 							{
	 								validResponse = false;
	 								
	 								Operate.delay(1000);
	 								System.out.println("The totem didn't understand you.");
	 								Operate.delay(2000);
	 							}
	 							
	 						} while(!validResponse);
	 						
	 						Operate.delay(500);
	 						
	 						//Print next dialogue string based on the player's response
	 						System.out.println("\nTOTEM:");
	 						switch(doingHereResponse)
	 						{
	 							//"I just woke up here."
	 							case 1:
	 							{
	 								Text.scrollLine("\"Ahh, really?@ So that actually disproves my previous idea of\n"
	 											   + " total deviancy.@ It seems as though some things do,@ in fact,@\n"
	 											   + " remain the same.@@", 40);
	 								
	 								break;
	 							}
	 							//"I can't remember."
	 							case 2:
	 							{
	 								Text.scrollLine("\"Hmm.@ Absolutely no memory whatsoever from previous events.@\n"
	 											   + " It's as if each time is a completely fresh start.@@", 40);
	 								
	 								break;
	 							}
	 							//"I could ask the same to you."
	 							case 3:
	 							{
	 								Text.scrollLine("\"Jeez,@ I don't remember the last one being so petulant.@@ Although,@\n"
	 											   + " that could suggest that personality does not remain constant\n"
	 											   + " in between refreshes.@ That could open up a whole new world\n"
	 											   + " of theories in and of itself.@@", 40);
	 								
	 								break;
	 							}
	 						}
	 						
	 						Text.scrollLine("\n Thank you, " + player.getName() + ",@ you've helped me out tremendously.@\n"
	 									    + " Now if you'll excuse me,@ I've got some thinking to do.\"", 38);
	 						
	 						Operate.delay(1500);
	 						
	 						//Print new map
	 						lastShownText = "The totem goes back to thinking.";
	 						
	 						printMap(true);
	 					}
	 					else if(numTotemTalks == 3)
	 						System.out.println("\nTOTEM:\n"
	 										 + "\"Just doesn't make any sense! A man shouldn't be able to live\n"
	 										  + " twice in the same cycle, let alone with different mortality\n"
	 										  + " states.\"");
	 					else if(numTotemTalks == 4)
	 						System.out.println("\nTOTEM:\n"
	 										+ "\"Could it be a cycle refresh error, or timeline merging, or...\n"
	 										 + " Oh man, I don't know...\"");
	 					else
	 						System.out.println("The totem is mumbling to itself. You can't understand it at\n"
	 										 + "all.");
	 				}
	 					
	 				break;
	 			}
	 			case "take":
	 			{
	 				Commands.promptTake(takeable);
	 				break;
	 			}
	 			case "map":
	 			{
	 				printMap(true);
	 				break;
	 			}
	 			case "north":
	 			{
	 				System.out.println("You go NORTH.\n\n");
	 				Operate.delay(500);
	 				return "upstairs_hallway";
	 			}
	 			case "east":
	 			case "west":
	 			case "south":
	 			case "up":
	 			case "down":
	 				System.out.println(Strings.CANT_MOVE + command.toUpperCase() + '.');
	 		}
	 	}
	}
	
	//Despawns the totem. Called by prison_cell_a after the player takes the portrait
	public void despawnTotem()
	{
		talkable.remove(TOTEM);
		totemPresent = false;
	}
	
	//Return numTotemTalks
	public int numTotemTalks()
	{
		return numTotemTalks;
	}
	
	//Return strangeResponse
	public int strangeReponse()
	{
		return strangeResponse;
	}
	
	//Survey fun event
	private void playSurvey()
	{
		if(skipSurvey)
			return;
		
		int response = -1;					//The player's response to each survey question (responses are not used anywhere in the game)
		boolean validResponse = true;		//Whether or not the player's response to the prompted survey question was valid
		
		final String badResponse = "Please pick from the options above.";
		
		Operate.delay(1250);
		
		Text.scrollLine("Congratuations!@@", 30);
		Text.scrollLine("You have been randomly selected to take part in a short survey.@@", 35);
		
		Text.scrollLine("\nThe purpose of this survey is to collect a snapshot of our\n"
						+ "players' demographics so that we can work to improve our\n"
						+ "products in the future.@@", 38);
		
		Text.scrollLine("\nThe survey should only take a few moments.@ Please answer\n"
					    + "each question as honestly as possible.@@@", 38);
		
		//Question 1
		Text.scrollLine("\nQuestion 1:@ Your in-game name is set as " + player.getName() + ".@ Is this your\n"
						+ "actual name?", 38);
		
		do {
			validResponse = true;
			try {
				String[] responses = {"Yes", "No"};
				response = Text.promptDialogueResponse(keyboard, responses);
			}
			catch(BadDialogueResponseException e)
			{
				validResponse = false;
				System.out.println(badResponse);
			}
		} while(!validResponse);
		
		//Question 1a: If the player said this wasn't their name, prompt for their real one
		if(response == 2)
		{
			Operate.delay(1000);
			Text.scroll("\nPlease enter your actual name: ", 30);
			keyboard.nextLine();
			Text.scrollLine("@@Got it.@ Thank you.@@", 30);
		}
		
		//Question 2
		Text.scrollLine("\n\n@@@Question 2:@ Which of these age ranges do you fall under?", 38);
		
		do {
			validResponse = true;
			try {
				String[] responses = {"Under 18", "18-24", "25-34", "35-44", "45-64", "65 or above"};
				response = Text.promptDialogueResponse(keyboard, responses);
			}
			catch(BadDialogueResponseException e)
			{
				validResponse = false;
				System.out.println(badResponse);
			}
		} while(!validResponse);
		
		System.out.println("Thanks.");
		
		//Question 3
		Text.scrollLine("\n\n@@@Question 3:@ What is your ethnicity?", 38);
		
		do {
			validResponse = true;
			try {
				String[] responses = {"Asian or Pacific Islander", "Black or African American", "Hispanic or Latino", "Native American or Native Alaskan", "White or Caucasian", "Multiracial or Biracial", "Another race/ethnicity not listed here"};
				response = Text.promptDialogueResponse(keyboard, responses);
			}
			catch(BadDialogueResponseException e)
			{
				validResponse = false;
				System.out.println(badResponse);
			}
		} while(!validResponse);
		
		System.out.println("Got it.");
		
		//Question 4
		Text.scrollLine("\n\n@@@Question 4:@ What gender do you identify as?", 38);
		
		do {
			validResponse = true;
			try {
				String[] responses = {"Male", "Female", "Multiple Genders", "Non-Binary", "I'd Prefer Not to Answer"};
				response = Text.promptDialogueResponse(keyboard, responses);
			}
			catch(BadDialogueResponseException e)
			{
				validResponse = false;
				System.out.println(badResponse);
			}
		} while(!validResponse);
		
		System.out.println("Fascinating!");
		
		Operate.delay(1500);
		Text.scrollLine("Only a few more.@@", 35);
		
		//Question 5
		Text.scrollLine("\n\n@@@Question 4:@ Why did you choose to play this game?", 38);
		
		do {
			validResponse = true;
			try {
				String[] responses = {"A friend recommended it to me.", "I thought it looked interesting.", "I wanted to try something new", "I just found it on my computer."};
				response = Text.promptDialogueResponse(keyboard, responses);
			}
			catch(BadDialogueResponseException e)
			{
				validResponse = false;
				System.out.println(badResponse);
			}
		} while(!validResponse);
		
		Text.scrollLine("@@Mmm-hmm.@@", 35);
		
		//Question 6
		Text.scrollLine("\n\nQuestion 6:@ Are you under the belief that games can cause\n"
							+ "real-world physical harm?", 40);
		
		do {
			validResponse = true;
			try {
				String[] responses = {"Yes", "No", "Perhaps", "I Don't Know"};
				response = Text.promptDialogueResponse(keyboard, responses);
			}
			catch(BadDialogueResponseException e)
			{
				validResponse = false;
				System.out.println(badResponse);
			}
		} while(!validResponse);
		
		//Question 7
		Text.scrollLine("\n\n@@@Question 7:@ If you were to suddenly realize the game was\n"
						     + "sentient,@ how would you react?", 40);
		
		do {
			validResponse = true;
			try {
				String[] responses = {"I would be happy.", "I would feel neutral.", "I would be horrified."};
				response = Text.promptDialogueResponse(keyboard, responses);
			}
			catch(BadDialogueResponseException e)
			{
				validResponse = false;
				System.out.println(badResponse);
			}
		} while(!validResponse);
		
		//Question 8
		Text.scrollLine("\n\n@@@Question 8:@ If you were to choose how you wanted to die,@\n"
							 + "what would you choose?", 41);
		
		do {
			validResponse = true;
			try {
				String[] responses = {"I'd want to die in my sleep.", "I'd want it to happen unexpectedly.", "I'd want to be able to prepare for it.", "I don't want to die."};
				response = Text.promptDialogueResponse(keyboard, responses);
			}
			catch(BadDialogueResponseException e)
			{
				validResponse = false;
				System.out.println(badResponse);
			}
		} while(!validResponse);
		
		//If the player selected option 4, print "That's cute"
		if(response == 4)
			System.out.println("That's cute.");
		
		//Question 9
		Text.scroll("\n\n@@@Question 9:@ Are you aware of the person standing behind you", 38);
		Text.scrollLine("uuuuuuuuuuu", 100);
		
		System.err.println("Exception in thread \"main\" sidewaysdungeon.rooms.UnexpectedSurveyQueryException: Bad question id \"hello there\"\n"
						   + "\tat sidewaysdungeon.rooms.upstairs_bathroom.playRoom(UpstairsBathroom.java:82)\n"
						   + "\tat sidewaysdungeon.rooms.upstairs_bathroom.playSurvey(UpstairsBathroom.java:581)\n"
						   + "\tat sidewaysdungeon.¶¶¶.Survey.getQuestionID(Survey.java:666666666)");
		
		Operate.delay(7150);
		
		//Clear the console screen
		System.out.print("\033[H\033[2J");
	    System.out.flush();
	    
	    Operate.delay(4500);
	    
	    //Print ending message
	    Text.scrollLine("Thanks for your feedback!@ Be seeing you soon!@@@", 30);
	}
}