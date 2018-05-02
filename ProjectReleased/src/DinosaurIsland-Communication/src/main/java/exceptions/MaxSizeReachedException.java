/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package exceptions;

public class MaxSizeReachedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MaxSizeReachedException(String theCause) {
		super(theCause);
	}

}
