/**
 * Copyright (c) 2011 RAAXXO
 * 
 * This file is part of Dinosaur Island.
 * 
 */
package gameplay.map;

import exceptions.InvalidPositionException;
import util.Position;

/**
 * <p>
 * VegetationCell class is a subclass of {@linkCell} and defines a cell
 * containing walkable terrain and vegetation <b>Collaborators</b><br>
 * <p>
 * {@link Food}
 * </p>
 * 
 * @author AXXO
 * @version 1.1
 * 
 */
public class VegetationCell extends Cell {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor sets the position of the Cell
	 * 
	 * @param thePosition
	 *            The desired position of the cell
	 * @throws InvalidPositionException
	 *             if the position given as parameter is invalid
	 * 
	 */
	VegetationCell(Position thePosition) {
		super(thePosition);
		try {
			addFood();
		} catch (Exception e) {
			/*
			 * this exception is never thrown at the creation of the vegetation
			 * cell
			 */
		}
		setWalkable(true);
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#addFood()
	 */
	public void addFood() throws Exception {
		if (getFood() == null)
			setFood(new Vegetation());
		else
			throw new Exception("There is already one carrion in this cell");
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
	 * @see gameplay.map.Cell#getSymbol()
	 */
	public String getSymbol() {
		return "v";
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[v," + getFood().getEnergy() + "]";
	}

	/*
	 * {@inheritDoc}
	 * 
	 * @see gameplay.map.Cell#cloneWithoutDinosaur()
	 */
	protected VegetationCell cloneWithoutDinosaur() {

		VegetationCell cell = new VegetationCell(getPosition().clone());

		Vegetation vegetation = ((Vegetation) (getFood())).clone();

		cell.setFood(vegetation);
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