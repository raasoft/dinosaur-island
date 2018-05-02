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
 * WaterCell class is a subclass of {@linkCell} and defines a cell containing
 * not walkable water and no food
 * </p>
 * 
 * @author AXXO
 * @version 1.1
 * 
 */
public class WaterCell extends Cell {

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
	WaterCell(Position thePosition) {
		super(thePosition);
		setWalkable(false);
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#getSymbol()
	 */
	public String getSymbol() {
		return "a";
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#addFood()
	 */
	public void addFood() throws Exception {
		throw new Exception("Can't add food to a water cell");
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#getMainMapSymbol()
	 */
	public String getMainMapSymbol() {
		return "[" + getSymbol() + "]";
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[" + getSymbol() + "]";
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#cloneWithoutDinosaur()
	 */
	protected WaterCell cloneWithoutDinosaur() {

		WaterCell cell = new WaterCell(getPosition().clone());

		cell.setFood(getFood());
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
