/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay.map;

import java.io.Serializable;

import util.Position;
import util.Vector2D;

/**
 * <b>Overview</b><br>
 * <p>
 * The MapData class stores map informations. It can be used to load or save the cells contained in a map without dinosaurs' information
 * <br>It includes:
 * <ul>
 * <li>The dimension of a map
 * <li>The array of cells contained in the map
 * </ul>
 * </p>
 *
 * <b>Collaborators</b><br>
 * <p>
 * {@link Map}, {@link Vector2D} 
 * </p>
 * 
 * @author	RAA
 * @version 1.0
 *
 */
public class MapData implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Cell[][] map;
	private Vector2D dimension;
	
	/**
	 * The constructor initilizes the dimension of the map and the array containing its cells 
	 * 
	 * @param theMapToBeSaved
	 * 
	 */
	MapData(Cell[][] theMapToBeSaved) {
		
		int dimX = theMapToBeSaved[0].length;
		int dimY = theMapToBeSaved.length;
		
		dimension = new Vector2D(dimX, dimY);
		
		map = new Cell[dimX][dimY];
		
		for (int y = 0; y < dimY; y++) {
			for (int x = 0; x < dimX; x++) {
				map[x][y] = Map.getInstance().getCell(new Position(x,y)).cloneWithoutDinosaur();
			}
		}
	}
	
	Vector2D getDimension() {
		return new Vector2D(dimension);
	}
	
	Cell[][] getMap() {
		
		return map ;
	}
}
