package gui.ingame.dinosaurislandgame;

import gui.ingame.core.Dinosaur;
import gui.ingame.core.DinosaurManager;

import java.awt.Image;
import java.util.ArrayList;

import util.Position;

import beans.LocalView;
import beans.MainMap;


/**
    The TileMap class contains the data for a tile-based
    map, including Sprites. Each tile is a reference to an
    Image. Of course, Images are used multiple times in the tile
    map.
*/
public class TileMap {

    private Image[][] tiles;
    private int[][] foodEnergy;
    private String[][] dinosaurID;
    private GameRenderer renderer;
    
    protected Position cellSourceSelected;
  	protected Position cellDestinationSelected;
    
    final int NO_VALUE = -1;

	/**
        Creates a new TileMap with the specified width and
        height (in number of tiles) of the map.
    */
    public TileMap() {
        tiles = new Image[40][40];
        foodEnergy = new int[40][40];
        dinosaurID = new String[40][40];
        renderer = null;
        cellSourceSelected = null;
        cellDestinationSelected = null;
    }


	public void freeResources() {
		setCellSourceSelected(null);
	}
    
    /**
	 * @return the cellSourceSelected
	 */
	public Position getCellSourceSelected() {
		return cellSourceSelected;
	}

	/**
	 * @param cellSourceSelected the cellSourceSelected to set
	 */
	public void setCellSourceSelected(Position theCellSourceSelected) {
		cellSourceSelected = theCellSourceSelected;
	}

	/**
	 * @return the cellDestinationSelected
	 */
	public Position getCellDestinationSelected() {
		return cellDestinationSelected;
	}

	/**
	 * @param cellDestinationSelected the cellDestinationSelected to set
	 */
	public void setCellDestinationSelected(Position theCellDestinationSelected) {
		cellDestinationSelected = theCellDestinationSelected;
	}

	public void setBlankMap() {
    	  for (int y = getHeight()-1; y >= 0; y--) {
  			for (int x = 0; x < getWidth(); x++) {
  				setTile(x, y,  ResourceManager.getInstance().getTileImage(new Character('b')));
  				foodEnergy[x][y] = NO_VALUE;
  				dinosaurID[x][y] = null;
  			}
  		}
    }
	
	public int getCellEnergy(Position theCell) {
		
		int x = theCell.getIntX();
		int y = theCell.getIntY();
		
		if (dinosaurID == null || foodEnergy == null)
			return -1;
		
		
		if (dinosaurID[x][y] == null && foodEnergy[x][y] >= 0)
			return foodEnergy[x][y];
		else
			return -1;
	}

	public String getCellDinosaurID(Position theCell) {
		
		int x = theCell.getIntX();
		int y = theCell.getIntY();
		
		return dinosaurID[x][y]; 
	}
	
    /**
        Gets the width of this TileMap (number of tiles across).
    */
    public int getWidth() {
        return tiles.length;
    }


    /**
        Gets the height of this TileMap (number of tiles down).
    */
    public int getHeight() {
        return tiles[0].length;
    }


    /**
        Gets the tile at the specified location. Returns null if
        no tile is at the location or if the location is out of
        bounds.
    */
    public Image getTile(int x, int y) {
        if (x < 0 || x >= getWidth() ||
            y < 0 || y >= getHeight())
        {
            return null;
        }
        else {
            return tiles[x][y];
        }
    }

    /**
        Sets the tile at the specified location.
    */
    public void setTile(int x, int y, Image tile) {
        tiles[x][y] = tile;
    }


    /**
	 * @return the renderer
	 */
	public GameRenderer getRenderer() {
		return renderer;
	}


	/**
	 * @param renderer the renderer to set
	 */
	public void setRenderer(GameRenderer theRenderer) {
		renderer = theRenderer;
		renderer.setTileMap(this);
	}

	public String getDinosaurIDSource() {
		
		if (getCellSourceSelected() != null) {
			return getCellDinosaurID(getCellSourceSelected());
		}
		else {
			return null;
		}
		
	}
	
