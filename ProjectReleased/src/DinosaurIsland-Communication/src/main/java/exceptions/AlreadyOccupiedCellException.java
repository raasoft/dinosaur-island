/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package exceptions;

public class AlreadyOccupiedCellException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AlreadyOccupiedCellException(String theCause) {
		super(theCause);
	}
}
