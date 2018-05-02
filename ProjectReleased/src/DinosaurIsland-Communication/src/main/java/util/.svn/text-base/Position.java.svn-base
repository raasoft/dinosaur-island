package util;

import java.io.Serializable;



/**
 * <b>Overview</b><br>
 * <p>
 * A 2-element vector that is represented by floating point x,y coordinates.<br>
 * It is used to indicate the position of a particular object.
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * None.
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * None. 
 * </p>
 * 
 * @author	RAA
 * @version 1.6
 * @since	May 21, 2011@5:48:55 PM
 *
 */
public class Position extends Vector2D  implements Serializable 
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Position(double theX, double theY) {
		this((int) Math.round(theX), (int) Math.round(theY));
	}
	
	public Position(int theX, int theY) {
		super(theX, theY);
	}
	/**
	 * @param parseVector
	 * 
	 * @since Jun 13, 2011@6:53:45 AM
	 */
	public Position(Vector2D theVector2D) {
		this(theVector2D.getX(), theVector2D.getY());
	}
	/** 
	 * The method getIntX return the closest integer value of the x-coordinate.
	 *
	 * @return the closest integer value of the x-coordinate.   
	 *  
	 * @since	May 21, 2011@5:59:31 PM
	 * 
	 * @see java.math#round(double)
	 * 
	 */
	public int getIntX() 
	{
		return   (int) Math.round(x);
	}

	/** 
	 * The method getIntY return the closest integer value of the y-coordinate.
	 *
	 * @return the closest integer value of the y-coordinate.   
	 *  
	 * @since	May 21, 2011@5:59:31 PM
	 * 
	 * @see java.math#round(double)
	 * 
	 */
	public int getIntY()
	{
		return (int) Math.round(y);
	}
	
	public String toString() {
		return "(" + getIntX() + "," + getIntY() + ")";
	}
	
	public Position clone() {
		return new Position(getIntX(), getIntY());
	}
	
	public boolean equals(Position thePosition) {
		return thePosition == null ? false : thePosition.getIntX() == getIntX() && thePosition.getIntY() == getIntY();
	}
}
