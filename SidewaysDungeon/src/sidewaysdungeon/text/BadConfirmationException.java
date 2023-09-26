package sidewaysdungeon.text;

/**
 * The BadConfirmationException will be thrown by the promptYesNoConfirmation()
 * in the Text class if the user enters something other than "yes" or "no".
 * 
 * @author Kevyn Thompson
 */
@SuppressWarnings("serial")
public class BadConfirmationException extends Exception
{
	public BadConfirmationException(String message)
	{
		super(message);
	}
}