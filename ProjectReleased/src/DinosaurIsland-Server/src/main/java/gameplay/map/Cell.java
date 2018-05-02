package gameplay.map;

import java.io.Serializable;

import util.Position;

import exceptions.InvalidPositionException;
import gameplay.Dinosaur;

/**
 * <b>Overview</b><br>
 * <p>
 * A cell represent a generic unit of map. <br>
 * There can be four types of cell: Terrain, Vegetation, Carrion and Water; <br>
 * the Cell class is the parent of the {@link TerrainCell},
 * {@link VegetationCell}, {@link WaterCell} <br>
 * Cell stores information about:
 * <ul>
 * <li>The dinosaur present on the cell
 * <li>The possibility to walk on the cell
 * <li>The food present in the cell
 * <li>Its position
 * </ul>
 * </p>
 * 
 * <b>Responsibilities</b><br>
 * <p>
 * The Cell class is responsible for storing information about the type of cell
 * present in the map
 * </p>
 * 
 * <br>
 * <b>Collaborators</b><br>
 * <p>
 * {@link Food}, {@link Position}, {@link Dinosaur}
 * </p>
 * 
 * @author AXXO
 * @version 1.1
 * 
 */
public abstract class Cell implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected boolean walkable = true;
	protected Food food = null;
	protected Dinosaur dinosaur = null;

	private Position position = null;

	/**
	 * @return the character which identifies the type of cell
	 * 
	 */
	abstract public String getSymbol();

	/**
	 * @return the string representing the cell for the MainMap command
	 * 
	 */
	abstract public String getMainMapSymbol();

	/**
	 * @return the string representing the cell for the LocalView command
	 * 
	 */
	abstract public String getLocalViewSymbol();

	/**
	 * The method addFood adds a specific type of food in a cell
	 * 
	 * @throws Exception
	 * 
	 * @see Food
	 * 
	 */
	abstract public void addFood() throws Exception;

	/**
	 * The method removeFood removes the food present on the cell
	 * 
	 */
	public void removeFood() {
		setFood(null);
	}

	/**
	 * Constructor sets the position of the Cell
	 * 
	 * @param thePosition
	 *            The desired position of the cell
	 * @throws InvalidPositionException
	 *             if the position given as parameter is invalid
	 * 
	 */
	public Cell(Position thePosition) throws InvalidPositionException {

		setFood(null);

		if (!Map.getInstance().validatePosition(thePosition)) {
			throw new InvalidPositionException("Invalid Position");
		}

		else {
			position = thePosition;
		}
	}

	/**
	 * @return the dinosaur present on the cell
	 */
	public Dinosaur getDinosaur() {
		return dinosaur;
	}

	/**
	 * @param dinosaur
	 *            the dinosaur to set
	 */
	public void setDinosaur(Dinosaur theDinosaur) {
		dinosaur = theDinosaur;
	}

	/**
	 * @return true if the cell is walkable
	 */
	public boolean isWalkable() {
		return walkable;
	}

	/**
	 * @return food The type of food present in the cell
	 */
	public Food getFood() {
		return food;
	}

	/**
	 * @return position The position of the cell
	 */
	public Position getPosition() {
		return position;
	}

	/**
	 * @param theValue
	 *            The desired boolean condition for walkable
	 */
	public void setWalkable(boolean theValue) {
		walkable = theValue;
	}

	/**
	 * @param theFood
	 *            the desired type of food to set on the cell
	 */
	protected void setFood(Food theFood) {
		food = theFood;
	}

	/**
	 * @param thePosition
	 *            The desired position of the cell
	 * @throws InvalidPositionException
	 *             if the position given as parameter is invalid
	 */
	public void setPosition(Position thePosition)
			throws InvalidPositionException {

		if (!Map.getInstance().validatePosition(thePosition)) {
			throw new InvalidPositionException("Invalid Position");
		}

		else {
			position = thePosition;
		}
	}

	/**
	 * The method cloneWithoutDinosaur creates a copy of the cell and its
	 * content except for the dinosaur present on it
	 * 
	 * @return the cloned cell
	 * 
	 */
	protected abstract Cell cloneWithoutDinosaur();
}