package exceptions;

public class MaxSpeciesAgeExceededException extends RuntimeException
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaxSpeciesAgeExceededException(String theCause)
	{
		super(theCause);
	}
}