	public void updateCellDestinationSelected(boolean mouseSelectionButtonIsPressed) {
	
		if (mouseSelectionButtonIsPressed) {
			
			if (getRenderer().getSelectedCell().equals(getCellSourceSelected()) == false) {
				setCellDestinationSelected(getRenderer().getSelectedCell());
			} else {
				setCellDestinationSelected(null);
			}
		} else {
			setCellDestinationSelected(null);
		}
	}
	
	
	public void updateCellSourceSelected(boolean mouseSelectionButtonIsPressed) {

		if (mouseSelectionButtonIsPressed) {
			
			if (getDinosaurIDSource() == null) {
				
				
				String dinoID = getCellDinosaurID(getRenderer().getSelectedCell());
				
				if (DinosaurManager.getInstance().getDinosaur(dinoID) != null) {
					setCellSourceSelected(getRenderer().getSelectedCell());
				}
				
			} else {
				
				if (getCellSourceSelected().equals(getRenderer().getSelectedCell())) {
					setCellSourceSelected(null);
				} else {

					String dinoID = getCellDinosaurID(getRenderer().getSelectedCell());
					
					if (DinosaurManager.getInstance().getDinosaur(dinoID) != null) {
						setCellSourceSelected(getRenderer().getSelectedCell());
					}
				}
			}
		}
	}		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
	/** 
	 * The method updateMainMap bla bla bla...
	 * Bla bla bla... as the indicated {@link NameClass1} bla bla bla...
	 * It is useful also insert some {@code arg1} references to the args
	 * while explaining.
	 *
	 * @param mainMap          
	 *
	 * @see		NameClass1 (remove the line if there is no need of it)
	 * @see		NameClass2 (remove the line if there is no need of it)
	 *  
	 * @since	Jun 9, 2011@10:19:01 PM
	 * 
	 */
	public void updateMainMap(MainMap mainMap) {
		
		if (mainMap == null) {
			throw new IllegalArgumentException("The map can't be null!");
		}
		
		int width = (int) mainMap.getDimension().getX();
		int height = (int) mainMap.getDimension().getY();
		

		if (tiles == null) {
			tiles = new Image[width][height];
		}
		
		if (foodEnergy == null)
			foodEnergy = new int[width][height]; 

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				foodEnergy[x][y] = NO_VALUE;
				setTile(x, y,  ResourceManager.getInstance().getTileImage(new Character(mainMap.getMap()[x][y])));
			}
		}
	}
	
	public void updateCellDinosaurID(String theDinosaurID, Position theCell) {
		dinosaurID[theCell.getIntX()][theCell.getIntY()] = theDinosaurID; 
	}
	
	public void resetCarrionTiles() {
		
		int dimX = (int) getWidth();
		int dimY = (int) getHeight();

		
		for (int y = 0; y < dimY; y++) {
			for (int x = 0; x < dimX; x++) {
				
				if (getTile(x,y).equals(ResourceManager.getInstance().getTileImage(new Character('c')))) {
					setTile(x, y, ResourceManager.getInstance().getTileImage(new Character('t')));
				}
				
			}
		}
		
	}
	
	public void updateLocalViews(ArrayList<LocalView> theLocalViewsList) {
		
		DinosaurManager.getInstance().getDinosaurIdToDinosaurMap().clear();
	
		for (LocalView localView : theLocalViewsList) {
			
			int dimX = (int) localView.getDimension().getX();
			int dimY = (int) localView.getDimension().getY();

			String[][] loadedAttributes = localView.getAttributes();
			Character[][] loadedLocalView = localView.getLocalView();
			
			int originX = (int) localView.getOrigin().getX(); 
			int originY = (int) localView.getOrigin().getY(); 
			
			int posY;
			int posX;
			
			for (int y = 0; y < dimY; y++) {
				for (int x = 0; x < dimX; x++) {
					
					posY = originY + y;
					posX = originX + x;
					
					if (loadedLocalView[x][y].equals('c') || loadedLocalView[x][y].equals('v')) {
						foodEnergy[posX][posY] = Integer.parseInt(loadedAttributes[x][y]);
						setTile(posX, posY,  ResourceManager.getInstance().getTileImage(loadedLocalView[x][y]));
					} else if (loadedLocalView[x][y].equals('t') || loadedLocalView[x][y].equals('a') ) {
						foodEnergy[x][y] = NO_VALUE;
						setTile(posX, posY,  ResourceManager.getInstance().getTileImage(loadedLocalView[x][y]));
					} else if (loadedLocalView[x][y].equals('d')) {
						foodEnergy[x][y] = NO_VALUE;

						if (getTile(posX,posY).equals(ResourceManager.getInstance().getTileImage(new Character('b')))) {
							setTile(posX, posY, ResourceManager.getInstance().getTileImage(new Character('t')));
						}
						updateCellDinosaurID(loadedAttributes[x][y], new Position(posX,posY));
						DinosaurManager.getInstance().getDinosaurIdToDinosaurMap().put(loadedAttributes[x][y], new Dinosaur(loadedAttributes[x][y]));
					}
				}
			}
		}
	}
}
