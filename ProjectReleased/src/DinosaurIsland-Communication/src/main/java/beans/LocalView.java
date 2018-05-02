package beans;

import java.io.Serializable;
import java.util.ArrayList;

import commands.CommandHelper;

import exceptions.InvalidLocalViewException;
import util.Vector2D;


final public class LocalView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Character[][] localView;
	private String[][] cellAttribute; //energy for food, id for dinosaurs
	private Vector2D dimension;
	private Vector2D origin;

	public LocalView(String thelocalView) throws NullPointerException, InvalidLocalViewException {

		if (thelocalView == null) {
			throw new NullPointerException();
		}
		String strLine = thelocalView;
		String strLineTemp;
		String commaSeparator = CommandHelper.getNewCommaSeparator();
		Character loadedGrid[][];
		String loadedAttributes[][];
		ArrayList<String> localViewLines;
		
		int commaPosition = strLine.indexOf(",");

		if (commaPosition < 4) {
			InvalidLocalViewException ex = new InvalidLocalViewException("The localView is not valid. Impossible load the localView dimension.");
			throw ex;
		}
	
		strLineTemp = strLine.substring(0, commaPosition);
		
		try {
			setOrigin(CommandHelper.parseVector(strLineTemp, commaSeparator));
		}
		catch (IllegalArgumentException e) {
			//getLogger().warning(StackTraceUtils.getThrowMessage(e));
			InvalidLocalViewException ex = new InvalidLocalViewException("The local view is not valid. Impossible parse the origin.");
			//getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}
		
		strLine = strLine.substring(commaPosition+1);
		commaPosition = strLine.indexOf(",");

		if (commaPosition < 4) {
			InvalidLocalViewException ex = new InvalidLocalViewException("The local view is not valid. Impossible load the local view dimension.");
			throw ex;
		}

		strLineTemp = strLine.substring(0, commaPosition);

		try {
			setDimension(CommandHelper.parseVector(strLineTemp, commaSeparator));
			loadedGrid =  new Character[(int) getDimension().getX()][(int) getDimension().getY()] ;
			loadedAttributes  = new String[(int) getDimension().getX()][(int) getDimension().getY()] ;
		}
		catch (IllegalArgumentException e) {
			//getLogger().warning(StackTraceUtils.getThrowMessage(e));
			InvalidLocalViewException ex = new InvalidLocalViewException("The local view is not valid. Impossible parse the local view dimension.");
			//getLogger().warning(StackTraceUtils.getThrowMessage(ex));
			throw ex;
		}

		/* Preparing to read the local view content */
		/* Setting up the regexp localView table from the dimension parameter just readed from the file */
		ArrayList<String> regexplocalViewLines = new ArrayList<String>();
		String row = "";

		for (int i = 0; i < dimension.getX(); i++) {
			row = row + "(\\[(a|t|v,([0-9]+)|c,([0-9]+)|d,(\\w+))\\]|\\[(a|t|v/([0-9]+)|c/([0-9]+)|d/(\\w+))\\])";
		}
		
		for (int y = 0; y < dimension.getY(); y++) {
			regexplocalViewLines.add(row);
		}

		localViewLines = new ArrayList<String>();
		strLine = strLine.substring(commaPosition+1);

		String[] rowsLoadedFromFiles = strLine.split(";");

		for (String str : rowsLoadedFromFiles) {
			localViewLines.add(str);
		}
		
		CommandHelper.validate(localViewLines, regexplocalViewLines);
 
		ArrayList<String> cellStrings = new ArrayList<String>(); 
		String[] cellStringsTemporary;
		
		for (String str : localViewLines) {

			str = str.substring(1) + "[";
			cellStringsTemporary = str.split("\\]\\[");

			for (String cellStr : cellStringsTemporary)
				cellStrings.add(cellStr);

		}
		
		localViewLines = null; // Freeing memory
		
		 String attribute;
		 int cellNumber = 0;
		 int x,y;
		 commaPosition = 0;
		 
		 for (String str : cellStrings) {
			 
			 commaPosition = str.indexOf(",");
			 if (commaPosition == -1)
				 commaPosition = str.indexOf("/");
			 
			 x = (int) (cellNumber % dimension.getX());
			 y = (cellNumber / (int) dimension.getX());
			 
			 loadedGrid[x][y] = str.charAt(0);
			 
			 if (commaPosition > 0) {

				 attribute = str.substring(commaPosition+1, str.length());		 
				 loadedAttributes[x][y] = attribute;
			 }

			 cellNumber++;
		 }
		
		localView = loadedGrid;
		cellAttribute = loadedAttributes;

	}

	public String toString() {

		String str = "{"+(int)getOrigin().getX()+","+(int)getOrigin().getY()+"},";
		str += "{"+(int)getDimension().getX()+","+(int)getDimension().getY()+"},";

		for (int y = 0; y < getDimension().getY(); y++) {
			for (int x = 0; x < getDimension().getX(); x++) {
				
				
				if (cellAttribute[x][y] != null) {
					str += "[" + localView[x][y] +","+cellAttribute[x][y]+ "]";
				} else {
					str += "[" + localView[x][y] + "]";	
				}
				
			}
			str+=";";
		}

		return str;
	}


	/**
	 * @return the localView
	 */
	public Character[][] getLocalView() {
		return localView;
	}
	
	public String[][] getAttributes() {
		 return cellAttribute;
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
	
	/**
	 * @return the dimension
	 */
	public Vector2D getOrigin() {
		return origin;
	}

	/**
	 * @param dimension the dimension to set
	 */
	public void setOrigin(Vector2D theOrigin) {
		origin = theOrigin;
	}
}
