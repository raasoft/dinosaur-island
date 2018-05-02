/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay.map;

import util.Position;

/**
 * <p>
 * TerrainCell is a subclass of {@linkCell} and defines a cell containing
 * walkable terrain and no food <b>Collaborators</b><br>
 * </p>
 * 
 * @author AXXO
 * @version 1.1
 * 
 */
public class TerrainCell extends Cell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sets the position of the Cell
	 * 
	 * @param thePosition
	 *            The desired position of the cell
	 * 
	 */
	TerrainCell(Position thePosition) {
		super(thePosition);
		setWalkable(true);
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#getSymbol()
	 */
	public String getSymbol() {
		if (getFood() == null) {
			return "t";
		} else {
			return "c";
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#getMainMapSymbol()
	 */
	public String getMainMapSymbol() {
		return "[t]";
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#addFood()
	 */
	public void addFood() throws Exception {
		if (getFood() == null)
			setFood(new Carrion());
		else
			throw new Exception("There is already one carrion in this cell");
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (getFood() == null) {
			return "[" + getSymbol() + "]";
		} else {
			return "[" + getSymbol() + "," + getFood().getEnergy() + "]";
		}
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#cloneWithoutDinosaur()
	 */
	protected TerrainCell cloneWithoutDinosaur() {

		TerrainCell cell = new TerrainCell(getPosition().clone());

		cell.setFood(null);

		if (getFood() != null) {

			Carrion carrion = ((Carrion) (getFood())).clone();
			cell.setFood(carrion);

		}
		cell.setPosition(getPosition().clone());
		cell.setWalkable(isWalkable());
		return cell;

	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#getLocalViewSymbol()
	 */
	@Override
	public String getLocalViewSymbol() {

		return toString();

	}
}
