package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class StringList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> stringList;

	public StringList() {
		stringList = new ArrayList<String>();
	}
	
	public StringList(ArrayList<String> thePlayersList) throws NullPointerException {
		
		if (thePlayersList == null) {
			throw new NullPointerException("The list from which create the this string list bean cannot be null");
		}
		
		stringList = new ArrayList<String>(thePlayersList);
	}
	
	/**
	 * @return the stringList
	 */
	public ArrayList<String> getStrings() {
		return new ArrayList<String>(stringList);
	}

	public int getStringsNumber() {
		return getStrings().size();
	}

	public String getString(int theNumber) throws IndexOutOfBoundsException {
		if (theNumber < 0 || theNumber > getStringsNumber()) {
			throw new IndexOutOfBoundsException("Cannot get the string in position "+ theNumber + " because it is out of the string list bounds");
		}
		return getStrings().get(theNumber);
	}

	public String toString() {
		
		String string = "";
		
		for (int i = 0; i < getStrings().size(); i++) {
			
			if (i != 0) {
				string = string + ",";
			}
			
			string = string + getStrings().get(i);
		}
		
		return string;
		
	}
}
