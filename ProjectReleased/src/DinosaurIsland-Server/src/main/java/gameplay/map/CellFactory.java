/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay.map;

import util.Position;

/**
 * <b>Overview</b><br>
 * <p>
 * The class CellFactory is responsible for creating new cells It stores:
 * <ul>
 * <li>Its unique instance
 * </ul>
 * </p>
 * 
 * <b>Collaborators</b><br>
 * <p>
 * {@link Cell}
 * </p>
 * 
 * @author RAA
 * @version 1.0
 * 
 */
public class CellFactory {

	static private CellFactory uniqueInstance = new CellFactory();

	/**
	 * @return the unique instance of the cell factory
	 */
	static public CellFactory getInstance() {
		return uniqueInstance;
	}

	/**
	 * The method createCell creates a new cell
	 * 
	 * @param theSymbol
	 *            the character which identifies the type of the new cell
	 * @param thePosition
	 *            the desired position of the new cell
	 * @return the new cell
	 * @throws IllegalArgumentException
	 *             if the given symbol is not recognized
	 * 
	 * @see WaterCell
	 * @see TerrainCell
	 * @see VegetationCell
	 * 
	 */
	Cell createCell(String theSymbol, Position thePosition)
			throws IllegalArgumentException {

		WaterCell wc = new WaterCell(thePosition);
		TerrainCell tc = new TerrainCell(thePosition);
		VegetationCell vc = new VegetationCell(thePosition);

		if (theSymbol.equals(wc.getSymbol())) {
			return new WaterCell(thePosition);
		} else if (theSymbol.equals(tc.getSymbol())) {
			return new TerrainCell(thePosition);
		} else if (theSymbol.equals(vc.getSymbol())) {
			return new VegetationCell(thePosition);
		} else if (theSymbol.equals("c")) {
			Cell cell = new TerrainCell(thePosition);
			cell.setFood(new Carrion());
			return cell;
		} else {
			throw new IllegalArgumentException("The current symbol: "
					+ theSymbol + " does not belong to any cell type");
		}

	}

	/**
	 * cloneWithoutDinosaur creates a copy of a generic cell and its content
	 * except for the dinosaur present on it
	 * 
	 * @param theCell
	 *            The cell to clone
	 * @return the cloned cell
	 * 
	 * @see Cell
	 * 
	 */
	Cell cloneWithoutDinosaur(Cell theCell) {
		return theCell.cloneWithoutDinosaur();
	}

	/**
	 * cloneWithoutDinosaur creates a copy of a vegetation cell and its content
	 * except for the dinosaur present on it
	 * 
	 * @param theCell
	 *            The cell to clone
	 * @return the cloned cell
	 * 
	 * @see VegetationCell
	 * 
	 */
	VegetationCell cloneWithoutDinosaur(VegetationCell theCell) {
		return theCell.cloneWithoutDinosaur();
	}

	/**
	 * cloneWithoutDinosaur creates a copy of a terrain cell and its content
	 * except for the dinosaur present on it
	 * 
	 * @param theCell
	 *            The cell to clone
	 * @return the cloned cell
	 * 
	 * @see TerrainCell
	 * 
	 */
	TerrainCell cloneWithoutDinosaur(TerrainCell theCell) {
		return theCell.cloneWithoutDinosaur();
	}

	/**
	 * cloneWithoutDinosaur creates a copy of a water cell and its content
	 * except for the dinosaur present on it
	 * 
	 * @param theCell
	 *            The cell to clone
	 * @return the cloned cell
	 * 
	 * @see WaterCell
	 * 
	 */
	WaterCell cloneWithoutDinosaur(WaterCell theCell) {
		return theCell.cloneWithoutDinosaur();
	}

}
