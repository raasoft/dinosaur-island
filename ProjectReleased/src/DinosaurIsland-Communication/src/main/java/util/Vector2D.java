package util;

import java.io.Serializable;

/**
 * <b>Overview</b><br>
 * <p>
 * A 2-element vector that is represented by floating point x,y coordinates.
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
public class Vector2D  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected double x = 0;
	protected double y = 0;

	/**
	 *  Constructs and initializes a Vector2D to (0,0).
	 * 
	 * @since May 21, 2011@5:49:36 PM
	 */
	public Vector2D() {
		setX(0);
		setY(0);
	}

	/**
	 * Constructs and initializes a Vector2D to (theX,theY).
	 * 
	 * @param theX is the x coordinate of the vector
	 * @param theY is the y coordinate of the vector
	 * 
	 * @since May 21, 2011@5:50:13 PM
	 */
	public Vector2D(double theX, double theY) {
		setX(theX);
		setY(theY);
	}
	
	/**
	 *  Constructs and initializes a Vector2d to (theVector.getX(),theVector.getY()).
	 *  
	 * @param theVector is the Vector2D to be copied in the current new Vector2D 
	 * 
	 * @since May 21, 2011@5:53:20 PM
	 */
	public Vector2D(Vector2D theVector) {
		setX(theVector.getX());
		setY(theVector.getY());
	}
	
	/** 
	 * The method equals returns true if all of the data members of Vector2D theVector t1 are 
	 * equal to the corresponding data members in this Vector2D.
	 * @param theVector is the Vector2D to be compared to the current one.
	 * @return {@code true} or {@code false}
	 *  
	 * @since	May 21, 2011@5:51:13 PM
	 * 
	 */
	public boolean equals(Vector2D theVector) {
		return theVector == null ? false : theVector.getX() == getX() && theVector.getY() == getY();
	}

	/** 
	 * Returns a string that contains the values of this Vector2D. The form is (x,y).
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "(" + getX() + "," + getY() + ")";
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public void setX(double theX) {
		x = theX;
	}

	public void setY(double theY) {
		y = theY;
	}

}
