package sidewaysdungeon.text;

/**
 * The NameTooShortException will be thrown by the promptName() method in the Text class
 * if the entered name is shorter than the specified minimum length.
 * 
 * @author Kevyn Thompson
 *
 */
@SuppressWarnings("serial")
public class NameTooShortException extends Exception
{
	public NameTooShortException(String message)
	{
		super(message);
	}
}