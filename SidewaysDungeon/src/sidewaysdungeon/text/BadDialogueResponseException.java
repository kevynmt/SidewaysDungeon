package sidewaysdungeon.text;

/**
 * The BadDialogueResponseException will be thrown by the promptDialogueResponse() method
 * in the Text class if the user enters a bad dialogue response.
 * 
 * @author Kevyn Thompson
 */
@SuppressWarnings("serial")
public class BadDialogueResponseException extends Exception
{
	public BadDialogueResponseException(String message)
	{
		super(message);
	}
}