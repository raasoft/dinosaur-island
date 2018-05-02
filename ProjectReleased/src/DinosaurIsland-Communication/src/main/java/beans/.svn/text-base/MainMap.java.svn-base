package beans;

import java.io.Serializable;
import java.util.ArrayList;

import commands.CommandHelper;

import exceptions.InvalidMapException;
import util.Vector2D;


final public class MainMap implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Character[][] map;

	private Vector2D dimension;

	public MainMap(String theMap) throws NullPointerException, InvalidMapException {

		if (theMap == null) {
			NullPointerException e = new NullPointerException();
			System.out.println("Trying to obtain a main map from a null main map string");
			throw e;
		}

		String strLine = theMap;
		String commaSeparator = CommandHelper.getNewCommaSeparator();
		Character loadedGrid[][];
		ArrayList<String> mapLines;

		int commaPosition = theMap.indexOf(",");

		if (commaPosition < 4) {
			InvalidMapException ex = new InvalidMapException("The map is not valid. Impossible load the map dimension.");
			throw ex;
		}

		strLine = theMap.substring(0, commaPosition);

		try {
			dimension = CommandHelper.parseVector(strLine, commaSeparator);
			setDimension(dimension);
			loadedGrid =  new Character[(int) dimension.getX()][(int) dimension.getY()] ;
		}
		catch (IllegalArgumentException e) {
			//getLogger().warning(StackTraceUtils.getThrowMessage(e));
			InvalidMapException ex = new InvalidMapException("The map is not valid. Impossible parse the map dimension.");
			//getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		/* Preparing to read the map content */
		/* Setting up the regexp map table from the dimension parameter just readed from the file */
		ArrayList<String> regexpMapLines = new ArrayList<String>();
		String row = "";

		for (int i = 0; i < dimension.getX(); i++) {
			row = row + "\\[(a|t|v|c|b)\\]";
		}
		
		for (int y = 0; y < dimension.getY(); y++) {
			regexpMapLines.add(row);
		}

		mapLines = new ArrayList<String>();
		strLine = theMap.substring(commaPosition+1);

		String[] rowsLoadedFromFiles = strLine.split(";");

		for (String str : rowsLoadedFromFiles) {
			mapLines.add(str);
		}
		
		CommandHelper.validate(mapLines, regexpMapLines);

		ArrayList<String> cellStrings = new ArrayList<String>(); 
		String[] cellStringsTemporary;
		
		for (String str : mapLines) {

			str = str.substring(1) + "[";
			cellStringsTemporary = str.split("\\]\\[");

			for (String cellStr : cellStringsTemporary)
				cellStrings.add(cellStr);

		}

		mapLines = null; // Freeing memory

		int cellNumber = 0;
		int x,y;
		commaPosition = 0;

		for (String cellType : cellStrings) {			 

			x = (int) (cellNumber % dimension.getX());
			y = (cellNumber / (int) dimension.getX());

			loadedGrid[x][y] = cellType.charAt(0);
			cellNumber++;
		}

		map = loadedGrid;

	}

	public String toString() {

		String str = "{"+(int)getDimension().getX()+","+(int)getDimension().getY()+"},";

		for (int y = 0; y < getDimension().getY(); y++) {
			for (int x = 0; x < getDimension().getX(); x++) {
				str += "[" + map[x][y] + "]";
			}
			str+=";";
		}

		return str;
	}


	/**
	 * @return the map
	 */
	public Character[][] getMap() {
		return map;
	}

	/**
	 * @return the dimension
	 */
	public Vector2D getDimension() {
		return dimension;
	}

	/**
	 * @param dimension the dimension to set
	 */
	public void setDimension(Vector2D theDimension) {
		dimension = theDimension;
	}
}
