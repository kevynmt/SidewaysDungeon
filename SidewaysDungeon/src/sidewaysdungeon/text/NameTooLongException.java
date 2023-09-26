package sidewaysdungeon.text;

/**
 * The NameTooLongException will be thrown by the promtName() method in the Text class
 * if the entered name is longer than the specified maximum length.
 * 
 * @author Kevyn Thompson
 *
 */
@SuppressWarnings("serial")
public class NameTooLongException extends Exception
{
	public NameTooLongException(String message)
	{
		super(message);
	}
}